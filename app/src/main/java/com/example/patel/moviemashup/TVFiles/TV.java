package com.example.patel.moviemashup.TVFiles;

/**
 * Created by patel on 30-Mar-17.
 */

public class TV {

    private long id;                // ID of Movie
    private String title;           // Movie Name
    private String releaseDate;       // Release Date
    private double votes;           // Ratings
    private String overView;        // Overview of the Movie
    private String urlPoster;       // Url of the Movie Poster

    public TV(long id,String title,String releaseDate,double votes,String overView,String urlPoster){
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.votes = votes;
        this.overView = overView;
        this.urlPoster = urlPoster;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getVotes() {
        return votes;
    }

    public long getId() {
        return id;
    }

    public String getOverView() {
        return overView;
    }

    public String getUrlPoster() {
        return urlPoster;
    }
}
