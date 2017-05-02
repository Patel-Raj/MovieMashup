package com.example.patel.moviemashup.WatchList;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by patel on 23-Apr-17.
 */

public class WatchDetails {

    long idd;
    String name;
    String type;
    String img;

    public WatchDetails(){

    }

    public WatchDetails(long idd,String name,String type,String img){
        this.idd = idd;
        this.name = name;
        this.type = type;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getType() {
        return type;
    }

    public long getIdd() {
        return idd;
    }
}
