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
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.SocSeti;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class LiceumLayout extends Her {
    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/mNBXEa9Vr4Z11wA23f4DOTXSq5wvRyrxeU0EVixJ1jVcriG_qljT3iOSbsdQy9T0N_tryELAEuviZ50qrdTu8A.png",
                "https://future-leaders.ru/resuorces/others/bKnyPUWLbESBA8sMIOBwLCCjs51z7GNjfQaEr1hfhzDDRvqKKXLuutUyxO1XaBuXdZaSwUEVRhbpvPxJy9cvbg.png",
                "https://future-leaders.ru/resuorces/others/iuZGAsSfVW5E-T86ZOFZtvsgebfZt3JLWEtpgFRnmyQwJ35PrCUoyCFP-az2pCZjp9mhYu3VPfeZIpZU-vKq6A.png",
                "https://future-leaders.ru/resuorces/others/oiYup5WmHgxPF9S8qJVilGn-rGRo8MaUTSGgwi7SQbM-0nrbCQEuCNq-XyVYAUYRCN5ZsVHJwNtniT0_4pmRPA.png"
        );
        for (int i = 0; i < urls.size(); i++) {
            ImageView v = grid.addImageElement(null, false);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), v, this);
        }
        setTitle("Лицей Будущих Лидеров");
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","О проекте");
            intent.putExtra("id",-70);
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(this, Moscov.class);
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v -> {
            Intent intent = new Intent(this, Blagoveshensk.class);
            startActivity(intent);
        });
        views.get(3).setOnClickListener(v -> {
            Intent intent = new Intent(this, SocSeti.class);
            intent.putExtra("title","Соцсети");
            intent.putExtra("urls",new ArrayList<>(Arrays.asList(
                    "https://future-leaders.ru/resuorces/others/Oaok4-DmdhjiNqaNLh-KIYOsIhBK0LiVuQFQRGW4NMG34sWBcH4t1ILv3fl4bfEM6PxFpRKTU8sklrbFZ0KRJg.png")));
            intent.putExtra("names",new ArrayList<>(Arrays.asList(
                    "Вконтакте"
            )));
            intent.putExtra("redirect",new ArrayList<>(Arrays.asList(
                    "https://vk.com/lyceum_future"
            )));
            startActivity(intent);
        });
    }
}