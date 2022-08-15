package me.solo_team.futureleader.ui.menu.horizontal_menu.idea;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class CreateIdea extends Her {

    EditText label;
    EditText text;
    View rootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_idea_layout);
        setTitle("Предложить идею");

        label = findViewById(R.id.create_idea_label);
        text = findViewById(R.id.create_idea_text);
        rootView = findViewById(R.id.create_idea_layout);

    }

    public void Click() {
        if (label.getText().length() == 0) {
            Utils.ShowSnackBar.show(getApplicationContext(), "Заголовок не может быть пустым!", rootView);
            return;
        } else if (label.getText().length() > 45) {
            Utils.ShowSnackBar.show(getApplicationContext(), "заголовок не может превышать 45 сим.!", rootView);
            return;
        }
        if (text.getText().length() == 0) {
            Utils.ShowSnackBar.show(getApplicationContext(), "Текст не может быть пустым!", rootView);
            return;
        }

        API.createIdea(new ApiListener() {
            Dialog d;

            @Override
            public void onError(JSONObject json) throws JSONException {
                d.dismiss();
                setResult(-1);
                finish();
            }

            @Override
            public void inProcess() {
                d = openWaiter(CreateIdea.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                d.dismiss();
                Intent data = new Intent();
                data.putExtra("label",label.getText().toString());
                data.putExtra("text",text.getText().toString());
                setResult(1,data);
                finish();
            }
        }, new CustomString("token", Constants.user.token),new CustomString("label",label.getText().toString()),new CustomString("text",text.getText().toString()));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "")
                .setIcon(R.drawable.done)
                .setOnMenuItemClickListener(item -> {
                    Click();
                    return true;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);
    }
}
