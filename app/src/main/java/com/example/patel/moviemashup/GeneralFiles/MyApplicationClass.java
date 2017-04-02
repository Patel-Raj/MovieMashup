package com.example.patel.moviemashup.GeneralFiles;

import android.app.Application;
import android.content.Context;

/**
 * Created by patel on 27-Mar-17.
 */

public class MyApplicationClass extends Application {

    public static final String API_KEY = "856c5d9494aa6def37bbc042678115d4";    // TMDB API Key
    private static MyApplicationClass sInstance;
   @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
    public static MyApplicationClass getsInstance(){
        return sInstance;
    }
    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
