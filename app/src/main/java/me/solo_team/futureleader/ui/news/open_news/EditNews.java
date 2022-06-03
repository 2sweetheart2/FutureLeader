package me.solo_team.futureleader.ui.news.open_news;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class EditNews extends Her {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_news_layout);

        setTitle("Изменение новости");

        TextView addItem = findViewById(R.id.edit_new_add_item_btn);
        LinearLayout list = findViewById(R.id.edit_new_item_list);
        EditText tag = findViewById(R.id.edit_new_tag);
        EditText name = findViewById(R.id.edit_new_title);



    }
}
