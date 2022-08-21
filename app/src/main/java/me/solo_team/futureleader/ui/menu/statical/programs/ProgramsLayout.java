package me.solo_team.futureleader.ui.menu.statical.programs;

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
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.future_leaders.Meropriyatia;

public class ProgramsLayout extends Her {

    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__),this,getWindowManager());
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/6yWSWKqTFjebnUOtBIMUGt22U9R0QAb8rNLN5fK0PFGF_gx7PHMx6je5sBHQFRTy11wqZjmBpeJXdlTJzfWiAA.png",
                "https://future-leaders.ru/resuorces/others/qfYpS99Uu1Q3wIWPgvVWgW5yTzBQnsBXPCEK5te727vCQ8fd0EKKjlt36MNOzyWpBq6zAQqRDVCRFZAvaqZUWQ.png",
                "https://future-leaders.ru/resuorces/others/LA4srQWZtbzlCsW2lWUO_B-C1rJkUP-roPpaqYOnt_hHg7d0mnbSfnFFIsdwShIitQFVwFfHEy8Lok9uVlkG6w.png",
                "https://future-leaders.ru/resuorces/others/1K5VIaz1pHU9L2EFfSIQ6IDFCNoX8oZj4gB_F-cf382WYUg7Raju4IxcjK05nwzvJMhOpRlpzSm5M7Syqu4lBA.png",
                "https://future-leaders.ru/resuorces/others/mAQD57dXnkXdqbCayY9xX2pssWGZuGgNVCfZaY-_7081p1axoSWosyCTIOtnHZtJZDlIdBnZr5EZn5oKodngyw.png",
                "https://future-leaders.ru/resuorces/others/ic3bTLdoe0946jCWxUMyj7cG16UBgX8_VjZWBtc-QvenmZvpPgnX_b2lkSqePNa3iom_QEatnVQTz5Nraz-MpQ.png"
        );
        for (int i =0;i<urls.size();i++){
            int column=0;
            if (i % 2 != 0)
                column = 1;
            int row = i / 2;
            ImageView v = grid.addImageElement(null, false);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i),v,this);


        }
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, Layout.class);
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v ->{

        });
        views.get(2).setOnClickListener(v ->{
            Intent intent = new Intent(this, ContactLayout.class);
            startActivity(intent);
        });
        setTitle("Программы");
    }
}
