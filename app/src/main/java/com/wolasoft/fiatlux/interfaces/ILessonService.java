package com.wolasoft.fiatlux.interfaces;

import com.wolasoft.fiatlux.models.Lesson;

import java.util.List;

/**
 * Created by osiris on 13/06/16.
 */
public interface ILessonService {
    void getAll(String lessonTypeId, CallBack<List<Lesson>> myCallBack);
    void getById(String id, CallBack<Lesson> myCallBack);
    interface CallBack<T>{
        void onSuccess(T data);
        void onFailure(int statusCode, String message);
    }
}
