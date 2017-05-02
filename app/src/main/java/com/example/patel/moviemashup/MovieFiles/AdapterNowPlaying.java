package com.example.patel.moviemashup.MovieFiles;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.patel.moviemashup.R;
import com.example.patel.moviemashup.GeneralFiles.VolleySingelton;

import java.util.ArrayList;

/**
 * Created by patel on 29-Mar-17.
 */

public class AdapterNowPlaying extends RecyclerView.Adapter<AdapterNowPlaying.ViewHolderNowPlaying> {

    private LayoutInflater layoutInflater;
    private ArrayList<Movies> listMovies = new ArrayList<>();
    private VolleySingelton volleySingelton;
    private ImageLoader imageLoader;
    private static Context context;

    public AdapterNowPlaying(Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        volleySingelton = VolleySingelton.getsInstance();
        imageLoader = volleySingelton.getImageLoader();
    }

    public void setMovieList(ArrayList<Movies> listMovies){
        this.listMovies = listMovies;
        notifyItemChanged(0,listMovies.size());
    }

    @Override
    public AdapterNowPlaying.ViewHolderNowPlaying onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_movie_row,parent,false);
        ViewHolderNowPlaying viewHolderNowPlaying = new ViewHolderNowPlaying(view);
        return viewHolderNowPlaying;
    }

    @Override
    public void onBindViewHolder(final AdapterNowPlaying.ViewHolderNowPlaying holder, int position) {
        final Movies currentMovie = listMovies.get(position);
        holder.movieTitle.setText(currentMovie.getTitle());
        String urlImage  = currentMovie.getUrlPoster();

        imageLoader.get(urlImage, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.movieThumbnail.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,MovieDetails.class);
                i.putExtra("MovieID",currentMovie.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    static class ViewHolderNowPlaying extends RecyclerView.ViewHolder{

        private ImageView movieThumbnail;
        private TextView movieTitle;

        public ViewHolderNowPlaying(View itemView){
            super(itemView);
            movieThumbnail = (ImageView) itemView.findViewById(R.id.rowImage);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
        }
    }
}
