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

        //aboutTextView.setTypeface(contentTypeFace);
        new DownloadImageTask().execute();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private class DownloadImageTask extends AsyncTask<Void, Void, String> {
        /**
         * The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute()
         */
        @Override
        protected String doInBackground(Void... voids) {
            String text = getString(R.string.about_text);
            return  text;
        }

        /**
         * The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground()
         */
        protected void onPostExecute(String text) {
            aboutTextView.loadUrl("file:///android_asset/html/about.html");
        }


    }
}