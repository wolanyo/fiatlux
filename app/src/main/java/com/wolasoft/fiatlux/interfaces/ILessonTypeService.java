package com.wolasoft.fiatlux.interfaces;

import com.wolasoft.fiatlux.models.LessonType;

import java.util.List;

/**
 * Created by osiris on 13/06/16.
 */
public interface ILessonTypeService {
    void getAll(String lessonTypeId, CallBack<List<LessonType>> myCallBack);
    void getById(String id, CallBack<LessonType> myCallBack);
    interface CallBack<T>{
        void onSuccess(T data);
        void onFailure(int statusCode, String message);
    }
}
