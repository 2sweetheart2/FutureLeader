package me.solo_team.futureleader.ui.menu.statical.Media;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Audio;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class PopularMusic extends Her {


    String currentSearch = "";
    TextView popMusicText;
    TextView yourMusic;
    EditText searchPopMusic;
    LinearLayout musicList;
    LinearLayout yourMusicList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_music);
        setTitle("Музыка");

        popMusicText = findViewById(R.id.pop_music_pop_text);
        searchPopMusic = findViewById(R.id.pop_music_search);
        musicList = findViewById(R.id.pop_music_list);
        yourMusic = findViewById(R.id.pop_music_your_music);
        yourMusicList = findViewById(R.id.pop_music_your_music_list);

        searchPopMusic.addTextChangedListener(textChenger);

        API.getMusics(new ApiListener() {
                          Dialog d;

                          @Override
                          public void onError(JSONObject json) throws JSONException {
                              d.dismiss();
                              finish();
                          }

                          @Override
                          public void inProcess() {
                              d = openWaiter(PopularMusic.this);
                          }

                          @Override
                          public void onSuccess(JSONObject json) throws JSONException {
                              System.out.println(json);
                              JSONArray your_music = json.getJSONArray("your_musics");
                              JSONArray pop_music = json.getJSONArray("pop_musics");
                              Constants.audioCache.yourMusics.clear();
                              Constants.audioCache.popMusics.clear();
                              for (int i = 0; i < your_music.length(); i++)
                                  Constants.audioCache.yourMusics.add(new Audio(your_music.getJSONObject(i), PopularMusic.this));
                              for (int i = 0; i < pop_music.length(); i++)
                                  Constants.audioCache.popMusics.add(new Audio(pop_music.getJSONObject(i), PopularMusic.this));
                              d.dismiss();
                              runOnUiThread(() -> update());
                          }
                      },
                new CustomString("token", Constants.user.token)
        );


    }

    private final TextWatcher textChenger = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            System.out.println("EDNED TEXT: " + searchPopMusic.getText());
            currentSearch = searchPopMusic.getText().toString();
            update();
        }
    };


    private void update() {
        musicList.removeAllViews();
        yourMusicList.removeAllViews();
        if (currentSearch.length() != 0) {
            popMusicText.setVisibility(View.GONE);
            yourMusicList.setVisibility(View.GONE);
            yourMusic.setVisibility(View.GONE);
        } else {
            yourMusicList.setVisibility(View.VISIBLE);
            yourMusic.setVisibility(View.VISIBLE);
            addYourMusic();
            popMusicText.setVisibility(View.VISIBLE);
            addPopMusicViews();
        }

    }

    private void addYourMusic() {
            for(Audio audio : Constants.audioCache.yourMusics){
                View view = getLayoutInflater().inflate(R.layout.obj_music, null, false);
                ((TextView) view.findViewById(R.id.obj_music_author)).setText(audio.author);
                ((TextView) view.findViewById(R.id.obj_music_name)).setText(audio.name);
                ((TextView) view.findViewById(R.id.obj_music_duratation)).setText(String.valueOf(audio.duratation));
                Constants.cache.addPhoto(audio.urlPhoto,false,((ImageView)view.findViewById(R.id.obj_music_image)),this);
                Constants.audioCache.yourMusicsViews.add(view);
                yourMusicList.addView(view);
            }
    }

    private void addPopMusicViews() {
            for(Audio audio : Constants.audioCache.popMusics){
                View view = getLayoutInflater().inflate(R.layout.obj_music, null, false);
                ((TextView) view.findViewById(R.id.obj_music_author)).setText(audio.author);
                ((TextView) view.findViewById(R.id.obj_music_name)).setText(audio.name);
                ((TextView) view.findViewById(R.id.obj_music_duratation)).setText(String.valueOf(audio.duratation));
                Constants.cache.addPhoto(audio.urlPhoto,false,((ImageView)view.findViewById(R.id.obj_music_image)),this);
                Constants.audioCache.popMusicsViews.add(view);
                musicList.addView(view);
            }
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "")
                .setIcon(R.drawable.plus)
                .setOnMenuItemClickListener(item -> {
                    startActivity(new Intent(PopularMusic.this,AddMusic.class));
                    return true;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);
    }


}
