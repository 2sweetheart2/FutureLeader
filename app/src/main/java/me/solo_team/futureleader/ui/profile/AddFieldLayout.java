package me.solo_team.futureleader.ui.profile;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class AddFieldLayout extends Her {
    EditText text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_field_layout);
        String[] values = getIntent().getStringExtra("values").split(",");
        Spinner spinner = findViewById(R.id.edit_fields_name);
        text = findViewById(R.id.edit_fields_value);
        Button button = findViewById(R.id.edit_fields_btn);
        MyCustomAdapter adapter = new MyCustomAdapter(this,
                R.layout.spinner_text, values);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text.setText("");
                String type = Constants.user.editedFieldsTypes.get(values[position]);
                System.out.println(values[position]);
                try {
                    switch (type) {
                        case "text":
                            text.setInputType(InputType.TYPE_CLASS_TEXT);
                            break;
                        case "phone":
                            text.setInputType(InputType.TYPE_CLASS_PHONE);
                            break;
                    }
                } catch (NullPointerException e) {
                    text.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setTitle("Добавить поле");
        button.setOnClickListener(v -> {
            String type = Constants.user.editedFieldsTypes.get(values[spinner.getSelectedItemPosition()]);
            if (text.getText().length() == 0)
                Snackbar.make(v, "Данные введены некоректно", Snackbar.LENGTH_LONG).show();
            String data = text.getText().toString();
            switch (type) {
                case "text":
                    editInfo(values[spinner.getSelectedItemPosition()], data);
                    break;
                case "phone":
                    if (data.length() != 12 || data.indexOf("+") != 0 || !data.startsWith("+7")) {
                        Utils.ShowSnackBar.show(getApplicationContext(), "Номер телефона введен некоректно\nПример: +79112220000", v);
                        return;
                    }
                    editInfo(values[spinner.getSelectedItemPosition()], data);
                    break;
            }
        });
        //

    }

    private void editInfo(String name, String value) {
        try {
            Constants.user.user_fields.put(Constants.user.editedFieldsTypes.get(name.toLowerCase()), value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        API.updateFields(new ApiListener() {
            Dialog d;

            @Override
            public void inProcess() {
                d = openWaiter(AddFieldLayout.this);
            }

            @Override
            public void onError(JSONObject json) {

            }

            @Override
            public void onSuccess(JSONObject json) {
                d.dismiss();
            }
        }, new CustomString("fields", Constants.currentUser.getFields()), new CustomString("token", Constants.user.token),new CustomString("current_id",String.valueOf(Constants.currentUser.id)));

        finish();
    }

    public class MyCustomAdapter extends ArrayAdapter<String> {
        String[] objects;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               String[] objects) {
            super(context, textViewResourceId, objects);
            this.objects = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {

            return getCustomView(position, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return getCustomView(position, parent);
        }

        public View getCustomView(int position,
                                  ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.spinner_text, parent, false);
            TextView label = row.findViewById(R.id.spinner_text);
            label.setText(objects[position]);
            return row;
        }

    }
}
