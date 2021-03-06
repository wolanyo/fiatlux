package com.wolasoft.fiatlux.services;

import com.wolasoft.fiatlux.dao.FiatLuxClient;
import com.wolasoft.fiatlux.dao.FiatLuxServiceGenerator;
import com.wolasoft.fiatlux.enums.HttpStatus;
import com.wolasoft.fiatlux.interfaces.ILessonService;
import com.wolasoft.fiatlux.models.Lesson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kkoudo on 17/04/2016.
 */
public class LessonService implements ILessonService {
    private static volatile LessonService instance = null;

    private LessonService(){
        super();
    }

    public static final LessonService getInstance(){
        if (LessonService.instance == null){
            synchronized (LessonService.class){
                if (LessonService.instance == null) {
                    LessonService.instance = new LessonService();
                }
            }
        }
        return LessonService.instance;
    }

    @Override
    public void getAll(String lessonTypeId, final CallBack<List<Lesson>> callback) {
        //here id represent lesson id, lesson chapter is nested to
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        //here id is lesson type id
        Call<List<Lesson>> call = client.listLesson(lessonTypeId);

        call.enqueue(new Callback<List<Lesson>>() {
            @Override
            public void onResponse(Call<List<Lesson>> call, Response<List<Lesson>> response) {
                if (response.isSuccessful()) {
                    if(callback!=null){
                        callback.onSuccess(response.body());
                    }
                } else {
                    if (callback!=null){
                        callback.onFailure(response.code(), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Lesson>> call, Throwable t) {
                if (callback!=null){
                    callback.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }

    @Override
    public void getById(String id, final CallBack<Lesson> callback) {
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<Lesson> call = client.getLessonbyId(id) ;

        call.enqueue(new Callback<Lesson>() {
            @Override
            public void onResponse(Call<Lesson> call, Response<Lesson> response) {
                if (response.isSuccessful()) {
                    if(callback!=null){
                        callback.onSuccess(response.body());
                    }
                } else {
                    if (callback!=null){
                        callback.onFailure(response.code(), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<Lesson> call, Throwable t) {
                if (callback!=null){
                    callback.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }

}
