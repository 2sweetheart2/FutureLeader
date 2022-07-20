package me.solo_team.futureleader.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.MainActivity;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.News;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class NewsFragment extends Fragment {


    private LayoutInflater inflater;
    private ViewGroup container;
    private LinearLayout nw;
    JSONArray news;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        requireActivity().invalidateOptionsMenu();
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        nw = root.findViewById(R.id.news_list);
        this.inflater = inflater;
        this.container = container;
        root.findViewById(R.id.news_btn_add).setOnClickListener(v -> {
            if(isEnd()) Snackbar.make(v, "Больше нет новостей", Snackbar.LENGTH_LONG).show();
            else offset++;
        });
        if(Constants.newsCache.news.length()==0) {
            API.getNews(new ApiListener() {
                @Override
                public void onError(JSONObject json) {
                    System.err.println(json.toString());
                }

                @Override
                public void inProcess() {

                }

                @Override
                public void onSuccess(JSONObject json) throws JSONException {
                    System.out.println(json.toString());
                    news = json.getJSONArray("news");
                    Constants.newsCache.news = news;
                    createClick();
                }
            }, new CustomString("token", Constants.user.token));
        }
        else {
            try {
                news = Constants.newsCache.news;
                createClick();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return root;
    }

    private void createClick() throws JSONException {
        for (int i = news.length()-1; i >= getLastIndex(); i--) {
            JSONObject o = news.getJSONObject(i);
            News new_ = new News(
                    o.getInt("id"),
                    o.getString("photo"),
                    o.getString("title"));
            requireActivity().runOnUiThread(() -> addElement(new_.photoUrl, new_.title).setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), OpenNewsFragment.class);
                intent.putExtra("tag", new_.title);
                intent.putExtra("id", new_.id);
                startActivity(intent);
            }));
        }
    }

    private boolean isEnd(){
        return offset*10>news.length();
    }
    private int offset = 1;
    private int getLastIndex() {
        if(news.length()>10){
            return news.length()-1-(10*offset);
        }else{
            return 0;
        }
    }

    private View addElement(String uri, String name) {
        ConstraintLayout row = (ConstraintLayout) inflater.inflate(R.layout.news_news, container, false);
        ConstraintLayout cn = row.findViewById(R.id.news_obj);
        Constants.cache.addPhoto(uri, true, (ImageView) cn.getChildAt(1), this);
        ((TextView) cn.getChildAt(2)).setText(name);
        switch (MainActivity.wightwindowSizeClass) {
            case COMPACT:
                ((TextView) cn.getChildAt(2)).setTextSize(12f);
                break;
            case MEDIUM:
                ((TextView) cn.getChildAt(2)).setTextSize(15f);
                break;
            case EXPANDED:
                ((TextView) cn.getChildAt(2)).setTextSize(20);
                break;
        }
        nw.addView(row);
        return row;
    }


}