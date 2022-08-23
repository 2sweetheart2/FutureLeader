package me.solo_team.futureleader.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.style.DynamicDrawableSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import me.solo_team.futureleader.R;

public class VideoView extends AppCompatActivity {

    // Your Video URL
    MediaController mediaController;
    android.widget.VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view_l);
        int uiOptions;
        uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        // прячем панель навигации и строку состояния
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        // finding videoview by its id
        videoView = findViewById(R.id.video_view);
        // Uri object to refer the
        // resource from the videoUrl
        Uri uri = Uri.parse(getIntent().getStringExtra("url"));

        // sets the resource from the
        // videoUrl to the videoView
        videoView.setVideoURI(uri);
        // creating object of
        // media controller class
        mediaController = new MediaController(this);

        // sets the anchor view
        // anchor view for the videoView
        mediaController.setAnchorView(videoView);

        // sets the media player to the videoView
        mediaController.setMediaPlayer(videoView);

        videoView.setOnCompletionListener(mp ->
        {
            System.out.println("COMPLETE");
        });
        videoView.setOnErrorListener((mp, what, extra) -> {
            System.out.println("ERROR");
            return true;
        });
        videoView.setOnInfoListener((mp, what, extra) -> {
            System.out.println("INFO");
            return true;
        });
            // sets the media controller to the videoView
        videoView.setMediaController(mediaController);
        videoView.start();
        // starts the video
    }

    public int dur = 0;
    Thread thread = null;
    boolean click = false;

    boolean isInFront = false;

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("RESUME ON DUR:"+dur);
        isInFront = true;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        System.out.println("CHANGE_SCRRENOR");
    }


    @Override
    public void onPause() {
        super.onPause();
        isInFront = false;
    }

}