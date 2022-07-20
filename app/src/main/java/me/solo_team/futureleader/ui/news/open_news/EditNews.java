package me.solo_team.futureleader.ui.news.open_news;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.news.AlertDialogForDeleteField;

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
            try {
                Constants.newsCache.curentNew.put("title", tag.getText());
                Constants.newsCache.curentNew.put("name", name.getText());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                Constants.newsCache.curentNew.put("token", Constants.user.token);
                if(Constants.newsCache.curentNew.getString("type").equals("photo")){
                    Constants.newsCache.curentNew.put("extras","full_screen");
                }
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
                    }

                    @Override
                    public void inProcess() {
                        d = openWaiter(EditNews.this);
                    }

                    @Override
                    public void onSuccess(JSONObject json) throws JSONException {
                        d.dismiss();
                        System.out.println(json);
                        Constants.newsCache.curentNew = null;
                        finish();
                    }
                }, Constants.newsCache.curentNew);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
        logo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!checkPerm(getApplicationContext())) return false;
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                //Тип получаемых объектов - image:
                photoPickerIntent.setType("image/*");
                //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
                startActivityForResult(Intent.createChooser(photoPickerIntent, "Выбирите изображение"), 1);
                return true;
            }
        });
    }


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
                    Constants.cache.addPhoto(o.getString("value"), true, imageView, EditNews.this);
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
                API.uploadImage(new ApiListener() {
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
                        Constants.cache.addPhoto(json.getString("url"), true, logo, EditNews.this);
                    }
                }, bitmap, new CustomString("token", Constants.user.token));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
