package com.example.patel.moviemashup.WatchList;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.patel.moviemashup.GeneralFiles.VolleySingelton;
import com.example.patel.moviemashup.R;

import java.util.List;

import static com.example.patel.moviemashup.GeneralFiles.TmdbStrings.IMAGE_PREFIX;
import static com.example.patel.moviemashup.GeneralFiles.TmdbStrings.POSTER_PATH;
import static com.example.patel.moviemashup.GeneralFiles.TmdbStrings.POSTER_SIZE;

/**
 * Created by patel on 23-Apr-17.
 */

public class WatchAdapter extends ArrayAdapter<WatchDetails> {

    private Activity context;
    private List<WatchDetails> watchList;
    private ImageLoader imageLoader;
    private VolleySingelton volleySingelton;
    private RequestQueue requestQueue;

    public WatchAdapter(Activity context, List<WatchDetails> watchList){
        super(context, R.layout.single_movie_row, watchList);
        this.context = context;
        this.watchList = watchList;
        volleySingelton = VolleySingelton.getsInstance();
        requestQueue = volleySingelton.getmRequestQueue();
        imageLoader = volleySingelton.getImageLoader();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.single_movie_row,null,true);

        final ImageView imageView = (ImageView) listViewItem.findViewById(R.id.rowImage);
        TextView textView = (TextView) listViewItem.findViewById(R.id.movieTitle);

        WatchDetails watchDetails = watchList.get(position);

        imageLoader.get(watchDetails.getImg(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                imageView.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        textView.setText(watchDetails.getName());
        return listViewItem;
    }
}
