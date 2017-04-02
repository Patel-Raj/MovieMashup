package com.example.patel.moviemashup.CelebritiesFiles;

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
public class FragmentCeleb extends Fragment {
    private RecyclerView recyclerView;
    private VolleySingelton volleySingelton;
    private RequestQueue requestQueue;

    private ArrayList<Celebrities> celebrities = new ArrayList<>();
    private AdapterCeleb adapterCeleb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        volleySingelton = VolleySingelton.getsInstance();
        requestQueue = volleySingelton.getmRequestQueue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_celeb,container,false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.recycleListC);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterCeleb = new AdapterCeleb(getActivity());
        recyclerView.setAdapter(adapterCeleb);
        sendJsonRequest(1);
        sendJsonRequest(2);
        sendJsonRequest(3);
        sendJsonRequest(4);
        return layout;
    }
    public static String getRequestUrl(int page) {
        return PREFIX + PER_POPULAR +  API_KEY + LANGUAGE + PAGE + page;
    }
    private void sendJsonRequest(int p){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestUrl(p), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                celebrities.addAll(parseJsonResponse(response));
                adapterCeleb.setCelebritiesArrayListt(celebrities);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Internet Connection Lost", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }
    private ArrayList<Celebrities> parseJsonResponse(JSONObject response) {
        ArrayList<Celebrities> listCelebrities = new ArrayList<>();
        if (response != null && response.length() > 0) {

            try {
                JSONArray CelebritiesArray = response.getJSONArray(RESULTS);
                for (int i = 0; i < CelebritiesArray.length(); i++) {
                    long id = 0;
                    String title = NA;
                    String urlPoster = NA;

                    JSONObject currentCelebrities = CelebritiesArray.getJSONObject(i);

                    if(currentCelebrities.has(ID) && !currentCelebrities.isNull(ID)) {
                        id = currentCelebrities.getLong(ID);
                    }
                    if(currentCelebrities.has(NAME) && !currentCelebrities.isNull(NAME)) {
                        title = currentCelebrities.getString(NAME);
                    }
                    if(currentCelebrities.has(PROFILE_PATH) && !currentCelebrities.isNull(PROFILE_PATH)) {
                        urlPoster = IMAGE_PREFIX + POSTER_SIZE + currentCelebrities.getString(PROFILE_PATH);
                    }

                    Celebrities Celebrities = new Celebrities(id, title,urlPoster);

                    if(id != -1 && title!=NA) {
                        listCelebrities.add(Celebrities);
                    }
                }
                //Toast.makeText(getBaseContext(), data, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listCelebrities;
    }
}
