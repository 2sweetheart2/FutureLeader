package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout;

import android.content.Intent;
import android.net.Uri;
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

public class PartnersC extends Her {
    List<View> views = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Партнёры");
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList("https://future-leaders.ru/resuorces/others/8aFR9bIFUlXwh2ZeVx3Idfuv-NMaUD0ECRj4lpT_n1HiYBtfwbQ5lvQk2ApbHVhPdsLShzRzzGZqZ7HuZfq4-g.png",
                "https://future-leaders.ru/resuorces/others/Nh9QQEbdQ5LbTnuGMQ0oE5qZ9WXDTZ6rp21h2we70DH2EjmbfUg7vSUrOiAMYdCCxfshRfC0Dszne4nS_bQMIg.png",
                "https://future-leaders.ru/resuorces/others/O2eoz2e0yB6CHoEJRbYg-uQo0xE7_zihYhphIW7hVeYGee-CyyQpNXJSyz3sZTNtcaTwYREzz1LPfFV-JvHWRg.png",
                "https://future-leaders.ru/resuorces/others/GjJSS3x8Sxb-u9R7aBfMSewZdrDm1Bi5d9zGiuRs7I9DaDzowqpRTbYkKusDfjWYTM7S-f8Tjh-MjF-sAElGyg.png",
                "https://future-leaders.ru/resuorces/others/a_P-EGH8rAa-MXtXAtbk9TBw5dd2JgFn-fa2z6GnZ6LM2Kaa7NxL3LmfcGcpe3hHjGCrdCqdHMZo5oqt6jG3pQ.png",
                "https://future-leaders.ru/resuorces/others/8URHC04N7VaspkY45ucABJsXXq2T8rIAb9ZIp66LRrTKI6LDYCQV_F850CFnhDlajxt_vlUu_FGCpW2SlTtvww.png");
        for (int i = 0; i < urls.size(); i++) {
            ImageView v = grid.addImageElement(null, false);
            Constants.cache.addPhoto(urls.get(i), v, this);
            views.add(v);
        }
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(PartnersC.this,Mer.class);
            intent.putExtra("urls", new ArrayList<>(Arrays.asList("https://future-leaders.ru/resuorces/others/QFyXWPfRxWQRYYhdRJtXRIKdPv2qRwMcvvbFkmep88w3xNCsX4oAns0Szlp4YvjzIj7JTu2y69efj9cKxkbkMQ.png", (urls.get(0)))));
            intent.putExtra("name","encore|Сумбат Оганов");
            intent.putExtra("id",-83);
            intent.putExtra("redirect","https://encore.ru/");
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(PartnersC.this,Mer.class);
            intent.putExtra("urls", new ArrayList<>(Arrays.asList("https://future-leaders.ru/resuorces/others/gv6oWEAAQukDaLxykm1DIVHi7ggxyM4ImIyA_-1viXSPAcckkplxjBJKuPX3vl5e-qUwYrZZ0jHQFkV4nY2dag.png", (urls.get(1)))));
            intent.putExtra("name","RBI|Эдуард Тиктинский");
            intent.putExtra("id",-84);
            intent.putExtra("redirect","https://www.rbi.ru/");
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.585zolotoy.ru/"));
            startActivity(intent);
        });
        views.get(3).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "Бизнес-клуб");
            intent.putExtra("id", -85);
            startActivity(intent);
        });
        views.get(4).setOnClickListener(v -> {
            Intent intent = new Intent(PartnersC.this,Mer.class);
            intent.putExtra("urls", new ArrayList<>(Arrays.asList("https://future-leaders.ru/resuorces/others/qVy2XKew5Ex3FgEwy4Gj3mc09GIjwaAv2ZQ9rIE4DV1ECOjpbRwWKf9E9Vor-OWDGCAp8x6coaq6QBo9rMnlcA.png", (urls.get(4)))));
            intent.putExtra("name","НОСТРОЙ|Никита Загускин");
            intent.putExtra("id",-86);
            intent.putExtra("redirect","https://www.nostroy.ru/");
            startActivity(intent);
        });
        views.get(5).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "Стать партнёром");
            intent.putExtra("id", -87);
            startActivity(intent);
        });
    }
}