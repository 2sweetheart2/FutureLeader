package me.solo_team.futureleader.ui.menu.statical.admining.layouts.stat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CDateTime;
import me.solo_team.futureleader.Objects.ChatMember;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.User;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.profile.AddFieldLayout;
import me.solo_team.futureleader.ui.profile.view_prof.ViewProfile;

public class LoginStat extends Her {
    LinearLayout list;
    Button button;
    List<Logins> members = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Журнал входов");
        if(!Constants.user.permission.can_get_log_log){
            setResult(-500);
            finish();
        }
        setContentView(R.layout.stat);
        list = findViewById(R.id.list);
        button = findViewById(R.id.btn);
        button.setOnClickListener(v -> {offset+=25;render();});
        API.getLoginLogs(new ApiListener() {
            Dialog d;
            @Override
            public void onError(JSONObject json) throws JSONException {
                System.out.println(json);
                d.dismiss();
                finish();
            }

            @Override
            public void inProcess() {
                d = openWaiter(LoginStat.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                JSONArray ar = json.getJSONArray("login_logs");
                for(int i=0;i<ar.length();i++){
                    members.add(new Logins(ar.getJSONObject(i)));
                }
                runOnUiThread(()->{render();d.dismiss();});
            }
        },new CustomString("token", Constants.user.token));
    }

    int offset = 25;

    public void render(){
        int of;
        System.out.println(members.size());
        if(members.size()<offset)
            of = members.size();
        else
            of = offset;
        list.removeAllViews();
        for(int i=0;i<of;i++){
            Logins member = members.get(i);
            View view = getLayoutInflater().inflate(R.layout.admin_unverified_user,null);
            ((TextView)view.findViewById(R.id.unverifi_name)).setText(member.chatMember.getFullName());
            ((TextView)view.findViewById(R.id.unverifi_phone)).setText(member.dateTime.toString(true));
            view.setOnClickListener(v -> {
                if(!Constants.user.permission.can_get_user)
                {
                    Utils.ShowSnackBar.show(LoginStat.this,"отказано в доступе!",list);
                    return;
                }
                API.getUser(new ApiListener() {
                    Dialog d;
                    @Override
                    public void onError(JSONObject json) throws JSONException {
                        d.dismiss();
                        createNotification(list,json.getString("message"));
                    }

                    @Override
                    public void inProcess() {
                        d = openWaiter(LoginStat.this);
                    }

                    @Override
                    public void onSuccess(JSONObject json) throws JSONException {
                        User user = new User(json.getJSONObject("user"));
                        d.dismiss();
                        runOnUiThread(()->{
                            Constants.currentUser = user;
                            Intent intent = new Intent(LoginStat.this, ViewProfile.class);
                            intent.putExtra("removeSelf",false);
                            startActivity(intent);
                        });
                    }
                },new CustomString("token",Constants.user.token),new CustomString("id",String.valueOf(member.chatMember.userId)));
            });
            list.addView(view);
        }
    }

    class Logins{
        public CDateTime dateTime;
        public ChatMember chatMember;

        public Logins(JSONObject o){
            try{
                dateTime = new CDateTime(o.getString("date"),true);
                chatMember = new ChatMember(o);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
