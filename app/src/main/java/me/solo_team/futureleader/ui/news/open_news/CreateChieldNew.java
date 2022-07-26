package me.solo_team.futureleader.ui.news.open_news;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class CreateChieldNew extends Her {
    LinearLayout list;
    String url;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_new_list_object);


        setTitle("Добавить объект");
        list = findViewById(R.id.edit_new_list_obj_list);
        Button btn = findViewById(R.id.edit_new_list_obj_btn);
        Spinner spinner = findViewById(R.id.edit_new_list_obj_selector);

        MyCustomAdapter adapter = new MyCustomAdapter(this,
                R.layout.spinner_text, new String[]{"Текст", "Изображение"});
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        list.removeAllViews();
                        EditText editText = new EditText(getApplicationContext());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(0, 5, 0, 5);
                        editText.setLayoutParams(lp);
                        list.addView(editText);
                        break;
                    case 1:
                        list.removeAllViews();
                        ImageView imageView = new ImageView(getApplicationContext());
                        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(200, 200);
                        lp2.gravity = Gravity.CENTER_HORIZONTAL;
                        lp2.setMargins(0, 5, 0, 5);
                        imageView.setLayoutParams(lp2);
                        imageView.setImageResource(R.drawable.resize_300x0);
                        imageView.setBackground(getDrawable(R.drawable.gray_gradient_with_corners));
                        list.addView(imageView);
                        CreateChieldNew.this.imageView = imageView;
                        imageView.setOnClickListener(v -> {
                            if (!checkPerm(getApplicationContext())) return;
                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                            //Тип получаемых объектов - image:
                            photoPickerIntent.setType("image/*");
                            //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
                            startActivityForResult(Intent.createChooser(photoPickerIntent, "Выбирите изображение"), 1);
                        });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn.setOnClickListener(v_ -> {
            View v = list.getChildAt(0);
            if (v == null) return;
            JSONObject o = new JSONObject();
            if (v instanceof TextView) {
                if (((TextView) v).getText() == null) return;
                if (((TextView) v).getText().length() == 0) return;
                try {
                    o.put("type", "text");
                    o.put("value", ((TextView) v).getText().toString());
                    Constants.newsCache.curentNew.getJSONArray("objects").put(o);
                    Constants.newsCache.updObjects();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (v instanceof ImageView) {
                try {
                    if (url == null) return;
                    o.put("type", "photo");
                    o.put("value", url);
                    o.put("extras","full_screen");
                    Constants.newsCache.curentNew.getJSONArray("objects").put(o);
                    Constants.newsCache.updObjects();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            finish();
        });
    }


    public class MyCustomAdapter extends ArrayAdapter<String> {
        String[] objects;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               String[] objects) {
            super(context, textViewResourceId, objects);
            this.objects = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {

            return getCustomView(position, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return getCustomView(position, parent);
        }

        public View getCustomView(int position,
                                  ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.spinner_text, parent, false);
            TextView label = row.findViewById(R.id.spinner_text);
            label.setText(objects[position]);
            return row;
        }

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

                    }

                    @Override
                    public void inProcess() {

                    }

                    @Override
                    public void onSuccess(JSONObject json) throws JSONException {
                        url = json.getString("url");
                        Constants.cache.addPhoto(json.getString("url"),true,imageView,CreateChieldNew.this);
                    }
                }, bitmap, new CustomString("token", Constants.user.token));
                //picture.setImageBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
