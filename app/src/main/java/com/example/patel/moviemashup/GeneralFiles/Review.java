package com.example.patel.moviemashup.GeneralFiles;

/**
 * Created by patel on 19-Apr-17.
 */

public class Review {
    String id;
    String user;
    float ratings;
    String review;

    public Review(){

    }
    public Review(String id, String user, float ratings, String review){
        this.id = id;
        this.user = user;
        this.ratings = ratings;
        this.review = review;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public float getRatings() {
        return ratings;
    }

    public String getReview() {
        return review;
    }
}
