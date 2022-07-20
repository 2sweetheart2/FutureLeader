package me.solo_team.futureleader.ui.menu.horizontal_menu.idea;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class Showidea extends Her {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idea_full);
        String label = getIntent().getStringExtra("label");
        String status = getIntent().getStringExtra("status");
        String text = getIntent().getStringExtra("text");
        setTitle("Идеи");
        switch (status){
            case "wait":
                status = "ожидает";
                break;
            case "deny":
                status = "откланён";
                break;
            case "allow":
                status = "одобрено";
                break;
        }

        TextView label_ = findViewById(R.id.idea_full_label);
        TextView status_ = findViewById(R.id.idea_full_status);
        TextView text_ = findViewById(R.id.idea_full_text);

        label_.setText(label);
        status_.setText(status_.getText()+status);
        text_.setText(text);
    }
}
