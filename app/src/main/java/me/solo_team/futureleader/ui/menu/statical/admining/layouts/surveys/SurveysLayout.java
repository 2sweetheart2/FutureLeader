package me.solo_team.futureleader.ui.menu.statical.admining.layouts.surveys;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Surveys;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.SelectMembers;
import me.solo_team.futureleader.ui.menu.horizontal_menu.surveys.DoSurvey;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class SurveysLayout extends Her {
    LinearLayout list;
    boolean showStatistic = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Опросы");
        setContentView(R.layout.only_linearlayout);
        list = findViewById(R.id.list);
        showStatistic = getIntent().getBooleanExtra("showStatistic", false);

        API.getSurveysAdmin(new ApiListener() {
            Dialog d;

            @Override
            public void onError(JSONObject json) throws JSONException {
                d.dismiss();
                finish();
            }

            @Override
            public void inProcess() {
                d = openWaiter(SurveysLayout.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                JSONArray s = json.getJSONArray("surveys");
                Constants.surveysCache.allSurveys.clear();
                for (int i = 0; i < s.length(); i++) {
                    Constants.surveysCache.allSurveys.add(new Surveys(s.getJSONObject(i)));
                }
                d.dismiss();
                runOnUiThread(() -> render());
            }
        }, new CustomString("token", Constants.user.token));
    }

    private void render() {
        list.removeAllViews();
        for (Surveys s : Constants.surveysCache.allSurveys) {
            View v = getLayoutInflater().inflate(R.layout.admin_survey_view, null);
            System.out.println(s.creator.profilePicture);
            Constants.cache.addPhoto(s.creator.profilePicture, v.findViewById(R.id.admin_surveys_view_creator_image), this);
            ((TextView) v.findViewById(R.id.admin_surveys_view_creator_name)).setText(s.creator.firstName + " " + s.creator.lastName);
            ((TextView) v.findViewById(R.id.admining_survey_view_date)).setText(s.date.toVisibleStr());
            ((TextView) v.findViewById(R.id.admin_surveys_view_name)).setText(s.name);
            v.setOnClickListener(v1 -> {
                Intent intent = new Intent(SurveysLayout.this, DoSurvey.class);
                intent.putExtra("type", "check_admin");
                intent.putExtra("id", s.id);
                startActivity(intent);
            });
            if (showStatistic)
                v.setOnClickListener(v1 -> {
                    Intent intent = new Intent(SurveysLayout.this, SelectMembers.class);
                    intent.putExtra("showStatistic", true);
                    intent.putExtra("needStuff", false);
                    intent.putExtra("id", String.valueOf(s.id));
                    startActivity(intent);
                });
            list.addView(v);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        render();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(!showStatistic)
            menu.add(0, 1, 0, "")
                .setIcon(R.drawable.plus)
                .setOnMenuItemClickListener(item -> {
                    startActivityForResult(new Intent(SurveysLayout.this, CreateSurveys.class), 100);
                    return true;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            if (requestCode == 100)
                Utils.ShowSnackBar.show(SurveysLayout.this, "Опрос успешно создан!", list);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(showStatistic)
            setResult(1);
    }
}
