package me.solo_team.futureleader.ui.menu.statical.admining.layouts.users;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class UsersLayout extends Her {

    LinearLayout usersList;
    EditText searchUser;
    TextView noResult;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admining_users_layout);
        setTitle("Пользователи");
        usersList = findViewById(R.id.layout_with_users);
        searchUser = findViewById(R.id.adminig_users_serach);
        noResult = findViewById(R.id.layout_with_users_no_result);
        // TODO: делается запрос на серв для получения пользователей

        JSONObject users = new JSONObject();
        JSONObject user = new JSONObject();
        JSONArray us = new JSONArray();
        try {
            user.put("picture",null);
            user.put("name","Test Name");
            user.put("post","Участник ШБЛ");
            user.put("email","test@test");
            user.put("division","Набор 2020");
            user.put("status",1);
            users.put("users",us.put(user));
            JSONObject user1 = new JSONObject();
            user1.put("picture",null);
            user1.put("name","Anastasia Kostina");
            user1.put("post","Участник ШБЛ");
            user1.put("email","kostinalipsoon@gmail.com");
            user1.put("division","Набор 2020");
            user1.put("status",0);
            users.put("users",us.put(user1));

            addUsers(users.getJSONArray("users"),null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        searchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    addUsers(users.getJSONArray("users"),s.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

    private void addUsers(JSONArray arr, String filterName) throws JSONException {
        usersList.removeAllViews();
        int count = 0;
        for(int i=0;i<arr.length();i++){

            JSONObject o = arr.getJSONObject(i);
            if(filterName!=null) {
                if(!filterName.equals(""))
                    if(!o.getString("name").toLowerCase().contains(filterName.toLowerCase())) continue;
            }
            ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.admining_user_content_layout,null);
            ((TextView) constraintLayout.findViewById(R.id.admining_user_content_name)).setText(o.getString("name"));
            ((TextView) constraintLayout.findViewById(R.id.admining_user_content_email)).setText("email: "+o.getString("email"));
            ((TextView) constraintLayout.findViewById(R.id.admining_user_content_division)).setText(o.getString("division"));
            ((TextView) constraintLayout.findViewById(R.id.admining_user_content_post)).setText(o.getString("post"));
            switch (o.getInt("status")){
                case 0:
                    ((ImageView)constraintLayout.findViewById(R.id.admining_user_content_activity_status)).setColorFilter(Color.RED);
                    break;
//                по дефолту уже 1
//                case 1:
//                    break;
                case 2:
                    ((ImageView)constraintLayout.findViewById(R.id.admining_user_content_activity_status)).setColorFilter(Color.YELLOW);
                    break;

            }
            usersList.addView(constraintLayout);
            count++;
        }
        if(count==0){
            noResult.setText("По вашему запросу \""+filterName+"\" нет результатов :(");
            noResult.setVisibility(View.VISIBLE);
        }
        else{
            noResult.setVisibility(View.GONE);
        }
    }
}
