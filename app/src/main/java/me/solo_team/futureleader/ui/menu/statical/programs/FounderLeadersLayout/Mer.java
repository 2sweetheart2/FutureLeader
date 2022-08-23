package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.List;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.MenuGrid;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class Mer extends Her {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra("title"));
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = getIntent().getStringArrayListExtra("urls");
        String name = getIntent().getStringExtra("name").split("\\|")[1];
        setTitle(getIntent().getStringExtra("name").split("\\|")[0]);
        Integer id = getIntent().getIntExtra("id", -1);
        String redirect = getIntent().getStringExtra("redirect");

        ImageView v = grid.addImageElement(null, false);
        Constants.cache.addPhoto(urls.get(0), v, this);
        v.setOnClickListener(v1 -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag",name);
            intent.putExtra("id",id);
            startActivity(intent);
        });

        ImageView v2 = grid.addImageElement(null, false);
        Constants.cache.addPhoto(urls.get(1), v2, this);
        v2.setOnClickListener(v1 -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(redirect));
            startActivity(intent);
        });

    }
}