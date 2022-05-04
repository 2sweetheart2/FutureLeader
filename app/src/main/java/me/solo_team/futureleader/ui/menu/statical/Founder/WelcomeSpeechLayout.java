package me.solo_team.futureleader.ui.menu.statical.Founder;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import me.solo_team.futureleader.R;
import me.solo_team.futureleader.Utils;

public class WelcomeSpeechLayout extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.only_image_layout);
        ImageView v = findViewById(R.id.only_image_view);
        setTitle("Приветственное слово");
        Utils.getBitmapFromURL("https://sun9-3.userapi.com/s/v1/if2/CqksijSMSeoUmGIksdfBZEoawPz9I_1KOizT-RywhzMYTGtZ8p9a1Q-e7bmOiLpmmVhQjeE84blvPddHqm8yztME.jpg?size=1916x2160&quality=96&type=album",bitmap -> {
            if(bitmap!=null){
                runOnUiThread(()->{
                   v.setImageBitmap(Utils.getRoundedCornerBitmap(bitmap,10));
                });
            }
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}