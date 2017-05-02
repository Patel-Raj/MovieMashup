package com.example.patel.moviemashup.CelebritiesFiles;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.patel.moviemashup.GeneralFiles.TmdbStrings.*;
public class CelebritiesDetails extends BaseActivity implements View.OnClickListener{

    private long currentId;
    private VolleySingelton volleySingelton;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    DatabaseReference reviewDatabase;
    List<Review> reviewList;


    ImageView VbackgroundImage;
    ImageView VposterImage;
    TextView VdateOfBirth;
    TextView VmovieName;
    TextView VmovieOverview;
    TextView Vgender;
    TextView VplaceOfBirth;
    TextView Vpopularity;
    RatingBar VratingBar;
    EditText Vreview;
    Button VpostReview;
    RecyclerView VrecyclerView;
    ReviewList adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_celebrities_details, null, false);
        mDrawerLayout.addView(contentView, 0);

        initViews();
        reviewList = new ArrayList<>();
        VpostReview.setOnClickListener(this);
        Bundle b = getIntent().getExtras();
        currentId = b.getLong("CID");
        reviewDatabase = FirebaseDatabase.getInstance().getReference(Long.toString(currentId));
        volleySingelton = VolleySingelton.getsInstance();
        requestQueue = volleySingelton.getmRequestQueue();
        imageLoader = volleySingelton.getImageLoader();
        sendJsonRequest(Long.toString(currentId));
    }
    public void initViews(){
        VbackgroundImage = (ImageView) findViewById(R.id.backgroundImage);
        VposterImage = (ImageView) findViewById(R.id.posterImage);
        VdateOfBirth = (TextView) findViewById(R.id.dateOfBirth);
        VmovieName = (TextView) findViewById(R.id.personName);
        VmovieOverview = (TextView) findViewById(R.id.movieOverview);
        Vgender  = (TextView) findViewById(R.id.gender);
        VplaceOfBirth = (TextView) findViewById(R.id.placeOfBirth);
        Vpopularity = (TextView) findViewById(R.id.popularity);
        VratingBar = (RatingBar) findViewById(R.id.ratingBarC);
        Vreview = (EditText) findViewById(R.id.reviewC);
        VpostReview = (Button) findViewById(R.id.postReviewC);
        VrecyclerView = (RecyclerView) findViewById(R.id.recycleListC);
    }
    public static String getRequestUrl(String movieId) {
        return PREFIX + PERSON +  movieId + API_KEY + LANGUAGE;
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
                String name = NA;
                String dob = NA;
                String overView = NA;
                String pob = NA;
                String pop = NA;
                String urlPoster = NA;
                String urlBackground = NA;

                if(response.has(ID) && !response.isNull(ID)) {
                    id = response.getLong(ID);
                }
                if(response.has(NAME) && !response.isNull(NAME)) {
                    name = response.getString(NAME);
                    VmovieName.setText(name);
                }
                if(response.has(DOB) && !response.isNull(DOB)) {
                    dob = response.getString(DOB);
                    VdateOfBirth.setText("Date of Birth : " + dob);
                }
                if(response.has(BIOGRAPHY) && !response.isNull(BIOGRAPHY)) {
                    overView = response.getString(BIOGRAPHY);
                    VmovieOverview.setText("Biography : " + overView);
                }
                if(response.has(PROFILE_PATH) && !response.isNull(PROFILE_PATH)) {
                    urlPoster = IMAGE_PREFIX + POSTER_SIZE + response.getString(PROFILE_PATH);
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
                if(response.has(GENDER) && !response.isNull(GENDER)) {
                    if(response.getInt(GENDER) == 1)
                        Vgender.setText("Gender : " + "Female");
                    else
                        Vgender.setText("Gender : " + "Male");
                }
                if(response.has(PROFILE_PATH) && !response.isNull(PROFILE_PATH)) {
                    urlBackground = IMAGE_PREFIX + BACKSIZE + response.getString(PROFILE_PATH);
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
                if(response.has(POB) && !response.isNull(POB)) {
                    pob = response.getString(POB);
                    VplaceOfBirth.setText("Place of Birth : " + pob);
                }
                if(response.has(POPULARITY) && !response.isNull(POPULARITY)) {
                    pop = Double.toString(response.getDouble(POPULARITY));
                    Vpopularity.setText("Popularity : " + pop);
                }
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
                Toast.makeText(CelebritiesDetails.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == VpostReview){
            float ratingPosted = VratingBar.getRating();
            String reviewPosted = Vreview.getText().toString().trim();
            if(reviewPosted.length() == 0){
                Toast.makeText(this,"Please Write Review",Toast.LENGTH_SHORT).show();
            }
            else{
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    String uname = user.getEmail();
                    String id = reviewDatabase.push().getKey();
                    Review review = new Review(id,uname,ratingPosted,reviewPosted);
                    reviewDatabase.child(id).setValue(review);
                    Toast.makeText(this,"Posted Success",Toast.LENGTH_SHORT).show();
                    VratingBar.setRating(0);
                    Vreview.setText("");
                }
            }
        }
    }
}
