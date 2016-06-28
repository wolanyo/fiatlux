package com.wolasoft.fiatlux.services;

import android.content.Context;
import android.widget.TextView;

import com.wolasoft.fiatlux.adapters.LessonTypeListAdapter;
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

    LessonTypeListAdapter adapter = null;
    Context context = null;
    TextView textView = null;

    public LessonTypeService() {
    }

    public LessonTypeService(LessonTypeListAdapter adapter, Context context) {
        this.adapter = adapter;
        this.context = context;
    }

    public LessonTypeService(LessonTypeListAdapter adapter, Context context, TextView textView) {
        this.adapter = adapter;
        this.context = context;
        this.textView = textView;
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
