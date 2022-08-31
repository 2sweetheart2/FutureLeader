package me.solo_team.futureleader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.news.open_news.EditNews;

public class EditStatus extends Her {
    EditText editText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.only_edit_text);
        setTitle(getIntent().getStringExtra("title"));
        editText = findViewById(R.id.edit_text);
        editText.setHint(getIntent().getStringExtra("hint"));

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "")
                .setIcon(R.drawable.done)
                .setOnMenuItemClickListener(item -> {
                    if(editText.getText().toString().length()==0){
                        Utils.ShowSnackBar.show(EditStatus.this,"отсутствует текст",editText);
                        return false;
                    }
                    Intent data = new Intent();
                    data.putExtra("text",editText.getText().toString());
                    setResult(1,data);
                    finish();
                    return true;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);


        return super.onPrepareOptionsMenu(menu);
    }
}
