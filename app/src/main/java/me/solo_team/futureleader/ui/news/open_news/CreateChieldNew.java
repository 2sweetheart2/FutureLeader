package me.solo_team.futureleader.ui.news.open_news;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Audio;
import me.solo_team.futureleader.Objects.ChatMember;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.AddVideo;
import me.solo_team.futureleader.ui.SelectMembers;
import me.solo_team.futureleader.ui.menu.statical.Media.AddMusic;
import me.solo_team.futureleader.ui.menu.statical.Media.SearchMusic;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class CreateChieldNew extends Her {
    LinearLayout list;
    String url;
    ImageView imageView;
    String textUrl;
    int type = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_new_list_object);


        setTitle("Добавить объект");
        list = findViewById(R.id.edit_new_list_obj_list);
        Button btn = findViewById(R.id.edit_new_list_obj_btn);
        Spinner spinner = findViewById(R.id.edit_new_list_obj_selector);

        MyCustomAdapter adapter = new MyCustomAdapter(this,
                R.layout.spinner_text, new String[]{"Текст","текст-ссылка", "Изображение","аудио","видео","юзер"});
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = position;
                switch (position) {
                    case 0:
                        list.removeAllViews();
                        EditText editText = new EditText(getApplicationContext());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(0, 5, 0, 5);
                        editText.setLayoutParams(lp);
                        list.addView(editText);
                        break;
                    case 2:
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
                            startActivityForResult(Intent.createChooser(photoPickerIntent, "Выберите изображение"), 1);
                        });
                        break;
                    case 3:
                        list.removeAllViews();
                        AlertDialog.Builder obj = new AlertDialog.Builder(CreateChieldNew.this);
                        obj.setTitle("аудио");
                        obj.setMessage("выбрать аудио из...");
                        obj.setIcon(R.drawable.resize_300x0);
                        obj.setPositiveButton("существующих", (dialog, which) -> getAudio(true));
                        obj.setNegativeButton("добавить новый аудио файл", (dialog, which) -> getAudio(false));
                        obj.show();
                        break;
                    case 4:
                        list.removeAllViews();
                        list.removeAllViews();
                        AlertDialog.Builder obj2 = new AlertDialog.Builder(CreateChieldNew.this);
                        obj2.setTitle("видео");
                        obj2.setMessage("добавить видео из библиотеки?");
                        obj2.setIcon(R.drawable.resize_300x0);
                        obj2.setPositiveButton("да", (dialog, which) -> getVideo());
                        obj2.show();
                        break;
                    case 1:
                        list.removeAllViews();
                        EditText editText1 = new EditText(getApplicationContext());
                        LinearLayout.LayoutParams lp5 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp5.setMargins(0, 5, 0, 5);
                        editText1.setLayoutParams(lp5);
                        editText1.setHint("ссылка...");
                        list.addView(editText1);
                        break;
                    case 5:
                        list.removeAllViews();
                        Intent intent = new Intent(CreateChieldNew.this, SelectMembers.class);
                        intent.putExtra("needStuff", false);
                        intent.putExtra("checker", false);
                        intent.putExtra("removeSelf", false);
                        intent.putExtra("selectOne", true);
                        intent.putExtra("title", "Выбор пользователя");
                        intent.putExtra("needUserObject", true);
                        startActivityIfNeeded(intent, 102);
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
            if (type == 4) {
                try {
                    o.put("type", "video");

                    o.put("value", videoUrl);
                    o.put("extras", videoName);
                    Constants.newsCache.curentNew.getJSONArray("objects").put(o);
                    Constants.newsCache.updObjects();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish();
                return;
            }
            if (v instanceof EditText) {
                if (((EditText) v).getText() == null) return;
                if (((EditText) v).getText().length() == 0) return;
                try {

                    if (type == 1) {
                        if (((EditText) v).getText().toString().split("//").length == 1) {
                            Utils.ShowSnackBar.show(this, "Ссылка имеет не верный формат!", v);
                            return;
                        }
                        String pref = ((EditText) v).getText().toString().split("//")[0];
                        if (!pref.equals("https:") && !pref.equals("http:")) {
                            Utils.ShowSnackBar.show(this, "Ссылка имеет не верный формат!", v);
                            return;
                        }
                        o.put("type", "text_link");
                        o.put("value", ((EditText) v).getText().toString());
                    } else {
                        if (type == 0) {
                            o.put("type", "text");
                            o.put("value", ((TextView) v).getText().toString());
                        }
                    }
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
            else if (v instanceof ConstraintLayout){
                try {
                    if (selectedUser != null) {
                        o.put("type", "user");
                        o.put("value", selectedUser.getFullName());
                        o.put("id", String.valueOf(selectedUser.userId));
                        o.put("extras", selectedUser.profilePicture);
                    } else if (audio != null) {
                        o.put("type", "audio");
                        o.put("value", audio.toString());
                    } else
                        return;
                    Constants.newsCache.curentNew.getJSONArray("objects").put(o);
                    Constants.newsCache.updObjects();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            finish();
        });
    }

    private void getVideo(){
        Intent intent = new Intent(CreateChieldNew.this, AddVideo.class);
        startActivityIfNeeded(intent,101);
    }

    private void getAudio(boolean state){
        Intent intent;
        if(state){
            intent = new Intent(CreateChieldNew.this, SearchMusic.class);
            intent.putExtra("needOne",true);
            intent.putExtra("needFav",false);
        }
        else {
            intent = new Intent(CreateChieldNew.this, AddMusic.class);
            intent.putExtra("rrr",true);
        }
        startActivityIfNeeded(intent,100);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode+" "+resultCode);
        if (requestCode == 1 && data != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                API.uploadImageNotResized(new ApiListener() {
                    @Override
                    public void onError(JSONObject json) {

                    }

                    @Override
                    public void inProcess() {

                    }

                    @Override
                    public void onSuccess(JSONObject json) throws JSONException {
                        url = json.getString("url");
                        Constants.cache.addPhoto(json.getString("url"),imageView,CreateChieldNew.this);
                    }
                }, bitmap, new CustomString("token", Constants.user.token));
                //picture.setImageBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(requestCode==100 && resultCode==1){
            View v = getLayoutInflater().inflate(R.layout.obj_music,null);
            try {
                audio = new Audio(new JSONObject(data.getStringExtra("audio")), CreateChieldNew.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Constants.cache.addPhoto(audio.urlPhoto, v.findViewById(R.id.obj_music_image), this);
            ((TextView) v.findViewById(R.id.obj_music_name)).setText(audio.name);
            ((TextView) v.findViewById(R.id.obj_music_author)).setText(audio.author);
            v.findViewById(R.id.obj_music_fav).setVisibility(View.GONE);
            list.addView(v);
        }
        if (requestCode == 101 && resultCode == 1) {
            TextView textView = new TextView(CreateChieldNew.this);
            textView.setText("Имя видео файла: " + data.getStringExtra("name"));
            textView.setTextColor(Color.BLACK);
            list.addView(textView);
            videoName = data.getStringExtra("name");
            videoUrl = data.getStringExtra("url");
        }
        if (requestCode == 102 && resultCode == 1) {
            try {
                selectedUser = new ChatMember(new JSONObject(data.getStringExtra("user")));
                View view1 = getLayoutInflater().inflate(R.layout.obj_chat_member, null);
                ((TextView) view1.findViewById(R.id.chat_member_name)).setText(selectedUser.getFullName());
                Constants.cache.addPhoto(selectedUser.profilePicture, view1.findViewById(R.id.chat_member_image), this);
                list.addView(view1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    ChatMember selectedUser;
    String videoName;
    String videoUrl = null;
    Audio audio = null;
}
