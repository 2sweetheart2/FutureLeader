package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.Liceum;

import android.content.Intent;
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
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.MeropRiytia;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class Blagoveshensk extends Her {
    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/Q_3_ApfxNOqXofz7aFMvlK-0KA0uObwyD6PyF8imZYhm83MZRIKSMQkVFlvGZQ63hBOrHl8sIUNyAiebCjtQ2g.png"
        );
        for (int i = 0; i < urls.size(); i++) {
            ImageView
                    v = grid.addImageElement(null, false);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), v, this);
        }
        setTitle("Благовещенск");
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Отборочный кейс-чемпионат Lego Education");
            intent.putExtra("id",-78);
            startActivity(intent);
        });
    }
}
