package me.solo_team.futureleader.ui.login_or_register;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.romainpiel.shimmer.Shimmer;

import org.json.JSONException;
import org.json.JSONObject;

import me.solo_team.futureleader.API.API;
import me.solo_team.futureleader.API.ApiListener;
import me.solo_team.futureleader.API.HTTPS;
import me.solo_team.futureleader.BuildConfig;
import me.solo_team.futureleader.CSettings;
import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.MainActivity;
import me.solo_team.futureleader.Objects.CustomString;
import me.solo_team.futureleader.Objects.User;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ResetPassword;
import me.solo_team.futureleader.dialogs.OldVersion;
import me.solo_team.futureleader.stuff.Utils;
import me.solo_team.futureleader.ui.WebViewsContent.MyWebViewClient;

public class LoginOrRegisterLayout extends AppCompatActivity {

    private Animation mFadeInAnimation, mFadeOutAnimation, FadeInAnimatio2;
    ConstraintLayout huina;
    Switch aSwitch;
    ImageView imageView;
    WebView webView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String action = getIntent().getAction();
        final String data = getIntent().getDataString();

        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            final String jobId = data.substring(data.lastIndexOf("/") + 1);
            Intent intent = new Intent(this, ResetPassword.class);
            intent.putExtra("data", jobId);
            startActivity(intent, null);
        }

        API.getVersion(new ApiListener() {
            Dialog d;

            @Override
            public void onError(JSONObject json) throws JSONException {
                d.dismiss();
                runOnUiThread(()->setBlocked());
            }

            @Override
            public void inProcess() {
                d = openWaiter(LoginOrRegisterLayout.this);
            }

            @Override
            public void onSuccess(JSONObject json) throws JSONException {
                d.dismiss();
                if (!BuildConfig.VERSION_NAME.equals(json.getString("version")))
                    runOnUiThread(()->setBlocked());
                else
                    runOnUiThread(()->enter());
            }
        });


    }

    public void setBlocked() {
        OldVersion oldVersion = new OldVersion(this);
        oldVersion.show(getSupportFragmentManager(), null);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void enter() {
        Constants.settings = new CSettings(LoginOrRegisterLayout.this);
        setContentView(R.layout.login_or_register_layout);
        aSwitch = findViewById(R.id.login_or_register_swith);

        if (getIntent().hasExtra("notif"))
            Utils.ShowSnackBar.show(this, getIntent().getStringExtra("notif"), aSwitch);

        webView = findViewById(R.id.webvuiew_login_or_register_layout);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl("file:///android_asset/index.html");
        huina = findViewById(R.id.huina);
        imageView = findViewById(R.id.logo);

        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                webView.setVisibility(View.VISIBLE);
            else
                webView.setVisibility(View.GONE);
            Constants.settings.saveStartChecker(isChecked);
        });
        aSwitch.setChecked(Constants.settings.getStartChecker());

        if (Constants.settings.getStartScreen()) {
            mFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
            mFadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fadeout);
            FadeInAnimatio2 = AnimationUtils.loadAnimation(this, R.anim.fadein);
            mFadeInAnimation.setAnimationListener(animationFadeInListener);
            mFadeOutAnimation.setAnimationListener(animationFadeOutListener);
            imageView.startAnimation(mFadeInAnimation);
        } else {
            webView.setAlpha(1);
            findViewById(R.id.huina).setAlpha(1);
            imageView.setAlpha(0);
            check();
        }
    }

    public void check() {
        GFB();
        Constants.MainContext = getApplicationContext();
        HTTPS.getMobileToken(token -> {
            if (Constants.settings.getUserEmail() != null) {
                login(Constants.settings.getUserEmail(), Constants.settings.getUserPassword(), token, true);
            }
            new Shimmer().setDuration(1800).start(findViewById(R.id.login_button));
            new Shimmer().setDuration(1800).setDirection(1).start(findViewById(R.id.register_button));

            findViewById(R.id.register_button).setOnClickListener(v -> startActivityIfNeeded(new Intent(this, RegisterLayout.class), 101));
            findViewById(R.id.login_button).setOnClickListener(v -> {
                ConstraintLayout view = (ConstraintLayout) getLayoutInflater().inflate(R.layout.login_btns, null);
                huina.addView(view, huina.getChildAt(0).getLayoutParams());
                huina.removeViewAt(0);
                new Shimmer().setDuration(1800).start(findViewById(R.id.login_button));
                EditText login = findViewById(R.id.log_edit_text);
                EditText pass = findViewById(R.id.pass_edit_text);
                TextView textView = findViewById(R.id.forgot_password);
                textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                textView.setOnClickListener(v1 -> {
                    startActivityIfNeeded(new Intent(this, ResetPasswordLayout.class), 100);
                });

                findViewById(R.id.login_button).setOnClickListener(v1 -> {
                    if (login.getText().length() == 0) {
                        Utils.ShowSnackBar.show(LoginOrRegisterLayout.this, "почта не может быть пустой!", v1);
                        return;
                    }
                    if (pass.getText().length() == 0) {
                        Utils.ShowSnackBar.show(LoginOrRegisterLayout.this, "пароль не может быть пустым!", v1);
                        return;
                    }
                    login(login.getText().toString(), pass.getText().toString(), token, false);
                    // sweet-heart@swht.one f1779008
                });
            });
        });

    }

    public void login(String login, String password, String token, boolean bySave) {
        API.loginUser(new ApiListener() {
                          Dialog d = null;

                          @Override
                          public void onError(JSONObject json) throws JSONException {
                              this.createNotification(findViewById(R.id.huina), json.getString("message"));
                              d.dismiss();
                              if (bySave) {
                                  Constants.settings.removeUserInfo();
                              }
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
                              if (!bySave)
                                  Constants.settings.saveUserInfo(password, login);
                              finish();
                          }
                      },
                new CustomString("email", login),
                new CustomString("password", password)
        );
    }

