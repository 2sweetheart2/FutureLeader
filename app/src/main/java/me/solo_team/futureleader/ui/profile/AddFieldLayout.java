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

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Field;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class AddFieldLayout extends Her {
    EditText text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Field> fieldsThatNotAdded = new ArrayList<>();
        for(Field f :Constants.currentUser.fieldsStuff.fieldsCanEdit){
            if(!Constants.currentUser.fieldsStuff.fields.contains(f))
                fieldsThatNotAdded.add(f);
        }


        String[] values = new String[fieldsThatNotAdded.size()];
        for(int i=0;i<fieldsThatNotAdded.size();i++){
            values[i] = fieldsThatNotAdded.get(i).visualName;
        }

        setContentView(R.layout.add_field_layout);

        View root = findViewById(R.id.edit_fields_layout);

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
                String type = fieldsThatNotAdded.get(position).type;
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
            Field field = fieldsThatNotAdded.get(spinner.getSelectedItemPosition());
            String type = field.type;
            if (text.getText().length() == 0)
                Snackbar.make(v, "Данные введены некоректно", Snackbar.LENGTH_LONG).show();
            String data = text.getText().toString();
            Field newFields = new Field(field.name,field.visualName,data,type,true);
            switch (type) {
                case "text":
                    editInfo(newFields,root);
                    break;
                case "phone":
                    if (data.length() != 12 || data.indexOf("+") != 0 || !data.startsWith("+7")) {
                        Utils.ShowSnackBar.show(getApplicationContext(), "Номер телефона введен некоректно\nПример: +79112220000", v);
                        return;
                    }
                    editInfo(newFields,root);
                    break;
            }

        });

    }

    private void editInfo(Field field,View rootView) {
        List<CustomString> params = new ArrayList<>();
        if(Constants.user.adminStatus>0 && Constants.currentUser.id!=Constants.user.id)
            params.add(new CustomString("for_user",String.valueOf(Constants.currentUser.id)));
        params.add(new CustomString("name", field.name));
        params.add(new CustomString("token", Constants.user.token));
        params.add(new CustomString("value",field.value));
        API.updateFields(new ApiListener() {
            Dialog d;

            @Override
            public void inProcess() {
                d = openWaiter(AddFieldLayout.this);
            }

            @Override
            public void onError(JSONObject json) throws JSONException {
                this.createNotification(rootView,json.getString("message"));
                d.dismiss();
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                Constants.currentUser.addFields(json.getString("fields"));
                d.dismiss();
                finish();
            }
        },params);


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
