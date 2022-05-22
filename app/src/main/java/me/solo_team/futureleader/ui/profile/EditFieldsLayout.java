package me.solo_team.futureleader.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.Utils;
import me.solo_team.futureleader.dialogs.EditFieldsDialog;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class EditFieldsLayout extends Her {
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String type = getIntent().getStringExtra("type");
        String name = getIntent().getStringExtra("name");
        if (type == null || name == null) {
            finish();
            return;
        }
        setContentView(R.layout.edit_fields_layout);
        EditText editText = findViewById(R.id.edit_fields_value);
        TextView textView = findViewById(R.id.edit_fields_name);
        Button button = findViewById(R.id.edit_fields_btn);
        constraintLayout = findViewById(R.id.edit_fields_layout);
        setTitle(name);
        textView.setText(textView.getText() + " \"" + name + "\":");
        switch (type) {
            case "text":
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case "phone":
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
        }
        button.setOnClickListener(v -> {
            if (editText.getText().length() == 0) {
                cr(name,getCurrentFocus());
                return;
            }
            String data = editText.getText().toString();
            switch (type) {
                case "text":
                    editInfo(name, data);
                    break;
                case "phone":
                    if (data.length() != 12 || data.indexOf("+") != 0) {
                        Utils.ShowSnackBar.show(getApplicationContext(), "Номер телефона введен некоректно\nПример: +79112220000", v);
                        return;
                    }
                    editInfo(name, data);
                    break;
            }
        });


    }

    private boolean cr(String value,View v){
        EditFieldsDialog cl = new EditFieldsDialog(result -> {
            if(!result) return;
            Constants.user.info_fields.remove(Constants.user.enums.get(value.toLowerCase()));
            finish();
        },value,"Вы действиетльно хотите удалить параметр \""+value+"\"?");
        cl.show(getSupportFragmentManager(),"myDialog");
        return true;
    }

    private void editInfo(String name, String value) {
        try {
            Constants.user.info_fields.put(Constants.user.enums.get(name.toLowerCase()), value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finish();
    }

    private void uncorrectDate(Context c, View v) {
        Utils.ShowSnackBar.show(c, "Дата введена некоректно\nПример: 01/01/2000", v);
    }

}
