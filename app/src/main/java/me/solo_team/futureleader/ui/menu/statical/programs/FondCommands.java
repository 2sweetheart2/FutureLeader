package me.solo_team.futureleader.ui.menu.statical.programs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.MenuGrid;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.Partners;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.PartnersC;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class FondCommands extends Her {

    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/qnuCkz3xgwd42iBfqanTvvo4FV0it6OzLdKOPnOV4oZDBXgc5KOgDy6wpbOnSYQB9piovvGioKM63BxhhIDJuA.png",
                "https://future-leaders.ru/resuorces/others/6oUzl7-8S1n-1MDLNq7Sy7Z2i428ZYIdLB32qp7g9wo-dzPuc44rrRltrpOKlqn-MC1jSRr3bernqeroWF6c9A.png",
                "https://future-leaders.ru/resuorces/others/uEvwO8ZIjhIZqEEz-7RJWQBbPI_Qd6UR_eThjoLArhf8SCCOXHIEmQDeqnrkRU_YQgjxjnn4USwBM4X2PMeb4A.png",
                "https://future-leaders.ru/resuorces/others/FH7U6l2OmbPPPtD1dM7asdrZGIF_lvrpPE3RaiMuog8VqmeoZM7IDpz0mw8K6u4p9rWoFIT4PjvI-YWqdbt1wQ.png"
        );
        for (int i = 0; i < urls.size(); i++) {
            ImageView v = grid.addImageElement(null, false);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), v, this);
        }
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "Руководство Фонда");
            intent.putExtra("id", -81);
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(this, PartnersC.class);
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "Попечители");
            intent.putExtra("id", -82);
            startActivity(intent);
        });
        views.get(3).setOnClickListener(v -> Utils.ShowSnackBar.show(FondCommands.this, "Страница не найдена!", v));
        setTitle("Команда Фонда");
    }
}
