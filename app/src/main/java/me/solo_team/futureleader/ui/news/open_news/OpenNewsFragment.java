package me.solo_team.futureleader.ui.news.open_news;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class OpenNewsFragment extends Her {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_open);
        setTitle(getIntent().getStringExtra("tag"));
        ((TextView) findViewById(R.id.news_open_title)).setText(getIntent().getStringExtra("title"));
        TextView textView = new TextView(getApplicationContext(), null, R.style.first_column);
        textView.setText(getIntent().getStringExtra("text"));
        ((LinearLayout) findViewById(R.id.news_open_list)).addView(textView);

    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (Constants.user.adminStatus != 0) {
            if(menu.size()==0)
                menu.add(0, 1, 0, "")
                        .setIcon(R.drawable.plus)
                        .setOnMenuItemClickListener(item -> {
                            Intent intent = new Intent(OpenNewsFragment.this,EditNews.class);
                            intent.putExtra("tag",getIntent().getStringExtra("tag"));
                            intent.putExtra("title",getIntent().getStringExtra("title"));
                            startActivity(intent);
                            return true;
                        })
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        } else menu.removeItem(1);
        return super.onPrepareOptionsMenu(menu);
    }

}
