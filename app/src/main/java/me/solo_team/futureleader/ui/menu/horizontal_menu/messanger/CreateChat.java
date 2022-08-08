package me.solo_team.futureleader.ui.menu.horizontal_menu.messanger;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.socket.client.Ack;
import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.API.websocket.WebScoketClient;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.ChatMember;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.dialogs.ChatInfodialog;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.SelectMembers;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class CreateChat extends Her {

    ListView listView;
    EditText title;
    FloatingActionButton btn;
    Button add;
    ChatInfodialog.MemberAdapter adapter;
    CheckBox privat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_chat);

        listView = findViewById(R.id.list);
        title = findViewById(R.id.create_chat_title);
        add = findViewById(R.id.create_chat_btn);
        btn = findViewById(R.id.complete);
        privat = findViewById(R.id.create_chat_private);
        setTitle("Создание чата");
        adapter = new ChatInfodialog.MemberAdapter(getApplicationContext(), this);

        Drawable wrappedDrawable = DrawableCompat.wrap(getDrawable(R.drawable.trash));
        DrawableCompat.setTint(wrappedDrawable, Color.BLACK);
        adapter.setCustomImageForUtilBtn(wrappedDrawable);

        adapter.addUtilBtn(member -> {
            selectedusers.remove(member);
            runOnUiThread(() -> adapter.remove(member));
        });
        Intent intent = new Intent(this, SelectMembers.class);
        intent.putExtra("needStuff", false);
        intent.putExtra("checker", true);
        intent.putExtra("removeSelf", true);
        add.setOnClickListener(v -> startActivityForResult(intent, 100));
        listView.setAdapter(adapter);

        privat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (selectedusers.size() > 1) {
                    privat.setChecked(false);
                    Utils.ShowSnackBar.show(getApplicationContext(), "В приватном чате могут находиться только два человека (вы и ещё 1)", buttonView);
                }
            }
        });

        btn.setOnClickListener(v -> {
            System.out.println(getUserIds());

            if (title.getText().length() == 0) {
                Utils.ShowSnackBar.show(getApplicationContext(), "название чата не должно быть пустым", v);
                return;
            }
            if (selectedusers.size() == 0) {
                Utils.ShowSnackBar.show(getApplicationContext(), "не выбрано ни одного пользователя", v);
                return;
            }
            JSONObject jsonInput = new JSONObject();
            try {
                jsonInput.put("title", title.getText().toString());
                jsonInput.put("user_ids", getUserIds());
                jsonInput.put("token", Constants.user.token);
                jsonInput.put("private", privat.isChecked());
                jsonInput.put("mobile_token", Constants.user.mobileToken);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            API.createChat(new ApiListener() {
                Dialog d;
                @Override
                public void onError(JSONObject json) throws JSONException {
                    createNotification(v,json.getString("message"));
                }

                @Override
                public void inProcess() {
                    d = openWaiter(CreateChat.this);
                }

                @Override
                public void onSuccess(JSONObject json) throws JSONException {
                    System.out.println(json);
                    d.dismiss();
                    finish();

                }
            },
                    jsonInput);

        });
    }



    private String getUserIds() {
        StringBuilder text = new StringBuilder();
        for (int i=0;i<selectedusers.size();i++) {
            if(i!=selectedusers.size()-1)
                text.append(selectedusers.get(i).userId).append(",");
            else
                text.append(selectedusers.get(i).userId);
        }

        return text.toString();
    }


    List<ChatMember> selectedusers = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode);
        System.out.println(resultCode);
        if (resultCode == 100) {
            if (requestCode == 100) {
                String[] users = data.getStringArrayExtra("users");
                selectedusers.clear();
                for (String s : users) {
                    try {
                        selectedusers.add(new ChatMember(new JSONObject(s)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                addIntoAdapter();
            }
        }
    }

    private void addIntoAdapter() {
        adapter.clear();
        if (selectedusers.size() > 1)
            privat.setChecked(false);
        adapter.addAll(selectedusers);
    }
}
