package me.solo_team.futureleader.ui.WebViewsContent;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.solo_team.futureleader.R;

public class WebView extends AppCompatActivity {
    private android.webkit.WebView webView;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.only_web_view);
        webView = findViewById(R.id.webView);

        // включаем поддержку JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        // указываем страницу загрузки
        if(getIntent().getStringExtra("localhost")!=null){
            webView.loadUrl(getIntent().getStringExtra("localhost"));
        }
        else if(getIntent().getStringExtra("video")!=null){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            webView.loadUrl(getIntent().getStringExtra("video"));
        }
        else if(getIntent().getStringExtra("photo")!=null){
            webView.setBackgroundColor(Color.BLACK);
            webView.getSettings().setSupportZoom(true);
            webView.setInitialScale(-100);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.setPadding(0, 0, 0, 0);
            webView.setScrollbarFadingEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.loadUrl(getIntent().getStringExtra("photo"));
        }
        else
            webView.loadUrl(getIntent().getStringExtra("url"));

    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
