package com.wolasoft.fiatlux.config;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by osiris on 12/06/16.
 */
public class Utils {
    private static final String imageUrlBase= "http://fiatlux.pythonanywhere.com";

    public static void loadImage(Context context, ImageView imageView, String imageName){
        Picasso.with(context).load(imageUrlBase+imageName).into(imageView);
    }
}
