package com.wolasoft.fiatlux.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.interfaces.ICdDvdService;
import com.wolasoft.fiatlux.models.CdDvd;
import com.wolasoft.fiatlux.services.CdDvdService;

public class CdDvdDetailActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    private static final String BOOK_ID = "cddvd_id";
    private static final int RECOVERY_REQUEST = 1;
    private static final String YOUTUBE_API_KEY =" AIzaSyAOjvOBFGL3DdJdCdqHOoJvvfjL3jBkLTU ";
    private static final String MEDIA_URL = "media_url";
    private TextView cdDvdTitle;
    private TextView cdDvdAuthor;
    private TextView cdDvdDuration;
    private TextView cdDvdResume;
    private TextView buyButton;
    private CdDvdService service;
    private String mediaURL = "";
    private YouTubePlayerView youTubePlayerView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cd_dvd_detail);

        String cdDvdId = getIntent().getStringExtra(BOOK_ID);
        mediaURL = getIntent().getStringExtra(MEDIA_URL);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(YOUTUBE_API_KEY, this);

        Typeface titleTypeFace = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensedRegular.ttf");
        Typeface contentTypeFace = Typeface.createFromAsset(getAssets(), "fonts/RobotoLight.ttf");

        cdDvdTitle = (TextView) findViewById(R.id.video_title);
        cdDvdTitle.setTypeface(titleTypeFace);
        cdDvdAuthor = (TextView) findViewById(R.id.video_author);
        cdDvdAuthor.setTypeface(titleTypeFace);
        cdDvdDuration = (TextView) findViewById(R.id.video_duration);
        cdDvdResume = (TextView) findViewById(R.id.video_resume);
        cdDvdResume.setTypeface(contentTypeFace);
        buyButton = (Button) findViewById(R.id.video_buy);
        buyButton.setTypeface(titleTypeFace);

        Log.i("CD DVD ID", cdDvdId);

        service = CdDvdService.getInstance();

        initializeView(cdDvdId);
    }

    void initializeView(String id){
        service.getById(id, new ICdDvdService.CallBack<CdDvd>() {
            @Override
            public void onSuccess(final CdDvd data) {
                cdDvdTitle.setText(data.getTitle());
                cdDvdAuthor.setText(data.getAuthor());
                cdDvdResume.setText(Html.fromHtml(data.getExcerpt()));
                cdDvdDuration.setText(data.getDuration()+" min");
                buyButton.setText("Acheter ("+data.getPrice()+" €)");
            }

            @Override
            public void onFailure(int statusCode, String message) {
                Toast.makeText(getApplicationContext(), R.string.network_issue, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if(!wasRestored){
            youTubePlayer.cueVideo(mediaURL);
            youTubePlayer.setFullscreen(false);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = getString(R.string.player_error) + youTubeInitializationResult.toString();
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubePlayerView;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YOUTUBE_API_KEY, this);
        }
    }
}
