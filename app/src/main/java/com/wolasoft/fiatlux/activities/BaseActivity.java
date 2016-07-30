package com.wolasoft.fiatlux.activities;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.wolasoft.fiatlux.R;

public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog progressDialog;
    protected Typeface titleTypeFace;
    protected Typeface contentTypeFace;
    protected Typeface dateTypeFace;
    protected Typeface timeTypeFace;
    protected long enqueue;
    protected DownloadManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        titleTypeFace = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensedRegular.ttf");
        contentTypeFace = Typeface.createFromAsset(getAssets(), "fonts/RobotoLight.ttf");
        dateTypeFace = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensedLight.ttf");
        timeTypeFace = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensedLight.ttf");
        //setupWindowAnimations();

        /*BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    long downloadId = intent.getLongExtra(
                            DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(enqueue);
                    Cursor c = dm.query(query);
                    if (c.moveToFirst()) {
                        int columnIndex = c
                                .getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c
                                .getInt(columnIndex)) {

                            String uriString = c
                                    .getString(c
                                            .getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                        }
                    }
                }
            }
        };
        registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));*/
    }
    
    public void showProgress(String message, boolean cancellable) {
        if (null == progressDialog)
            progressDialog = new ProgressDialog(this);

        if (!progressDialog.isShowing()) {
            progressDialog.setMessage(message);
            progressDialog.setCancelable(cancellable);
            progressDialog.show();
        }
    }

    public void showProgress(int message, boolean cancellable) {
        showProgress(
                this.getResources().getString(message),
                cancellable);
    }

    public void showProgress() {
        showProgress(this.getResources().getString(R.string.on_progress), true);
    }

    public void hideProgress() {
        if (null != progressDialog && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public Typeface getTitleTypeFace() {
        return titleTypeFace;
    }

    public Typeface getContentTypeFace() {
        return contentTypeFace;
    }

    public Typeface getDateTypeFace() {
        return dateTypeFace;
    }

    public Typeface getTimeTypeFace() {
        return timeTypeFace;
    }


    private void setupWindowAnimations() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Transition fade = TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
            getWindow().setEnterTransition(fade);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void showDownload() {
        Intent i = new Intent();
        i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(i);
    }
}
