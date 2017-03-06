package com.example.patel.moviemashup;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MoviePage extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_movie_page,null,false);
        mDrawerLayout.addView(contentView,0);

        // Displays the list of movies in the list view.
        displayMovieList();
    }
    private void displayMovieList()
    {
        final ArrayList<Movies> movieList = new ArrayList<Movies>();
        movieList.add(new Movies(R.drawable.commando2,"Commando 2","(2017)",new String[]{"Vidyut Jammwal","Adah Sharma","Esha Gupta"}));
        movieList.add(new Movies(R.drawable.dangal,"Dangaal","(2016)",new String[]{"Aamir Khan","Fatima Sana Shaikh","Sanya Malhotra","Sakshi Tanwar"}));
        movieList.add(new Movies(R.drawable.jaggajasoos,"Jagga Jasoos","(2017)",new String[]{"Ranbir Kapoor","Katrina Kaif","Govinda","Adah Sharma"}));
        movieList.add(new Movies(R.drawable.crack,"CRACK","(2017)",new String[]{"Akshay Kumar"}));
        movieList.add(new Movies(R.drawable.irada,"Irada","(2017)",new String[]{"Naseeruddin Shah","Arshad Warsi"," Sagarika Ghatge"}));
        movieList.add(new Movies(R.drawable.rangoon,"Rangoon","(2017)",new String[]{"Shahid Kapoor","Saif Ali Khan","Kangana Ranaut"}));
        movieList.add(new Movies(R.drawable.robot2,"Robot 2.0","(2017)",new String[]{"Rajinikanth","Akshay Kumar","Amy Jacksons"}));

        MoviesAdapter adapter = new MoviesAdapter(this,movieList);
        final ListView listView = (ListView) findViewById(R.id.moviesListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movies m = movieList.get(i);
                Toast.makeText(MoviePage.this,m.getMmovieName(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
