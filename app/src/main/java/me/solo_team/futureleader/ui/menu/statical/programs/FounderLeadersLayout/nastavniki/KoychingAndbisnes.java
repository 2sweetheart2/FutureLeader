package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.nastavniki;

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

public class KoychingAndbisnes extends Her {
    List<ImageView> views = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Коучинг и личный бренд");
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
            "https://future-leaders.ru/resuorces/others/nEHd4I0d_jattIfxqFeMPvggMhx6xpEJPGyd0HsFsHE82nKt1VtPd-ddqQ030idK5Ppx4RpZHWw10G4sEKRsLg.png",
                "https://future-leaders.ru/resuorces/others/0Q5yoemp2UQ0p6ZjdG6eajU5nGLwRYZdbGfMqwDt73FYex-RoKoBkSFq-LUHeQfiLZ9S7Ksfmvzkz-hLjYwzRQ.png",
                "https://future-leaders.ru/resuorces/others/r9Db7zQF7tdInonIy7sFX-KNI_qp7iRW38ZSm2VsMnwmDb3uMJvQv80NxNZR9gOJVtgy6R8U3DYTgVVW1JfQ9w.png",
                "https://future-leaders.ru/resuorces/others/sqQ5af1zbA0MneoE1bsiUHbFUe3CSCaxhcw21cZpggKTFdzbG9vdHYzWF45nO4-nfOKm0pSloqo56KwSL7ekfg.png"
        );
        for (int i = 0; i < urls.size(); i++) {
            ImageView
                    v = grid.addImageElement(null, false);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), v, this);
        }
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Анна Захарова");
            intent.putExtra("id",-107);
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Ольга Бут");
            intent.putExtra("id",-108);
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Армен Манукян");
            intent.putExtra("id",-109);
            startActivity(intent);
        });
        views.get(3).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Ламара Карчава");
            intent.putExtra("id",-110);
            startActivity(intent);
        });
    }
}
