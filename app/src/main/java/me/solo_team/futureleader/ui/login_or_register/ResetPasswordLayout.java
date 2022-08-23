package me.solo_team.futureleader.ui.login_or_register;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;

public class ResetPasswordLayout extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.reset_password2);

        findViewById(R.id.reset_password_btn).setOnClickListener(v -> {
            String email = ((EditText)findViewById(R.id.reset_password_email)).getText().toString();

            if(email.length()==0){
                Utils.ShowSnackBar.show(this,"почта пустая!",findViewById(R.id.reset_password_email));
                return;
            }
            API.sendResetPasswordRequest(new ApiListener() {
                Dialog d;
                @Override
                public void onError(JSONObject json) throws JSONException {
                    d.dismiss();
                    createNotification(findViewById(R.id.reset_password_email),json.getString("message"));
                }

                @Override
                public void inProcess() {
                    d = openWaiter(ResetPasswordLayout.this);
                }

                @Override
                public void onSuccess(JSONObject json) throws JSONException {
                    setResult(1);
                    d.dismiss();
                    finish();
                }
            }, new CustomString("email",email));
        });
    }
}
