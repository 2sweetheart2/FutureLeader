package me.solo_team.futureleader.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.news.open_news.EditNews;

public class AddVideo extends Her {

    Button button;
    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_video);
        button = findViewById(R.id.add_video_btn);
        editText = findViewById(R.id.add_video_name);
        textView = findViewById(R.id.add_video_file_name);
        button.setOnClickListener(v -> {
            Intent chooseFile;
            Intent intent;
            chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
            chooseFile.setType("*/*");
            intent = Intent.createChooser(chooseFile, "Choose a file");
            startActivityForResult(intent, 10);
        });
    }

    Uri uri = null;
    String url;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode);
        System.out.println(resultCode);
        if (requestCode == 10) {
            if (resultCode == 1 || data.getData() != null) {
                Uri uri = data.getData();
                String fileFormat = Utils.getFileFormat(uri, AddVideo.this);
                System.out.println(fileFormat);
                if (!"mp4".equals(fileFormat)) {
                    Utils.ShowSnackBar.show(AddVideo.this, "Файл должен быть формата mp4!", textView);
                    return;
                }
                API.addVideoFile(new ApiListener() {
                                     Dialog d;

                                     @Override
                                     public void onError(JSONObject json) throws JSONException {
                                         d.dismiss();
                                         createNotification(textView, json.getString("message"));
                                     }

                                     @Override
                                     public void inProcess() {
                                         d = openWaiter(AddVideo.this);
                                     }

                                     @Override
                                     public void onSuccess(JSONObject json) throws JSONException {
                                         url = json.getString("url");
                                         d.dismiss();
                                         runOnUiThread(() -> textView.setText("имя файла: " + Utils.getFileName(uri, AddVideo.this)));
                                     }
                                 },
                        getBytesFromUri(uri), Utils.getFileName(uri, AddVideo.this), new CustomString("token", Constants.user.token)
                );
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "")
                .setIcon(R.drawable.done)
                .setOnMenuItemClickListener(item -> {
                    if(url==null){
                        Utils.ShowSnackBar.show(AddVideo.this,"видео не выбрано!",textView);
                        return false;
                    }
                    if(editText.getText().length()==0){
                        Utils.ShowSnackBar.show(AddVideo.this,"название не может быть пустым!",textView);
                        return false;
                    }
                    API.addVideo(new ApiListener() {
                        Dialog d;
                        @Override
                        public void onError(JSONObject json) throws JSONException {
                            d.dismiss();
                            createNotification(textView,json.getString("message"));
                        }

                        @Override
                        public void inProcess() {
                            d = openWaiter(AddVideo.this);
                        }

                        @Override
                        public void onSuccess(JSONObject json) throws JSONException {
                            Intent data = new Intent();
                            data.putExtra("url",url);
                            data.putExtra("name",editText.getText().toString());
                            setResult(1,data);
                            d.dismiss();
                            finish();
                        }
                    },
                            new CustomString("url",url),new CustomString("token",Constants.user.token),new CustomString("name",editText.getText().toString())
                            );
                    return true;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);
    }

    private byte[] getBytesFromUri(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            return Utils.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
