package com.wolasoft.fiatlux.interfaces;

import com.wolasoft.fiatlux.models.Music;

import java.util.List;

/**
 * Created by kkoudo on 10/07/2016.
 */
public interface IMusicService {
    void getAll(CallBack<List<Music>> myCallBack);
    void getById(String id, CallBack<Music> myCallBack);
    interface CallBack<T>{
        void onSuccess(T data);
        void onFailure(int statusCode, String message);
    }
}
