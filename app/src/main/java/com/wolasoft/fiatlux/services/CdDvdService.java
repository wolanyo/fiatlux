package com.wolasoft.fiatlux.services;

import android.content.Context;
import android.widget.TextView;

import com.wolasoft.fiatlux.adapters.CdDvdListAdapter;
import com.wolasoft.fiatlux.dao.FiatLuxClient;
import com.wolasoft.fiatlux.dao.FiatLuxServiceGenerator;
import com.wolasoft.fiatlux.enums.HttpStatus;
import com.wolasoft.fiatlux.interfaces.ICdDvdService;
import com.wolasoft.fiatlux.models.CdDvd;
import com.wolasoft.fiatlux.models.CdDvd;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kkoudo on 17/04/2016.
 */
public class CdDvdService implements ICdDvdService {

    CdDvdListAdapter adapter = null;
    Context context = null;
    TextView textView = null;

    public CdDvdService() {
    }

    public CdDvdService(CdDvdListAdapter adapter, Context context) {
        this.adapter = adapter;
        this.context = context;
    }

    public CdDvdService(CdDvdListAdapter adapter, Context context, TextView textView) {
        this.adapter = adapter;
        this.context = context;
        this.textView = textView;
    }

    @Override
    public void getAll(String section, String type, final CallBack<List<CdDvd>> callback) {
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<List<CdDvd>> call = client.listCdDvd(type) ;

        call.enqueue(new Callback<List<CdDvd>>() {
            @Override
            public void onResponse(Call<List<CdDvd>> call, Response<List<CdDvd>> response) {
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
            public void onFailure(Call<List<CdDvd>> call, Throwable t) {
                if (callback!=null){
                    callback.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }

    @Override
    public void getById(String id, final CallBack<CdDvd> callback) {
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<CdDvd> call = client.getCdDvdById(id) ;

        call.enqueue(new Callback<CdDvd>() {
            @Override
            public void onResponse(Call<CdDvd> call, Response<CdDvd> response) {
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
            public void onFailure(Call<CdDvd> call, Throwable t) {
                if (callback!=null){
                    callback.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }

}
