package com.wolasoft.fiatlux.interfaces;

import com.wolasoft.fiatlux.models.TimeTable;

import java.util.List;

/**
 * Created by kkoudo on 10/07/2016.
 */
public interface ITimeTableService {
    void getAll(String section, CallBack<List<TimeTable>> myCallBack);
    void getById(String id, CallBack<TimeTable> myCallBack);
    interface CallBack<T>{
        void onSuccess(T data);
        void onFailure(int statusCode, String message);
    }
}
