package com.example.patel.moviemashup.CelebritiesFiles;

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

public class AdapterCeleb extends RecyclerView.Adapter<AdapterCeleb.ViewHolderCeleb> {

    private LayoutInflater layoutInflater;
    private ArrayList<Celebrities> celebritiesArrayList = new ArrayList<>();
    private VolleySingelton volleySingelton;
    private ImageLoader imageLoader;
    private static Context context;

    public AdapterCeleb(Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        volleySingelton = VolleySingelton.getsInstance();
        imageLoader = volleySingelton.getImageLoader();
    }

    public void setCelebritiesArrayListt(ArrayList<Celebrities> listCeleb){
        this.celebritiesArrayList = listCeleb;
        notifyItemChanged(0,celebritiesArrayList.size());
    }
    @Override
    public ViewHolderCeleb onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_movie_row,parent,false);
        ViewHolderCeleb viewHolderCeleb = new ViewHolderCeleb(view);
        return viewHolderCeleb;
    }

    @Override
    public void onBindViewHolder(final ViewHolderCeleb holder, int position) {
        final Celebrities celebrities = celebritiesArrayList.get(position);
        holder.TvTitle.setText(celebrities.getName());
        String urlImage  = celebrities.getUrlPoster();

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
                Intent i = new Intent(context,CelebritiesDetails.class);
                i.putExtra("CID",celebrities.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return celebritiesArrayList.size();
    }

    static class ViewHolderCeleb extends RecyclerView.ViewHolder{

        private ImageView TVThumbnail;
        private TextView TvTitle;

        public ViewHolderCeleb(View itemView){
            super(itemView);
            TVThumbnail = (ImageView) itemView.findViewById(R.id.rowImage);
            TvTitle = (TextView) itemView.findViewById(R.id.movieTitle);
        }
    }
}
