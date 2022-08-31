package me.solo_team.futureleader.ui.news.open_news;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Audio;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.User;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.WebViewsContent.WebView;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.news.AlertDialogForDeleteField;
import me.solo_team.futureleader.ui.profile.view_prof.ViewProfile;

public class EditNews extends Her {

    JSONObject new_;
    LinearLayout list;
    ImageView logo;
    View root;
    HashMap<View, Integer> views = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_news_layout);
        root = findViewById(R.id.edit_news_layout);
        setTitle("Создание новости");

        TextView addItem = findViewById(R.id.edit_new_add_item_btn);
        list = findViewById(R.id.edit_new_item_list);
        EditText tag = findViewById(R.id.edit_new_tag);
        EditText name = findViewById(R.id.edit_new_title);
        TextView saveBtn = findViewById(R.id.edit_new_save_btn);
        logo = findViewById(R.id.edit_new_logo);
        try {
            new_ = Constants.newsCache.curentNew;

            tag.setText(new_.getString("title"));
            name.setText(new_.getString("name"));
            drawObject(new_.getJSONArray("objects"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        addItem.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateChieldNew.class);
            startActivity(intent);
        });

        saveBtn.setOnClickListener(v -> {
            if (tag.getText().length() == 0) {
                error("Тэг не может быть пустым", v);
                return;
            }
            if (name.getText().length() == 0) {
                error("Имя не может быть пустым", v);
                return;
            }
            if (list.getChildCount() == 0) {
                error("Объектов не может быть 0", v);
                return;
            }
            if(!Constants.newsCache.curentNew.has("photo")){
                error("Отсутствует обложка", v);
                return;
            }
            try {
                Constants.newsCache.curentNew.put("title", tag.getText());
                Constants.newsCache.curentNew.put("name", name.getText());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                Constants.newsCache.curentNew.put("token", Constants.user.token);
                API.addNew(new ApiListener() {
                    Dialog d;

                    @Override
                    public void onError(JSONObject json) {
                        System.out.println(json);
                        try {
                            createNotification(EditNews.this.root,json.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        d.dismiss();
                    }

                    @Override
                    public void inProcess() {
                        d = openWaiter(EditNews.this);
                    }

                    @Override
                    public void onSuccess(JSONObject json) throws JSONException {
                        d.dismiss();
                        System.out.println(json);
                        Constants.newsCache.news.put(Constants.newsCache.curentNew);
                        Constants.newsCache.curentNew = null;
                        setResult(1);
                        finish();
                    }
                }, Constants.newsCache.curentNew);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
        logo.setOnClickListener(v -> {
            if (!checkPerm(getApplicationContext())) return;
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            //Тип получаемых объектов - image:
            photoPickerIntent.setType("image/*");
            //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
            startActivityForResult(Intent.createChooser(photoPickerIntent, "Выберите изображение"), 1);
        });
    }


    @SuppressLint("NewApi")
    private boolean checkPerm(Context context) {
        int permissionStatus = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
            return false;
        }
    }

    private void error(String mess, View v) {
        try {
            Snackbar.make(v, mess, Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", view -> {
                    })
                    .setActionTextColor(Color.RED)
                    .show();
        } catch (Exception ignored) {
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        try {
            drawObject(Constants.newsCache.curentNew.getJSONArray("objects"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void drawObject(JSONArray objects) throws JSONException {
        views.clear();
        list.removeAllViews();
        System.out.println(objects);
        for (int i = 0; i < objects.length(); i++) {
            JSONObject o = new JSONObject(objects.getString(i));
            switch (o.getString("type")) {
                case "text":
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0, 5, 0, 5);
                    TextView textView = new TextView(getApplicationContext(), null);
                    textView.setTextColor(Color.BLACK);
                    textView.setLayoutParams(lp);
                    textView.setText(o.getString("value"));
                    int finalI = i;
                    textView.setOnLongClickListener(v -> {
                        AlertDialogForDeleteField al = new AlertDialogForDeleteField((result, view) -> {
                            if (!result) return;
                            Constants.newsCache.curentNew.getJSONArray("objects").remove(view);
                            drawObject(Constants.newsCache.curentNew.getJSONArray("objects"));
                        }, finalI);
                        al.show(getSupportFragmentManager(), null);
                        return true;
                    });
                    runOnUiThread(() -> list.addView(textView));
                    break;
                case "photo":
                    LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(200, 200);
                    lp2.setMargins(0, 5, 0, 5);
                    ImageView imageView = new ImageView(getApplicationContext(), null);
                    imageView.setLayoutParams(lp2);
                    Constants.cache.addPhoto(o.getString("value"), imageView, EditNews.this);
                    int finalI1 = i;
                    imageView.setOnLongClickListener(v -> {
                        AlertDialogForDeleteField al = new AlertDialogForDeleteField((result, view) -> {
                            if (!result) return;
                            Constants.newsCache.curentNew.getJSONArray("objects").remove(view);
                            drawObject(Constants.newsCache.curentNew.getJSONArray("objects"));
                        }, finalI1);
                        al.show(getSupportFragmentManager(), null);
                        return true;
                    });
                    runOnUiThread(() -> list.addView(imageView));
                    break;
                case "audio":
                    LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(200, 200);
                    lp3.setMargins(0, 5, 0, 5);
                    Audio meow = new Audio (new JSONObject(o.getString("value")),EditNews.this);
                    View v = getLayoutInflater().inflate(R.layout.obj_music,null);
                    Constants.cache.addPhoto(meow.urlPhoto, v.findViewById(R.id.obj_music_image), this);
                    ((TextView) v.findViewById(R.id.obj_music_name)).setText(meow.name);
                    ((TextView) v.findViewById(R.id.obj_music_author)).setText(meow.author);
                    v.findViewById(R.id.obj_music_fav).setVisibility(View.GONE);
                    int finalI2 = i;
                    v.setOnLongClickListener(v1 -> {
                        AlertDialogForDeleteField al = new AlertDialogForDeleteField((result, view) -> {
                            if (!result) return;
                            Constants.newsCache.curentNew.getJSONArray("objects").remove(view);
                            drawObject(Constants.newsCache.curentNew.getJSONArray("objects"));
                        }, finalI2);
                        al.show(getSupportFragmentManager(), null);
                        return true;
                    });
                    runOnUiThread(() -> list.addView(v));
                    break;
                case "video":
                    LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(200, 200);
                    lp4.setMargins(0, 5, 0, 5);
                    View view = getLayoutInflater().inflate(R.layout.obj_video,null);
                    ((TextView)view.findViewById(R.id.obj_video_name)).setText(o.getString("extras"));
                    int finalI3 = i;
                    view.setOnLongClickListener(v1 -> {
                        AlertDialogForDeleteField al = new AlertDialogForDeleteField((result, view2) -> {
                            if (!result) return;
                            Constants.newsCache.curentNew.getJSONArray("objects").remove(view2);
                            drawObject(Constants.newsCache.curentNew.getJSONArray("objects"));
                        }, finalI3);
                        al.show(getSupportFragmentManager(), null);
                        return true;
                    });
                    runOnUiThread(() -> list.addView(view));
                case "text_link":
                    String url = o.getString("value");
                    SpannableString ss = new SpannableString(url);
                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View textView) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            startActivity(intent);
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setUnderlineText(false);
                        }
                    };
                    ss.setSpan(clickableSpan, 0, url.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    TextView textView1 = new TextView(getApplicationContext());
                    textView1.setText(ss);
                    textView1.setMovementMethod(LinkMovementMethod.getInstance());
                    textView1.setHighlightColor(Color.TRANSPARENT);
                    LinearLayout.LayoutParams lp5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp5.setMargins(0, 5, 0, 5);
                    textView1.setLayoutParams(lp5);
                    int finalI4 = i;
                    textView1.setOnLongClickListener(v1 -> {
                        AlertDialogForDeleteField al = new AlertDialogForDeleteField((result, view2) -> {
                            if (!result) return;
                            Constants.newsCache.curentNew.getJSONArray("objects").remove(view2);
                            drawObject(Constants.newsCache.curentNew.getJSONArray("objects"));
                        }, finalI4);
                        al.show(getSupportFragmentManager(), null);
                        return true;
                    });
                    runOnUiThread(() -> list.addView(textView1));
                    break;
                case "user":
                    View view1 = getLayoutInflater().inflate(R.layout.obj_chat_member,null);
                    ((TextView) view1.findViewById(R.id.chat_member_name)).setText(o.getString("value"));
                    Constants.cache.addPhoto(o.getString("extras"),view1.findViewById(R.id.chat_member_image),this);
                    if(Constants.user.permission.can_get_user)
                        view1.setOnClickListener(v1 -> {
                            try {
                                API.getUser(new ApiListener() {
                                    Dialog d;
                                    @Override
                                    public void onError(JSONObject json) throws JSONException {
                                        d.dismiss();
                                        createNotification(v1,json.getString("message"));
                                    }

                                    @Override
                                    public void inProcess() {
                                        d = openWaiter(EditNews.this);
                                    }

                                    @Override
                                    public void onSuccess(JSONObject json) throws JSONException {
                                        User user = new User(json.getJSONObject("user"));
                                        d.dismiss();
                                        runOnUiThread(()->{
                                            Constants.currentUser = user;
                                            Intent intent = new Intent(EditNews.this, ViewProfile.class);
                                            intent.putExtra("removeSelf",false);
                                            startActivity(intent);
                                        });
                                    }
                                },new CustomString("token",Constants.user.token),new CustomString("id",o.getString("id")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    int finalI5 = i;
                    view1.setOnLongClickListener(v1 -> {
                        AlertDialogForDeleteField al = new AlertDialogForDeleteField((result, view2) -> {
                            if (!result) return;
                            Constants.newsCache.curentNew.getJSONArray("objects").remove(view2);
                            drawObject(Constants.newsCache.curentNew.getJSONArray("objects"));
                        }, finalI5);
                        al.show(getSupportFragmentManager(), null);
                        return true;
                    });
                    runOnUiThread(() -> list.addView(view1));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                API.uploadImageNotResized(new ApiListener() {
                    @Override
                    public void onError(JSONObject json) {
                        System.out.println(json);
                    }

                    @Override
                    public void inProcess() {

                    }

                    @Override
                    public void onSuccess(JSONObject json) throws JSONException {
                        Constants.newsCache.curentNew.put("photo", json.getString("url"));
                        Constants.cache.addPhoto(json.getString("url"), logo, EditNews.this);
                    }
                }, bitmap, new CustomString("token", Constants.user.token));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
