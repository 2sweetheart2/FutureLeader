package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.future_leaders;

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

public class Meropriyatia extends Her {
    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/d4enkyek3_TmZ-9L1bMe488YLMqhdVob5MyL5H-aZ4scAq6B0a4FwCUjW998IDql2hSvxYf1nPucuDPjGYIHMA.png",
                "https://future-leaders.ru/resuorces/others/0sSr8qvYDlyL5MiCh_iVtLFBTMbmwIi3nZGndP5eD1QQFGoS-PnFns9JcZJJxXz3LMcM8fgykU6lVsRVcMScoA.png",
                "https://future-leaders.ru/resuorces/others/A1LRutb2j1ZfCuvvCw4dSlF9JygQMxFjL345KFTcGvIKrNPw1pFhagFWvJKP_7ytWyvrbu75db229uj1goeBWA.png",
                "https://future-leaders.ru/resuorces/others/Tt7qsK662wf2pFyJhGkzDna1njnM9daOJtUg5eiQY_-JxFHR219bTgrNULVDFS0ZMkk815AIamNh1h7TFObqEg.png",
                "https://future-leaders.ru/resuorces/others/5bNZ0xI_unySdRuSU-ysgzaviGVI0n8aIZyJdOABlsGA52VX8PjFtX7UWnMbBE0u8uM8Vvcwd92VAKTCB0zvmA.png",
                "https://future-leaders.ru/resuorces/others/tu0fDLWPlOFiWbfpSFD8RMdMHrJwQX9OHhV0fAbcdICoPPzrRAmY6XIY1p3JIlBvRSrhvkjwbxuqeZ-H9tSldQ.png",
                "https://future-leaders.ru/resuorces/others/SkPC9aNFGThd97PuLKW3Q9vPPZKgibT0NKIx7LGFNmPw1aqN9pW8STGRE5N0mVnEzb7BUXpS6Q91yAmLA2m6dw.png",
                "https://future-leaders.ru/resuorces/others/DmnZ6fEAmJ5RpMDpN8kodBWDzjwTBcvKrSMpgfpop4MbfE-_cBzHw5HTCsN-qoTdXMouEqo4hd99f7DNqjHPgw.png",
                "https://future-leaders.ru/resuorces/others/xHvxoiigYTJvu4ujq5RouC7xb0fshQ69N4X_H9V7srsFMUbtw5A9blCXtH6aoNiCqNyA-blOSUgSZkfxOlgGKw.png",
                "https://future-leaders.ru/resuorces/others/Glf4YGCflHrDLNqc8wMoPy_CU-V8MH931Pvanm3VwE6F3jn7XvGU459NhSrTxWKZTf4HfjzLyJRPaVBAKS4XuQ.png"
        );
        for (int i = 0; i < urls.size(); i++) {
            int column = 0;
            if (i % 2 != 0)
                column = 1;
            int row = i / 2;
            boolean onAllColumn = false;
            ImageView v = grid.addImageElement(null, onAllColumn);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), v, this);
        }
        setTitle("Мероприятия");
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Лидерская зимняя серия игра \"Что? Где? Когда?\"");
            intent.putExtra("id",-4);
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","OFFER");
            intent.putExtra("id",-5);
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Кейс-турнир БУШЕ");
            intent.putExtra("id",-6);
            startActivity(intent);
        });
        views.get(3).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Leaders Are Among Us");
            intent.putExtra("id",-7);
            startActivity(intent);
        });
        views.get(4).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Открытие образовательной программы 2020-2021");
            intent.putExtra("id",-8);
            startActivity(intent);
        });
        views.get(5).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Кейс-Турнир FERRERO");
            intent.putExtra("id",-9);
            startActivity(intent);
        });
        views.get(6).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","#Leader App Party");
            intent.putExtra("id",-10);
            startActivity(intent);
        });
        views.get(7).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","GLS120 - Global Leadership Summit");
            intent.putExtra("id",-11);
            startActivity(intent);
        });
        views.get(8).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Кейс-турнир UNILEVER");
            intent.putExtra("id",-12);
            startActivity(intent);
        });
        views.get(9).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","#Leaderdoc");
            intent.putExtra("id",-13);
            startActivity(intent);
        });
    }

}