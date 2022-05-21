package me.solo_team.futureleader.ui.menu.statical.Founder;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.Utils;

public class BiographyLayout extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.only_image_layout);

        Utils.ScalingImage image = new Utils.ScalingImage(getBaseContext());
        setTitle("Биография");
        Constants.cache.addPhoto("https://sun9-78.userapi.com/s/v1/if2/mCBdZV048HGyy2PEGzIoUUX8e8GpvemTZ2eYArzYJhPF8kXaO1SC3d6cz4I4Sz4bbGF9kobtdQsihYwZwAHwqzsx.jpg?size=1527x2160&quality=96&type=album",
                true,image,this);
        addContentView(image, findViewById(R.id.only_image_view).getLayoutParams());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
