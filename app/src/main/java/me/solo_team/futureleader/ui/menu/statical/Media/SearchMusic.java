package me.solo_team.futureleader.ui.menu.statical.Media;

import android.app.Dialog;
import android.content.Intent;
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
                runOnUiThread(() -> render());
                d.dismiss();
            }
        },
                new CustomString("token",Constants.user.token)
                );
    }

    public void render() {
        list.removeAllViews();
        for (Audio audio : r) {
            View v = getLayoutInflater().inflate(R.layout.obj_music, null);
            Constants.cache.addPhoto(audio.urlPhoto, v.findViewById(R.id.obj_music_image), this);
            ((TextView) v.findViewById(R.id.obj_music_name)).setText(audio.name);
            ((TextView) v.findViewById(R.id.obj_music_author)).setText(audio.author);
            if (needFav) {
                if (audio.liked)
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
