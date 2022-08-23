package me.solo_team.futureleader.ui.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.Nullable;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.MediaAudioAdapters.VideoAdapter.VideoView;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.login_or_register.LoginOrRegisterLayout;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class UserSettings extends Her {
    CheckBox checkBox;
    CheckBox web;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("настройки");
        setContentView(R.layout.user_settings);

        checkBox = findViewById(R.id.set_user_previe);
        web = findViewById(R.id.set_user_webview);
        Button exit = findViewById(R.id.set_user_exit);

        setParams();

        exit.setOnClickListener(v -> {
            AlertDialog.Builder obj = new AlertDialog.Builder(UserSettings.this);
            obj.setTitle("Вы действительно хотите выйти?");
            obj.setIcon(R.drawable.resize_300x0);
            obj.setPositiveButton("да", (dialog, which) -> {
                Constants.settings.removeUserInfo();
                startActivity(new Intent(this, LoginOrRegisterLayout.class));
                finish();
            });
            obj.setNegativeButton("нет", null);
            obj.show();
        });
    }

    public void setParams(){
        checkBox.setChecked(Constants.settings.getStartScreen());
        web.setChecked(Constants.settings.getStartChecker());

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Constants.settings.setStartScreen(isChecked);
        });
        web.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Constants.settings.saveStartChecker(isChecked);
        });
    }
}
