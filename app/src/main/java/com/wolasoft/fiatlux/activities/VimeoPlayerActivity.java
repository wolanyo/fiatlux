package com.wolasoft.fiatlux.activities;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.vimeo.networking.VimeoClient;
import com.wolasoft.fiatlux.R;

public class VimeoPlayerActivity extends AppCompatActivity {

    private WebView vimeoWebView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vimeo_player);

        vimeoWebView = (WebView) findViewById(R.id.vimeo_webview);

        vimeoWebView.getSettings().setJavaScriptEnabled(true);
        vimeoWebView.getSettings().setAppCacheEnabled(true);
        vimeoWebView.getSettings().setDomStorageEnabled(true);

// how plugin is enabled change in API 8
        vimeoWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        vimeoWebView.loadUrl("file:///android_asset/html/vimeo.html");

    }
}
