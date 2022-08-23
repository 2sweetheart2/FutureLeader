package me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class SocSeti extends Her {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.her_ne_eby_name);
        ImageView contact1 = findViewById(R.id.contact_1);
        ImageView contact2 = findViewById(R.id.contact_2);
        ImageView contact3 = findViewById(R.id.contact_3);
        List<String> urls = getIntent().getStringArrayListExtra("urls");
        List<String> names = getIntent().getStringArrayListExtra("names");
        List<String> redirect = getIntent().getStringArrayListExtra("redirect");
        System.out.println("SIZE: "+urls.size());
        switch (urls.size()){
            case 1:
                contact2.setVisibility(View.GONE);
                ((TextView) findViewById(R.id.text_2)).setVisibility(View.GONE);
                ((TextView) findViewById(R.id.text_3)).setVisibility(View.GONE);
                contact3.setVisibility(View.GONE);

                Constants.cache.addPhoto(urls.get(0),contact1,this);
                ((TextView) findViewById(R.id.text_1)).setText(names.get(0));
                contact1.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(redirect.get(0)));
                    startActivity(intent);
                });
                break;
            case 2:
                ((TextView) findViewById(R.id.text_3)).setVisibility(View.GONE);
                contact3.setVisibility(View.GONE);

                Constants.cache.addPhoto(urls.get(0),contact1,this);
                ((TextView) findViewById(R.id.text_1)).setText(names.get(0));
                contact1.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(redirect.get(0)));
                    startActivity(intent);
                });

                Constants.cache.addPhoto(urls.get(1),contact2,this);
                ((TextView) findViewById(R.id.text_2)).setText(names.get(1));
                contact2.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(redirect.get(1)));
                    startActivity(intent);
                });
                break;
            case 3:
                Constants.cache.addPhoto(urls.get(0),contact1,this);
                Constants.cache.addPhoto(urls.get(1),contact2,this);
                Constants.cache.addPhoto(urls.get(2),contact3,this);
                ((TextView) findViewById(R.id.text_1)).setText(names.get(0));
                ((TextView) findViewById(R.id.text_2)).setText(names.get(1));
                ((TextView) findViewById(R.id.text_3)).setText(names.get(2));

                contact1.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(redirect.get(0)));
                    startActivity(intent);
                });
                contact2.setOnClickListener(v ->{
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(redirect.get(1)));
                    startActivity(intent);
                });
                contact3.setOnClickListener(v ->{
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(redirect.get(2)));
                    startActivity(intent);
                });
        }

        setTitle(getIntent().getStringExtra("title"));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}
