package me.solo_team.futureleader.ui.menu.statical.admining.layouts.gameifecation;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.news.open_news.EditNews;
import me.solo_team.futureleader.ui.profile.ProfileFragment;

public class CereateAchievement extends Her {

    EditText name;
    EditText des;
    EditText limit;
    EditText fbl;
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!Constants.user.permission.can_create_achievement){
            setResult(-500);
            finish();
        }
        setTitle("Создане достижения");
        setContentView(R.layout.admin_create_achievement);
        name = findViewById(R.id.admin_create_achievement_name);
        des = findViewById(R.id.admin_create_achievement_description);
        limit = findViewById(R.id.admin_create_achievement_limit);
        imageView = findViewById(R.id.admin_create_achievement_image);
        fbl = findViewById(R.id.admin_create_achievement_coins);
        imageView.setOnClickListener(v -> {
            if (!checkPerm(CereateAchievement.this, Manifest.permission.READ_EXTERNAL_STORAGE)) return;
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            //Тип получаемых объектов - image:
            photoPickerIntent.setType("image/*");
            //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
            startActivityForResult(Intent.createChooser(photoPickerIntent, "Выберите изображение"), 1);
        });
    }
    String imageUrl;

    public void end(){
        if(name.getText().length()==0){
            Utils.ShowSnackBar.show(CereateAchievement.this,"имя не может быть пустым!",name);
            return;
        }
        if(des.getText().length()==0){
            Utils.ShowSnackBar.show(CereateAchievement.this,"описание не может быть пустым!",name);
            return;
        }
        if(fbl.getText().length()==0)
        {
            Utils.ShowSnackBar.show(CereateAchievement.this,"Награда за достижение не назначена!",name);
            return;
        }
        try{
            int cur = Integer.parseInt(fbl.getText().toString());
        }catch (Exception e){
            Utils.ShowSnackBar.show(CereateAchievement.this,"награда должна быть целым числом!",limit);
            return;
        }
        if(imageUrl==null){
            Utils.ShowSnackBar.show(CereateAchievement.this,"пустое изображение!",name);
            return;
        }
        JSONObject o = new JSONObject();
        try{
            o.put("name",name.getText().toString());
            o.put("description",des.getText().toString());
            o.put("coins",Integer.parseInt(fbl.getText().toString()));
            o.put("url",imageUrl);
            o.put("token", Constants.user.token);
            o.put("mobile_token",Constants.user.mobileToken);
            if(limit.getText().length()!=0 && Integer.parseInt(limit.getText().toString())>=0)
                o.put("limited",Integer.parseInt(limit.getText().toString()));
            else
                o.put("limited",-1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        API.createAchievement(new ApiListener() {
            Dialog d;
            @Override
            public void onError(JSONObject json) throws JSONException {
                d.dismiss();
                createNotification(imageView,json.getString("message"));
            }

            @Override
            public void inProcess() {
                d = openWaiter(CereateAchievement.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                d.dismiss();
                setResult(1);
                finish();

            }
        },
                o
                );
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "")
                .setIcon(R.drawable.done)
                .setOnMenuItemClickListener(item -> {
                    end();
                    return true;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean checkPerm(Context context, String permission) {
        int permissionStatus = ContextCompat.checkSelfPermission(context, permission);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
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
                    Dialog d;
                    @Override
                    public void onError(JSONObject json) {
                        System.out.println(json);
                        d.dismiss();
                    }

                    @Override
                    public void inProcess() {
                        d = openWaiter(CereateAchievement.this);
                    }

                    @Override
                    public void onSuccess(JSONObject json) throws JSONException {
                        imageUrl =  json.getString("url");
                        Constants.cache.addPhoto(imageUrl,imageView,CereateAchievement.this);
                        d.dismiss();
                    }
                }, bitmap, new CustomString("token", Constants.user.token));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
