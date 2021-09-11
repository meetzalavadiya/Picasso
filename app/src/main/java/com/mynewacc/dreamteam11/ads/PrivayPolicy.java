package com.mynewacc.dreamteam11.ads;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.mynewacc.dreamteam11.R;


public class PrivayPolicy extends AppCompatActivity {

    private WebView web;
    private ImageView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_privacy_policy);

        web = findViewById(R.id.web);
        menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        web.setWebViewClient(new MyWebViewClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDisplayZoomControls(true);

        web.loadUrl("http://autho-playstoreapps.com/privacy_policy.html");
    }

    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}