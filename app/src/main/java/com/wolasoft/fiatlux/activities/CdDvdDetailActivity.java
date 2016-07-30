package com.wolasoft.fiatlux.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.config.Utils;
import com.wolasoft.fiatlux.interfaces.ICdDvdService;
import com.wolasoft.fiatlux.models.CdDvd;
import com.wolasoft.fiatlux.services.CdDvdService;

public class CdDvdDetailActivity extends BaseActivity{

    private static final String BOOK_ID = "cddvd_id";
    private String MEDIA_ID_TAG = "media_id";
    private TextView cdDvdTitle;
    private TextView cdDvdAuthor;
    private TextView cdDvdDuration;
    private TextView cdDvdResume;
    private TextView buyButton;
    private CdDvdService service;
    private ImageView imageView;
    private FloatingActionButton fab;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cd_dvd_detail);

        String cdDvdId = getIntent().getStringExtra(BOOK_ID);

        imageView = (ImageView) findViewById(R.id.image_view);

        cdDvdTitle = (TextView) findViewById(R.id.video_title);
        cdDvdTitle.setTypeface(getTitleTypeFace());
        cdDvdAuthor = (TextView) findViewById(R.id.video_author);
        cdDvdAuthor.setTypeface(getTitleTypeFace());
        cdDvdDuration = (TextView) findViewById(R.id.video_duration);
        cdDvdResume = (TextView) findViewById(R.id.video_resume);
        cdDvdResume.setTypeface(getContentTypeFace());
        buyButton = (Button) findViewById(R.id.video_buy);
        buyButton.setTypeface(getTitleTypeFace());

        //Log.i("CD DVD ID", cdDvdId);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        service = CdDvdService.getInstance();

        initializeView(cdDvdId);
    }

    void initializeView(String id){
        service.getById(id, new ICdDvdService.CallBack<CdDvd>() {
            @Override
            public void onSuccess(final CdDvd data) {
                if (data.getImage() != null) {
                    if (!data.getImage().isEmpty()) {
                        Utils.loadImage(getApplicationContext(), imageView, data.getImage());
                    }
                }
                cdDvdTitle.setText(data.getTitle());
                cdDvdAuthor.setText(data.getAuthor());
                cdDvdResume.setText(Html.fromHtml(data.getExcerpt()));
                cdDvdDuration.setText(data.getDuration()+" min");
                buyButton.setText("Acheter ("+data.getPrice()+" €)");

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), VimeoPlayerActivity.class);
                        intent.putExtra(MEDIA_ID_TAG, data.getMediaURL());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, String message) {
                Toast.makeText(getApplicationContext(), R.string.network_issue, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
