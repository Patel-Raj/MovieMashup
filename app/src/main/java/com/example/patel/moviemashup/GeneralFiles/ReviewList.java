package com.example.patel.moviemashup.GeneralFiles;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.patel.moviemashup.MovieFiles.MovieDetails;
import com.example.patel.moviemashup.R;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by patel on 19-Apr-17.
 */

public class ReviewList extends RecyclerView.Adapter<ReviewList.MyViewHolder> {
    /*
    private Activity context;

    public ReviewList(Activity context,List<Review> reviewList){
        super(context, R.layout.review_layout,reviewList);
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.review_layout,null,true);

        TextView textViewEmail = (TextView) listViewItem.findViewById(R.id.userEmail);
        RatingBar ratingBarRating = (RatingBar) listViewItem.findViewById(R.id.ratingBar2);
        TextView textViewReview = (TextView) listViewItem.findViewById(R.id.userReview);


        Review review = reviewList.get(position);

        textViewEmail.setText(review.getUser());
        ratingBarRating.setRating((float) review.getRatings());
        textViewReview.setText(review.getReview());

        return listViewItem;
    }

    */
    private LayoutInflater inflator;
    private List<Review> reviewList = Collections.emptyList();

    public ReviewList(Context context, List<Review> reviewList){
        inflator = LayoutInflater.from(context);
        this.reviewList = reviewList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflator.inflate(R.layout.review_layout,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Review currentObject = reviewList.get(position);
        holder.textViewEmail.setText(currentObject.getUser());
        holder.ratingBarRating.setRating(currentObject.getRatings());
        holder.textViewReview.setText(currentObject.getReview());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView textViewEmail;
        RatingBar ratingBarRating;
        TextView textViewReview;
        public MyViewHolder(View itemView) {
            super(itemView);
            textViewEmail = (TextView) itemView.findViewById(R.id.userEmail);
            ratingBarRating = (RatingBar) itemView.findViewById(R.id.ratingBar2);
            textViewReview = (TextView) itemView.findViewById(R.id.userReview);
        }
    }
}
