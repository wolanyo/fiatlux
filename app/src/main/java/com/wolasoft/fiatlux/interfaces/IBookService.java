package com.wolasoft.fiatlux.interfaces;

import com.wolasoft.fiatlux.models.Book;

import java.util.List;

/**
 * Created by osiris on 13/06/16.
 */
public interface IBookService {
    void getAll(String section, CallBack<List<Book>> myCallBack);
    void getById(String id, CallBack<Book> myCallBack);
    interface CallBack<T>{
        void onSuccess(T data);
        void onFailure(int statusCode, String message);
    }
}
