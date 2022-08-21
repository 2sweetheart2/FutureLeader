package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout;

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
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class UsersProjects extends Her {
    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/wvvr9t3drtyKvTRWlRduWgpn6T1r_SK9sfCYgtrTdXMOsNktPCfUF7rZO4lKMxxHLdDqHTcz3_b49vpYjBndIw.png",
                "https://future-leaders.ru/resuorces/others/GGh5dMMCgktDiELbDEl5om1Bis-4Tkr04V156G0d0R4ujf9Ou8yiPl0LkKK9T5PpEupLhl8RyXlbaWTMo6E-Jw.png",
                "https://future-leaders.ru/resuorces/others/JCFwcMnMhfzavY7lZ_T8BSEJhR6qWwfj1WNvjI2ofDNbcJNqwaDYKvZ3Zmv6juHMfJSrDmgtyEXzqsOnZGFOyg.png"
        );
        for (int i = 0; i < urls.size(); i++) {
            int column = 0;
            if (i % 2 != 0)
                column = 1;
            int row = i / 2;
            boolean onAllColumn = false;
            if(i==2) onAllColumn = true;
            ImageView v = grid.addImageElement(null, onAllColumn);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), v, this);
        }
        setTitle("Будущие Лидеры");
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Лидерская зимняя серия игра \"Что? Где? Когда?\"");
            intent.putExtra("id",-4);
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","О проекте");
            intent.putExtra("id",-3);
            startActivity(intent);
        });
    }

}