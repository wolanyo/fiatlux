package com.wolasoft.fiatlux.activities;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.config.Utils;
import com.wolasoft.fiatlux.interfaces.IBookService;
import com.wolasoft.fiatlux.models.Book;
import com.wolasoft.fiatlux.services.BookService;
import com.wolasoft.fiatlux.services.ServiceInterface;

public class BookDetailActivity extends BaseActivity {

    private static final String BOOK_ID = "book_id";
    private ImageView bookImage;
    private TextView bookTitle;
    private TextView bookAuthor;
    private TextView bookResume;
    private Button previewButton;
    private TextView buyButton;
    private BookService service;
    private String PREVIEW_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        bookImage = (ImageView) findViewById(R.id.book_image);
        bookTitle = (TextView) findViewById(R.id.book_title);
        bookTitle.setTypeface(getTitleTypeFace());
        bookAuthor = (TextView) findViewById(R.id.book_author);
        bookAuthor.setTypeface(getTitleTypeFace());
        bookResume = (TextView) findViewById(R.id.book_resume);
        bookResume.setTypeface(getContentTypeFace());
        previewButton = (Button) findViewById(R.id.book_preview);
        previewButton.setTypeface(getTitleTypeFace());
        buyButton = (Button) findViewById(R.id.book_buy);
        buyButton.setTypeface(getTitleTypeFace());
        buyButton.setEnabled(false);

        String bookId = getIntent().getStringExtra(BOOK_ID);

        service = BookService.getInstance();

        initializeView(bookId);

        BroadcastReceiver receiver = new BroadcastReceiver() {
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
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void initializeView(String id){
        service.getById(id, new IBookService.CallBack<Book>() {
            @Override
            public void onSuccess(final Book data) {
                Utils.loadImage(getApplicationContext(), bookImage, data.getImage());
                bookTitle.setText(data.getTitle());
                bookAuthor.setText(data.getAuthor());
                bookResume.setText(Html.fromHtml(data.getExcerpt()));
                buyButton.setText("Acheter ("+data.getPrice()+" €)");
                PREVIEW_URL = data.getExcerptFile();
                previewButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), R.string.download, Toast.LENGTH_SHORT).show();
                        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(
                                Uri.parse(Utils.DOWNLOAD_BASE_URL+data.getExcerptFile()));
                        enqueue = dm.enqueue(request);
                        showDownload();
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
