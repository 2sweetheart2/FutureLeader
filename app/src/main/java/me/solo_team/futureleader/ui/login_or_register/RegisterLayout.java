package me.solo_team.futureleader.ui.login_or_register;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerButton;
import com.romainpiel.shimmer.ShimmerTextView;

import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.Date;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.DateTimePicker;

public class RegisterLayout extends AppCompatActivity {

    ShimmerButton button;
    ShimmerTextView textView;

    Date date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.register_layout);


        EditText name = findViewById(R.id.reg_first_name);
        EditText last_name = findViewById(R.id.reg_last_name);
        EditText email = findViewById(R.id.reg_email);
        EditText phone = findViewById(R.id.reg_phone);
        EditText pass = findViewById(R.id.reg_password);
        TextView birth = findViewById(R.id.reg_burthday);
        birth.setOnClickListener(v -> {
            Intent intent = new Intent(this,DateTimePicker.class);
            intent.putExtra("needTime",false);
            startActivityIfNeeded(intent,100);
        });
        ShimmerButton btn = findViewById(R.id.reg_complete);

        new Shimmer().setDuration(1800).start(btn);

        btn.setOnClickListener(v -> {
            if(name.getText().toString().length()==0){
                Utils.ShowSnackBar.show(this,"пустое имя!",name);
                return;
            }
            if(last_name.getText().toString().length()==0){
                Utils.ShowSnackBar.show(this,"пустая фамилия!",name);
                return;
            }
            if(email.getText().toString().length()==0){
                Utils.ShowSnackBar.show(this,"пустая почта!",name);
                return;
            }
            if(phone.getText().toString().length()==0){
                Utils.ShowSnackBar.show(this,"пустой номер телефона!",name);
                return;
            }
            if(pass.getText().toString().length()==0){
                Utils.ShowSnackBar.show(this,"пустой пароль!",name);
                return;
            }
            if(date==null){
                Utils.ShowSnackBar.show(this,"дата рождения не выбрана!",name);
                return;
            }
            if (phone.getText().toString().length() != 12 || phone.getText().toString().indexOf("+") != 0 || !phone.getText().toString().startsWith("+7")) {
                Utils.ShowSnackBar.show(getApplicationContext(), "Номер телефона введен некоректно\nПример: +79112220000", v);
                return;
            }

            API.registerUser(new ApiListener() {
                Dialog d;
                @Override
                public void onError(JSONObject json) throws JSONException {
                    if(json.getInt("code")==11){
                        Utils.ShowSnackBar.show(RegisterLayout.this,"это почта уже ожидает подтверждения",email,"выслать код снова?",v1 -> {
                            API.resendVerification(new ApiListener() {
                                Dialog d1;
                                @Override
                                public void onError(JSONObject json) throws JSONException {
                                    d1.dismiss();
                                    createNotification(email,json.getString("message"));
                                }

                                @Override
                                public void inProcess() {
                                    d1 = openWaiter(RegisterLayout.this);
                                }

                                @Override
                                public void onSuccess(JSONObject json) throws JSONException {
                                    setResult(1);
                                    d1.dismiss();
                                    finish();
                                }
                            }, new CustomString("email",email.getText().toString()));
                        });
                    }else{
                        createNotification(email,json.getString("message"));
                    }
                    d.dismiss();
                }

                @Override
                public void inProcess() {
                    d = openWaiter(RegisterLayout.this);
                }

                @Override
                public void onSuccess(JSONObject json) throws JSONException {
                    setResult(1);
                    d.dismiss();
                    finish();
                }
            },
                    new CustomString("first_name",name.getText().toString()),
                    new CustomString("last_name",last_name.getText().toString()),
                    new CustomString("email",email.getText().toString()),
                    new CustomString("password",pass.getText().toString()),
                    new CustomString("phone",phone.getText().toString()),
                    new CustomString("birth",date.toStr())
                    );
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==1){
            date = new Date(data.getStringExtra("date"));
            date.month=date.month+1;
            System.out.println(date.toStr());
            System.out.println(date.toVisibleStr());
            ((TextView) findViewById(R.id.reg_burthday)).setText("день рождения: "+date.toVisibleStrV2());
        }
    }
}
