package com.wolasoft.fiatlux.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

import com.wolasoft.fiatlux.R;

import java.io.IOException;

public class ForewordActivity extends BaseActivity {

    private WebView aboutTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        aboutTextView = (WebView) findViewById(R.id.about_textview);

        aboutTextView.loadUrl("file:///android_asset/html/about.html");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}