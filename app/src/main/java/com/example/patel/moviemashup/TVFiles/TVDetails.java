package com.example.patel.moviemashup.TVFiles;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.patel.moviemashup.GeneralFiles.BaseActivity;
import com.example.patel.moviemashup.GeneralFiles.Review;
import com.example.patel.moviemashup.GeneralFiles.ReviewList;
import com.example.patel.moviemashup.GeneralFiles.VolleySingelton;
import com.example.patel.moviemashup.MovieFiles.MovieDetails;
import com.example.patel.moviemashup.R;
import com.example.patel.moviemashup.WatchList.WatchDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.patel.moviemashup.GeneralFiles.TmdbStrings.*;
public class TVDetails extends BaseActivity implements View.OnClickListener{

    private long currentId;
    private VolleySingelton volleySingelton;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    DatabaseReference reviewDatabase;
    DatabaseReference watchlist;
    List<Review> reviewList;
    Boolean added = false;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ImageView VbackgroundImage;
    ImageView VposterImage;
    TextView VyearAndTime;
    TextView VmovieName;
    TextView VgenreList;
    TextView VmovieOverview;
    TextView VreleaseDate;
    TextView VmovieBudget;
    TextView VmovieRevenue;
    RatingBar VratingBar;
    EditText Vreview;
    Button VpostReview;
    ImageView Vwatchlist;
    RecyclerView VrecyclerView;
    ReviewList adapter;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_tvdetails, null, false);
        mDrawerLayout.addView(contentView, 0);

        initViews();
        reviewList = new ArrayList<>();
        VpostReview.setOnClickListener(this);
        Vwatchlist.setOnClickListener(this);
        Bundle b = getIntent().getExtras();
        currentId = b.getLong("TVID");
        reviewDatabase = FirebaseDatabase.getInstance().getReference(Long.toString(currentId));
        String email = user.getEmail();
        String newEmail = "";
        for(int i=0;i<email.length();i++){
            if(email.charAt(i) == '@')
                break;
            newEmail += email.charAt(i);
        }
        watchlist = FirebaseDatabase.getInstance().getReference(newEmail);
        volleySingelton = VolleySingelton.getsInstance();
        requestQueue = volleySingelton.getmRequestQueue();
        imageLoader = volleySingelton.getImageLoader();
        sendJsonRequest(Long.toString(currentId));
    }
    public void initViews(){
        VbackgroundImage = (ImageView) findViewById(R.id.backgroundImage);
        VposterImage = (ImageView) findViewById(R.id.posterImage);
        VyearAndTime = (TextView) findViewById(R.id.yearAndTime);
        VmovieName = (TextView) findViewById(R.id.tvName);
        VgenreList = (TextView) findViewById(R.id.genreList);
        VmovieOverview = (TextView) findViewById(R.id.tvOverview);
        VreleaseDate  = (TextView) findViewById(R.id.releaseDate);
        VmovieBudget = (TextView) findViewById(R.id.movieBudget);
        VmovieRevenue = (TextView) findViewById(R.id.movieRevenue);
        VratingBar = (RatingBar) findViewById(R.id.ratingBarTv);
        Vreview = (EditText) findViewById(R.id.reviewTv);
        VpostReview = (Button) findViewById(R.id.postReviewTv);
        VrecyclerView = (RecyclerView) findViewById(R.id.recycleListTv);
        Vwatchlist = (ImageView) findViewById(R.id.watchlistImageTV);
    }
    public static String getRequestUrl(String movieId) {
        return PREFIX + TV +  movieId + API_KEY + LANGUAGE;
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
                if(response.has(NAME) && !response.isNull(NAME)) {
                    title = response.getString(NAME);
                    VmovieName.setText(title);
                }
                if(response.has(AIRDATE) && !response.isNull(AIRDATE)) {
                    releaseDate = response.getString(AIRDATE);
                    VreleaseDate.setText("First On Air : " + releaseDate);
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
                    str = urlPoster;
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
                if(response.has(SEASONS) && !response.isNull(SEASONS)) {
                    time  = response.getInt(SEASONS);
                    VyearAndTime.setText("( Seasons : " + Integer.toString(time)  + " )");
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

    @Override
    protected void onStart() {
        super.onStart();
        reviewDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                reviewList.clear();
                for (DataSnapshot reviewSnapshot : dataSnapshot.getChildren()) {
                    Review reviewNow = reviewSnapshot.getValue(Review.class);
                    reviewList.add(reviewNow);
                }
                adapter = new ReviewList(getBaseContext(),reviewList);
                VrecyclerView.setAdapter(adapter);
                VrecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TVDetails.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });
        watchlist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot watch : dataSnapshot.getChildren()){
                    String TvID = watch.getKey();
                    if(TvID.equals(Long.toString(currentId))){
                        Vwatchlist.setImageResource(R.drawable.added);
                        added = true;
                        break;
                    }
                }
                if(added == false){
                    Vwatchlist.setImageResource(R.drawable.notadded);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TVDetails.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        reviewDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                reviewList.clear();
                for (DataSnapshot reviewSnapshot : dataSnapshot.getChildren()) {
                    Review reviewNow = reviewSnapshot.getValue(Review.class);
                    reviewList.add(reviewNow);
                }
                adapter = new ReviewList(getBaseContext(),reviewList);
                VrecyclerView.setAdapter(adapter);
                VrecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TVDetails.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });
        watchlist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot watch : dataSnapshot.getChildren()){
                    String TvID = watch.getKey();
                    if(TvID.equals(Long.toString(currentId))){
                        Vwatchlist.setImageResource(R.drawable.added);
                        added = true;
                        break;
                    }
                }
                if(added == false){
                    Vwatchlist.setImageResource(R.drawable.notadded);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TVDetails.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public void onClick(View view) {
        if(view == VpostReview) {
            float ratingPosted = VratingBar.getRating();
            String reviewPosted = Vreview.getText().toString().trim();
            if (reviewPosted.length() == 0) {
                Toast.makeText(this, "Please Write Review", Toast.LENGTH_SHORT).show();
            } else {
                if (user != null) {
                    String uname = user.getEmail();
                    String id = reviewDatabase.push().getKey();
                    Review review = new Review(id, uname, ratingPosted, reviewPosted);
                    reviewDatabase.child(id).setValue(review);
                    Toast.makeText(this, "Posted Success", Toast.LENGTH_SHORT).show();
                    VratingBar.setRating(0);
                    Vreview.setText("");
                }
            }
        }
        else if(view == Vwatchlist){
            if(added == false){
                Vwatchlist.setImageResource(R.drawable.added);
                added = true;
                watchlist.child(Long.toString(currentId)).setValue(new WatchDetails(currentId,VmovieName.getText().toString(),TV,str));
                Toast.makeText(this,"Added successfully",Toast.LENGTH_SHORT).show();
            }
            else{
                Vwatchlist.setImageResource(R.drawable.notadded);
                added = false;
                watchlist.child(Long.toString(currentId)).removeValue();
                Toast.makeText(this,"Removed successfully",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
