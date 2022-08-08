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
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_or_register_layout);
        Button button_login = findViewById(R.id.login_button);
        WebView webView = findViewById(R.id.webvuiew_login_or_register_layout);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl("file:///android_asset/index.html");
        Button button_register = findViewById(R.id.register_button);
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
                                      System.out.println(json);
                                      User user = new User();
                                      user.firstName = json.getString("first_name");
                                      user.lastName = json.getString("last_name");
                                      user.achievementsIds = json.getString("achievement_ids");
                                      user.adminStatus = json.getInt("admin_status");
                                      user.age = json.getInt("age");
                                      JSONObject field_stuff = json.getJSONObject("fields_stuff");
                                      user.addFields(field_stuff.getString("fields"));
                                      user.fieldsStuff = new FieldsStuff(user.fields, user.convertToFields(field_stuff.getString("can_edit_fields")), field_stuff.getInt("max_fields_size"));
                                      user.id = json.getInt("id");
                                      user.profilePictureLink = json.getString("profile_picture");
                                      user.status = json.getString("status");
                                      user.token = json.getString("token");
                                      user.currency = json.getInt("currency");
                                      user.mobileToken = token;
                                      System.out.println(json);
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
