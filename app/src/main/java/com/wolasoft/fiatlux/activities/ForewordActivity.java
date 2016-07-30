package com.wolasoft.fiatlux.activities;

import android.os.Bundle;
import android.webkit.WebView;

import com.wolasoft.fiatlux.R;

public class ForewordActivity extends BaseActivity {

    private WebView aboutTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreword);
        aboutTextView = (WebView) findViewById(R.id.about_textview);

        aboutTextView.loadUrl("file:///android_asset/html/about.html");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}