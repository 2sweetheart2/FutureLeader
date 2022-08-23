package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.MenuGrid;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class MeropRiytia extends Her {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra("title"));
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = getIntent().getStringArrayListExtra("urls");
        List<String> names = getIntent().getStringArrayListExtra("names");
        List<Integer> ids = new ArrayList<>();
        if(getIntent().hasExtra("ids"))
            ids = getIntent().getIntegerArrayListExtra("ids");
        boolean youTube = getIntent().getBooleanExtra("you_tube_link", false);
        List<Integer> finalIds = ids;
        for (int i = 0; i < urls.size(); i++) {
            ImageView v = grid.addImageElement(null, false);
            Constants.cache.addPhoto(urls.get(i), v, this);
            int finalI = i;
            if (!youTube) {
                v.setOnClickListener(v1 -> {
                    Intent intent = new Intent(this, OpenNewsFragment.class);
                    intent.putExtra("tag", names.get(finalI));
                    intent.putExtra("id", finalIds.get(finalI));
                    startActivity(intent);
                });
            }
            else
                v.setOnClickListener(v1 -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(names.get(finalI)));
                    startActivity(intent);
                });
        }
    }
}
