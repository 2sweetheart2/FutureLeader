package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.pazl;

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
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.SocSeti;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.future_leaders.Contacts;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.future_leaders.Meropriyatia;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.pazl.prof.Prof;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.pazl.programing.Programing;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.pazl.stroy.Stroy;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class PazlLayout extends Her {
    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/MzZ2PG6VCPSm2lt2hVC-Y0Wp0AZKpFybys9Rptwe7BjynCFZWgwuuRsIR00blW3UOopE9QasC0Ru34a7M3fqUw.png",
                "https://future-leaders.ru/resuorces/others/k9GEvrCYvvj_sjj4sqsBLNCZ91PygjfvodoVnEZUYDZg9LUbcWce8MNS8tZXL4PV64rp_AZ7LPtfTBQXZtlV5A.png",
                "https://future-leaders.ru/resuorces/others/yivAx0dPL3HM6-AmBbxqq9BHWtAg1k5_CKy03EkTai3_S2g9iCiL1w_PwPx7bPoV4OWXRImCJASfAPQU3Vt5Xw.png",
                "https://future-leaders.ru/resuorces/others/E9dgXxwHocUTrWgUqMhmo8wqk3SGuC7pXGqDYcbXxa10dWaq1Tje-M5ISdbpU7dES9ap_U9d1Hy9AjMYoDDMgA.png",
                "https://future-leaders.ru/resuorces/others/8drH-Aqf_NLOHYWWUMdv8xGBHieLUsR59Bhi7E4G7kV-btvp8_IXm8q6oFiYXJ-wvgtaZrJXNKIOM1jmhveK9w.png",
                "https://future-leaders.ru/resuorces/others/hVUziB92bZKUd34rCbtcV7E4fiXJivwunpCIZWvMhDj42gzSpT1vkaqAK9wNnLaY9kZ5MPEWxVjhunZGqkriuQ.png"
        );
        for (int i = 0; i < urls.size(); i++) {
            ImageView v = grid.addImageElement(null, false);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), v, this);
        }
        setTitle("Пазл");
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","О проекте");
            intent.putExtra("id",-38);
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(this, Prof.class);
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v -> {
            Intent intent = new Intent(this, Stroy.class);
            startActivity(intent);
        });
        views.get(3).setOnClickListener(v -> {
            Intent intent = new Intent(this, Programing.class);
            startActivity(intent);
        });
        views.get(4).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Парус");
            intent.putExtra("id",-46);
            startActivity(intent);
        });
        views.get(5).setOnClickListener(v -> {
            Intent intent = new Intent(this, SocSeti.class);
            intent.putExtra("title","Соцсети");
            intent.putExtra("urls",new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/_wklmU96N_alMPQWBGsFLJtdVUZgMjpPWtTBgttONn7rXslMPA1jZKOcHXtHQ2_uFpA_seQj0mjOil491QaKzw.png",
                    "https://future-leaders.ru/resuorces/others/FYv6yjuSajTrNiObSDDSrUkfgdFAikjBBXx7MlC6d02XnVqLG51q_wmlUiNnPH_Xw-ckn7l7cy6Ki4AStTrkWQ.png"
            )));
            intent.putExtra("names",new ArrayList<>(Arrays.asList(
                    "Вконтакте",
                    "сайт"
            )));
            intent.putExtra("redirect",new ArrayList<>(Arrays.asList(
                    "https://vk.com/puzzle_place",
                    "https://www.puzzleplace.ru/"
            )));
            startActivity(intent);
        });
    }
}
