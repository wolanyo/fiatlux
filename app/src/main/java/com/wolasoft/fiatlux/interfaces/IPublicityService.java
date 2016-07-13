package com.wolasoft.fiatlux.interfaces;

import com.wolasoft.fiatlux.models.Publicity;

import java.util.List;

/**
 * Created by kkoudo on 10/07/2016.
 */
public interface IPublicityService {
    void getAll(CallBack<List<Publicity>> myCallBack);
    void getById(String id, CallBack<Publicity> myCallBack);
    interface CallBack<T>{
        void onSuccess(T data);
        void onFailure(int statusCode, String message);
    }
}
