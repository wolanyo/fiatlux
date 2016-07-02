package com.wolasoft.fiatlux.activities;

import android.app.ProgressDialog;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        titleTypeFace = Typeface.createFromAsset(getAssets(), "fonts/quenta.otf");
        contentTypeFace = Typeface.createFromAsset(getAssets(), "fonts/lato-light.ttf");
        dateTypeFace = Typeface.createFromAsset(getAssets(), "fonts/quenta.otf");
        timeTypeFace = Typeface.createFromAsset(getAssets(), "fonts/quenta.otf");
        //setupWindowAnimations();
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
}
