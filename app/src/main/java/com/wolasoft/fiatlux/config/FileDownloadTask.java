package com.wolasoft.fiatlux.config;

import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by osiris on 16/07/16.
 */
public class FileDownloadTask extends AsyncTask<String, Void, Boolean> {
    boolean download = true;
    @Override
    protected Boolean doInBackground(String... params)
    {
        File SDCardRoot = Environment.getExternalStorageDirectory(); // location where you want to store
        File directory = new File(SDCardRoot, "/my_folder/"); //create directory to keep your downloaded file
        if (!directory.exists())
        {
            directory.mkdir();
        }
        String fileName = "mySong" + ".pdf"; //song name that will be stored in your device in case of song
        //String fileName = "myImage" + ".jpeg"; in case of image
        try
        {
            InputStream input = null;
            try{
                URL url = new URL(params[0]); // link of the song which you want to download like (http://...)
                input = url.openStream();
                OutputStream output = new FileOutputStream(new File(directory, fileName));
                download = true;
                try {
                    byte[] buffer = new byte[1024];
                    int bytesRead = 0;
                    while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0)
                    {
                        output.write(buffer, 0, bytesRead);
                        download = true;
                    }
                    output.close();
                }
                catch (Exception exception)
                {
                    //MyUtility.showLog("output exception in catch....."+ exception + "");
                    download = false;
                    output.close();
                    //progressDialog.dismiss();
                }
            }
            catch (Exception exception)
            {
                //MyUtility.showLog("input exception in catch....."+ exception + "");
                download = false;
                //progressDialog.dismiss();
            }
            finally
            {
                input.close();
            }
        }
        catch (Exception exception)
        {
            download = false;
            //progressDialog.dismiss();
        }
        return download;
    }
    @Override
    protected void onPostExecute(Boolean status)
    {
        /*if(download)
            //Toast.makeText(FileDownloadTask.this, "", Toast.LENGTH_SHORT).show();.makeText(getApplicationContext(), "Download successfull", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Download Failed", Toast.LENGTH_SHORT).show();*/
    }
}
