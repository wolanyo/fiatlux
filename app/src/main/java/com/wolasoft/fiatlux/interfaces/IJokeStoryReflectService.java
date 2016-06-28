package com.wolasoft.fiatlux.interfaces;

import com.wolasoft.fiatlux.models.JokeStoryReflect;

import java.util.List;

/**
 * Created by osiris on 13/06/16.
 */
public interface IJokeStoryReflectService {
    void getAll(String section, String type, CallBack<List<JokeStoryReflect>> myCallBack);
    void getById(String id, CallBack<JokeStoryReflect> myCallBack);
    interface CallBack<T>{
        void onSuccess(T data);
        void onFailure(int statusCode, String message);
    }
}
