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
import me.solo_team.futureleader.ui.menu.statical.Founder.ContactLayout;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.SocSeti;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.future_leaders.Layout;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.users_projects.UsersProjects;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class SchoolFutureLeaders extends Her {

    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/bQSyxxi87QbzcMxHbrCTsj9MVxKNiU8XUTo46FaSs_ndislgzQMDSkqjJT60h3KOoCVoDDfmC_jxcEOakrRMfA.png",
                "https://future-leaders.ru/resuorces/others/CPqlxBhr7RXwEKKMsrnHMqZeztdk0cNnvNnmGeAurjk6T48_DZHU0RElD4ncu2mvFNsl4hXRpUIxjQNrt3u1wg.png",
                "https://future-leaders.ru/resuorces/others/pb-s80hbXTzHJApquzbhIOkwcWowXnSG847cIDp8HU14FQs2kMLUTyNVGvZHM4GJMqFZAmpIvwrZm91XXyZSoQ.png",
                "https://future-leaders.ru/resuorces/others/P0YObSt-ydTFA6ElKZQxUSzN8UJyj5fto7gHwplKmwumKVd9loY6QYYJioKE5sNgLtJb-23cd6sQ99H5yNgN2w.png"
        );
        for (int i = 0; i < urls.size(); i++) {
            ImageView v = grid.addImageElement(null, false);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), v, this);


        }
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","О проекте");
            intent.putExtra("id",-47);
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(this, piter.class);
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v -> {
            Intent intent = new Intent(this, Sochi.class);
            startActivity(intent);
        });
        views.get(3).setOnClickListener(v -> {
            Intent intent = new Intent(this, SocSeti.class);
            intent.putExtra("title","Соцсети");
            intent.putExtra("urls",new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/i4zbLDzom9qbYteGJs0eV6W0HhTj0nAnSp6Mv2Wk4-Dd74JPbk3GikJYtXstO2VAWapyLrDgqHgYED29_G-H7A.png",
                    "https://future-leaders.ru/resuorces/others/Wi_l0-2rTe9RI8PS-AwBqb9mj1Zysh1ihcbfw3Yx_q8IRypk_fbnCAjIWirPm9vAfUtgE8IiqgBLBJ9azGIPHg.png"
            )));
            intent.putExtra("names",new ArrayList<>(Arrays.asList(
                    "YouTube",
                    "сайт"
            )));
            intent.putExtra("redirect",new ArrayList<>(Arrays.asList(
                    "https://www.youtube.com/channel/UCYHnTGpe0e7BM8GAZKkJ9fw",
                    "http://fbl.rbi.ru/"
            )));
            startActivity(intent);
        });
        setTitle("Программы");
    }
}
