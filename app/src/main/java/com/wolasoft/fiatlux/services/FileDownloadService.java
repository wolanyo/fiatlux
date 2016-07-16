package com.wolasoft.fiatlux.services;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.wolasoft.fiatlux.dao.FiatLuxClient;
import com.wolasoft.fiatlux.dao.FiatLuxFileServiceGenerator;
import com.wolasoft.fiatlux.enums.HttpStatus;
import com.wolasoft.fiatlux.interfaces.IDownloadService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by osiris on 16/07/16.
 */
public class FileDownloadService implements IDownloadService {

    private static volatile FileDownloadService instance = null;
    private Context context;

    private FileDownloadService(Context context){
        super();
        this.context = context;
    }

    public static final FileDownloadService getInstance(Context context){
        if (FileDownloadService.instance == null){
            synchronized (BookService.class){
                if (FileDownloadService.instance == null) {
                    FileDownloadService.instance = new FileDownloadService(context);
                }
            }
        }
        return FileDownloadService.instance;
    }

    @Override
    public void getFile(String url, final CallBack<ResponseBody> myCallBack) {
        final FiatLuxClient client = FiatLuxFileServiceGenerator.createService(FiatLuxClient.class);
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                Call<ResponseBody> call = client.downloadFileWithDynamicUrlSync(params[0]) ;
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            if(myCallBack!=null){
                                //myCallBack.onSuccess(response.body());
                                Log.d("TAG", "server contacted and has file");
                                boolean writtenToDisk = writeResponseBodyToDisk(response.body());
                                Log.d("TAG", "file download was a success? " + writtenToDisk);
                            }
                        } else {
                            if (myCallBack!=null){
                                //myCallBack.onFailure(response.code(), response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (myCallBack!=null){
                            myCallBack.onFailure(HttpStatus.UNKNONW.getStatusCode(), HttpStatus.UNKNONW.getDescription());
                        }
                    }
                });
                return null;
            }
        }.execute(url);
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile;
            if (isExternalStorageWritable()){
                futureStudioIconFile = new File(ContextCompat.getExternalFilesDirs(context, null) + File.separator + "Future Studio Icon.pdf");
            }
            else {
                futureStudioIconFile = new File(ContextCompat.getExternalFilesDirs(context, null) + File.separator + "Future Studio Icon.pdf");
            }

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("TAG", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    //inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
