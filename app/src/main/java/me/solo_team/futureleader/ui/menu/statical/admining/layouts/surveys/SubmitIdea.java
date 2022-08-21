package me.solo_team.futureleader.ui.menu.statical.admining.layouts.surveys;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class SubmitIdea extends Her {
    CheckBox checkBox;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!Constants.user.permission.can_set_ideas_status)
        {
            setResult(-500);
            finish();
        }
        String labelT = getIntent().getStringExtra("label");
        int idT = getIntent().getIntExtra("id", -1);
        String textT = getIntent().getStringExtra("text");
        String logoT = getIntent().getStringExtra("logo");
        String nameU = getIntent().getStringExtra("name");
        int userId = getIntent().getIntExtra("user_id", -1);
        setTitle(labelT);

        setContentView(R.layout.admin_view_idea);

        ((TextView) findViewById(R.id.admin_view_idea_name)).setText("название: "+labelT);
        Constants.cache.addPhoto(logoT, findViewById(R.id.admin_view_idea_user_logo), this);
        ((TextView) findViewById(R.id.admin_view_idea_user_name)).setText(nameU);
        ((TextView) findViewById(R.id.admin_view_idea_text)).setText("описание: "+textT);
        checkBox = findViewById(R.id.admin_view_idea_status);
        editText = findViewById(R.id.admin_view_idea_comment);
        editText.setFocusable(true);
        button = findViewById(R.id.admin_view_idea_submit);

        button.setOnClickListener(v -> {
            String status;
            if (checkBox.isChecked())
                status = "allow";
            else
                status = "deny";

            JSONObject o = new JSONObject();

            try {
                o.put("token", Constants.user.token);
                o.put("mobile_token", Constants.user.mobileToken);
                o.put("idea_id", idT);
                o.put("user_id", userId);
                o.put("status", status);
                if (editText.getText().length() > 0)
                    o.put("comment", editText.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            API.setIdeasStatus(new ApiListener() {
                Dialog d;
                                   @Override
                                   public void onError(JSONObject json) throws JSONException {
                                        d.dismiss();
                                       Intent data = new Intent();
                                       data.putExtra("message",json.getString("message"));
                                        setResult(-1,data);
                                        finish();
                                   }

                                   @Override
                                   public void inProcess() {
                                        d = openWaiter(SubmitIdea.this);
                                   }

                                   @Override
                                   public void onSuccess(JSONObject json) throws JSONException {
                                       Intent data = new Intent();
                                       data.putExtra("user_id",userId);
                                       data.putExtra("idea_id",idT);
                                       setResult(1,data);
                                       d.dismiss();
                                       finish();
                                   }
                               },
                    o
            );
        });
    }
}
