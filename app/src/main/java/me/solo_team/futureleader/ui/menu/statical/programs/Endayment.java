package me.solo_team.futureleader.ui.menu.statical.programs;

import android.content.Intent;
import android.net.Uri;
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

public class Endayment extends Her {

    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__), this, getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/agpVqploowRT4NSyCp8epnNNaMEbCcRYjYOr1bxym8e-LBMOV3oMHdaWffz4hdvmbeCUZlaQ2DRwZ3ArTBrNnA.png",
                "https://future-leaders.ru/resuorces/others/tAWH8ZNIY4oCseGCMbh1bnQgsBTFHh1Cu568OV2kaWBp_uQtIme-0kQwQHDHOUIhK8fGhREP_SXHyZPfaPs1RQ.png",
                "https://future-leaders.ru/resuorces/others/95v58OnoGq_hbsD8WvUU-bwuUJqG3x3tyrJyTkJUXSkj7ltmn-CkiT0nlyhlYws33eRZ56zk1ZWDblFh9T5ehA.png"
        );
        for (int i = 0; i < urls.size(); i++) {
            ImageView v = grid.addImageElement(null, false);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i), v, this);
        }
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "Узнать подробнее");
            intent.putExtra("id", -79);
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v -> {
            Intent intent = new Intent(this, OpenNewsFragment.class);
            intent.putExtra("tag", "Свой вклад уже внесли");
            intent.putExtra("id", -80);
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://russianleaders.org/endowment"));
            startActivity(intent);
        });
        setTitle("Эндаумент");
    }
}