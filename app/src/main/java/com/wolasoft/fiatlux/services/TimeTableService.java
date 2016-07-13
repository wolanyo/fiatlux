package com.wolasoft.fiatlux.services;

import com.wolasoft.fiatlux.dao.FiatLuxClient;
import com.wolasoft.fiatlux.dao.FiatLuxServiceGenerator;
import com.wolasoft.fiatlux.enums.HttpStatus;
import com.wolasoft.fiatlux.interfaces.ITimeTableService;
import com.wolasoft.fiatlux.models.TimeTable;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kkoudo on 10/07/2016.
 */
public class TimeTableService implements ITimeTableService{
    private static volatile TimeTableService instance = null;

    private TimeTableService(){
        super();
    }

    public static final TimeTableService getInstance(){
        if (TimeTableService.instance == null){
            synchronized (TimeTableService.class){
                if (TimeTableService.instance == null) {
                    TimeTableService.instance = new TimeTableService();
                }
            }
        }

        return TimeTableService.instance;
    }
    
    @Override
    public void getAll(String section, final CallBack<List<TimeTable>> callback) {
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<List<TimeTable>> call = client.listTimeTable() ;
        call.enqueue(new Callback<List<TimeTable>>() {
            @Override
            public void onResponse(Call<List<TimeTable>> call, Response<List<TimeTable>> response) {
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
            public void onFailure(Call<List<TimeTable>> call, Throwable t) {
                if (callback!=null){
                    callback.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }

    @Override
    public void getById(String id, final CallBack<TimeTable> callback) {
        FiatLuxClient client = FiatLuxServiceGenerator.createService(FiatLuxClient.class);
        Call<TimeTable> call = client.getTimeTableById(id) ;

        call.enqueue(new Callback<TimeTable>() {
            @Override
            public void onResponse(Call<TimeTable> call, Response<TimeTable> response) {
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
            public void onFailure(Call<TimeTable> call, Throwable t) {
                if (callback!=null){
                    callback.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                }
            }
        });
    }
}
