package me.solo_team.futureleader.ui.login_or_register;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.solo_team.futureleader.R;

public class LoginOrRegisterLayout extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_or_register_layout);
        Button button_login = findViewById(R.id.login_button);
        Button button_register = findViewById(R.id.register_button);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Пидаарас");
            }
        });
    }


}
