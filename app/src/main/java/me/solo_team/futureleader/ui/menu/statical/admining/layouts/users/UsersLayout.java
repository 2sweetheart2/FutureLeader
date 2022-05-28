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

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.User;
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


        API.getUsers(new ApiListener() {
            @Override
            public void onError(JSONObject json) {
                try {
                    createNotification(findViewById(R.id.admining_users_layout),json.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void inProcess() {

            }

            @Override
            public void onSuccess(JSONObject json) {
                try {
                    JSONArray arr = json.getJSONArray("users");
                    addUsers(arr,null);
                    System.out.println(arr.toString());
                    searchUser.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            try {
                                addUsers(arr,s.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {}
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new CustomString("token", Constants.user.token));

    }

    private void addUsers(JSONArray arr, String filterName) throws JSONException {
        runOnUiThread(()->usersList.removeAllViews());
        int count = 0;

        for(int i=0;i<arr.length();i++){
            JSONObject o = arr.getJSONObject(i);
            if(filterName!=null) {
                if(!filterName.equals(""))
                    if(!(o.getString("first_name")+" "+o.getString("last_name")).toLowerCase().contains(filterName.toLowerCase())) continue;
            }
            User user = new User();
            user.lastName = o.getString("last_name");
            user.firstName = o.getString("first_name");
            user.addFields(o.getString("fields"));

            String division;
            if(user.user_fields.has("division")) {
                String[] str = user.user_fields.getString("division").split(">");
                division = str[str.length-1];
            }
            else
                division = "Отсутствует";
            String post;
            if(user.user_fields.has("post"))
                post = user.user_fields.getString("post");
            else
                post = "Отсутствует";

            ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.admining_user_content_layout,null);
            ((TextView) constraintLayout.findViewById(R.id.admining_user_content_name)).setText(user.firstName+" "+user.lastName);
            ((TextView) constraintLayout.findViewById(R.id.admining_user_content_email)).setText("email: "+o.getString("email"));
            ((TextView) constraintLayout.findViewById(R.id.admining_user_content_division)).setText(division);
            ((TextView) constraintLayout.findViewById(R.id.admining_user_content_post)).setText(post);
            Constants.cache.addPhoto(o.getString("profile_picture"),true, constraintLayout.findViewById(R.id.admining_user_content_logo),this);
            runOnUiThread(()-> usersList.addView(constraintLayout));
            count++;
        }
        int finalCount = count;
        runOnUiThread(()->{
        if(finalCount ==0){
            noResult.setText("По вашему запросу \""+filterName+"\" нет результатов :(");
            noResult.setVisibility(View.VISIBLE);
        }
        else{
            noResult.setVisibility(View.GONE);
        }
        });
    }
}
