package com.wolasoft.fiatlux.services;

import android.content.Context;
import android.widget.TextView;

import com.wolasoft.fiatlux.adapters.ChapterListAdapter;
import com.wolasoft.fiatlux.dao.FiatLuxClient;
import com.wolasoft.fiatlux.dao.FiatLuxServiceGenerator;
import com.wolasoft.fiatlux.interfaces.IChapterService;
import com.wolasoft.fiatlux.models.Chapter;
import com.wolasoft.fiatlux.enums.HttpStatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kkoudo on 17/04/2016.
 */
public class ChapterService implements IChapterService {
    ChapterListAdapter adapter = null;
    Context context = null;
    TextView textView = null;

    public ChapterService() {
    }

    public ChapterService(ChapterListAdapter adapter, Context context) {
        this.adapter = adapter;
        this.context = context;
    }

    public ChapterService(ChapterListAdapter adapter, Context context, TextView textView) {
        this.adapter = adapter;
        this.context = context;
        this.textView = textView;
    }

    @Override
    public void getAll(String id, final CallBack<List<Chapter>> callback) {
        //here id represent lesson id, lesson chapter is nested to
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<List<Chapter>> call = client.listChapter(id);

        call.enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
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
            public void onFailure(Call<List<Chapter>> call, Throwable t) {
                if (callback!=null){
                    callback.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }

    @Override
    public void getById(String id, final CallBack<Chapter> myCallBack) {
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<Chapter> call = client.getChapterById(id) ;

        call.enqueue(new Callback<Chapter>() {
            @Override
            public void onResponse(Call<Chapter> call, Response<Chapter> response) {
                if (response.isSuccessful()) {
                    if(myCallBack!=null){
                        myCallBack.onSuccess(response.body());
                    }
                } else {
                    if (myCallBack!=null){
                        myCallBack.onFailure(response.code(), response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<Chapter> call, Throwable t) {
                if (myCallBack!=null){
                    myCallBack.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }
}
