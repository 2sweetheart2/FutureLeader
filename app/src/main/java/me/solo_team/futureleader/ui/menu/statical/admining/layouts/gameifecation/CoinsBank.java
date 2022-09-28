package me.solo_team.futureleader.ui.menu.statical.admining.layouts.gameifecation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.MediaAudioAdapters.VideoAdapter.VideoView;
import me.solo_team.futureleader.Objects.ChatMember;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.SelectMembers;
import me.solo_team.futureleader.ui.menu.statical.admining.AdminingLayout;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.menu.statical.admining.layouts.moderation.OnlyEditText;

public class CoinsBank extends Her {

    TextView all;
    TextView paid;
    TextView ver;
    Button reset;
    Button add;

    int has;
    int pa;
    int is;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Валютный банк");
        if(!Constants.user.permission.can_get_all_currency){
            setResult(-500);
            finish();
        }
        setContentView(R.layout.conis_bank);
        all = findViewById(R.id.all_fbl);
        paid = findViewById(R.id.paid_fbl);
        ver = findViewById(R.id.ver_fbl);
        reset = findViewById(R.id.reset_fbl_stat);
        add = findViewById(R.id.add_fbl);
        API.getCurrency(new ApiListener() {
            Dialog d;
            @Override
            public void onError(JSONObject json) throws JSONException {
                d.dismiss();
                createNotification(all,json.getString("message"));
            }

            @Override
            public void inProcess() {
                d = openWaiter(CoinsBank.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                has = json.getInt("has");
                pa = json.getInt("spent");
                is = json.getInt("issued");
                runOnUiThread(() -> render());
                d.dismiss();
            }
        },new CustomString("token",Constants.user.token));
    }

    public void addCurrency(int cost, int user_id, String reset){
        if(!Constants.user.permission.can_add_currency){
            Utils.ShowSnackBar.show(CoinsBank.this,"отказано в доступе!",all);
            return;
        }
        if(reset!=null){
            API.addCurrency(new ApiListener() {
                Dialog d;
                @Override
                public void onError(JSONObject json) throws JSONException {
                    d.dismiss();
                    createNotification(all,json.getString("message"));
                }

                @Override
                public void inProcess() {
                    d = openWaiter(CoinsBank.this);
                }

                @Override
                public void onSuccess(JSONObject json) throws JSONException {
                    runOnUiThread(()->{
                        pa=0;
                        is=0;
                        render();
                    });
                    d.dismiss();
                }
            },new CustomString("token",Constants.user.token),
                    new CustomString("user_id",6),
                    new CustomString("reset","true"),
                    new CustomString("coins","0")
                    );
        }else{
            API.addCurrency(new ApiListener() {
                                Dialog d;
                                @Override
                                public void onError(JSONObject json) throws JSONException {
                                    d.dismiss();
                                    createNotification(all,json.getString("message"));
                                }

                                @Override
                                public void inProcess() {
                                    d = openWaiter(CoinsBank.this);
                                }

                                @Override
                                public void onSuccess(JSONObject json) throws JSONException {
                                    runOnUiThread(()->{
                                        if(cost<0)
                                            pa=pa+cost*-1;
                                        else
                                            is=is+cost;
                                        has=has+cost;
                                        render();
                                    });
                                    d.dismiss();
                                }
                            },new CustomString("token",Constants.user.token),
                    new CustomString("user_id",user_id),
                    new CustomString("coins",String.valueOf(cost))
            );
        }
    }


    public void render(){
        all.setText("Всего FBL у пользователей: "+has);
        paid.setText("Всего FBL было потрачено: "+pa);
        ver.setText("Всего FBL было выдано: "+is);
        reset.setOnClickListener(v -> {
            AlertDialog.Builder obj = new AlertDialog.Builder(CoinsBank.this);
            obj.setTitle("Сбросить статистику?");
            obj.setIcon(R.drawable.resize_300x0);
            obj.setPositiveButton("да", (dialog, which) -> {
                addCurrency(0,0,"true");
            });
            obj.setNegativeButton("нет", null);
            obj.show();
        });

        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, SelectMembers.class);
            intent.putExtra("needStuff", false);
            intent.putExtra("removeSelf", false);
            intent.putExtra("selectOne", true);
            intent.putExtra("forChat", true);
            intent.putExtra("isChatMember", true);
            intent.putExtra("needUserObject", true);
            intent.putExtra("needShowSurveyStat", false);
            startActivityIfNeeded(intent,100);
        });
    }
    ChatMember member;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==1){
            try {
                String dataUser = data.getStringExtra("user");
                member = new ChatMember(new JSONObject(dataUser));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(CoinsBank.this, OnlyEditText.class);
            intent.putExtra("numbers",true);
            intent.putExtra("title","Выдача FBL");
            intent.putExtra("hint","FBL...");
            startActivityIfNeeded(intent,10);

        }
        if(requestCode==10 && resultCode==1){
            addCurrency(Integer.parseInt(data.getStringExtra("text")),member.userId,null);
        }
    }
}
