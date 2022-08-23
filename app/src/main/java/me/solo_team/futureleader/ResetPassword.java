package me.solo_team.futureleader;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.login_or_register.LoginOrRegisterLayout;

public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.reset_password);

        final Intent intent = getIntent();
        final String action = intent.getAction();
        final String data = intent.getDataString();
        if(Constants.settings==null)
            Constants.settings = new CSettings(this);
        if(Constants.settings.getUserEmail()!=null) {
            Intent intent1 = new Intent(ResetPassword.this, LoginOrRegisterLayout.class);
            intent1.putExtra("notif","Простите, но вы уже авторизированы!");
            startActivity(intent1);
            finish();
        }
        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            final String code = data.substring(data.lastIndexOf("/") + 1).split("=")[1];
            findViewById(R.id.reset_password_btn).setOnClickListener(v -> {
                String password = ((EditText) findViewById(R.id.reset_password_1)).getText().toString();
                if(password.length()==0){
                    Utils.ShowSnackBar.show(this,"введите пароль!",findViewById(R.id.reset_password_1));
                    return;
                }
                if(!password.equals(((EditText)findViewById(R.id.reset_password_2)).getText().toString())){
                    Utils.ShowSnackBar.show(this,"Пароли не совпадают!",findViewById(R.id.reset_password_2));
                    return;
                }
                API.resetPassword(new ApiListener() {
                    Dialog d;
                    @Override
                    public void onError(JSONObject json) throws JSONException {
                        d.dismiss();
                        createNotification(findViewById(R.id.reset_password_1),json.getString("message"));
                    }

                    @Override
                    public void inProcess() {
                        d = openWaiter(ResetPassword.this);
                    }

                    @Override
                    public void onSuccess(JSONObject json) throws JSONException {
                        setResult(1);
                        d.dismiss();
                        Intent intent1 = new Intent(ResetPassword.this, LoginOrRegisterLayout.class);
                        intent1.putExtra("notif","Пароль успешно измененён");
                        startActivity(intent1);
                        finish();
                    }
                }, new CustomString("code",code),new CustomString("password",password));
            });

        }
    }
}
