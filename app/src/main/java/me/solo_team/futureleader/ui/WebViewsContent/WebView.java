package me.solo_team.futureleader.ui.WebViewsContent;

import android.annotation.SuppressLint;
import android.os.Bundle;

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
