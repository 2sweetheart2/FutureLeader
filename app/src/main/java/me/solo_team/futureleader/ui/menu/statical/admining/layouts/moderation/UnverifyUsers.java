package me.solo_team.futureleader.ui.menu.statical.admining.layouts.moderation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Date;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;

public class UnverifyUsers extends Her {

    List<UnverifiedUser> unverifiedUsers = new ArrayList<>();
    LinearLayout list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("подтверждение пользователей");
        if(!Constants.user.permission.can_get_unverified_users){
            setResult(-500);
            finish();
        }
        setContentView(R.layout.only_linearlayout);
        list = findViewById(R.id.list);
        API.getUnverifiedUsers(new ApiListener() {
            Dialog d;
            @Override
            public void onError(JSONObject json) throws JSONException {
                d.dismiss();
                setResult(-1);
                finish();
            }

            @Override
            public void inProcess() {
                d = openWaiter(UnverifyUsers.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                JSONArray us = json.getJSONArray("users");
                for(int i=0;i<us.length();i++){
                    unverifiedUsers.add(new UnverifiedUser(us.getJSONObject(i)));
                }
                runOnUiThread(()->{render();d.dismiss();});
            }
        }, new CustomString("token", Constants.user.token));
    }

    HashMap<String, View> views = new HashMap<>();

    public void render(){
        list.removeAllViews();
        for(UnverifiedUser user : unverifiedUsers){
            View view = getLayoutInflater().inflate(R.layout.admin_unverified_user,null);
            ((TextView) view.findViewById(R.id.unverifi_name)).setText(user.getFullName());
            ((TextView) view.findViewById(R.id.unverifi_date)).setText(user.date.toVisibleStr());
            ((TextView) view.findViewById(R.id.unverifi_email)).setText(user.email);
            ((TextView) view.findViewById(R.id.unverifi_phone)).setText(user.phone);
            view.setOnClickListener(v -> {
                Intent intent = new Intent(UnverifyUsers.this,VerifiUser.class);
                intent.putExtra("email",user.email);
                intent.putExtra("name",user.getFullName());
                intent.putExtra("date",user.date.toStr());
                intent.putExtra("phone",user.phone);
                startActivityIfNeeded(intent,100);
            });
            views.put(user.email,view);
            list.addView(view);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(resultCode==1){
                assert data != null;
                String email = data.getStringExtra("email");
                list.removeView(views.get(email));
                for(UnverifiedUser u : unverifiedUsers){
                    if(u.email.equals(email)){
                        unverifiedUsers.remove(u);
                        break;
                    }
                }
                Utils.ShowSnackBar.show(UnverifyUsers.this,"Профиль пользователя успешно подтверждён",list);
            }
            if(resultCode==-1){
                assert data != null;
                Utils.ShowSnackBar.show(UnverifyUsers.this,data.getStringExtra("message"),list);
            }
            if(resultCode==-500){
                Utils.ShowSnackBar.show(UnverifyUsers.this,"отказано в доступе!",list);
            }
            if(resultCode==10){
                assert data != null;
                String email = data.getStringExtra("email");
                list.removeView(views.get(email));
                for(UnverifiedUser u : unverifiedUsers){
                    if(u.email.equals(email)){
                        unverifiedUsers.remove(u);
                        break;
                    }
                }
                Utils.ShowSnackBar.show(UnverifyUsers.this,"Заявка успешно удалена!",list);
            }
        }
    }

    static class UnverifiedUser{
        public String email;
        public String first_name;
        public String last_name;
        public Date date;
        public String phone;

        public UnverifiedUser(JSONObject payload){
            try{
                email = payload.getString("email");
                first_name = payload.getString("first_name");
                last_name = payload.getString("last_name");
                date = new Date(payload.getString("birth"));
                phone = payload.getString("phone");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public String getFullName(){
            return first_name+" "+last_name;
        }
    }
}
