package me.solo_team.futureleader.ui.menu.statical.admining.layouts.users;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.surveys.SurveysLayout;

public class UsersStatus extends Her {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(UsersStatus.this, SurveysLayout.class);
        intent.putExtra("showStatistic",true);
        startActivity(intent);
    }
}
