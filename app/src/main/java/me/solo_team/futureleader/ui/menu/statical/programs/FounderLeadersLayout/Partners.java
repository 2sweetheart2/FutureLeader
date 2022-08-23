package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.MenuGrid;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class Partners extends Her {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra("title"));
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = getIntent().getStringArrayListExtra("urls");
        List<String> names = getIntent().getStringArrayListExtra("names");
        List<String> urlNames = getIntent().getStringArrayListExtra("url_names");
        List<String> redirects = getIntent().getStringArrayListExtra("redirects");
        List<Integer> ids = getIntent().getIntegerArrayListExtra("ids");
        for (int i = 0; i < urls.size(); i++) {
            ImageView v = grid.addImageElement(null, false);
            Constants.cache.addPhoto(urls.get(i), v, this);

            int finalI = i;
            v.setOnClickListener(v1 -> {
                Intent intent = new Intent(Partners.this,Mer.class);
                intent.putExtra("urls",new ArrayList<>(Arrays.asList(urlNames.get(finalI),urls.get(finalI))));
                intent.putExtra("name",names.get(finalI));
                intent.putExtra("id",ids.get(finalI));
                intent.putExtra("redirect",redirects.get(finalI));
                intent.setData(Uri.parse(names.get(finalI)));
                startActivity(intent);
            });
        }
    }
}