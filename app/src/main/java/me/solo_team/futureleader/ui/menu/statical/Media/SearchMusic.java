package me.solo_team.futureleader.ui.menu.statical.Media;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Audio;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class SearchMusic extends Her {

    boolean selectOne = false;
    boolean needFav = false;
    LinearLayout list;
    EditText text;
    List<Audio> r = new ArrayList<>();
    List<Audio> searchAudio = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Поиск музыки");
        selectOne = getIntent().getBooleanExtra("needOne", false);
        needFav = getIntent().getBooleanExtra("needFav", true);
        setContentView(R.layout.search_musics);
        list = findViewById(R.id.search_music_list);
        text = findViewById(R.id.search_music_text);
        API.searchMusicByName(new ApiListener() {
            Dialog d;

            @Override
            public void onError(JSONObject json) throws JSONException {
                System.out.println(json.getString("message"));
                finish();
                d.dismiss();
            }

            @Override
            public void inProcess() {
                d = openWaiter(SearchMusic.this);

            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                JSONArray a = json.getJSONArray("audios");
                for (int i = 0; i < a.length(); i++) {
                    r.add(new Audio(a.getJSONObject(i), SearchMusic.this));
                }
                runOnUiThread(() -> render(r,50));
                d.dismiss();
            }
        },
                new CustomString("token",Constants.user.token)
                );
        findViewById(R.id.serach_musics).getViewTreeObserver().addOnGlobalLayoutListener(
                () -> {
                    Rect r = new Rect();
                    findViewById(R.id.serach_musics).getWindowVisibleDisplayFrame(r);
                    int screenHeight = findViewById(R.id.serach_musics).getRootView().getHeight();
                    int keypadHeight = screenHeight - r.bottom;
                    if (keypadHeight > screenHeight * 0.15) {
                        if (!isKeyboardShowing) {
                            isKeyboardShowing = true;
                            onKeyboardVisibilityChanged(true);
                        }
                    } else {
                        if (isKeyboardShowing) {
                            isKeyboardShowing = false;
                            onKeyboardVisibilityChanged(false);
                        }
                    }
                });
    }

    boolean isKeyboardShowing = false;
    void onKeyboardVisibilityChanged(boolean opened) {
        if (!opened && text.length() != 0)
            getMusicByName();
    }

    private void getMusicByName() {
        API.searchMusicByName(new ApiListener() {
            Dialog d;

            @Override
            public void onError(JSONObject json) throws JSONException {
                d.dismiss();
                runOnUiThread(() -> {
                    text.setText("");
                    render(r,35);
                });
            }

            @Override
            public void inProcess() {
                d = openWaiter(SearchMusic.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                JSONArray ar = json.getJSONArray("audios");
                searchAudio.clear();
                for (int i = 0; i < ar.length(); i++) {
                    searchAudio.add(new Audio(ar.getJSONObject(i), SearchMusic.this));
                }
                d.dismiss();
                runOnUiThread(() -> render(searchAudio,35));
            }
        }, new CustomString("token", Constants.user.token), new CustomString("name", text.getText().toString()));
    }

    public void render(List<Audio> audios,int limit) {
        list.removeAllViews();
        if(audios.size()<limit)
            limit = audios.size()-1;
        for (int i=0;i<=limit;i++) {
            Audio audio = audios.get(i);
            View v = getLayoutInflater().inflate(R.layout.obj_music, null);
            Constants.cache.addPhoto(audio.urlPhoto, v.findViewById(R.id.obj_music_image), this);
            ((TextView) v.findViewById(R.id.obj_music_name)).setText(audio.name);
            ((TextView) v.findViewById(R.id.obj_music_author)).setText(audio.author);
            if (needFav) {
                if (Constants.audioCache.checkHas(audio))
                    ((ImageView) v.findViewById(R.id.obj_music_fav)).setImageResource(R.drawable.favorite_true);
                else
                    ((ImageView) v.findViewById(R.id.obj_music_fav)).setImageResource(R.drawable.favorite_false);
            }
            else
                ((ImageView) v.findViewById(R.id.obj_music_fav)).setVisibility(View.GONE);
            if (selectOne) {
                v.setOnClickListener(v1 -> {
                    Intent data = new Intent();
                    data.putExtra("audio",audio.toString());
                    setResult(1,data);
                    finish();
                });
            }
            list.addView(v);
        }
    }
}