//                button_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                API.loginUser(new ApiListener() {
//                                  Dialog d = null;
//
//                                  @Override
//                                  public void onError(JSONObject json) throws JSONException {
//                                      this.createNotification(view, json.getString("message"));
//                                      d.dismiss();
//                                  }
//
//                                  @Override
//                                  public void inProcess() {
//                                      d = this.openWaiter(LoginOrRegisterLayout.this);
//                                  }
//
//                                  @Override
//                                  public void onSuccess(JSONObject json) throws JSONException {
//                                      User user = new User(json);
//                                      user.mobileToken = token;
//                                      Constants.user = user;
//                                      d.dismiss();
//                                      startActivity(new Intent(LoginOrRegisterLayout.this, MainActivity.class));
//                                      finish();
//                                  }
//                              },
//                        new CustomString("email", "sweet-heart@swht.one"),
//                        new CustomString("password", "f1779008")
//                );
//
//            }
    //}));


    Animation.AnimationListener animationFadeOutListener = new Animation.AnimationListener() {

        @Override
        public void onAnimationEnd(Animation animation) {
            imageView.setAlpha(0);
            check();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }
    };

    Animation.AnimationListener animationFadeInListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            webView.setAlpha(1);
            findViewById(R.id.huina).setAlpha(1);
            webView.startAnimation(FadeInAnimatio2);
            findViewById(R.id.huina).startAnimation(FadeInAnimatio2);
            imageView.startAnimation(mFadeOutAnimation);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }
    };


    private void GFB() {
        final String CHANNEL_ID = "my_channel_01";
        final String CHANNEL_NAME = "все уведомления";
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 1)
            Utils.ShowSnackBar.show(this, "на вашу почту выслано письмо о сбросе пароля, проверте её!", findViewById(R.id.huina));
        if (requestCode == 101 && resultCode == 1)
            Utils.ShowSnackBar.show(this, "на вашу почту выслано письмо с подтверждением. После, ожидайте подтверждение модерации!", findViewById(R.id.huina));
    }
}
