package com.example.patel.moviemashup.MovieFiles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.patel.moviemashup.R;
import com.example.patel.moviemashup.GeneralFiles.VolleySingelton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import static com.example.patel.moviemashup.GeneralFiles.TmdbStrings.*;
/**
 * Created by patel on 29-Mar-17.
 */

public class FragmentNowPlaying extends Fragment {

    private RecyclerView recyclerView;
    private VolleySingelton volleySingelton;
    private RequestQueue requestQueue;

    private ArrayList<Movies> movieList = new ArrayList<>();
    private AdapterNowPlaying adapterNowPlaying;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        volleySingelton = VolleySingelton.getsInstance();
        requestQueue = volleySingelton.getmRequestQueue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_now_playing,container,false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.recycleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterNowPlaying = new AdapterNowPlaying(getActivity());
        recyclerView.setAdapter(adapterNowPlaying);
        sendJsonRequest(1);
        sendJsonRequest(2);
        sendJsonRequest(3);
        sendJsonRequest(4);
        return layout;
    }

    public static String getRequestUrl(int page) {
        return PREFIX + NOW_PLAYING +  API_KEY + LANGUAGE + PAGE + page;
    }

    private void sendJsonRequest(int p){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestUrl(p), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                movieList.addAll(parseJsonResponse(response));
                adapterNowPlaying.setMovieList(movieList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Internet Connection Lost", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }
    private ArrayList<Movies> parseJsonResponse(JSONObject response) {
        ArrayList<Movies> listMovies = new ArrayList<>();
        if (response != null && response.length() > 0) {

            try {
                JSONArray moviesArray = response.getJSONArray(RESULTS);
                for (int i = 0; i < moviesArray.length(); i++) {
                    long id = 0;
                    String title = NA;
                    String releaseDate = NA;
                    double votes = -1;
                    String overView = NA;
                    String urlPoster = NA;

                    JSONObject currentMovie = moviesArray.getJSONObject(i);

                    if(currentMovie.has(ID) && !currentMovie.isNull(ID)) {
                        id = currentMovie.getLong(ID);
                    }
                    if(currentMovie.has(TITLE) && !currentMovie.isNull(TITLE)) {
                        title = currentMovie.getString(TITLE);
                    }
                    if(currentMovie.has(RELEASE_DATE) && !currentMovie.isNull(RELEASE_DATE)) {
                        releaseDate = currentMovie.getString(RELEASE_DATE);
                    }
                    if(currentMovie.has(VOTE_AVERAGE) && !currentMovie.isNull(VOTE_AVERAGE)) {
                        votes = currentMovie.getDouble(VOTE_AVERAGE);
                    }
                    if(currentMovie.has(OVERVIEW) && !currentMovie.isNull(OVERVIEW)) {
                        overView = currentMovie.getString(OVERVIEW);
                    }
                    if(currentMovie.has(POSTER_PATH) && !currentMovie.isNull(POSTER_PATH)) {
                        urlPoster = IMAGE_PREFIX + POSTER_SIZE + currentMovie.getString(POSTER_PATH);
                    }

                    Movies movies = new Movies(id, title, releaseDate, votes, overView, urlPoster);

                    if(id != -1 && title!=NA) {
                        listMovies.add(movies);
                    }
                }
                //Toast.makeText(getBaseContext(), data, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listMovies;
    }
}
