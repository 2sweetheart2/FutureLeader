package me.solo_team.futureleader.ui.menu.statical.Media;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Audio;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class AddMusic extends Her {

    EditText name;
    EditText author;
    TextView selectAudio;
    TextView info;
    ImageView photo;

    FloatingActionButton send;
    byte[] bytes;
    Uri currentAudioUri = null;
    String currentPhotoUrl = null;

    View root;    String audioUrl = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("добавление аудио");
        setContentView(R.layout.add_audio);

        name = findViewById(R.id.add_audio_name);
        author = findViewById(R.id.add_audio_author);
        selectAudio = findViewById(R.id.add_audio_select_btn);
        info = findViewById(R.id.add_audio_info);
        send = findViewById(R.id.add_audio_complete_btn);
        photo = findViewById(R.id.add_audio_photo);
        root = findViewById(R.id.add_audio);

        selectAudio.setOnClickListener(v -> selectMP3());
        photo.setOnClickListener(v -> selectImage());
        send.setOnClickListener(sendListener);
    }

    private final View.OnClickListener sendListener = v -> {
        if (name.getText().length() == 0) {
            Utils.ShowSnackBar.show(AddMusic.this, "название не должно быть пустым!", v);
            return;
        }
        if (author.getText().length() == 0) {
            Utils.ShowSnackBar.show(AddMusic.this, "автор не должен быть пустым!", v);
            return;
        }
        if (audioUrl == null && audioUrl.length()==0) {
            Utils.ShowSnackBar.show(AddMusic.this, "аудио файл не выбран!", v);
            return;
        }
        if (currentPhotoUrl == null) {
            Utils.ShowSnackBar.show(AddMusic.this, "фото забыли:)!", v);
            return;
        }

        API.addMusic(new ApiListener() {
                         Dialog d;

                         @Override
                         public void onError(JSONObject json) throws JSONException {
                             System.out.println(json);
                             d.dismiss();
                         }

                         @Override
                         public void inProcess() {
                             d = openWaiter(AddMusic.this);
                         }

                         @Override
                         public void onSuccess(JSONObject json) throws JSONException {
                             System.out.println(json);
                             if (getIntent().getBooleanExtra("rrr",false)){
                                 Intent intent = new Intent ();
                                 intent.putExtra("audio",(new Audio(json.getJSONObject("audio"),AddMusic.this)).toString());
                                 setResult(1,intent);
                             }
                             d.dismiss();
                             finish();
                         }
                     },
                new CustomString("audio",audioUrl),
                new CustomString("token", Constants.user.token),
                new CustomString("name", decodeString(name.getText().toString())),
                new CustomString("author", decodeString(author.getText().toString())),
                new CustomString("photo", currentPhotoUrl)

        );
    };

    public String decodeString(String input) {
        try {
            String transportString = URLEncoder.encode(input, "UTF-8");
            return URLDecoder.decode(transportString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void selectImage() {
        if (!checkPerm(AddMusic.this, Manifest.permission.READ_EXTERNAL_STORAGE)) return;
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        //Тип получаемых объектов - image:
        photoPickerIntent.setType("image/*");
        //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
        startActivityForResult(Intent.createChooser(photoPickerIntent, "Выбирите изображение"), 1002);
    }


    private final int REQ_CODE_PICK_SOUNDFILE = 1001;

    private void selectMP3() {
        if (!checkPerm(AddMusic.this, Manifest.permission.READ_EXTERNAL_STORAGE)) return;
        Intent intent_upload = new Intent();
        //intent_upload.setType("audio/*"); // For All Audio Files
        intent_upload.setType("audio/mpeg"); // For only MP3 Files
        intent_upload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityIfNeeded(intent_upload, REQ_CODE_PICK_SOUNDFILE);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_PICK_SOUNDFILE && resultCode == Activity.RESULT_OK) {
            if ((data != null) && (data.getData() != null)) {
                Uri audioFileUri = data.getData();
                System.out.println("PATH: "+audioFileUri.getPath());
                try {
                    InputStream  inputStream = getContentResolver().openInputStream(audioFileUri);
                    System.out.println("STEP 2");
                    bytes = Utils.read(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                    Utils.ShowSnackBar.show(AddMusic.this, "что-то с вашим файлом песни не так:/", root);
                    return;
                }
                if(!Utils.getFileFormat(audioFileUri,AddMusic.this).equals("mp3")){
                    Utils.ShowSnackBar.show(AddMusic.this,"формат файла должен быть MP3",selectAudio);
                    return;
                }
                currentAudioUri = audioFileUri;

                API.addAudioFile(new ApiListener() {
                    Dialog d;
                    @Override
                    public void onError(JSONObject json) throws JSONException {
                        d.dismiss();
                        createNotification(send,json.getString("message"));
                    }

                    @Override
                    public void inProcess() {
                        d = openWaiter(AddMusic.this);
                    }

                    @Override
                    public void onSuccess(JSONObject json) throws JSONException {
                        audioUrl = json.getString("url");
                        d.dismiss();
                        runOnUiThread(()->{
                            info.setText("имя файла: " + Utils.getFileName(audioFileUri, AddMusic.this));
                        });
                        Utils.ShowSnackBar.show(AddMusic.this,"файл получен!",send);
                    }
                },bytes,
                        Utils.getFileName(currentAudioUri, AddMusic.this),new CustomString("token",Constants.user.token));


            }
        }
        if (requestCode == 1002 && resultCode == Activity.RESULT_OK) {
            if ((data != null) && (data.getData() != null)) {
                Uri selectedImage = data.getData();
                try {
                    Bitmap currentPhotoBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                    photo.setBackground(null);
                    API.addMusicPhoto(new ApiListener() {
                        Dialog d;

                        @Override
                        public void onError(JSONObject json) throws JSONException {
                            d.dismiss();
                        }

                        @Override
                        public void inProcess() {
                            d = openWaiter(AddMusic.this);
                        }

                        @Override
                        public void onSuccess(JSONObject json) throws JSONException {
                            runOnUiThread(() -> photo.setImageBitmap(currentPhotoBitmap));
                            currentPhotoUrl = json.getString("url");
                            d.dismiss();
                        }
                    }, currentPhotoBitmap, new CustomString("token", Constants.user.token));
                } catch (FileNotFoundException e) {
                    System.out.println("CANT LOAD PHOTO");
                    e.printStackTrace();
                }

            }
        }
    }

    @SuppressLint("NewApi")
    public boolean checkPerm(Context context, String permission) {
        int permissionStatus = ContextCompat.checkSelfPermission(context, permission);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
            return false;
        }
    }



}
