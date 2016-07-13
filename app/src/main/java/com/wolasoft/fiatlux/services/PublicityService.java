package com.wolasoft.fiatlux.services;

import com.wolasoft.fiatlux.dao.FiatLuxClient;
import com.wolasoft.fiatlux.dao.FiatLuxServiceGenerator;
import com.wolasoft.fiatlux.enums.HttpStatus;
import com.wolasoft.fiatlux.interfaces.IPublicityService;
import com.wolasoft.fiatlux.models.Publicity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kkoudo on 10/07/2016.
 */
public class PublicityService implements IPublicityService {
    private static volatile PublicityService instance = null;

    private PublicityService(){
        super();
    }

    public static final PublicityService getInstance(){
        if (PublicityService.instance == null){
            synchronized (PublicityService.class){
                if (PublicityService.instance == null) {
                    PublicityService.instance = new PublicityService();
                }
            }
        }

        return PublicityService.instance;
    }

    @Override
    public void getAll(final CallBack<List<Publicity>> myCallBack) {
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<List<Publicity>> call = client.listPublicity() ;
        call.enqueue(new Callback<List<Publicity>>() {
            @Override
            public void onResponse(Call<List<Publicity>> call, Response<List<Publicity>> response) {
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
            public void onFailure(Call<List<Publicity>> call, Throwable t) {
                if (myCallBack!=null){
                    myCallBack.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }

    @Override
    public void getById(String id, final CallBack<Publicity> myCallBack) {
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<Publicity> call = client.getPublicityById(id) ;

        call.enqueue(new Callback<Publicity>() {
            @Override
            public void onResponse(Call<Publicity> call, Response<Publicity> response) {
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
            public void onFailure(Call<Publicity> call, Throwable t) {
                if (myCallBack!=null){
                    myCallBack.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }
}
