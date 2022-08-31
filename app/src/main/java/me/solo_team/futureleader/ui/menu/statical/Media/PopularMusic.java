package me.solo_team.futureleader.ui.menu.statical.Media;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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

import java.util.HashMap;
import java.util.Map;

import io.socket.client.Ack;
import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.API.websocket.WebScoketClient;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.MainActivity;
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
        findViewById(R.id.pop_music).getViewTreeObserver().addOnGlobalLayoutListener(
                () -> {
                    Rect r = new Rect();
                    findViewById(R.id.pop_music).getWindowVisibleDisplayFrame(r);
                    int screenHeight = findViewById(R.id.pop_music).getRootView().getHeight();
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
        if (!opened && currentSearch.length() != 0)
            getMusicByName();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 66 && currentSearch.length() != 0)
            getMusicByName();
        return super.onKeyDown(keyCode, event);
    }

    private void getMusicByName() {
        API.searchMusicByName(new ApiListener() {
            Dialog d;

            @Override
            public void onError(JSONObject json) throws JSONException {
                d.dismiss();
                runOnUiThread(() -> {
                    searchPopMusic.setText("");
                    currentSearch = "";
                    update();
                });
            }

            @Override
            public void inProcess() {
                d = openWaiter(PopularMusic.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                JSONArray ar = json.getJSONArray("audios");
                Constants.audioCache.seaechedAudios.clear();
                for (int i = 0; i < ar.length(); i++) {
                    Constants.audioCache.seaechedAudios.add(new Audio(ar.getJSONObject(i), PopularMusic.this));
                }
                d.dismiss();
                runOnUiThread(() -> update());
            }
        }, new CustomString("token", Constants.user.token), new CustomString("name", currentSearch));
    }

    private void update() {
        musicList.removeAllViews();
        yourMusicList.removeAllViews();
        if (currentSearch.length() != 0) {
            popMusicText.setVisibility(View.GONE);
            yourMusicList.setVisibility(View.GONE);
            yourMusic.setVisibility(View.GONE);
            popMusicsViews.clear();
            youMusicsViews.clear();
            addSearchMusic();
        } else {
            Constants.audioCache.seaechedAudios.clear();
            searchMusicsViews.clear();
            yourMusicList.setVisibility(View.VISIBLE);
            yourMusic.setVisibility(View.VISIBLE);
            addYourMusic();
            popMusicText.setVisibility(View.VISIBLE);
            addPopMusicViews();
        }

    }

    private void addSearchMusic() {
        for (Audio audio : Constants.audioCache.seaechedAudios) {
            View view = getLayoutInflater().inflate(R.layout.obj_music, null, false);
            ((TextView) view.findViewById(R.id.obj_music_author)).setText(audio.author);
            ((TextView) view.findViewById(R.id.obj_music_name)).setText(audio.name);
            Constants.cache.addPhoto(audio.urlPhoto, ((ImageView) view.findViewById(R.id.obj_music_image)), this);
            Constants.audioCache.popMusicsViews.add(view);
            if (Constants.audioCache.checkHas(audio))
                ((ImageView) view.findViewById(R.id.obj_music_fav)).setImageResource(R.drawable.favorite_true);
            view.setOnClickListener(v -> {
                Constants.audioCache.curAudio = null;
                Constants.audioCache.setCurrentAudio(2, audio);
                Intent intent = new Intent(PopularMusic.this, MusicPlayer.class);
                startActivity(intent);
            });
            view.findViewById(R.id.obj_music_fav).setOnClickListener(v -> {
                sendMessage(audio.id);
            });
            musicList.addView(view);
            searchMusicsViews.put(audio, view);
        }
    }

    public HashMap<Audio, View> youMusicsViews = new HashMap<>();
    public HashMap<Audio, View> popMusicsViews = new HashMap<>();
    public HashMap<Audio, View> searchMusicsViews = new HashMap<>();

    private void addYourMusic() {
        for (Audio audio : Constants.audioCache.yourMusics) {
            View view = getLayoutInflater().inflate(R.layout.obj_music, null, false);
            ((TextView) view.findViewById(R.id.obj_music_author)).setText(audio.author);
            ((TextView) view.findViewById(R.id.obj_music_name)).setText(audio.name);
            ((ImageView) view.findViewById(R.id.obj_music_fav)).setImageResource(R.drawable.favorite_true);
            Constants.cache.addPhoto(audio.urlPhoto, ((ImageView) view.findViewById(R.id.obj_music_image)), this);
            Constants.audioCache.yourMusicsViews.add(view);
            view.setOnClickListener(v -> {
                Constants.audioCache.setCurrentAudio(0, audio);
                Constants.audioCache.curAudio = null;
                Intent intent = new Intent(PopularMusic.this, MusicPlayer.class);
                startActivity(intent);
            });
            view.findViewById(R.id.obj_music_fav).setOnClickListener(v -> {
                sendMessage(audio.id);
            });
            yourMusicList.addView(view);
                youMusicsViews.put(audio,view);
            }
    }

    public void sendMessage(int id){
        JSONObject jsonInput = new JSONObject();
        try {
            jsonInput.put("id", id);
            jsonInput.put("token", Constants.user.token);
            jsonInput.put("mobile_token",Constants.user.mobileToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("SET LIKE");
        System.out.println(id);
        WebScoketClient.mSocket.emit("set_like_audio", jsonInput, emitterCallback);
    }

    public Ack emitterCallback = args -> {
        try {
            JSONObject o = ((JSONObject) args[0]).getJSONObject("response");
            if (currentSearch.length() == 0) {
                for (Map.Entry<Audio, View> entry : youMusicsViews.entrySet()) {
                    if (entry.getKey().id == o.getInt("audio_id")) {
                        runOnUiThread(() -> {
                            try {
                                if (o.getBoolean("add")) {
                                    ((ImageView) entry.getValue().findViewById(R.id.obj_music_fav)).setImageResource(R.drawable.favorite_true);
                                    Constants.audioCache.yourMusics.add(entry.getKey());
                                }
                                else {
                                    ((ImageView) entry.getValue().findViewById(R.id.obj_music_fav)).setImageResource(R.drawable.favorite_false);
                                    if(Constants.audioCache.pos!=-1)
                                        if(Constants.audioCache.getCurrentAudio().equals(entry.getKey())){
                                            MainActivity.mediaController.getTransportControls().skipToNext();
                                        }
                                    Constants.audioCache.yourMusics.remove(entry.getKey());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                        break;
                    }
                }
                for (Map.Entry<Audio, View> entry : popMusicsViews.entrySet()) {
                    if (entry.getKey().id == o.getInt("audio_id")) {
                        runOnUiThread(() -> {
                            try {
                                if (o.getBoolean("add"))
                                    ((ImageView) entry.getValue().findViewById(R.id.obj_music_fav)).setImageResource(R.drawable.favorite_true);
                                else
                                    ((ImageView) entry.getValue().findViewById(R.id.obj_music_fav)).setImageResource(R.drawable.favorite_false);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                        break;
                    }
                }
            } else
                for (Map.Entry<Audio, View> entry : searchMusicsViews.entrySet()) {
                    if (entry.getKey().id == o.getInt("audio_id")) {
                        runOnUiThread(() -> {
                            try {
                                if (o.getBoolean("add"))
                                    ((ImageView) entry.getValue().findViewById(R.id.obj_music_fav)).setImageResource(R.drawable.favorite_true);
                                else
                                    ((ImageView) entry.getValue().findViewById(R.id.obj_music_fav)).setImageResource(R.drawable.favorite_false);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                        removeLike(entry.getKey(),o.getBoolean("add"));
                        break;
                    }
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    public void removeLike(Audio a, boolean liked) {
        if (!liked) {
            for (Audio audio : Constants.audioCache.yourMusics) {
                if (audio.id == a.id) {
                    Constants.audioCache.yourMusics.remove(audio);
                    audio.liked = false;
                }
            }
        } else {
            a.liked = true;
            Constants.audioCache.yourMusics.add(a);
        }
    }


    private void addPopMusicViews() {
        for (Audio audio : Constants.audioCache.popMusics) {
            View view = getLayoutInflater().inflate(R.layout.obj_music, null, false);
            ((TextView) view.findViewById(R.id.obj_music_author)).setText(audio.author);
            ((TextView) view.findViewById(R.id.obj_music_name)).setText(audio.name);
            Constants.cache.addPhoto(audio.urlPhoto, ((ImageView) view.findViewById(R.id.obj_music_image)), this);
            Constants.audioCache.popMusicsViews.add(view);
            if (Constants.audioCache.checkHas(audio))
                ((ImageView) view.findViewById(R.id.obj_music_fav)).setImageResource(R.drawable.favorite_true);
            view.setOnClickListener(v -> {
                Constants.audioCache.curAudio = null;
                Constants.audioCache.setCurrentAudio(1, audio);
                Intent intent = new Intent(PopularMusic.this, MusicPlayer.class);
                startActivity(intent);
            });
            view.findViewById(R.id.obj_music_fav).setOnClickListener(v -> {
                sendMessage(audio.id);
            });
            musicList.addView(view);
                popMusicsViews.put(audio,view);
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
