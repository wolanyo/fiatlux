package com.wolasoft.fiatlux.interfaces;

import com.wolasoft.fiatlux.models.Post;

import java.util.List;

/**
 * Created by osiris on 13/06/16.
 */
public interface IPostService {
    void getAll(String section, CallBack<List<Post>> myCallBack);
    void getAllMultimedia(String section, String mediaType, CallBack<List<Post>> myCallBack);
    void getById(String id, CallBack<Post> myCallBack);
    interface CallBack<T>{
        void onSuccess(T data);
        void onFailure(int statusCode, String message);
    }
}
