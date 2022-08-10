package me.solo_team.futureleader.ui.menu.horizontal_menu.surveys;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

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
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

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
                if(getIntent().getBooleanExtra("custom_check",false))
                    currentSurveys =Constants.surveysCache.currentSurvey;
                else
                    currentSurveys = Constants.surveysCache.getCompleteById(getIntent().getIntExtra("id", -1));
                break;
            case "check_admin":
                currentSurveys = Constants.surveysCache.getAllByIdAdmin(getIntent().getIntExtra("id", -1));
        }

        if (currentSurveys == null)
            finish();

        setContentView(R.layout.do_survey);
        label = findViewById(R.id.do_survey_label);
        list = findViewById(R.id.do_survey_list);

        root = findViewById(R.id.do_survey);
        setTitle(currentSurveys.name);
        if (type.equals("check")) {
            label.setVisibility(View.GONE);
            findViewById(R.id.do_survey_next_btn).setVisibility(View.GONE);
            for (Surveys.SurveysObject s : currentSurveys.fields) {
                LinearLayout linearLayout = new LinearLayout(DoSurvey.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                String answer = null;
                try {
                     answer = currentSurveys.answers.get(currentSurveys.fields.indexOf(s));
                }catch (IndexOutOfBoundsException e){
                    setResult(-1);
                    finish();
                }
                if(answer==null){
                    setResult(-1);
                    finish();
                }
                switch (s.type) {
                    case "text":
                        TextView editText = new TextView(DoSurvey.this);
                        editText.setTextColor(Color.BLACK);
                        editText.setLayoutParams(lp);
                        editText.setText((index + 1) + ") " + s.name + "\n- " + answer);
                        linearLayout.addView(editText);
                        if (s.image != null) {
                            ImageView imageView = new ImageView(DoSurvey.this);
                            imageView.setLayoutParams(lp);
                            Constants.cache.addPhoto(s.image, true, imageView, this);
                            linearLayout.addView(imageView);
                        }
                        list.addView(linearLayout);
                        break;
                    case "one_of":
                        TextView textView = new TextView(DoSurvey.this);
                        textView.setTextColor(Color.BLACK);
                        textView.setLayoutParams(lp);
                        textView.setText((index + 1) + ") " + s.name);
                        linearLayout.addView(textView);
                        for (String s_ : s.extras.split("&")) {
                            CheckBox checkBox = new CheckBox(DoSurvey.this);
                            checkBox.setLayoutParams(lp);
                            checkBox.setText(s_);
                            checkBox.setEnabled(false);
                            checkBox.setChecked(false);
                            if (checkBox.getText().toString().equals(answer))
                                checkBox.setChecked(true);
                            linearLayout.addView(checkBox);
                        }
                        if (s.image != null) {
                            ImageView imageView = new ImageView(DoSurvey.this);
                            imageView.setLayoutParams(lp);
                            Constants.cache.addPhoto(s.image, true, imageView, this);
                            linearLayout.addView(imageView);
                        }
                        list.addView(linearLayout);
                        break;
                }
                index++;
            }
            return;
        }
        if (type.equals("check_admin")) {
            label.setVisibility(View.GONE);
            findViewById(R.id.do_survey_next_btn).setVisibility(View.GONE);
            for (Surveys.SurveysObject s : currentSurveys.fields) {
                LinearLayout linearLayout = new LinearLayout(DoSurvey.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                switch (s.type) {
                    case "text":
                        TextView editText = new TextView(DoSurvey.this);
                        editText.setTextColor(Color.BLACK);
                        editText.setLayoutParams(lp);
                        editText.setText((index + 1) + ") " + s.name);
                        linearLayout.addView(editText);
                        if (s.image != null) {
                            ImageView imageView = new ImageView(DoSurvey.this);
                            imageView.setLayoutParams(lp);
                            Constants.cache.addPhoto(s.image, true, imageView, this);
                            linearLayout.addView(imageView);
                        }
                        list.addView(linearLayout);
                        break;
                    case "one_of":
                        TextView textView = new TextView(DoSurvey.this);
                        textView.setTextColor(Color.BLACK);
                        textView.setLayoutParams(lp);
                        textView.setText((index + 1) + ") " + s.name);
                        linearLayout.addView(textView);
                        for (String s_ : s.extras.split("&")) {
                            CheckBox checkBox = new CheckBox(DoSurvey.this);
                            checkBox.setLayoutParams(lp);
                            checkBox.setText(s_);
                            checkBox.setEnabled(false);
                            checkBox.setChecked(false);
                            linearLayout.addView(checkBox);
                        }
                        if (s.image != null) {
                            ImageView imageView = new ImageView(DoSurvey.this);
                            imageView.setLayoutParams(lp);
                            Constants.cache.addPhoto(s.image, true, imageView, this);
                            linearLayout.addView(imageView);
                        }
                        list.addView(linearLayout);
                        break;
                }
                index++;
            }
            return;
        }
        label.setText("1) " + currentSurveys.fields.get(0).name);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (currentSurveys.fields.get(0).type.equals("text")) {
            EditText editText = new EditText(getApplicationContext());
            editText.setLayoutParams(lp);
            list.addView(editText);
            currentTextObject = editText;
        }
        index = 0;
        reRender(currentSurveys.fields.get(0).type);
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
            case "one_of":
                for (CheckBox checkBox : currentCheckBox) {
                    if (checkBox.isChecked()) {
                        value = checkBox.getText().toString();
                    }
                }
                if(value.length()==0) {
                    Utils.ShowSnackBar.show(getApplicationContext(), "Нужно выбрать один из ответов!", root);
                    return;
                }
                break;

        }


        answer.add(value);
        index++;
        if (index == currentSurveys.fields.size()) {
            Utils.ShowSnackBar.show(getApplicationContext(), "конец!", root);
            setResult(1);
            end();
            return;
        }
        reRender(currentSurveys.fields.get(index).type);
    }

    List<CheckBox> currentCheckBox = new ArrayList<>();

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
                LinearLayout linearLayout = new LinearLayout(DoSurvey.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                EditText editText = new EditText(DoSurvey.this);
                editText.setLayoutParams(lp);
                linearLayout.addView(editText);
                if (currentSurveys.fields.get(index).image != null) {
                    ImageView imageView = new ImageView(DoSurvey.this);
                    imageView.setLayoutParams(lp);
                    Constants.cache.addPhoto(currentSurveys.fields.get(index).image, true, imageView, this);
                    linearLayout.addView(imageView);
                }
                currentTextObject = editText;
                list.addView(linearLayout);
                break;
            case "one_of":
                LinearLayout linearLayout1 = new LinearLayout(DoSurvey.this);
                linearLayout1.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                for (String s : currentSurveys.fields.get(index).extras.split("&")) {
                    CheckBox checkBox = new CheckBox(DoSurvey.this);
                    checkBox.setLayoutParams(lp1);
                    checkBox.setText(s);
                    checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (isChecked) {
                            for (CheckBox checkBox1 : currentCheckBox)
                                if(checkBox1!=checkBox)
                                    checkBox1.setChecked(false);
                        }
                    });
                    currentCheckBox.add(checkBox);
                    linearLayout1.addView(checkBox);
                }
                if (currentSurveys.fields.get(index).image != null) {
                    ImageView imageView = new ImageView(DoSurvey.this);
                    imageView.setLayoutParams(lp1);
                    Constants.cache.addPhoto(currentSurveys.fields.get(index).image, true, imageView, this);
                    linearLayout1.addView(imageView);
                }
                list.addView(linearLayout1);
                break;
        }
    }

    private void end() {
        StringBuilder answers = new StringBuilder();
        for (String ans : answer)
            answers.append(ans).append("{{{<--->}}}");
        System.out.println(answers.substring(0, answers.toString().length() - 11));
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
                new CustomString("token", Constants.user.token),
                new CustomString("answers", answers.substring(0, answers.toString().length() - 11)),
                new CustomString("id", String.valueOf(currentSurveys.id)));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (type.equals("check_admin"))
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
                                    createNotification(list, json.getString("message"));
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
                            }, new CustomString("token", Constants.user.token), new CustomString("survey_id", String.valueOf(currentSurveys.id)));
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
