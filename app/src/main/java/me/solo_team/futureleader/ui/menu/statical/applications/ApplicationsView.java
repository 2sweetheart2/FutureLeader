package me.solo_team.futureleader.ui.menu.statical.applications;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.MenuGrid;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class ApplicationsView extends Her {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("заявки");
        setContentView(R.layout.applications);
        Button button = findViewById(R.id.applications_btn);
        List<View> views = new ArrayList<>();
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/6n5R4g0uYWSpKbtnR-EyUJQm2_BuPDBMH7WRkOUcSqgmro2cYRzEms1E7MfNWiosdTe4I6adg69rtko3Up4seg.png",
                "https://future-leaders.ru/resuorces/others/884phLzXiUVtwnVUpkZEclKUL-ocwbMucvSLXo7M59OWyE2LSCcbbHtxmQ3Rmbpqb-cw3fIsNXcrEdFZRvqXnQ.png",
                "https://future-leaders.ru/resuorces/others/BiNoF_Py2QaH6IoDHehLutC6E6AKMryc7MhroFKCzci8hWOmsKIptk0ImcRg6ThWEhG37JeVjIYw6gIrlr7ySA.png",
                "https://future-leaders.ru/resuorces/others/PHbRiI64HMSqHNoFB4p1vxuaNef9Hvvk3Gby9HF-QLaJ6NkOXSOdgEv3vv3OjOXc1abbfz6gBTkTT7RXm1ntlQ.png",
                "https://future-leaders.ru/resuorces/others/9n7s_174C2OY2n-Jk6ZojPgXXV5Zj4eLDhV4q0M1OuTUjuB0TmcnZXo6zUfAU4PMoOMDNENHh5iANkpE31FK-Q.png"
        );


        MenuGrid grid = new MenuGrid(findViewById(R.id.back__),this,getWindowManager());


        for (int i =0;i<urls.size();i++){
            int column=0;
            if (i % 2 != 0)
                column = 1;
            int row = i / 2;
            int finalColumn = column;
            boolean onAllColumn = false;

            ImageView v = (ImageView) grid.addImageElement(null,onAllColumn);
            Constants.cache.addPhoto(urls.get(i),false,v,this);
            v.setBackground(getDrawable(R.drawable.gray_gradient_with_corners));

            views.add(v);

        }
        button.setOnClickListener(v -> startActivity(new Intent(ApplicationsView.this,YourApplications.class)));
        Intent intent = new Intent(this,Uslugi.class);
        views.get(0).setOnClickListener(v -> {
            intent.putExtra("type",0);
            startActivity(intent);
        });
    }
}
