package me.solo_team.futureleader.MediaAudioAdapters.VideoAdapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.solo_team.futureleader.R;

public class VideoView extends AppCompatActivity {
    android.widget.VideoView vw;
    int lastDur;
    boolean playing;
    boolean landscape = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_layout);
        vw = findViewById(R.id.vidvw);
        vw.setMediaController(new MediaController(this));
        vw.setOnCompletionListener(mp -> {
            AlertDialog.Builder obj = new AlertDialog.Builder(VideoView.this);
            obj.setTitle("Видео закончилось!");
            obj.setIcon(R.drawable.resize_300x0);
            MyListener m = new MyListener();
            obj.setPositiveButton("Начать с начала", m);
            obj.setNegativeButton("Закрыть", m);
            obj.setMessage("Хотите начать просмотр с начала или закрыть?");
            obj.show();
        });
        ImageView resize = findViewById(R.id.full_screeen_xyi);
        resize.setOnClickListener(v -> {
            if(landscape) setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            else setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            landscape=!landscape;
        });
        // video name should be in lower case alphabet.
        String uriPath = getIntent().getStringExtra("url");
        Uri uri = Uri.parse(uriPath);
        vw.setVideoURI(uri);
        vw.seekTo(0);
    }

    class MyListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            if (which == -1) {
                vw.seekTo(0);
                vw.start();
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        vw.seekTo(lastDur);
        if(playing) vw.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        lastDur = vw.getDuration();
        playing = vw.isPlaying();
    }

}
