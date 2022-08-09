package me.solo_team.futureleader.ui.menu.horizontal_menu.surveys;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Surveys;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.horizontal_menu.surveys.fragments.SurveysForMe;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.news.open_news.EditNews;

public class DoSurvey extends Her implements View.OnClickListener {
    Surveys currentSurveys = null;
    List<String> answer = new ArrayList<>();
    int index = 0;
    View root;
    EditText currentTextObject = null;
    TextView label;
    LinearLayout list;

    String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = getIntent().getStringExtra("type");

        switch (type) {
            case "me":
                currentSurveys = Constants.surveysCache.getMeById(getIntent().getIntExtra("id", -1));
                break;
            case "all":
                currentSurveys = Constants.surveysCache.getAllById(getIntent().getIntExtra("id", -1));
                break;
            case "check":
                currentSurveys = Constants.surveysCache.getCompleteById(getIntent().getIntExtra("id", -1));
                break;
            case "check_admin":
                currentSurveys = Constants.surveysCache.getAllByIdAdmin(getIntent().getIntExtra("id",-1));
        }

        if (currentSurveys == null)
            finish();

        setContentView(R.layout.do_survey);
        label = findViewById(R.id.do_survey_label);
        list = findViewById(R.id.do_survey_list);

        root = findViewById(R.id.do_survey);
        setTitle(currentSurveys.name);

        if(type.equals("check")){
            findViewById(R.id.do_survey_next_btn).setVisibility(View.GONE);
            StringBuilder text = new StringBuilder();
            for(String s : currentSurveys.answers){
                text.append(index+1).append(')').append(" ").append(currentSurveys.fields.get(index).name).append("\n")
                        .append('-').append(' ').append(s).append("\n");
                index++;
            }
            label.setText(text.toString());
            return;
        }
        if(type.equals("check_admin")){
            findViewById(R.id.do_survey_next_btn).setVisibility(View.GONE);
            StringBuilder text = new StringBuilder();
            for(Surveys.SurveysObject s : currentSurveys.fields){
                text.append(index+1).append(')').append(" ").append(s.name).append("\n");
                index++;
            }
            label.setText(text.toString());
            return;
        }
        label.setText("1) " + currentSurveys.fields.get(0).name);

        if (currentSurveys.fields.get(0).type.equals("text")) {
            EditText editText = new EditText(getApplicationContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            editText.setLayoutParams(lp);
            list.addView(editText);
            currentTextObject = editText;
        }
        findViewById(R.id.do_survey_next_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String type = currentSurveys.fields.get(index).type;
        String value = "";
        switch (type) {
            case "text":
                value = currentTextObject.getText().toString();
                if (value.length() == 0) {
                    Utils.ShowSnackBar.show(getApplicationContext(), "Текстовое поле не может быть пустым!", root);
                    return;
                }
                break;
        }


        answer.add(value);
        index++;
        reRender(type);
    }

    private void reRender(String type) {
        list.removeAllViews();
        if (index == currentSurveys.fields.size()) {
            Utils.ShowSnackBar.show(getApplicationContext(), "конец!", root);
            end();
            return;
        }
        label.setText((index + 1) + ") " + currentSurveys.fields.get(index).name);
        switch (type) {
            case "text":
                EditText editText = new EditText(getApplicationContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                editText.setLayoutParams(lp);
                list.addView(editText);
                currentTextObject = editText;
                break;
        }
    }

    private void end() {
        StringBuilder answers = new StringBuilder();
        for(String ans : answer)
            answers.append(ans).append("{{{<--->}}}");
        System.out.println(answers.substring(0,answers.toString().length()-11));
        API.addAnser(new ApiListener() {
            Dialog d;

            @Override
            public void onError(JSONObject json) throws JSONException {
                d.dismiss();
                createNotification(root, json.getString("message"));
            }

            @Override
            public void inProcess() {
                d = openWaiter(DoSurvey.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                switch (type) {
                    case "me":
                        Constants.surveysCache.addCompleteFromMe(currentSurveys.id);
                        break;
                    case "all":
                        Constants.surveysCache.addCompleteFromAll(currentSurveys.id);
                        break;
                }
                d.dismiss();
                finish();
            }
        },
                new CustomString("token",Constants.user.token),
                new CustomString("answers",answers.substring(0,answers.toString().length()-11)),
                new CustomString("id",String.valueOf(currentSurveys.id)));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(type.equals("check_admin"))
            menu.add(0, 1, 0, "")
                    .setIcon(R.drawable.trash)
                    .setOnMenuItemClickListener(item -> {
                        AlertDialog.Builder obj = new AlertDialog.Builder(DoSurvey.this);
                        obj.setTitle("Удалить опрос?");
                        obj.setIcon(R.drawable.resize_300x0);
                        obj.setPositiveButton("да", (dialog, which) -> {
                            API.deleteSurvey(new ApiListener() {
                                Dialog d;
                                @Override
                                public void onError(JSONObject json) throws JSONException {
                                    d.dismiss();
                                    createNotification(list,json.getString("message"));
                                }

                                @Override
                                public void inProcess() {
                                    d = openWaiter(DoSurvey.this);
                                }

                                @Override
                                public void onSuccess(JSONObject json) throws JSONException {
                                    d.dismiss();
                                    Constants.surveysCache.deleteSurvey(currentSurveys.id);
                                    finish();
                                }
                            },new CustomString("token",Constants.user.token),new CustomString("survey_id",String.valueOf(currentSurveys.id)));
                        });
                        obj.setNegativeButton("нет", null);
                        obj.setMessage("этот опрос удалится вместе с результатами");
                        obj.show();
                        return true;
                    })
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);
    }
}
