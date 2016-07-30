package com.wolasoft.fiatlux.services;

import com.wolasoft.fiatlux.dao.FiatLuxClient;
import com.wolasoft.fiatlux.dao.FiatLuxServiceGenerator;
import com.wolasoft.fiatlux.enums.HttpStatus;
import com.wolasoft.fiatlux.interfaces.IChapterService;
import com.wolasoft.fiatlux.models.Chapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kkoudo on 17/04/2016.
 */
public class ChapterService implements IChapterService {
    private static volatile ChapterService instance = null;

    private ChapterService(){
        super();
    }

    public static final ChapterService getInstance(){
        if (ChapterService.instance == null){
            synchronized (ChapterService.class){
                if (ChapterService.instance == null) {
                    ChapterService.instance = new ChapterService();
                }
            }
        }
        return ChapterService.instance;
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
