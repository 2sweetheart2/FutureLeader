package me.solo_team.futureleader.ui.news.open_news;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import io.socket.client.Ack;
import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.API.websocket.WebScoketClient;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Audio;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.User;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.FullScreenPhoto;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.VideoView;
import me.solo_team.futureleader.ui.menu.statical.Media.MusicPlayer;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.stat.LoginStat;
import me.solo_team.futureleader.ui.profile.view_prof.ViewProfile;

public class OpenNewsFragment extends Her {
    String news;
    JSONObject oldJson;
    View root;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_open);
        root = findViewById(R.id.news_open);
        ((LinearLayout) findViewById(R.id.news_open_list)).removeAllViews();
        setTitle(getIntent().getStringExtra("tag"));
        API.getNew(new ApiListener() {
                       Dialog d;

                       @Override
                       public void onError(JSONObject json) {

                       }

                       @Override
                       public void inProcess() {
                           d = openWaiter(OpenNewsFragment.this);
                       }

                       @Override
                       public void onSuccess(JSONObject json) throws JSONException {
                           JSONObject new_ = json.getJSONObject("new");
                           String objects_ = new_.getString("objects");
                           try {
                               new_.put("objects", pareseStringToJSAray(objects_));
                           }catch (JSONException e){
                               d.dismiss();
                               finish();
                           }
                           JSONArray objects = new_.getJSONArray("objects");
                           Constants.newsCache.curentNew = new_;
                           oldJson = Constants.newsCache.curentNew;
                           runOnUiThread(() -> {
                               try {
                                   ((TextView) findViewById(R.id.news_open_title)).setText(new_.getString("name"));
                                   draw(objects);
                               } catch (JSONException e) {

                                   e.printStackTrace();
                               }
                           });
                           d.dismiss();




                       }
                   },
                new CustomString("token", Constants.user.token),
                new CustomString("id", String.valueOf(getIntent().getIntExtra("id", -1)))
        );
    }


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        try {
            if (Constants.newsCache.curentNew == null) return;
            draw(Constants.newsCache.curentNew.getJSONArray("objects"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void draw(JSONArray objects) throws JSONException {
        ((LinearLayout) findViewById(R.id.news_open_list)).removeAllViews();

        for (int i = 0; i < objects.length(); i++) {
            JSONObject o = new JSONObject(objects.getString(i));
            ((TextView) findViewById(R.id.news_view_likes)).setText(Constants.newsCache.curentNew.getString("likes"));
            ((TextView) findViewById(R.id.news_view_count)).setText(Constants.newsCache.curentNew.getString("views_count"));
            Intent intent2 = new Intent(OpenNewsFragment.this,CheckUsers.class);
            findViewById(R.id.huina).setOnClickListener(v -> {
                if (!Constants.user.permission.can_view_new_views)
                    return;
                intent2.putExtra("liked",false);
                startActivity(intent2);
            });
            findViewById(R.id.huina2).setOnClickListener(v -> {
                if (!Constants.user.permission.can_view_new_views)
                {
                    setLiked();
                    return;
                }
                AlertDialog.Builder obj = new AlertDialog.Builder(OpenNewsFragment.this);
                obj.setTitle("выбор действия");
                obj.setIcon(R.drawable.resize_300x0);
                obj.setPositiveButton("лайкнуть", (dialog, which) -> setLiked());
                obj.setNegativeButton("просмотреть статистику", (dialog1, which) -> {
                    intent2.putExtra("liked",true);
                    startActivity(intent2);
                });
                obj.show();

            });
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 2, 0, 2);
            System.out.println(o);
            switch (o.getString("type")) {
                case "text":
                    TextView textView = new TextView(getApplicationContext(), null);
                    textView.setTextColor(Color.BLACK);
                    textView.setLayoutParams(lp);
                    textView.setText(o.getString("value"));
                    if(o.has("extras"))
                    {
                        String[] params = o.getString("extras").split("\\|");
                        for(String param : params){
                            if(param.equals("bold"))
                                textView.setTypeface(null,Typeface.BOLD);
                        }
                    }
                    ((LinearLayout) findViewById(R.id.news_open_list)).addView(textView);
                    break;
                case "photo":
                    List<String> params = Arrays.asList(o.getString("extras").split(","));
                    Point size = new Point();
                    getWindowManager().getDefaultDisplay().getSize(size);
                    ImageView imageView = new ImageView(getApplicationContext(), null);
                    if (params.contains("full_screen_v2")) {
                        lp.height = (int) (size.y / 1.5);
                        imageView.setLayoutParams(lp);
                        Constants.cache.addPhoto(o.getString("value"), imageView, OpenNewsFragment.this);
                        ((LinearLayout) findViewById(R.id.news_open_list)).addView(imageView);
                        break;
                    }
                    lp.height = (int) (size.y / 1.5);
                    lp.gravity = Gravity.CENTER_HORIZONTAL;
                    imageView.setLayoutParams(lp);

                    Constants.cache.addPhoto(o.getString("value"), imageView, OpenNewsFragment.this);
                    ((LinearLayout) findViewById(R.id.news_open_list)).addView(imageView);
                    if (params.contains("full_screen")) {
                        System.out.println("FULL SCREEN");
                        int finalI = i;
                        imageView.setOnClickListener(v -> {
                                    Intent intent = new Intent(OpenNewsFragment.this, FullScreenPhoto.class);
                                    try {
                                        intent.putExtra("url", o.getString("value"));
                                        String name = getIntent().getStringExtra("tag");
                                        if (getIntent().getStringExtra("tag").startsWith("#"))
                                            name = getIntent().getStringExtra("tag").substring(1);
                                        intent.putExtra("name", name + "-" + finalI + ".png");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(intent);

                        });
                    }
                    break;
                case "text_link":
                    String url = o.getString("value");
                    SpannableString ss = new SpannableString(url);
                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View textView) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            startActivity(intent);
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setUnderlineText(false);
                        }
                    };
                    ss.setSpan(clickableSpan, 0, url.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    TextView textView1 = new TextView(getApplicationContext());
                    textView1.setText(ss);
                    textView1.setMovementMethod(LinkMovementMethod.getInstance());
                    textView1.setHighlightColor(Color.TRANSPARENT);
                    textView1.setLayoutParams(lp);
                    ((LinearLayout) findViewById(R.id.news_open_list)).addView(textView1);
                    break;
                case "audio":
                    Audio audio = new Audio(new JSONObject(o.getString("value")),OpenNewsFragment.this);
                    View v = getLayoutInflater().inflate(R.layout.obj_music, null);
                    Constants.cache.addPhoto(audio.urlPhoto, v.findViewById(R.id.obj_music_image), this);
                    ((TextView) v.findViewById(R.id.obj_music_name)).setText(audio.name);
                    ((TextView) v.findViewById(R.id.obj_music_author)).setText(audio.author);
                    v.findViewById(R.id.obj_music_fav).setVisibility(View.GONE);
                    v.setOnClickListener(v1 -> {
                        Intent intent = new Intent(OpenNewsFragment.this, MusicPlayer.class);
                        Constants.audioCache.curAudio = audio;
                        startActivity(intent);
                    });
                    ((LinearLayout) findViewById(R.id.news_open_list)).addView(v);
                    break;
                case "video":
                    View view = getLayoutInflater().inflate(R.layout.obj_video, null);
                    ((TextView) view.findViewById(R.id.obj_video_name)).setText(o.getString("extras"));
                    view.setOnClickListener(v12 -> {
                        Intent intent = new Intent(OpenNewsFragment.this, VideoView.class);
                        try {
                            intent.putExtra("url", o.getString("value"));
                            runOnUiThread(() -> startActivity(intent));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                    ((LinearLayout) findViewById(R.id.news_open_list)).addView(view);
                    break;
                case "user":
                    View view1 = getLayoutInflater().inflate(R.layout.obj_chat_member,null);
                    ((TextView) view1.findViewById(R.id.chat_member_name)).setText(o.getString("value"));
                    Constants.cache.addPhoto(o.getString("extras"),view1.findViewById(R.id.chat_member_image),this);
                    if(Constants.user.permission.can_get_user)
                        view1.setOnClickListener(v1 -> {
                            try {
                                API.getUser(new ApiListener() {
                                    Dialog d;
                                    @Override
                                    public void onError(JSONObject json) throws JSONException {
                                        d.dismiss();
                                        createNotification(v1,json.getString("message"));
                                    }

                                    @Override
                                    public void inProcess() {
                                        d = openWaiter(OpenNewsFragment.this);
                                    }

                                    @Override
                                    public void onSuccess(JSONObject json) throws JSONException {
                                        User user = new User(json.getJSONObject("user"));
                                        d.dismiss();
                                        runOnUiThread(()->{
                                            Constants.currentUser = user;
                                            Intent intent = new Intent(OpenNewsFragment.this, ViewProfile.class);
                                            intent.putExtra("removeSelf",false);
                                            startActivity(intent);
                                        });
                                    }
                                },new CustomString("token",Constants.user.token),new CustomString("id",o.getString("id")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    ((LinearLayout) findViewById(R.id.news_open_list)).addView(view1);
                    break;
            }
        }
    }

    public void setLiked(){
        JSONObject jsonInput = new JSONObject();
        try {
            jsonInput.put("id", Constants.newsCache.curentNew.getInt("id"));
            jsonInput.put("token", Constants.user.token);
            jsonInput.put("mobile_token",Constants.user.mobileToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        WebScoketClient.mSocket.emit("like_new", jsonInput, emitterCallback);
    }

    public Ack emitterCallback = args -> {
        JSONObject o = (JSONObject) args[0];
        try {
            boolean stat = o.getJSONObject("response").getBoolean("succes");
            System.out.println("EMIT: "+ stat);
            if(!stat)
                Utils.ShowSnackBar.show(OpenNewsFragment.this,"уже лайкнуто!",root);
            else {
                Utils.ShowSnackBar.show(OpenNewsFragment.this, "лайк поставлен!", root);
                System.out.println(Constants.newsCache.curentNew);
                Constants.newsCache.curentNew.put("likes",Integer.parseInt(Constants.newsCache.curentNew.getString("likes")+1));
                System.out.println(Constants.newsCache.curentNew);
                runOnUiThread(()->{
                    try {
                        ((TextView) findViewById(R.id.news_view_likes)).setText(Constants.newsCache.curentNew.getString("likes"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };


    private JSONArray pareseStringToJSAray(String s) throws JSONException {
        return new JSONArray(s);
    }


}
