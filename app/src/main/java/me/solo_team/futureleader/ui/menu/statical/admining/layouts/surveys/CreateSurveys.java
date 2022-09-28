package me.solo_team.futureleader.ui.menu.statical.admining.layouts.surveys;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.ChatMember;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.dialogs.ChatInfodialog;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.SelectMembers;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class CreateSurveys extends Her {
    JSONArray objects = new JSONArray();
    LinearLayout list;
    EditText name;
    Button addNew;
    Button forCurrecnUsers;
    LinearLayout usersList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Создание опроса");



        setContentView(R.layout.admin_create_surveys);
        list = findViewById(R.id.admin_create_surveys_list);
        name = findViewById(R.id.admin_create_surveys_name);
        addNew = findViewById(R.id.admin_create_surveys_btn);
        forCurrecnUsers = findViewById(R.id.create_surveys_btn_add_for);
        usersList = findViewById(R.id.create_surveys_list);
        addNew.setOnClickListener(v -> {
            startActivityForResult(new Intent(CreateSurveys.this, CreateSurveysObject.class), 100);
        });


        forCurrecnUsers.setOnClickListener(v -> {
            Intent intent = new Intent(this, SurveusForUsers.class);
            startActivityIfNeeded(intent, 101);
        });
    }

    List<ChatMember> selectedusers = new ArrayList<>();


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "")
                .setIcon(R.drawable.done)
                .setOnMenuItemClickListener(item -> {
                    if(objects.length()==0){
                        Utils.ShowSnackBar.show(CreateSurveys.this,"Пустой опрос???",list);
                        return false;
                    }
                    if(name.getText().toString().length()==0){
                        Utils.ShowSnackBar.show(CreateSurveys.this,"Название не должно быть пустным",list);
                        return false;
                    }
                    JSONObject o = new JSONObject();
                    try{
                        o.put("token",Constants.user.token);
                        o.put("mobile_token",Constants.user.mobileToken);
                        o.put("objects",objects.toString());
                        o.put("name",name.getText().toString());
                        if(selectedusers.size()>0)
                            o.put("user_ids",getUserIds());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    API.createSurveys(new ApiListener() {
                        Dialog d;
                        @Override
                        public void onError(JSONObject json) throws JSONException {
                            createNotification(list,json.getString("message"));
                            d.dismiss();
                        }

                        @Override
                        public void inProcess() {
                            d = openWaiter(CreateSurveys.this);
                        }

                        @Override
                        public void onSuccess(JSONObject json) throws JSONException {
                            d.dismiss();
                            runOnUiThread(()->{
                                setResult(1);
                                finish();
                            });
                        }
                    },o);
                    return true;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);
    }

    private void render() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        list.removeAllViews();
        for (int i = 0; i < objects.length(); i++) {
            try {
                LinearLayout linearLayout = new LinearLayout(CreateSurveys.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                TextView view = new TextView(CreateSurveys.this);

                JSONObject o = objects.getJSONObject(i);

                view.setTextColor(Color.BLACK);
                view.setLayoutParams(lp);
                view.setText((i + 1) + ") " + o.getString("name"));
                linearLayout.addView(view);
                if (o.getString("type").equals("one_of") && o.has("extra")) {
                    TextView textView = new TextView(CreateSurveys.this);
                    String[] s = o.getString("extra").split("&");
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String s_ : s) {
                        stringBuilder.append("• ").append(s_).append("\n");
                    }
                    textView.setText(stringBuilder.toString());
                    textView.setLayoutParams(lp);
                    textView.setTextColor(Color.BLACK);
                    linearLayout.addView(textView);
                }
                if (o.has("image")) {
                    ImageView imageView = new ImageView(CreateSurveys.this);
                    Constants.cache.addPhoto(o.getString("image"), imageView, this);
                    imageView.setLayoutParams(lp);
                    linearLayout.addView(imageView);
                }
                int finalI = i;
                linearLayout.setOnLongClickListener(v -> {
                    AlertDialog.Builder obj = new AlertDialog.Builder(CreateSurveys.this);
                    obj.setTitle("Удалить вопрос?");
                    obj.setIcon(R.drawable.resize_300x0);
                    obj.setPositiveButton("да", (dialog, which) -> {
                        objects.remove(finalI);
                        render();
                    });
                    obj.setNegativeButton("нет", null);
                    obj.show();
                    return true;
                });
                list.addView(linearLayout);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101 && resultCode==1){
            String[] users = data.getStringArrayExtra("users");
            selectedusers.clear();
            for (String s : users) {
                try {
                    ChatMember chatMember = new ChatMember(new JSONObject(s));
                    selectedusers.add(chatMember);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            renderMembers();
        }
        if (resultCode == 1) {
            if (requestCode == 100) {
                String q = data.getStringExtra("q");
                String extra = null;
                String imageurl = null;
                if (data.hasExtra("extra"))
                    extra = data.getStringExtra("extra");
                if (data.hasExtra("image"))
                    imageurl = data.getStringExtra("image");
                JSONObject o = new JSONObject();
                try {
                    o.put("type", data.getStringExtra("type"));
                    o.put("name", q);
                    if (extra != null)
                        o.put("extra", extra);
                    if (imageurl != null)
                        o.put("image", imageurl);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                objects.put(o);
                render();
            }
        }
    }


    private void renderMembers(){
        for(ChatMember chatMember : selectedusers){
            View view = getLayoutInflater().inflate(R.layout.member,null);
            Constants.cache.addPhoto(chatMember.profilePicture,((ImageView)view.findViewById(R.id.member_photo)),this);
            ((TextView)view.findViewById(R.id.member_name)).setText(chatMember.firstName+" "+chatMember.lastName);
            ImageButton imageButton = view.findViewById(R.id.member_btn);
            Drawable wrappedDrawable = DrawableCompat.wrap(getDrawable(R.drawable.trash));
            DrawableCompat.setTint(wrappedDrawable, Color.BLACK);
            imageButton.setImageDrawable(wrappedDrawable);
            imageButton.setOnClickListener(v -> {
                selectedusers.remove(chatMember);
                usersList.removeView(view);
            });
            usersList.addView(view);
        }
    }

    private String getUserIds() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < selectedusers.size(); i++) {
            if (i != selectedusers.size() - 1)
                text.append(selectedusers.get(i).userId).append(",");
            else
                text.append(selectedusers.get(i).userId);
        }

        return text.toString();
    }

}
