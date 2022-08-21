package me.solo_team.futureleader.ui.menu.statical.admining.layouts.surveys;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class SurveysStatistic extends Her {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!Constants.user.permission.can_get_complete_surveys){
            setResult(-500);
            finish();
            return;
        }
        Intent intent = new Intent(SurveysStatistic.this, SurveysLayout.class);
        intent.putExtra("needShowSurveysStat",true);
        intent.putExtra("showStatistic", true);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100)
            finish();
    }
}
