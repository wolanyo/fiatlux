package com.wolasoft.fiatlux.interfaces;

import okhttp3.ResponseBody;

/**
 * Created by osiris on 16/07/16.
 */
public interface IDownloadService {
    void getFile(String url, CallBack<ResponseBody> myCallBack);
    interface CallBack<ResponseBody>{
        void onSuccess(okhttp3.ResponseBody data);
        void onFailure(int statusCode, String message);
    }
}
