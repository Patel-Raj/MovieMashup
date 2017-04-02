package com.example.patel.moviemashup.AllFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.patel.moviemashup.GeneralFiles.BaseActivity;
import com.example.patel.moviemashup.R;
import com.example.patel.moviemashup.GeneralFiles.VolleySingelton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.patel.moviemashup.GeneralFiles.TmdbStrings.*;
public class MovieDetails extends BaseActivity {

    private long currentId;
    private VolleySingelton volleySingelton;
    private RequestQueue requestQueue;

    ImageView VbackgroundImage;
    ImageView VposterImage;
    TextView VyearAndTime;
    TextView VmovieName;
    TextView VgenreList;
    TextView VmovieOverview;
    TextView VreleaseDate;
    TextView VmovieBudget;
    TextView VmovieRevenue;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_movie_details, null, false);
        mDrawerLayout.addView(contentView, 0);

        initViews();
        Bundle b = getIntent().getExtras();
        currentId = b.getLong("MovieID");
        volleySingelton = VolleySingelton.getsInstance();
        requestQueue = volleySingelton.getmRequestQueue();
        imageLoader = volleySingelton.getImageLoader();
        sendJsonRequest(Long.toString(currentId));
    }
    public void initViews(){
        VbackgroundImage = (ImageView) findViewById(R.id.backgroundImage);
        VposterImage = (ImageView) findViewById(R.id.posterImage);
        VyearAndTime = (TextView) findViewById(R.id.yearAndTime);
        VmovieName = (TextView) findViewById(R.id.movieName);
        VgenreList = (TextView) findViewById(R.id.genreList);
        VmovieOverview = (TextView) findViewById(R.id.movieOverview);
        VreleaseDate  = (TextView) findViewById(R.id.releaseDate);
        VmovieBudget = (TextView) findViewById(R.id.movieBudget);
        VmovieRevenue = (TextView) findViewById(R.id.movieRevenue);
    }
    public static String getRequestUrl(String movieId) {
        return PREFIX + MOVIE +  movieId + API_KEY + LANGUAGE;
    }
    private void sendJsonRequest(String p){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestUrl(p), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJsonResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "Internet Connection Lost", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }
    private void parseJsonResponse(JSONObject response) {
        if (response != null && response.length() > 0) {

            try {
                long id = -1;
                String title = NA;
                String releaseDate = NA;
                double votes = -1;
                String overView = NA;
                String urlPoster = NA;
                String urlBackground = NA;
                int time = -1;
                ArrayList<String> gList = new ArrayList<>();
                long budget = -1;
                long revenue = -1;


                if(response.has(ID) && !response.isNull(ID)) {
                    id = response.getLong(ID);
                }
                if(response.has(TITLE) && !response.isNull(TITLE)) {
                    title = response.getString(TITLE);
                    VmovieName.setText(title);
                }
                if(response.has(RELEASE_DATE) && !response.isNull(RELEASE_DATE)) {
                    releaseDate = response.getString(RELEASE_DATE);
                    VreleaseDate.setText("Release Date : " + releaseDate);
                }
                if(response.has(VOTE_AVERAGE) && !response.isNull(VOTE_AVERAGE)) {
                    votes = response.getDouble(VOTE_AVERAGE);
                }
                if(response.has(OVERVIEW) && !response.isNull(OVERVIEW)) {
                    overView = response.getString(OVERVIEW);
                    VmovieOverview.setText("Gist : " + overView);
                }
                if(response.has(POSTER_PATH) && !response.isNull(POSTER_PATH)) {
                    urlPoster = IMAGE_PREFIX + POSTER_SIZE + response.getString(POSTER_PATH);
                    imageLoader.get(urlPoster, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            VposterImage.setImageBitmap(response.getBitmap());
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                }
                if(response.has(RUNTIME) && !response.isNull(RUNTIME)) {
                    time  = response.getInt(RUNTIME);
                    int hours = time/60;
                    int min = time%60;
                    VyearAndTime.setText("( " + Integer.toString(hours) + "h " + Integer.toString(min) + "m )");
                }
                if(response.has(BACKGROUND_PATH) && !response.isNull(BACKGROUND_PATH)) {
                    urlBackground = IMAGE_PREFIX + BACKSIZE + response.getString(BACKGROUND_PATH);
                    imageLoader.get(urlBackground, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            VbackgroundImage.setImageBitmap(response.getBitmap());
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                }
                if(response.has(BUDGET) && !response.isNull(BUDGET)) {
                    budget = response.getLong(BUDGET);
                    VmovieBudget.setText("Budget : $" + Long.toString(budget));
                }
                if(response.has(REVENUE) && !response.isNull(REVENUE)) {
                    revenue = response.getLong(REVENUE);
                    VmovieRevenue.setText("Revenue : $"  + Long.toString(revenue));
                }
                JSONArray array = response.getJSONArray(GENRE);
                ArrayList<String> genList = new ArrayList<>();
                for(int i=0;i<array.length();i++){
                    JSONObject currGenre = array.getJSONObject(i);
                    genList.add(currGenre.getString(NAME));
                }
                String genString = "";
                for(int i=0;i<genList.size();i++){
                    genString += genList.get(i);
                    if(i != genList.size() -1){
                        genString += ",";
                    }
                }
                VgenreList.setText(genString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
