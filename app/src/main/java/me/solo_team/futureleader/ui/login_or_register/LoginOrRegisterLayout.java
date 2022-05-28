package me.solo_team.futureleader.ui.login_or_register;

import android.annotation.SuppressLint;
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
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.MainActivity;
import me.solo_team.futureleader.Objects.CustomString;
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
        //webView.loadUrl("file:///android_asset/index.html");
        Button button_register = findViewById(R.id.register_button);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                API.loginUser(new ApiListener() {
                    @Override
                    public void onError(JSONObject json) {
                        try {
                            this.createNotification(view, json.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSuccess(JSONObject json) {
                        try {
                            System.out.println(json);
                            User user = new User();
                            user.firstName = json.getString("first_name");
                            user.lastName = json.getString("last_name");
                            user.achievementsIds = json.getString("achievement_ids");
                            user.adminStatus = json.getInt("admin_status");
                            user.age = json.getInt("age");
                            user.addFields(json.getString("fields"));
                            user.id = json.getInt("id");
                            user.profilePictureLink = json.getString("profile_picture");
                            user.status = json.getString("status");
                            user.token = json.getString("token");
                            Constants.user = user;
                            startActivity(new Intent(LoginOrRegisterLayout.this, MainActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new CustomString("email", "sweet-heart@swht.one"), new CustomString("password", "f1779008"));

            }
        });
    }


}
