package com.wolasoft.fiatlux.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.wolasoft.fiatlux.R;

public class VimeoPlayerActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vimeo_player);
        webView = (WebView) findViewById(R.id.vimeo_webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        String venkat="<video width=\"100%\" height=\"100%\" controls  poster=\"http://www.supportduweb.com/page/media/videoTag/BigBuckBunny.png\">\n" +
                "\t<source src=\"http://www.supportduweb.com/page/media/videoTag/BigBuckBunny.mp4\" type=\"video/mp4\">\n" +
                "\t<source src=\"http://www.supportduweb.com/page/media/videoTag/BigBuckBunny.ogg\" type=\"video/ogg\">\n" +
                "\t<source src=\"http://www.supportduweb.com/page/media/videoTag/BigBuckBunny.webm\" type=\"video/webm\">\n" +
                "\tYour browser does not support the video tag or the file format of this video. <a href=\"http://www.supportduweb.com/\">http://www.supportduweb.com/</a>\n" +
                "</video>";
        webView.loadData(venkat,"text/html","UTF-8");

    }
}
