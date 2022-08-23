package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.users_projects.flip;

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
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.future_leaders.Layout;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.users_projects.UsersProjects;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class FlipLayout extends Her {

    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/X8dBze6cVNoLhS5ZzNdDb5t9CUCARXhNbD0daejtb1GZPcVTJrTZkEvFPStCgLeHxecdpAByAs4BI6nRkF4J6g.png",
                "https://future-leaders.ru/resuorces/others/rYngHrzcbw3XO4b0vdkAWOebxtNmKm_7u2bE2EoBr_oQkvDpJMFzp0QwI7PVGP2W_i5Yslg3OAcwoLrkePgAaQ.png",
                "https://future-leaders.ru/resuorces/others/ApNxJ4Mo-DZ_fy7K1E-8rmKpTc_9T4N3jK63XM3TUwMAc7260ZpCSI2K7Tqn44zIESaD2nGcyoythH8XyzzEKg.png",
                "https://future-leaders.ru/resuorces/others/Z-Xe8yMiYyggCVq7x9pRLXpI7dnfmzABb8YnUeddk0afZnj_91FC8W8Ko-kCGQR8rcQ5TzeR7w5WhGsyEurwig.png",
                "https://future-leaders.ru/resuorces/others/SxaW7Mc8US2is3e3NMYB_9GLoF2dRFBBt6Fw9zVe8Nup6vZl-sp6oYvHdwAYPcmYwbR0F_80zvHfKY2BjfcxWw.png",
                "https://future-leaders.ru/resuorces/others/qBeprOyD6ds7R7kBDsWllSGVsBpbOeBgFJh4avoiWDLM2augls-OvnavFzXIEpWm5sKfQq-hUN_M_fg8UxcXvw.png",
                "https://future-leaders.ru/resuorces/others/-GeUQOEUKZYeBZqf1ESa1i1TzqqD90i7rhxck4hs3Bm1t3YQndGaAy2LS114kFTaQOdRfcv_S3CRRnFQsWQyDg.png",
                "https://future-leaders.ru/resuorces/others/3Y-GPneiZisDQr_hZpuuvZJzHqYqiVHEnztOh0Yrxcbk0AtawOwseHYquYeoL1Yoxwd0lmNgQG_Ap8kKdPQbMw.png",
                "https://future-leaders.ru/resuorces/others/1ZUyCaSrz0zwV-oHMcI3MFm7gKBsTsiJsSlRPksp9Z6Ts3bVgBlcypLX_GD1-PmAMczjrs9_0M2GkjQ_t2YMow.png",
                "https://future-leaders.ru/resuorces/others/bx3P7IuWecNSySZd3SoqZCUCF6Xssxb4ZBPs4KWWuT5OiAOM5-Mi3_ZTJneZNOBvcGsRzd63JwcZx5b6IzO6SA.png"
        );
        for (int i = 0; i < urls.size(); i++) {
            ImageView v = grid.addImageElement(null, false);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), v, this);
        }
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "Проект Александра Лазарева");
            intent.putExtra("id", -28);
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "Проект Анны Поповой");
            intent.putExtra("id", -29);
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "Проект Игоря Баранова");
            intent.putExtra("id", -30);
            startActivity(intent);
        });
        views.get(3).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "Проект Давида Геворгяна");
            intent.putExtra("id", -31);
            startActivity(intent);
        });
        views.get(4).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "Проект Даниила Лазуткина");
            intent.putExtra("id", -32);
            startActivity(intent);
        });
        views.get(5).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "Проект Елены Деменковой");
            intent.putExtra("id", -33);
            startActivity(intent);
        });
        views.get(6).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "Проект Михаила Гутникова");
            intent.putExtra("id", -34);
            startActivity(intent);
        });
        views.get(7).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "Проект Никиты Потравко");
            intent.putExtra("id", -35);
            startActivity(intent);
        });
        views.get(8).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "Проект Олеси Оболонковой");
            intent.putExtra("id", -36);
            startActivity(intent);
        });
        views.get(9).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "Проект Александра Войноловича");
            intent.putExtra("id", -37);
            startActivity(intent);
        });
        setTitle("Интеллектуальные проекты Будущих лидеров");
    }
}