package com.wolasoft.fiatlux.dao;

import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kkoudo on 15/04/2016.
 */
public class Request  {

    public static String retrieveData(String urlLink) {
        HttpURLConnection urlConnection = null ;
        InputStream inputStream = null;
        URL url = null;
        String data = "";
        try {
            //Log.i("INFO", "REQUEST LANCER");
            url = new URL(urlLink);
            urlConnection = (HttpURLConnection) url.openConnection();
            int statusCode = urlConnection.getResponseCode() ;
            switch (statusCode){
                case HttpURLConnection.HTTP_OK:
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    data = readStream(inputStream);
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    data = "HTTP_NOT_FOUND";
                    break;
                case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
                    data = "HTTP_CLIENT_TIMEOUT";
                    break;
                case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
                    data = "HTTP_GATEWAY_TIMEOUT";
                    break;
                default:
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    data = readStream(inputStream);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            data = "FAILED";
        }
        finally {
            urlConnection.disconnect();
        }
        return data;
    }

    protected static String readStream(InputStream in){
        BufferedReader reader = new BufferedReader(new InputStreamReader(in)) ;
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null)
                stringBuilder.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
