package com.wolasoft.fiatlux.config;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wolasoft.fiatlux.dao.FiatLuxServiceGenerator;
import com.wolasoft.fiatlux.services.FileDownloadService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;


/**
 * Created by osiris on 12/06/16.
 */
public class Utils {
    private static final String imageUrlBase= "http://fiatlux.pythonanywhere.com";
    private static String token[] ;
    public static final String DOWNLOAD_BASE_URL = "http://fiatlux.pythonanywhere.com";

    public static void loadImage(Context context, ImageView imageView, String imageName){
        Picasso.with(context).load(imageUrlBase+imageName).into(imageView);
    }

    public static String reverseDate(String date){
        token = date.split("-");
        return token[2]+"-"+token[1]+"-"+token[0];

    }
}
