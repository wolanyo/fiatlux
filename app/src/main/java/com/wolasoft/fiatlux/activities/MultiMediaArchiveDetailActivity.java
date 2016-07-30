package com.wolasoft.fiatlux.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.config.Utils;
import com.wolasoft.fiatlux.interfaces.IMultiMediaArchiveService;
import com.wolasoft.fiatlux.models.MultiMediaArchive;
import com.wolasoft.fiatlux.services.MultiMediaArchiveService;

public class MultiMediaArchiveDetailActivity extends BaseActivity {

    private static final String ARCHIVE_ID = "archive_id";
    private static final String MEDIA_ID_TAG = "media_id";
    private String archiveId = "";
    private ImageView imageView;

    private TextView archiveTitle ;
    private TextView archiveDate;
    private TextView archiveContent;
    private MultiMediaArchiveService service;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_media_archive_detail);
        archiveId = getIntent().getStringExtra(ARCHIVE_ID);

        imageView = (ImageView) findViewById(R.id.image_view);

        archiveTitle = (TextView) findViewById(R.id.archive_detail_title);
        archiveTitle.setTypeface(getTitleTypeFace());
        archiveDate = (TextView) findViewById(R.id.archive_detail_date);
        archiveDate.setTypeface(getTitleTypeFace());

        archiveContent = (TextView) findViewById(R.id.archive_detail_content);
        archiveContent.setTypeface(getContentTypeFace());

        fab = (FloatingActionButton) findViewById(R.id.fab);

        service = MultiMediaArchiveService.getInstance();
        initializeView(archiveId);

    }

    private void initializeView(String id){
        service.getById(id, new IMultiMediaArchiveService.CallBack<MultiMediaArchive>() {
            @Override
            public void onSuccess(final MultiMediaArchive data) {
                if (data.getImage() != null) {
                    if (!data.getImage().isEmpty()) {
                        Utils.loadImage(getApplicationContext(), imageView, data.getImage());
                    }
                }
                archiveTitle.setText(data.getTitle());
                archiveDate.setText(data.getPublishDate());
                archiveContent.setText(Html.fromHtml(data.getExcerpt()));
                //MEDIA_URL = data.getMediaURL();

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
