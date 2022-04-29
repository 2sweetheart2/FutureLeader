package me.solo_team.futureleader.ui.news.open_news;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.solo_team.futureleader.R;

public class OpenNewsFragment extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_open);
        setTitle("#КООРДИНАЦИОННЫЙ СОВЕТ");
        ((TextView) findViewById(R.id.news_open_title)).setText(getIntent().getStringExtra("title"));
        TextView textView = new TextView(getApplicationContext(), null, R.style.first_column);
        textView.setText(getIntent().getStringExtra("text"));
        ((LinearLayout) findViewById(R.id.news_open_list)).addView(textView);

    }

}
