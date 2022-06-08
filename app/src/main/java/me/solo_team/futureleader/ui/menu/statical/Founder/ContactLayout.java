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
import me.solo_team.futureleader.ui.news.open_news.OpenNewsFragment;

public class ContactLayout extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.her_ne_eby_name);
        ImageView contact1 = findViewById(R.id.contact_1);
        ImageView contact2 = findViewById(R.id.contact_2);
        ImageView contact3 = findViewById(R.id.contact_3);
        List<String> urls = Arrays.asList(
                "https://future-leaders.ru/resuorces/others/c-zYqjYNapfNFe6UP06NEY2X9lKt-mkBgt9XeWKLiV5lHwwQnX49XmJRJl3bek6E0xrjRJIwy4YyGeJP687wiA.png",
                "https://future-leaders.ru/resuorces/others/cADo7gjVkXdXh_chRmCigAYzz6iYnneDiJhdrqRlebePmE4g8qb2VAHMIJKYnpyzfn4JZtn00MGc9fzDcZggPw.png",
                "https://future-leaders.ru/resuorces/others/U6GvkkrKYEQcRSTGZCdG9J0V4woA5PmRlxDB89iuIvJizLLFHOe1zRKJHu8L7XCz7RcGFPebEVuuC2MwWUx-sA.png"        );
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
