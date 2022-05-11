package me.solo_team.futureleader.ui.menu.statical.Founder;

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
import me.solo_team.futureleader.Utils;
import me.solo_team.futureleader.ui.WebViewsContent.WebView;
import me.solo_team.futureleader.ui.menu.MenuGrid;

public class ContactLayout extends AppCompatActivity {

    List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_layout);
        MenuGrid grid = new MenuGrid(findViewById(R.id.back__),getApplicationContext(),getWindowManager());
        List<String> urls = Arrays.asList(
                "https://sun9-83.userapi.com/s/v1/if2/1qwoppOAKK9qnchI-1yyEE0Pmn25GbrVI9VTrfElNouUkm1LkXcdkgewbNICwNV42fVP0MHotTZxx6x_HxhKXz_M.jpg?size=800x450&quality=96&type=album",
                "https://sun9-47.userapi.com/s/v1/if2/Sk-2C5IRXmBhpQIxD39S2EXBP3UxumPA4GzYjsB1PtbIz-nucpmhNsHDr8UirJiyNUqEqxz3IQ98irYmk6r6vMV6.jpg?size=800x450&quality=96&type=album",
                "https://sun9-71.userapi.com/s/v1/if2/OTjZPkR6jYRxMFwWmibDirRB_Pj5wjNO8VikI1dY0eTeZ0rymx1qKbKs_KU5JTk033P-oUUFMzRkqMwqH7NsbxLF.jpg?size=800x450&quality=96&type=album"
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
            Intent intent = new Intent(this, WebView.class);
            intent.putExtra("url","https://vk.com/sheykin1980");
            startActivity(intent);
        });
        views.get(1).setOnClickListener(v ->{
            Intent intent = new Intent(this,WebView.class);
            intent.putExtra("url","https://www.instagram.com/a.sheikin/");
            startActivity(intent);
        });
        views.get(2).setOnClickListener(v ->{
            Intent intent = new Intent(this,WebView.class);
            intent.putExtra("url","https://www.facebook.com/sheykin.art");
            startActivity(intent);
        });
        setTitle("Контакты");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
