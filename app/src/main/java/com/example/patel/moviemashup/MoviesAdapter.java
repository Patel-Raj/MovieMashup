package com.example.patel.moviemashup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by patel on 15-Feb-17.
 */

public class MoviesAdapter extends ArrayAdapter<Movies> {
    public MoviesAdapter(Activity context,ArrayList<Movies> list){
        super(context,0,list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;
        if(listViewItem == null){
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.movie_list_item,parent,false);
        }
        Movies movie = getItem(position);
        ImageView movieImage = (ImageView) listViewItem.findViewById(R.id.movieImage);
        TextView movieName = (TextView) listViewItem.findViewById(R.id.movieName);
        TextView movieYear = (TextView) listViewItem.findViewById(R.id.movieYear);
        TextView leadCast = (TextView) listViewItem.findViewById(R.id.leadStars);

        movieImage.setImageResource(movie.getMimage());
        movieName.setText(movie.getMmovieName());
        movieYear.setText(movie.getMyear());
        String cast = convertToString(movie.getMstars());
        leadCast.setText(cast);
        return listViewItem;
    }
    private String convertToString(String []leadCast)
    {
        String returnString = "";
        for(int i=0;i<leadCast.length;i++)
        {
            returnString += leadCast[i];
            if(i != leadCast.length-1)
            {
                returnString += ",";
            }
        }
        return returnString;
    }
}
