package com.wolasoft.fiatlux.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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

        String bookId = getIntent().getStringExtra(BOOK_ID);

        service = BookService.getInstance();

        initializeView(bookId);

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
            }

            @Override
            public void onFailure(int statusCode, String message) {
                Toast.makeText(getApplicationContext(), R.string.network_issue, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
