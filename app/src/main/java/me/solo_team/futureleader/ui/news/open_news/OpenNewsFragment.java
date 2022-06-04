package me.solo_team.futureleader.ui.news.open_news;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class OpenNewsFragment extends Her {
    String news;
    JSONObject oldJson;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_open);
        setTitle(getIntent().getStringExtra("tag"));

        API.getNew(new ApiListener() {
                       @Override
                       public void onError(JSONObject json) {

                       }

                       @Override
                       public void inProcess() {

                       }

                       @Override
                       public void onSuccess(JSONObject json) throws JSONException {
                           JSONObject new_ = json.getJSONObject("new");
                           String objects_ = new_.getString("objects");
                           new_.put("objects",pareseStringToJSAray(objects_));
                           JSONArray objects = new_.getJSONArray("objects");
                           Constants.newsCache.curentNew = new_;
                           oldJson = new_;
                           runOnUiThread(() -> {
                               try {
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
            if(Constants.newsCache.curentNew==null) return;
            draw(Constants.newsCache.curentNew.getJSONArray("objects"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void draw(JSONArray objects) throws JSONException {
        runOnUiThread(()->((LinearLayout) findViewById(R.id.news_open_list)).removeAllViews());

        for (int i = 0; i < objects.length(); i++) {
            JSONObject o = new JSONObject(objects.getString(i));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 2, 0, 2);
            switch (o.getString("type")) {
                case "text":
                    TextView textView = new TextView(getApplicationContext(), null);
                    textView.setTextColor(Color.BLACK);
                    textView.setLayoutParams(lp);
                    textView.setText(o.getString("value"));
                    runOnUiThread(() -> ((LinearLayout) findViewById(R.id.news_open_list)).addView(textView));
                    break;
                case "photo":
                    ImageView imageView = new ImageView(getApplicationContext(), null);
                    lp.height = 200;
                    lp.weight = 200;
                    imageView.setLayoutParams(lp);
                    lp.gravity = Gravity.CENTER_HORIZONTAL;
                    Constants.cache.addPhoto(o.getString("value"),true,imageView,OpenNewsFragment.this);
                    runOnUiThread(() -> ((LinearLayout) findViewById(R.id.news_open_list)).addView(imageView));
                    break;

            }
        }
    }

    private JSONArray pareseStringToJSAray(String s) throws JSONException {
        return new JSONArray(s);
    }

//    @SuppressLint("ResourceType")
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        if (Constants.user.adminStatus != 0) {
//            if (menu.size() == 0)
//                menu.add(0, 1, 0, "")
//                        .setIcon(R.drawable.plus)
//                        .setOnMenuItemClickListener(item -> {
//                            Intent intent = new Intent(OpenNewsFragment.this, EditNews.class);
//                            intent.putExtra("new", news);
//                            startActivity(intent);
//                            return true;
//                        })
//                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//        } else menu.removeItem(1);
//        return super.onPrepareOptionsMenu(menu);
//    }

}
