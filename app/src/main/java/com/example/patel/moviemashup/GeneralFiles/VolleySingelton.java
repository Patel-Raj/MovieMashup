package com.example.patel.moviemashup.GeneralFiles;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by patel on 27-Mar-17.
 */

public class VolleySingelton {

    private static VolleySingelton sInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleySingelton(){
        mRequestQueue = Volley.newRequestQueue(MyApplicationClass.getAppContext());

        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private LruCache<String,Bitmap> cache = new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static VolleySingelton getsInstance(){
        if(sInstance == null){
            sInstance = new VolleySingelton();
        }
        return sInstance;
    }
    public RequestQueue getmRequestQueue(){
        return mRequestQueue;
    }
    public ImageLoader getImageLoader(){
        return mImageLoader;
    }
}