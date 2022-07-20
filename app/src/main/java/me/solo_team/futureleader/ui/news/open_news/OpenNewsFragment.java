package me.solo_team.futureleader.ui.news.open_news;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
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

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.Utils;
import me.solo_team.futureleader.dialogs.SaveOrSeePhoto;
import me.solo_team.futureleader.ui.WebViewsContent.WebView;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class OpenNewsFragment extends Her {
    String news;
    JSONObject oldJson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_open);
        setTitle(getIntent().getStringExtra("tag"));
        //TODO: нужно сделать +1 к просмотру в API
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
                           new_.put("objects", pareseStringToJSAray(objects_));
                           JSONArray objects = new_.getJSONArray("objects");
                           Constants.newsCache.curentNew = new_;
                           oldJson = new_;
                           runOnUiThread(() -> {
                               try {
                                   d.dismiss();
                                   ((TextView) findViewById(R.id.news_open_title)).setText(new_.getString("name"));
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           });
                           draw(objects);


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
        runOnUiThread(() -> ((LinearLayout) findViewById(R.id.news_open_list)).removeAllViews());

        for (int i = 0; i < objects.length(); i++) {
            JSONObject o = new JSONObject(objects.getString(i));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 2, 0, 2);
            System.out.println(o);
            switch (o.getString("type")) {
                case "text":
                    TextView textView = new TextView(getApplicationContext(), null);
                    textView.setTextColor(Color.BLACK);
                    textView.setLayoutParams(lp);
                    textView.setText(o.getString("value"));
                    runOnUiThread(() -> ((LinearLayout) findViewById(R.id.news_open_list)).addView(textView));
                    break;
                case "photo":
                    List<String> params = Arrays.asList(o.getString("extras").split(","));
                    Point size = new Point();
                    getWindowManager().getDefaultDisplay().getSize(size);
                    ImageView imageView = new ImageView(getApplicationContext(), null);
                    if (params.contains("full_screen_v2")) {
                        lp.height = (int) (size.y / 1.5);
                        imageView.setLayoutParams(lp);
                        Constants.cache.addPhoto(o.getString("value"), true, imageView, OpenNewsFragment.this);
                        runOnUiThread(() -> ((LinearLayout) findViewById(R.id.news_open_list)).addView(imageView));
                        break;
                    }
                    lp.height = (int) (size.y / 1.5);
                    lp.gravity = Gravity.CENTER_HORIZONTAL;
                    imageView.setLayoutParams(lp);

                    Constants.cache.addPhoto(o.getString("value"), true, imageView, OpenNewsFragment.this);
                    runOnUiThread(() -> ((LinearLayout) findViewById(R.id.news_open_list)).addView(imageView));
                    if (params.contains("full_screen")) {
                        System.out.println("FULL SCREEN");
                        int finalI = i;
                        imageView.setOnClickListener(v -> {
                            SaveOrSeePhoto saveOrSeePhoto = new SaveOrSeePhoto(new SaveOrSeePhoto.PressSee() {
                                @Override
                                public void pressSee(boolean result) {
                                    if(result){
                                        Intent intent = new Intent(OpenNewsFragment.this, WebView.class);
                                        try {
                                            intent.putExtra("photo", o.getString("value"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        startActivity(intent);
                                    }
                                    else{
                                        try {
                                            Utils.getBitmapFromURL(o.getString("value"),bitmap -> {
                                                if(bitmap==null)
                                                    return;
                                                Utils.saveImage(bitmap,"Future-leaders",getApplicationContext(),getIntent().getStringExtra("tag")+"-"+ finalI);
                                            });
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                            saveOrSeePhoto.show(getSupportFragmentManager(),null);

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
                    runOnUiThread(() -> ((LinearLayout) findViewById(R.id.news_open_list)).addView(textView1));
                    break;
            }
        }
    }

    private JSONArray pareseStringToJSAray(String s) throws JSONException {
        return new JSONArray(s);
    }


}
