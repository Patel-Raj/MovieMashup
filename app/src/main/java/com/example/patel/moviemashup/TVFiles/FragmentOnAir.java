package com.example.patel.moviemashup.TVFiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by patel on 30-Mar-17.
 */
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.patel.moviemashup.GeneralFiles.VolleySingelton;
import com.example.patel.moviemashup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.patel.moviemashup.GeneralFiles.TmdbStrings.*;
public class FragmentOnAir extends Fragment{
    private RecyclerView recyclerView;
    private VolleySingelton volleySingelton;
    private RequestQueue requestQueue;

    private ArrayList<TV> tvList = new ArrayList<>();
    private AdapterTV adapterTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        volleySingelton = VolleySingelton.getsInstance();
        requestQueue = volleySingelton.getmRequestQueue();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_on_air,container,false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.recycleListT1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterTV = new AdapterTV(getActivity());
        recyclerView.setAdapter(adapterTV);
        sendJsonRequest(1);
        sendJsonRequest(2);
        return layout;
    }

    public static String getRequestUrl(int page) {
        return PREFIX + ONAIR +  API_KEY + LANGUAGE + PAGE + page;
    }
    private void sendJsonRequest(int p){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestUrl(p), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                tvList.addAll(parseJsonResponse(response));
                adapterTV.setTVList(tvList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Internet Connection Lost", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }

    private ArrayList<TV> parseJsonResponse(JSONObject response) {
        ArrayList<TV> listTV = new ArrayList<>();
        if (response != null && response.length() > 0) {

            try {
                JSONArray tvArray = response.getJSONArray(RESULTS);
                for (int i = 0; i < tvArray.length(); i++) {
                    long id = 0;
                    String title = NA;
                    String releaseDate = NA;
                    double votes = -1;
                    String overView = NA;
                    String urlPoster = NA;

                    JSONObject currentTV = tvArray.getJSONObject(i);

                    if(currentTV.has(ID) && !currentTV.isNull(ID)) {
                        id = currentTV.getLong(ID);
                    }
                    if(currentTV.has(NAME) && !currentTV.isNull(NAME)) {
                        title = currentTV.getString(NAME);
                    }
                    if(currentTV.has(AIRDATE) && !currentTV.isNull(AIRDATE)) {
                        releaseDate = currentTV.getString(AIRDATE);
                    }
                    if(currentTV.has(VOTE_AVERAGE) && !currentTV.isNull(VOTE_AVERAGE)) {
                        votes = currentTV.getDouble(VOTE_AVERAGE);
                    }
                    if(currentTV.has(OVERVIEW) && !currentTV.isNull(OVERVIEW)) {
                        overView = currentTV.getString(OVERVIEW);
                    }
                    if(currentTV.has(POSTER_PATH) && !currentTV.isNull(POSTER_PATH)) {
                        urlPoster = IMAGE_PREFIX + POSTER_SIZE + currentTV.getString(POSTER_PATH);
                    }

                    TV tv = new TV(id, title, releaseDate, votes, overView, urlPoster);

                    if(id != -1 && title!=NA) {
                        listTV.add(tv);
                    }
                }
                //Toast.makeText(getBaseContext(), data, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listTV;
    }
}
