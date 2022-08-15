package me.solo_team.futureleader.ui.menu.statical.Media;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.TimeBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.MainActivity;
import me.solo_team.futureleader.Objects.Audio;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;

public class MusicPlayer extends AppCompatActivity {

    ImageView image;
    TextView name;
    TextView author;
    DefaultTimeBar timeBar;
    TextView time;

    ImageButton left;
    ImageButton pause;
    ImageButton right;
    Audio currentAudio = null;

    Drawable pauseIcon;
    Drawable playIcon;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pauseIcon = getResources().getDrawable(R.drawable.pause);
        playIcon = getResources().getDrawable(R.drawable.play);
        setContentView(R.layout.media_player);
        image = findViewById(R.id.music_player_image);
        name = findViewById(R.id.music_player_name);
        author = findViewById(R.id.music_player_author);
        timeBar = findViewById(R.id.music_player_seek_bar);
        time = findViewById(R.id.music_player_time);

        left = findViewById(R.id.music_player_left);
        pause = findViewById(R.id.music_player_pause);
        right = findViewById(R.id.music_player_right);
        System.out.println(Constants.audioCache.getCurrentAudio().name);
        pause.setOnClickListener(stopPlay);

        right.setOnClickListener(v -> {
            if (MainActivity.mediaController == null) return;
            MainActivity.mediaController.getTransportControls().skipToNext();
        });
        left.setOnClickListener(v->{
            if (MainActivity.mediaController == null) return;
            MainActivity.mediaController.getTransportControls().skipToPrevious();
        });


        timeBar.addListener(new TimeBar.OnScrubListener() {
            @Override
            public void onScrubStart(TimeBar timeBar, long position) {
                changeTimeStamp = true;
            }

            @Override
            public void onScrubMove(TimeBar timeBar, long position) {
                time.setText("- " + getStringFromMilSec(position));
            }

            @Override
            public void onScrubStop(TimeBar timeBar, long position, boolean canceled) {
                Constants.exoPlayer.seekTo(position);
                changeTimeStamp = false;
            }
        });


        MainActivity.mediaController.getTransportControls().play();
        if (MainActivity.mediaController.getMetadata() != null) {
            if (!MainActivity.mediaController.getMetadata().getString(MediaMetadataCompat.METADATA_KEY_TITLE).equals(Constants.audioCache.getCurrentAudio().name)
                    ||
                    !MainActivity.mediaController.getMetadata().getString(MediaMetadataCompat.METADATA_KEY_ARTIST).equals(Constants.audioCache.getCurrentAudio().author))
            {
                Bundle bundle = new Bundle();
                MainActivity.mediaController.getTransportControls().sendCustomAction("update", bundle);
            }
        }
        stopThread = false;
        if(getIntent().hasExtra("audio")) {
            try {
                Constants.audioCache.curAudio = new Audio(new JSONObject(getIntent().getStringExtra("audio")),MusicPlayer.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        zalupa();
    }

    Audio audio = null;

    private String getStringFromMilSec(long dur) {
        return DateUtils.formatElapsedTime(dur / 1000); // converting time in millis to minutes:second format eg 14:15 min
    }

    boolean stopThread = false;

    private void zalupa() {
        new Thread(() -> {
            while (true) {
                if (stopThread) return;
                if (MainActivity.mediaController != null) {
                    if (MainActivity.mediaController.getPlaybackState() != null) {
                        int state = MainActivity.mediaController.getPlaybackState().getState();
                        if (state == 3) {
                            runOnUiThread(() -> pause.setImageDrawable(pauseIcon));
                        } else {
                            runOnUiThread(() -> pause.setImageDrawable(playIcon));
                        }
                    }
                }
                Audio audio = Constants.audioCache.getCurrentAudio();
                if (audio != null) {

                    if (audio != currentAudio) {
                        runOnUiThread(() -> {
                            currentAudio = audio;
                            timeBar.setDuration(Constants.exoPlayer.getContentDuration());
                            timeBar.setPosition(0);
                            name.setText(audio.name);
                            author.setText(audio.author);
                            if (audio.imageBitmap != null) {
                                runOnUiThread(() -> image.setImageBitmap(Utils.getRoundedCornerBitmap(audio.imageBitmap, 25)));
                            } else if (audio.urlPhoto != null)
                                Utils.getBitmapFromURL(audio.urlPhoto, bitmap -> {
                                    Bundle bundle = new Bundle();
                                    Constants.audioCache.getCurrentAudio().imageBitmap = bitmap;
                                    MainActivity.mediaController.getTransportControls().sendCustomAction("update_logo", bundle);
                                    runOnUiThread(() -> image.setImageBitmap(Utils.getRoundedCornerBitmap(bitmap, 25)));
                                });
                        });

                    }
                }
                try {
                    if (Constants.exoPlayer != null) {
                        runOnUiThread(() -> {
                            if(Constants.exoPlayer.getContentDuration()>=0) timeBar.setDuration(Constants.exoPlayer.getContentDuration());
                            timeBar.setPosition(Constants.exoPlayer.getCurrentPosition());

                            if (!changeTimeStamp)
                                time.setText(getStringFromMilSec(Constants.exoPlayer.getCurrentPosition()));
                        });
                    }
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    boolean changeTimeStamp = false;

    @Override
    public void onResume() {
        super.onResume();
        stopThread = false;
        zalupa();
    }

    @Override
    public void onPause() {

        super.onPause();
        stopThread = true;
    }


    View.OnClickListener stopPlay = v -> {
        if (MainActivity.mediaController == null) return;
        if (MainActivity.mediaController.getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING)
            MainActivity.mediaController.getTransportControls().pause();
        else
            MainActivity.mediaController.getTransportControls().play();
    };


}
