package me.solo_team.futureleader.ui.menu.statical.admining.layouts.users;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Field;
import me.solo_team.futureleader.Objects.FieldsStuff;
import me.solo_team.futureleader.Objects.User;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class UsersLayout extends Her {

    LinearLayout usersList;
    EditText searchUser;
    TextView noResult;
    int offset = 0;
    int oldOffset;
    int curentSize = 0;
    TextView count;
    LinearLayout menu;
    JSONArray arr;
    JSONArray filteredList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admining_users_layout);
        setTitle("Пользователи");
        usersList = findViewById(R.id.layout_with_users);
        searchUser = findViewById(R.id.adminig_users_serach);
        noResult = findViewById(R.id.layout_with_users_no_result);
        menu = findViewById(R.id.layout_with_users_result);
        count = findViewById(R.id.layout_with_users_offset);
        TextView back = findViewById(R.id.layout_with_users_btn_back);
        TextView next = findViewById(R.id.layout_with_users_btn_next);
        // TODO: делается запрос на серв для получения пользователей


        API.getUsers(new ApiListener() {
            Dialog d;

            @Override
            public void onError(JSONObject json) {
                try {
                    createNotification(findViewById(R.id.admining_users_layout), json.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void inProcess() {
                d = openWaiter(UsersLayout.this);
            }

            @Override
            public void onSuccess(JSONObject json) {
                try {
                    arr = json.getJSONArray("users");

                    addUsers(null);
                    d.dismiss();
                    searchUser.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            try {
                                if (s.toString().length() == 0) offset = oldOffset;
                                else {
                                    oldOffset = offset;
                                    offset = 0;
                                }
                                addUsers(s.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new CustomString("token", Constants.user.token));
        back.setOnClickListener(v -> {
            if (offset == 0) Snackbar.make(v, "Это начало списка", Snackbar.LENGTH_LONG)
                    .show();
            else {
                offset--;
                try {
                    String text = null;
                    if (searchUser.getText().toString().length() != 0)
                        text = searchUser.getText().toString();
                    addUsers(text);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        next.setOnClickListener(v -> {
            if (offset == curentSize / 10)
                Snackbar.make(v, "Это конец списка", Snackbar.LENGTH_LONG)
                        .show();
            else {
                offset++;
                try {
                    String text = null;
                    if (searchUser.getText().toString().length() != 0)
                        text = searchUser.getText().toString();
                    addUsers(text);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void copy() {
        filteredList = arr;
        curentSize = filteredList.length();
    }

    private void filterArray(String filterName) throws JSONException {
        if (filterName == null) {
            copy();
            return;
        }
        if (filterName.equals("")) {
            copy();
            return;
        }
        filteredList = new JSONArray();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject o = arr.getJSONObject(i);
            if (!(o.getString("first_name") + " " + o.getString("last_name")).toLowerCase().contains(filterName.toLowerCase()))
                continue;
            filteredList.put(o);
        }
        curentSize = filteredList.length();
    }

    private void addUsers(String filterName) throws JSONException {
        runOnUiThread(() -> usersList.removeAllViews());
        int count = 0;
        int lastIndex = 0;
        filterArray(filterName);
        for (int i = (10 * offset); i < (10 * offset) + 10; i++) {
            if (i >= curentSize) continue;
            JSONObject o = filteredList.getJSONObject(i);
            lastIndex = i + 1;
            User user = new User();
            user.lastName = o.getString("last_name");
            user.firstName = o.getString("first_name");
            JSONObject field_stuff = o.getJSONObject("fields_stuff");
            user.addFields(field_stuff.getString("fields"));
            user.fieldsStuff = new FieldsStuff(user.fields, user.convertToFields(field_stuff.getString("can_edit_fields")), field_stuff.getInt("max_fields_size"));
            user.id = o.getInt("id");
            String division = "Отсутствует";
            String post = "Отсутствует";

            for (Field field : user.fields) {
                if (field.name.equals("division"))
                    division = field.value;
                if (field.name.equals("post"))
                    post = field.value;
            }
            ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.admining_user_content_layout, null);
            ((TextView) constraintLayout.findViewById(R.id.admining_user_content_name)).setText(user.firstName + " " + user.lastName);
            ((TextView) constraintLayout.findViewById(R.id.admining_user_content_email)).setText("email: " + o.getString("email"));
            ((TextView) constraintLayout.findViewById(R.id.admining_user_content_division)).setText(division);
            ((TextView) constraintLayout.findViewById(R.id.admining_user_content_post)).setText(post);
            Constants.cache.addPhoto(o.getString("profile_picture"), true, constraintLayout.findViewById(R.id.admining_user_content_logo), this);
            runOnUiThread(() -> usersList.addView(constraintLayout));
            constraintLayout.setOnClickListener(v -> {
                Intent intent = new Intent(this, ViewUserProfile.class);
                intent.putExtra("id", String.valueOf(user.id));
                startActivity(intent);
            });
            count++;
        }
        int finalCount = count;
        int finalLastIndex = lastIndex;
        runOnUiThread(() -> {
            if (finalCount == 0) {
                menu.setVisibility(View.GONE);
                noResult.setText("По вашему запросу \"" + filterName + "\" нет результатов :(");
                noResult.setVisibility(View.VISIBLE);
            } else {
                noResult.setVisibility(View.GONE);
                menu.setVisibility(View.VISIBLE);
                String text_;
                if (offset == 0) text_ = "1";
                else text_ = String.valueOf((10 * offset));
                this.count.setText("Показаны пользователи с " + text_ + " по " + finalLastIndex + " (из " + (curentSize) + " )");
            }
        });
    }
}
