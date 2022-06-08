package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class Layout extends Her {
    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/wv2vhjWEV9V30YEf1IO_ailL1ruWgii_DewdH4brYhsmZ1Q7__M1JBl8JRBeFWc6b65ckp44GYgAT7IVE_gMvQ.png",
                "https://future-leaders.ru/resuorces/others/EnZXGZoaxKmdsAqH28FqVkXlv4pRLRtTMl-Hhj9Ia-RTxatkMJXj2ErBkYpjkilyhCK2o-LEMphQ8-qt7ceQmQ.png",
                "https://future-leaders.ru/resuorces/others/JCFwcMnMhfzavY7lZ_T8BSEJhR6qWwfj1WNvjI2ofDNbcJNqwaDYKvZ3Zmv6juHMfJSrDmgtyEXzqsOnZGFOyg.png"
        );
        for (int i = 0; i < urls.size(); i++) {
            int column = 0;
            if (i % 2 != 0)
                column = 1;
            int row = i / 2;
            boolean onAllColumn = false;
            if(i==2) onAllColumn = true;
            ImageView v = grid.addImageElement(null, onAllColumn, row, column);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), true, v, this);
        }
        setTitle("Будущие Лидеры");
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","О проекте");
            intent.putExtra("id",5);
            startActivity(intent);
        });
    }

}
