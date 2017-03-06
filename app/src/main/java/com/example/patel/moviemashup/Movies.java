package com.example.patel.moviemashup;

/**
 * Created by patel on 15-Feb-17.
 */

public class Movies {

    private Integer Mimage;
    private String MmovieName;
    private String Myear;
    private String [] Mstars;

    public Movies(Integer picture,String name,String year,String []leadStars){
        Mimage = picture;
        MmovieName = name;
        Myear = year;
        Mstars = leadStars;
    }
    public Integer getMimage(){return Mimage;}
    public String getMmovieName(){return MmovieName;}
    public String getMyear(){return Myear;}
    public String[] getMstars(){return Mstars;}
}
