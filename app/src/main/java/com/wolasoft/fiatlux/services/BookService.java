package com.wolasoft.fiatlux.services;

import com.wolasoft.fiatlux.dao.FiatLuxClient;
import com.wolasoft.fiatlux.dao.FiatLuxServiceGenerator;
import com.wolasoft.fiatlux.interfaces.IBookService;
import com.wolasoft.fiatlux.models.Book;
import com.wolasoft.fiatlux.enums.HttpStatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kkoudo on 17/04/2016.
 */
public class BookService implements IBookService {

    private static volatile BookService instance = null;

    private BookService(){
        super();
    }

    public static final BookService getInstance(){
        if (BookService.instance == null){
            synchronized (BookService.class){
                if (BookService.instance == null) {
                    BookService.instance = new BookService();
                }
            }
        }
        return BookService.instance;
    }

    @Override
    public void getAll(String section, final CallBack<List<Book>> callback) {
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<List<Book>> call = client.listBook() ;
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    if(callback!=null){
                        callback.onSuccess(response.body());
                    }
                } else {
                    if (callback!=null){
                        callback.onFailure(response.code(), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                if (callback!=null){
                    callback.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }

    @Override
    public void getById(String id, final CallBack<Book> callback) {
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<Book> call = client.getBookById(id) ;

        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    if(callback!=null){
                        callback.onSuccess(response.body());
                    }
                } else {
                    if (callback!=null){
                        callback.onFailure(response.code(), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                if (callback!=null){
                    callback.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }
}
