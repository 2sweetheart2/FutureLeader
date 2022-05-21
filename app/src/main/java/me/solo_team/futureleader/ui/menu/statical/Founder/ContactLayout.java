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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.her_ne_eby_name);
        ImageView contact1 = findViewById(R.id.contact_1);
        ImageView contact2 = findViewById(R.id.contact_2);
        ImageView contact3 = findViewById(R.id.contact_3);
        List<String> urls = Arrays.asList(
                "https://sun9-83.userapi.com/s/v1/if2/1qwoppOAKK9qnchI-1yyEE0Pmn25GbrVI9VTrfElNouUkm1LkXcdkgewbNICwNV42fVP0MHotTZxx6x_HxhKXz_M.jpg?size=800x450&quality=96&type=album",
                "https://sun9-47.userapi.com/s/v1/if2/Sk-2C5IRXmBhpQIxD39S2EXBP3UxumPA4GzYjsB1PtbIz-nucpmhNsHDr8UirJiyNUqEqxz3IQ98irYmk6r6vMV6.jpg?size=800x450&quality=96&type=album",
                "https://sun9-71.userapi.com/s/v1/if2/OTjZPkR6jYRxMFwWmibDirRB_Pj5wjNO8VikI1dY0eTeZ0rymx1qKbKs_KU5JTk033P-oUUFMzRkqMwqH7NsbxLF.jpg?size=800x450&quality=96&type=album"
        );
        Constants.cache.addPhoto(urls.get(0),true,contact1,this);
        Constants.cache.addPhoto(urls.get(1),true,contact2,this);
        Constants.cache.addPhoto(urls.get(2),true,contact3,this);
        contact1.setOnClickListener(v -> {
            Intent intent = new Intent(this, WebView.class);
            intent.putExtra("url","https://vk.com/sheykin1980");
            startActivity(intent);
        });
        contact2.setOnClickListener(v ->{
            Intent intent = new Intent(this,WebView.class);
            intent.putExtra("url","https://www.instagram.com/a.sheikin/");
            startActivity(intent);
        });
        contact3.setOnClickListener(v ->{
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
