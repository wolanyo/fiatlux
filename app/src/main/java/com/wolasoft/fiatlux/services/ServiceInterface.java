package com.wolasoft.fiatlux.services;

import java.util.List;

/**
 * Created by osiris on 18/05/16.
 */
public interface ServiceInterface<T> {
    void getAll(String id, String section, CallBack<List<T>> myCallBack);
    void getById(String id, CallBack<T> myCallBack);
    interface CallBack<T>{
        void onSuccess(T data);
        void onFailure(int statusCode, String message);
    }
}
