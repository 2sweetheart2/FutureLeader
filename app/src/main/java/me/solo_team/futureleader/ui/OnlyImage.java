package me.solo_team.futureleader.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class OnlyImage extends Her {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.only_image_layout);
        setTitle(getIntent().getStringExtra("title"));
        String link = getIntent().getStringExtra("link");
        Utils.ScalingImage image = new Utils.ScalingImage(getBaseContext());
        Constants.cache.addPhoto(link,
                true,image,this);
        addContentView(image, findViewById(R.id.only_image_view).getLayoutParams());
    }
}
