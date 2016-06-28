package com.wolasoft.fiatlux.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.activities.BookDetailActivity;
import com.wolasoft.fiatlux.config.Utils;
import com.wolasoft.fiatlux.interfaces.QueryCallback;
import com.wolasoft.fiatlux.models.Book;

import java.util.List;

/**
 * Created by kkoudo on 05/03/2016.
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> implements QueryCallback<Book> {

    private List<Book> bookList = null;
    private Context context = null ;
    private static final String CURRENCY = " €";

    public BookListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (bookList != null)
            return bookList.size();
        else
            return 0;
    }

    public void onBindViewHolder(BookViewHolder holder, int i) {
        Book book = bookList.get(i) ;
        int numberOfBook = (bookList != null) ? bookList.size() : 0;
        //bookViewHolder.bookImage.setImageResource(R.drawable.spirit);
        holder.display(book, i);

    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_book, viewGroup, false) ;
        return new BookViewHolder(itemView, context) ;
    }

    @Override
    public void onDataReceived(List<Book> dataList, boolean hasMore) {

    }

    @Override
    public void onDataLoaded(List<Book> dataList) {
        this.bookList = dataList;
        notifyDataSetChanged();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder{
        private static final  String BOOK_ID = "book_id";
        Context context;
        private ImageView bookImage;
        private TextView bookTitle ;
        private TextView bookAuthor;
        private TextView bookPrice;
        private String bookId;

        public BookViewHolder(final View view, final Context context){
            super(view);
            Typeface titleTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/quenta.otf");
            Typeface contentTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/caviar.ttf");
            bookImage = (ImageView) view.findViewById(R.id.book_image);
            bookTitle = (TextView) view.findViewById(R.id.book_title);
            bookTitle.setTypeface(titleTypeFace);
            bookAuthor = (TextView) view.findViewById(R.id.book_author);
            bookAuthor.setTypeface(contentTypeFace);
            bookPrice = (TextView) view.findViewById(R.id.book_price);
            bookPrice.setTypeface(contentTypeFace);
            this.context = context;

            view.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(context, BookDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    intent.putExtra(BOOK_ID, bookId);
                    context.startActivity(intent);
                }
            });
        }

        public void display(Book book, int currentPosition){
            Utils.loadImage(context, bookImage, book.getImage());
            bookTitle.setText(book.getTitle());
            bookAuthor.setText(book.getAuthor());
            bookPrice.setText(Float.toString(book.getPrice())+CURRENCY);
            bookId = Integer.toString(book.getId());
        }
    }
}
