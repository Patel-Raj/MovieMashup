package com.example.patel.moviemashup.CelebritiesFiles;

/**
 * Created by patel on 30-Mar-17.
 */


/**
 * Created by patel on 30-Mar-17.
 */

public class Celebrities {

    private long id;                // ID of Movie
    private String name;           // Movie Name
    private String urlPoster;

    public Celebrities(long id,String name,String urlPoster){
        this.id = id;
        this.name = name;
        this.urlPoster = urlPoster;
    }

    public String getUrlPoster() {
        return urlPoster;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
