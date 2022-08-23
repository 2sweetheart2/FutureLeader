package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.SchoolFutureLeaders;

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

public class Sochi extends Her {

    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/GhjfpI73op00PRXDkoJkE3gED_6Od4NIznS1jYMUqq2DdEiUhOSTvT_9hlQA8QJ1qCkd-F16jbUCffoX6WeZMQ.png",
                "https://future-leaders.ru/resuorces/others/8Jm7rMUOM3ksT2gis6hyRSXPqGFgrUZ9YQbPu_VHCgrYeS3vtOAu4nrHJW2oNc9TbwDxwBiLH4VCDCnYPIv-NQ.png",
                "https://future-leaders.ru/resuorces/others/HTQ_VjxFP4PcNsnYFVSGVRv7UMl085kFAaGoMarZVCmUryfB9PrHK3pmopEvLuZ_eoWq-CqS5_alAmWNx2v48g.png"
        );
        for (int i = 0; i < urls.size(); i++) {
            ImageView v =  grid.addImageElement(null, false);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), v, this);


        }
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, MeropRiytia.class);
            intent.putStringArrayListExtra("urls", new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/u1ZoMesrevO-pWmR07csCRHhnckUeQe9OqOTEklxPZZ5cllKeOi4LljqgLeEzOYVdWGzByWcf4elPTGDsq9dqQ.png"
            )));
            intent.putExtra("names", new ArrayList<>(Arrays.asList(
                    "Первая лекция образовательной программы \"Школа Лидеров будущего\" подошла к концу!"
            )));
            intent.putExtra("ids",new ArrayList<>(Arrays.asList(
                    -63
            )));
            intent.putExtra("title", "Лекции");
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Открытие Школы Лидеров Будущего!");
            intent.putExtra("id",-65);
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Кейс турнир");
            intent.putExtra("id",-64);
            startActivity(intent);
        });
        setTitle("Сочи");
    }
}