package com.wolasoft.fiatlux.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;
import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.config.Utils;
import com.wolasoft.fiatlux.interfaces.IPublicityService;
import com.wolasoft.fiatlux.models.Publicity;
import com.wolasoft.fiatlux.services.PublicityService;

public class PublicityDetailsActivity extends BaseActivity {
    private ImageView publicityImage;
    private TextView publicityTitle ;
    private TextView publicityDate;
    private TextView publicityContent;
    //private ImageView mediaTypeImage ;
    private PublicityService service;
    private FloatingActionButton floatingActionButton;

    private final String PUBLICITY_ID = "current_publicity_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String publicityId = Integer.toString(getIntent().getIntExtra(PUBLICITY_ID, 0));
        
        publicityImage = (ImageView) findViewById(R.id.publicity_detail_image);

        publicityTitle = (TextView) findViewById(R.id.publicity_detail_title);
        publicityTitle.setTypeface(getTitleTypeFace());

        publicityDate = (TextView) findViewById(R.id.publicity_detail_date);
        publicityDate.setTypeface(getDateTypeFace());

        publicityContent = (TextView) findViewById(R.id.publicity_detail_content);
        publicityContent.setTypeface(getContentTypeFace());

        //mediaTypeImage = (ImageView) findViewById(R.id.media_type_image);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        service = PublicityService.getInstance();
        initializeView(publicityId);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeView(String publicityId){
        showProgress(R.string.on_progress, false);
        service.getById(publicityId, new IPublicityService.CallBack<Publicity>() {
            @Override
            public void onSuccess(final Publicity data) {
                if (!data.getImage().isEmpty()){
                    Utils.loadImage(getApplicationContext(), publicityImage, data.getImage());
                }

                publicityTitle.setText(data.getTitle());
                publicityDate.setText(data.getPublicationDate());
                publicityContent.setText(Html.fromHtml(data.getExcerpt()));

                if (data.getMediaType().compareToIgnoreCase("VIDEO") == 0) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                    //mediaTypeImage.setImageResource(R.drawable.ic_menu_movie);
                }
                else if(data.getMediaType().compareToIgnoreCase("AUDIO") == 0) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                    //mediaTypeImage.setImageResource(R.drawable.ic_menu_audio);
                }
                else {
                    floatingActionButton.setVisibility(View.GONE);
                    //mediaTypeImage.setImageResource(R.drawable.ic_text);
                }

                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), YouTubePlayer.class);
                        intent.putExtra("media_id", data.getMediaURL());
                        startActivity(intent);
                    }
                });

                hideProgress();
            }

            @Override
            public void onFailure(int statusCode, String message) {
                hideProgress();
            }
        });

    }
}
