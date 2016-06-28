package com.wolasoft.fiatlux.services;

import android.content.Context;
import android.widget.TextView;

import com.wolasoft.fiatlux.adapters.JokeStoryReflectListAdapter;
import com.wolasoft.fiatlux.dao.FiatLuxClient;
import com.wolasoft.fiatlux.dao.FiatLuxServiceGenerator;
import com.wolasoft.fiatlux.enums.HttpStatus;
import com.wolasoft.fiatlux.interfaces.IJokeStoryReflectService;
import com.wolasoft.fiatlux.models.JokeStoryReflect;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kkoudo on 17/04/2016.
 */
public class JokeStoryReflectService implements IJokeStoryReflectService{
    JokeStoryReflectListAdapter adapter = null;
    Context context = null;
    TextView textView = null;

    public JokeStoryReflectService() {
    }

    public JokeStoryReflectService(JokeStoryReflectListAdapter adapter, Context context) {
        this.adapter = adapter;
        this.context = context;
    }

    public JokeStoryReflectService(JokeStoryReflectListAdapter adapter, Context context, TextView textView) {
        this.adapter = adapter;
        this.context = context;
        this.textView = textView;
    }

    @Override
    public void getAll(String section, String type, final CallBack<List<JokeStoryReflect>> callback){
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<List<JokeStoryReflect>> call = client.listJokeStoryReflect(type) ;

        call.enqueue(new Callback<List<JokeStoryReflect>>() {
            @Override
            public void onResponse(Call<List<JokeStoryReflect>> call, Response<List<JokeStoryReflect>> response) {
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
            public void onFailure(Call<List<JokeStoryReflect>> call, Throwable t) {
                if (callback!=null){
                    callback.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }

    @Override
    public void getById(String id, final CallBack<JokeStoryReflect> callback) {
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<JokeStoryReflect> call = client.getJokeStoryReflectById(id) ;

        call.enqueue(new Callback<JokeStoryReflect>() {
            @Override
            public void onResponse(Call<JokeStoryReflect> call, Response<JokeStoryReflect> response) {
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
            public void onFailure(Call<JokeStoryReflect> call, Throwable t) {
                if (callback!=null){
                    callback.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }
}
