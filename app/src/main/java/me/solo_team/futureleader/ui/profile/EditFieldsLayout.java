package me.solo_team.futureleader.ui.profile;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Field;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.dialogs.EditFieldsDialog;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class EditFieldsLayout extends Her {
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = getIntent().getStringExtra("name");
        Field editField = null;
        for(Field fields : Constants.user.fieldsStuff.fieldsCanEdit){
            if (fields.visualName.equals(name)) {
                editField = fields;
                break;
            }
        }
        if (editField == null) {
            finish();
            return;
        }
        String type = editField.type;
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
        Field finalEditField = editField;
        button.setOnClickListener(v -> {
            if (editText.getText().length() == 0) {
                cr(finalEditField, getCurrentFocus());
                return;
            }
            finalEditField.value = editText.getText().toString();
            switch (type) {
                case "text":
                    editInfo(finalEditField,getCurrentFocus());
                    break;
                case "phone":
                    if (finalEditField.value.length() != 12 || finalEditField.value.indexOf("+") != 0 || !finalEditField.value.startsWith("+7")) {
                        Utils.ShowSnackBar.show(getApplicationContext(), "Номер телефона введен некоректно\nПример: +79112220000", v);
                        return;
                    }
                    editInfo(finalEditField,getCurrentFocus());
                    break;
            }
        });


    }

    private void cr(Field field, View v) {
        EditFieldsDialog cl = new EditFieldsDialog(result -> {
            if (!result) return;
            API.updateFields(new ApiListener() {
                Dialog d;

                @Override
                public void inProcess() {
                    d = openWaiter(EditFieldsLayout.this);
                }

                @Override
                public void onError(JSONObject json) throws JSONException {
                    this.createNotification(v,json.getString("message"));
                    d.dismiss();
                }

                @Override
                public void onSuccess(JSONObject json) throws JSONException {
                    Constants.user.addFields(json.getString("fields"));
                    d.dismiss();
                    finish();
                }
            }, new CustomString("name", field.name), new CustomString("token", Constants.user.token), new CustomString("value", ""));


        }, field.visualName, "Вы действиетльно хотите удалить параметр \"" + field.visualName + "\"?");
        cl.show(getSupportFragmentManager(), "myDialog");
    }

    private void editInfo(Field field, View rootView) {
        API.updateFields(new ApiListener() {
            Dialog d;

            @Override
            public void inProcess() {
                d = openWaiter(EditFieldsLayout.this);
            }

            @Override
            public void onError(JSONObject json) throws JSONException {
                this.createNotification(rootView,json.getString("message"));
                d.dismiss();
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                Constants.user.addFields(json.getString("fields"));
                d.dismiss();
                finish();
            }
        }, new CustomString("name", field.name), new CustomString("token", Constants.user.token), new CustomString("value", field.value));

    }

    private void uncorrectDate(Context c, View v) {
        Utils.ShowSnackBar.show(c, "Дата введена некоректно\nПример: 01/01/2000", v);
    }

}
