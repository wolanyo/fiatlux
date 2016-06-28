package com.wolasoft.fiatlux.services;

import android.content.Context;
import android.widget.TextView;

import com.wolasoft.fiatlux.adapters.MultiMediaArchiveListAdapter;
import com.wolasoft.fiatlux.dao.FiatLuxClient;
import com.wolasoft.fiatlux.dao.FiatLuxServiceGenerator;
import com.wolasoft.fiatlux.enums.HttpStatus;
import com.wolasoft.fiatlux.interfaces.IMultiMediaArchiveService;
import com.wolasoft.fiatlux.models.MultiMediaArchive;
import com.wolasoft.fiatlux.models.MultiMediaArchive;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kkoudo on 17/04/2016.
 */
public class MultiMediaArchiveService implements IMultiMediaArchiveService{

    public MultiMediaArchiveService() {
    }

    @Override
    public void getAll(String section, String archiveType, final CallBack<List<MultiMediaArchive>> callback){
        //here id represent archive type
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<List<MultiMediaArchive>> call = client.listMultiMediaArchive(section, archiveType) ;

        call.enqueue(new Callback<List<MultiMediaArchive>>() {
            @Override
            public void onResponse(Call<List<MultiMediaArchive>> call, Response<List<MultiMediaArchive>> response) {
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
            public void onFailure(Call<List<MultiMediaArchive>> call, Throwable t) {
                if (callback!=null){
                    callback.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }

    @Override
    public void getById(String id, final CallBack<MultiMediaArchive> callback) {
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<MultiMediaArchive> call = client.getMultiMediaArchiveById(id) ;

        call.enqueue(new Callback<MultiMediaArchive>() {
            @Override
            public void onResponse(Call<MultiMediaArchive> call, Response<MultiMediaArchive> response) {
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
            public void onFailure(Call<MultiMediaArchive> call, Throwable t) {
                if (callback!=null){
                    callback.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }
}
