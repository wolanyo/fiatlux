package com.wolasoft.fiatlux.services;

import com.wolasoft.fiatlux.dao.FiatLuxClient;
import com.wolasoft.fiatlux.dao.FiatLuxServiceGenerator;
import com.wolasoft.fiatlux.enums.HttpStatus;
import com.wolasoft.fiatlux.interfaces.ILessonTypeService;
import com.wolasoft.fiatlux.models.LessonType;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kkoudo on 17/04/2016.
 */
public class LessonTypeService implements ILessonTypeService {

    private static volatile LessonTypeService instance = null;

    private LessonTypeService(){
        super();
    }

    public static final LessonTypeService getInstance(){
        if (LessonTypeService.instance == null){
            synchronized (LessonTypeService.class){
                if (LessonTypeService.instance == null) {
                    LessonTypeService.instance = new LessonTypeService();
                }
            }
        }
        return LessonTypeService.instance;
    }

    @Override
    public void getAll(String section, final CallBack<List<LessonType>> callback) {
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<List<LessonType>> call = client.listLessonType(section) ;

        call.enqueue(new Callback<List<LessonType>>() {
            @Override
            public void onResponse(Call<List<LessonType>> call, Response<List<LessonType>> response) {
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
            public void onFailure(Call<List<LessonType>> call, Throwable t) {
                if (callback!=null){
                    callback.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }

    @Override
    public void getById(String id, CallBack<LessonType> myCallBack) {

    }
}
