package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.pazl.stroy;

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
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.SocSeti;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class Stroy extends Her {
    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/1VQFKBqw12mLCl3tHXZAi-0U7UsIlpO2OoHWRWQ8cnMR_lsEACX-iNpuYYcCCRoOlmMSc_jETqYoHKPoNDj_gA.png",
                "https://future-leaders.ru/resuorces/others/SEppl1HRReuQVncrfco5EHxMBabwzwN05CdcxeRIhUv7_jk7D4bjiz--KJm4TeSfp3tdMNqF_bF8DE9OK27UMA.png",
                "https://future-leaders.ru/resuorces/others/xGENMZU68wZrhp0O-p7PUtDZtzRxEhdThh0bjkJjvhWXudqV_BrdRZfvuPk2eUL0dqRYZAiJ46Blj7Z9ML753A.png"
        );
        for (int i = 0; i < urls.size(); i++) {
            ImageView v;
            if (i == 2)
                v = grid.addImageElement(null, true);
            else
                v = grid.addImageElement(null, false);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), v, this);
        }
        setTitle("Строительство");
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "О проекте - ПАЗЛ. Строительство");
            intent.putExtra("id", -40);
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(this, MeropRiytia.class);
            intent.putStringArrayListExtra("urls", new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/nviylPTXWh-G1qNfiJryNYo6-2EPOP3nyKlmthP2mA7PRYu_pclXfMuXKy7SH8RBsXHOA2iWsNvK4meZyd1kNg.png",
                    "https://future-leaders.ru/resuorces/others/eStr-3zb7EAjbv4PCQRnVeuu3d8ff6K4-HGhnHHiAn1Pw-pH_uQoehpDHc6JmM5jUHyvYzYnrlOghpzCy1KdoQ.png",
                    "https://future-leaders.ru/resuorces/others/paMplowQw2nz-APFcEBng2T6CX8zbYA6Cu3FoRw6phcrUGoAV62UesNA6WTFnolDbId_8UmbNqUKnxcT-ECFqA.png"
            )));
            intent.putExtra("names", new ArrayList<>(Arrays.asList(
                    "Занятие в колледже Метростроя",
                    "Занятие в СПбПУ",
                    "Занятие на полигоне \"Умный Труд\""
            )));
            intent.putExtra("ids",new ArrayList<>(Arrays.asList(
                    -41,-42,-43
            )));
            intent.putExtra("title", "Наши мероприятия");
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v -> {
            Intent intent = new Intent(this, SocSeti.class);
            intent.putExtra("title", "Соцсети ПАЗЛ. Строительство");
            intent.putExtra("urls", new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/QUdzUXn4APTdGvHQuW6ipqeyuRMHbNRWFXCWyYyryFoqLMXtvFOQQwiZAwmXvGrOBGnROEONFmm6igaaD9fbjQ.png")));
            intent.putExtra("names", new ArrayList<>(Arrays.asList(
                    "Вконтакте")));
            intent.putExtra("redirect", new ArrayList<>(Arrays.asList(
                    "https://vk.com/puzzle_construction2020")));
            startActivity(intent);
        });
    }
}