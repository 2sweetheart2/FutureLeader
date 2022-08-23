package me.solo_team.futureleader.ui.menu.statical.admining.layouts.moderation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Date;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.menu.statical.admining.Her;
import me.solo_team.futureleader.ui.news.open_news.EditNews;

public class VerifiUser extends Her {
    EditText phoneE;
    EditText division;
    EditText post;
    String phone;
    String email;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!Constants.user.permission.can_verifi_user){
            setResult(-500);
            finish();
        }
        setTitle(getIntent().getStringExtra("name"));
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
        System.out.println(phone);
        Date date = new Date(getIntent().getStringExtra("date"));
        setContentView(R.layout.admin_vefiri_user);
        ((TextView) findViewById(R.id.verifi_date)).setText("дата рождения: "+date.toVisibleStr());
        ((TextView)findViewById(R.id.verifi_name)).setText(getIntent().getStringExtra("name"));

        phoneE = findViewById(R.id.verifi_phone);
        division = findViewById(R.id.verifi_division);
        post = findViewById(R.id.verifi_post);

        phoneE.setText(phone);

    }

    public void send(){
        if(phoneE.getText().length()==0){
            Utils.ShowSnackBar.show(VerifiUser.this,"номер телефона не омэет быть пустой!",phoneE,"восстановить?",v -> {phoneE.setText(phone);});
            return;
        }
        if (phoneE.getText().toString().length() != 12 || phoneE.getText().toString().indexOf("+") != 0 || !phoneE.getText().toString().startsWith("+7")) {
            Utils.ShowSnackBar.show(getApplicationContext(), "Номер телефона введен некоректно\nПример: +79112220000", phoneE);
            return;
        }
        if(division.getText().length()==0){
            Utils.ShowSnackBar.show(VerifiUser.this,"Должность не может быть пустой!",phoneE);
            return;
        }
        if(post.getText().length()==0){
            Utils.ShowSnackBar.show(VerifiUser.this,"Подразделение не может быть пустым!",phoneE);
            return;
        }
        API.verifiUser(new ApiListener() {
            Dialog d;
            @Override
            public void onError(JSONObject json) throws JSONException {
                d.dismiss();
                Intent data = new Intent();
                data.putExtra("message",json.getString("message"));
                setResult(-1,data);
                finish();
            }

            @Override
            public void inProcess() {
                d = openWaiter(VerifiUser.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                Intent intent = new Intent();
                intent.putExtra("email",email);
                setResult(1,intent);
                d.dismiss();
                finish();
            }
        },
                new CustomString("token",Constants.user.token),
                new CustomString("email",email),
                new CustomString("division",division.getText().toString()),
                new CustomString("post",post.getText().toString())
                );
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "")
                .setIcon(R.drawable.done)
                .setOnMenuItemClickListener(item -> {
                    send();
                    return true;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onPrepareOptionsMenu(menu);

    }
}
