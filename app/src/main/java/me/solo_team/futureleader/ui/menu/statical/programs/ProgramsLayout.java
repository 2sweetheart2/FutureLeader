package me.solo_team.futureleader.ui.menu.statical.programs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.MenuGrid;
import me.solo_team.futureleader.ui.menu.statical.Founder.BiographyLayout;
import me.solo_team.futureleader.ui.menu.statical.Founder.ContactLayout;
import me.solo_team.futureleader.ui.menu.statical.Founder.WelcomeSpeechLayout;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.menu.statical.programs.FounderLeadersLayout.Layout;

public class ProgramsLayout extends Her {

    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__),this,getWindowManager());
        List<String> urls = Arrays.asList(
                "https://sun9-53.userapi.com/s/v1/if2/xn-GYxuYMyTEx4PyPuLj1a7oBwV976_JFIT9KljLr_HsADQKmfs2_fgT0xviM-wu9Moiug1AHuBs9U4G8Xji7fEu.jpg?size=800x800&quality=96&type=album",
                "https://sun9-36.userapi.com/s/v1/if2/Z3r74hsV14zY54MrHjjzkIvRycZvzRsi6vXKJTvjq2BhBFkkoZkAcQUeybkXMvhEishKJfDWKKhsdn4TLVCaD7ND.jpg?size=800x800&quality=96&type=album",
                "https://sun9-24.userapi.com/s/v1/if2/AQiYs2ujkjM8E4uptQuWPjROZZ-5Ga40upZIWSuwrcpY_jZ8OXhvhxAMquLOHxj_E1Oy-a_nbpHfu1SwaOfOkyZA.jpg?size=800x800&quality=96&type=album",
                "https://sun9-78.userapi.com/s/v1/if2/tMGg-vab4RVM_QFYzwWrp0ij1Ua2gmDdb7xghlPCv2UJQAzra4ZEYaoNfmTkE4mmWXU9q4R7MiVsa5HDj7MAswei.jpg?size=800x800&quality=96&type=album",
                "https://sun9-75.userapi.com/s/v1/if2/MFO6MPZI7Xbhd7R0UXvFVDMis3uy7BIYNjHrO5ZYrU2kw4zV3ln0UkkI1Mn9EctdbSZa7uh7RJQ1CYl_TpxxKxOS.jpg?size=800x800&quality=96&type=album",
                "https://sun9-45.userapi.com/s/v1/if2/vjp9Fd_-hiSNFcdOsBC1opzhhyG_9B0Aq7eMPgUQq-8YcGMWdZlMKr55nMgfnyeqm_1LM7vtpL6yq50Ezl5VM_PV.jpg?size=800x800&quality=96&type=album"
        );
        for (int i =0;i<urls.size();i++){
            int column=0;
            if (i % 2 != 0)
                column = 1;
            int row = i / 2;
            ImageView v = grid.addImageElement(null, false,row, column);
            views.add(v);
            Constants.cache.addPhoto(urls.get(i),true,v,this);


        }
        views.get(0).setOnClickListener(v -> {
            Intent intent = new Intent(this, Layout.class);
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v ->{
            Intent intent = new Intent(this, BiographyLayout.class);
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v ->{
            Intent intent = new Intent(this, ContactLayout.class);
            startActivity(intent);
        });
        setTitle("Программы");
    }
}
