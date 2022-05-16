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

public class ProgramsLayout extends AppCompatActivity {

    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__),this,getWindowManager());
        List<String> urls = Arrays.asList(
                "https://sun9-23.userapi.com/s/v1/if2/tM_Y_sBfisMuQ09vhw82DSAYqDqDp1q5g9JUM57n-S4brhRcMiSYFkmo9_daGjjO_5fgEvfqN7mCo23DASCtxpON.jpg?size=800x800&quality=96&type=album",
                "https://sun9-43.userapi.com/s/v1/if2/7e1Wwc235L4IEQYbXOleM2ItyonRzOvNOcDaCe0WSSHPQnkXGGHexdr0gUIQEgx7PZlWJ1gSszwXbnHrmlCU1Wnb.jpg?size=800x800&quality=96&type=album",
                "https://sun9-55.userapi.com/s/v1/if2/PrkagT_k8KcZVEliaC93v5HYHHxwBH0c2BVmfCZfapEoYV6Ho5A2BYZXNsQJIYadbgUgoWPJN1BFj178vIXNAL5i.jpg?size=800x800&quality=96&type=album"
        );
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
            Intent intent = new Intent(this, WelcomeSpeechLayout.class);
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
        setTitle("Основатель");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}
