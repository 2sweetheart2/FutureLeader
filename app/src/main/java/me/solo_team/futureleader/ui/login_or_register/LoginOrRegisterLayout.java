package me.solo_team.futureleader.ui.login_or_register;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.API.HTTPS;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.MainActivity;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.FieldsStuff;
import me.solo_team.futureleader.Objects.User;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.WebViewsContent.MyWebViewClient;

public class LoginOrRegisterLayout extends AppCompatActivity {

    Switch aSwitch;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_or_register_layout);
        Button button_login = findViewById(R.id.login_button);
        aSwitch = findViewById(R.id.login_or_register_swith);
        WebView webView = findViewById(R.id.webvuiew_login_or_register_layout);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl("file:///android_asset/index.html");
        Button button_register = findViewById(R.id.register_button);
        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                webView.setVisibility(View.VISIBLE);
            else
                webView.setVisibility(View.GONE);
        });
        GFB();
        Constants.MainContext = getApplicationContext();
        HTTPS.getMobileToken(token -> button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                API.loginUser(new ApiListener() {
                                  Dialog d = null;

                                  @Override
                                  public void onError(JSONObject json) throws JSONException {
                                      this.createNotification(view, json.getString("message"));
                                      d.dismiss();
                                  }

                                  @Override
                                  public void inProcess() {
                                      d = this.openWaiter(LoginOrRegisterLayout.this);
                                  }

                                  @Override
                                  public void onSuccess(JSONObject json) throws JSONException {
                                      User user = new User(json);
                                      user.mobileToken = token;
                                      Constants.user = user;
                                      d.dismiss();
                                      startActivity(new Intent(LoginOrRegisterLayout.this, MainActivity.class));
                                      finish();
                                  }
                              },
                        new CustomString("email", "sweet-heart@swht.one"),
                        new CustomString("password", "f1779008")
                );

            }
        }));

    }


    private void GFB() {
        final String CHANNEL_ID = "my_channel_01";
        final String CHANNEL_NAME = "Simplified Coding Notification";
        final String CHANNEL_DESCRIPTION = "future-leaders.ru";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            mChannel.setDescription(CHANNEL_DESCRIPTION);

            mChannel.setLightColor(getResources().getColor(R.color.secondary));
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }
}
