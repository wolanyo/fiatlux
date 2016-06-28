package com.wolasoft.fiatlux.interfaces;

import com.wolasoft.fiatlux.models.Chapter;

import java.util.List;

/**
 * Created by osiris on 13/06/16.
 */
public interface IChapterService {
    void getAll(String id, CallBack<List<Chapter>> myCallBack);
    void getById(String id, CallBack<Chapter> myCallBack);
    interface CallBack<T>{
        void onSuccess(T data);
        void onFailure(int statusCode, String message);
    }
}
