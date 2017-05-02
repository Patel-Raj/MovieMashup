package com.example.patel.moviemashup.TVFiles;

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
import com.example.patel.moviemashup.GeneralFiles.VolleySingelton;
import com.example.patel.moviemashup.R;

import java.util.ArrayList;

/**
 * Created by patel on 30-Mar-17.
 */

public class AdapterTV extends RecyclerView.Adapter<AdapterTV.ViewHolderTV> {

    private LayoutInflater layoutInflater;
    private ArrayList<TV> listTV = new ArrayList<>();
    private VolleySingelton volleySingelton;
    private ImageLoader imageLoader;
    private static Context context;

    public AdapterTV(Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        volleySingelton = VolleySingelton.getsInstance();
        imageLoader = volleySingelton.getImageLoader();
    }

    public void setTVList(ArrayList<TV> listTV){
        this.listTV = listTV;
        notifyItemChanged(0,listTV.size());
    }
    @Override
    public ViewHolderTV onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_movie_row,parent,false);
        ViewHolderTV viewHolderTV = new ViewHolderTV(view);
        return viewHolderTV;
    }

    @Override
    public void onBindViewHolder(final AdapterTV.ViewHolderTV holder, int position) {
        final TV currentTV = listTV.get(position);
        holder.TvTitle.setText(currentTV.getTitle());
        String urlImage  = currentTV.getUrlPoster();

        imageLoader.get(urlImage, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.TVThumbnail.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,TVDetails.class);
                i.putExtra("TVID",currentTV.getId());
                context.startActivity(i);
            }
        });
    }
    @Override
    public int getItemCount() {
        return listTV.size();
    }

    static class ViewHolderTV extends RecyclerView.ViewHolder{

        private ImageView TVThumbnail;
        private TextView TvTitle;

        public ViewHolderTV(View itemView){
            super(itemView);
            TVThumbnail = (ImageView) itemView.findViewById(R.id.rowImage);
            TvTitle = (TextView) itemView.findViewById(R.id.movieTitle);
        }
    }
}
