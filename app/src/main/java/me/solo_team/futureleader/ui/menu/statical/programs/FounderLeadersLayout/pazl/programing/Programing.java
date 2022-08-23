package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.pazl.programing;

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

public class Programing  extends Her {
    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/JU8FJRkdpmWtqm-ZcRJT8A8cv_vn3PPTU0kawzOsa75B9MOrCIf99Z8bNbnObcowUlaLswBN3ZDWa3lLPv9v3A.png",
                "https://future-leaders.ru/resuorces/others/2RGSeELfoE9ALFSrmMrQGMnwczeGpMhbTjCDtopfxD7XCFbDNczuob6hRkx3n4bvmg1wg_T-hGGga9dSLa-cCA.png",
                "https://future-leaders.ru/resuorces/others/j53xZtPwQU2Lf8fru5jpr4vRz_y-2wvQmvrZ6v05jJdBNE0Xr2hGxrJBvfs-qnuI6opKj8-bposcqqkKAiCuPQ.png"
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
        setTitle("Программирование");
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "О проекте");
            intent.putExtra("id", -44);
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "Стажировка топ-18 СЗФО");
            intent.putExtra("id", -45);
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
