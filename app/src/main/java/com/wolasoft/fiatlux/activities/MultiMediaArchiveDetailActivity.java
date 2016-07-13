package com.wolasoft.fiatlux.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.interfaces.IMultiMediaArchiveService;
import com.wolasoft.fiatlux.models.MultiMediaArchive;
import com.wolasoft.fiatlux.services.MultiMediaArchiveService;
import com.wolasoft.fiatlux.services.ServiceInterface;

public class MultiMediaArchiveDetailActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final String ARCHIVE_ID = "archive_id";
    private static final String YOUTUBE_API_KEY =" AIzaSyAOjvOBFGL3DdJdCdqHOoJvvfjL3jBkLTU ";
    private static final String MEDIA_URL = "media_url";
    private static final int RECOVERY_REQUEST = 1;
    private String archiveId = "";
    private String mediaURL = "";
    private YouTubePlayerView youTubePlayerView;

    private TextView archiveTitle ;
    private TextView archiveDate;
    private TextView archiveContent;
    private MultiMediaArchiveService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_media_archive_detail);
        mediaURL = getIntent().getStringExtra(MEDIA_URL);
        archiveId = getIntent().getStringExtra(ARCHIVE_ID);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(YOUTUBE_API_KEY, this);

        Typeface titleTypeFace = Typeface.createFromAsset(getAssets(), "fonts/quenta.otf");
        archiveTitle = (TextView) findViewById(R.id.archive_detail_title);
        archiveTitle.setTypeface(titleTypeFace);
        archiveDate = (TextView) findViewById(R.id.archive_detail_date);
        archiveDate.setTypeface(titleTypeFace);

        archiveContent = (TextView) findViewById(R.id.archive_detail_content);
        Typeface contentTypeFace = Typeface.createFromAsset(getAssets(), "fonts/caviar.ttf");
        archiveContent.setTypeface(contentTypeFace);

        service = MultiMediaArchiveService.getInstance();
        initializeView(archiveId);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubePlayerView;
    }

    private void initializeView(String id){
        service.getById(id, new IMultiMediaArchiveService.CallBack<MultiMediaArchive>() {
            @Override
            public void onSuccess(final MultiMediaArchive data) {
                archiveTitle.setText(data.getTitle());
                archiveDate.setText(data.getPublishDate());
                archiveContent.setText(Html.fromHtml(data.getExcerpt()));
                //MEDIA_URL = data.getMediaURL();
            }

            @Override
            public void onFailure(int statusCode, String message) {
                Toast.makeText(getApplicationContext(), R.string.network_issue, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
