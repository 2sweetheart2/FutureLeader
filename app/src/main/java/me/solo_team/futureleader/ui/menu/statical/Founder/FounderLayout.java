package me.solo_team.futureleader.ui.menu.statical.Founder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.Utils;
import me.solo_team.futureleader.ui.menu.MenuGrid;
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;
import okhttp3.internal.Util;

public class FounderLayout extends AppCompatActivity {

    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__),this,getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/9s0YOiNiyl2vUBgPLLKpU8bbCtgKKLUg6wbholv2ZLIEqCjxcA60f6hhBdku2mvmj7q1WMHVpOPVbiA2ly2bfQ.png",
                "https://future-leaders.ru/resuorces/others/07f52SZoea-jXwATYXe6w_DsGXRp0faxOI1W8wXRVvwRRSFi5Tkf4hjPmJ8T8hCDPIU1o76BjPM57ewm2LVrng.png",
                "https://future-leaders.ru/resuorces/others/JCFwcMnMhfzavY7lZ_T8BSEJhR6qWwfj1WNvjI2ofDNbcJNqwaDYKvZ3Zmv6juHMfJSrDmgtyEXzqsOnZGFOyg.png"        );
        for (int i =0;i<urls.size();i++){
            int column=0;
            if (i % 2 != 0)
                column = 1;
            int row = i / 2;
            int finalColumn = column;
            boolean onAllColumn = false;
            if(i==2) onAllColumn=true;
            boolean finalOnAllColumn = onAllColumn;
            ImageView v = grid.addImageElement(null, finalOnAllColumn,row, finalColumn);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i),true,v,this);


        }
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Приветственное слово");
            intent.putExtra("id",4);
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v ->{
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag","Биография");
            intent.putExtra("id",3);
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v ->{
            Intent intent = new Intent(this,ContactLayout.class);
            startActivity(intent);
        });
        setTitle("Основатель");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}
