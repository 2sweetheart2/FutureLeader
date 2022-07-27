package me.solo_team.futureleader.ui.menu.statical.Media;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.news.open_news.EditNews;

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
        update();
    }

    private TextWatcher textChenger = new TextWatcher() {
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
        if (currentSearch.length() != 0) {
            popMusicText.setVisibility(View.GONE);
            yourMusicList.setVisibility(View.GONE);
            yourMusic.setVisibility(View.GONE);
        }
        else {
            yourMusicList.setVisibility(View.VISIBLE);
            yourMusic.setVisibility(View.VISIBLE);
            addYourMusic();
            popMusicText.setVisibility(View.VISIBLE);
            addPopMusicViews();
        }

    }

    private void addYourMusic(){
        View view = getLayoutInflater().inflate(R.layout.obj_music, null, false);
        ((TextView) view.findViewById(R.id.obj_music_duratation)).setText("0/5");
        View view2 = getLayoutInflater().inflate(R.layout.obj_music, null, false);

        yourMusicList.addView(view);
        yourMusicList.addView(view2);
    }

    private void addPopMusicViews() {
        View view = getLayoutInflater().inflate(R.layout.obj_music, null, false);
        ((TextView) view.findViewById(R.id.obj_music_duratation)).setText("0/5");
        View view2 = getLayoutInflater().inflate(R.layout.obj_music, null, false);

        musicList.addView(view);
        musicList.addView(view2);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "")
                .setIcon(R.drawable.plus)
                .setOnMenuItemClickListener(item -> {selectMP3(); return true;})
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);
    }

    private final int REQ_CODE_PICK_SOUNDFILE = 1001;

    private void selectMP3(){
        Intent intent;
        intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/mpeg");
        startActivityForResult(Intent.createChooser(intent, "Выбрать аудио (MP3)"), REQ_CODE_PICK_SOUNDFILE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_PICK_SOUNDFILE && resultCode == Activity.RESULT_OK){
            if ((data != null) && (data.getData() != null)){
                Uri audioFileUri = data.getData();
                System.out.println(audioFileUri.toString());
                // Now you can use that Uri to get the file path, or upload it, ...
            }
        }
    }
}
