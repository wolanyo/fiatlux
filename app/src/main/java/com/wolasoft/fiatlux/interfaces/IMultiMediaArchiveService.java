package com.wolasoft.fiatlux.interfaces;

import com.wolasoft.fiatlux.models.MultiMediaArchive;

import java.util.List;

/**
 * Created by osiris on 13/06/16.
 */
public interface IMultiMediaArchiveService {
    void getAll(String section, String archiveType, CallBack<List<MultiMediaArchive>> myCallBack);
    void getById(String id, CallBack<MultiMediaArchive> myCallBack);
    interface CallBack<T>{
        void onSuccess(T data);
        void onFailure(int statusCode, String message);
    }
}
