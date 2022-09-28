package me.solo_team.futureleader.ui.menu.statical.admining.layouts.moderation;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.Nullable;

import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class OnlyEditText extends Her {

    EditText editText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.only_edit_text);
        setTitle(getIntent().getStringExtra("title"));
        boolean number = getIntent().getBooleanExtra("numbers",false);
        editText = findViewById(R.id.edit_text);
        if(number)
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);
        if(getIntent().hasExtra("hint"))
            editText.setHint(getIntent().getStringExtra("hint"));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "")
                .setIcon(R.drawable.done)
                .setOnMenuItemClickListener(item -> {
                    if(editText.getText().length()<=0){
                        Utils.ShowSnackBar.show(OnlyEditText.this,"текст не может быть пустым!",editText);
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
