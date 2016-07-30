package com.wolasoft.fiatlux.services;

import com.wolasoft.fiatlux.dao.FiatLuxClient;
import com.wolasoft.fiatlux.dao.FiatLuxServiceGenerator;
import com.wolasoft.fiatlux.enums.HttpStatus;
import com.wolasoft.fiatlux.interfaces.ICdDvdService;
import com.wolasoft.fiatlux.models.CdDvd;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kkoudo on 17/04/2016.
 */
public class CdDvdService implements ICdDvdService {
    private static volatile CdDvdService instance = null;

    private CdDvdService(){
        super();
    }

    public static final CdDvdService getInstance(){
        if (CdDvdService.instance == null){
            synchronized (CdDvdService.class){
                if (CdDvdService.instance == null) {
                    CdDvdService.instance = new CdDvdService();
                }
            }
        }
        return CdDvdService.instance;
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
