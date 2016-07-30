package com.wolasoft.fiatlux.interfaces;

import com.wolasoft.fiatlux.models.CdDvd;

import java.util.List;

/**
 * Created by osiris on 13/06/16.
 */
public interface ICdDvdService {
    void getAll(String section, String type, CallBack<List<CdDvd>> myCallBack);
    void getById(String id, CallBack<CdDvd> myCallBack);
    interface CallBack<T>{
        void onSuccess(T data);
        void onFailure(int statusCode, String message);
    }
}
