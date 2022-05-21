package me.solo_team.futureleader.ui.menu.statical.Founder;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.Utils;

public class WelcomeSpeechLayout extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.only_image_layout);
        Utils.ScalingImage image = new Utils.ScalingImage(getBaseContext());
        setTitle("Приветственное слово");
        Constants.cache.addPhoto("https://sun9-3.userapi.com/s/v1/if2/CqksijSMSeoUmGIksdfBZEoawPz9I_1KOizT-RywhzMYTGtZ8p9a1Q-e7bmOiLpmmVhQjeE84blvPddHqm8yztME.jpg?size=1916x2160&quality=96&type=album",
                true,image,this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        addContentView(image,findViewById(R.id.only_image_view).getLayoutParams());
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
