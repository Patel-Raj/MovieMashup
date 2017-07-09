package com.example.patel.moviemashup.WatchList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.example.patel.moviemashup.GeneralFiles.VolleySingelton;
import com.example.patel.moviemashup.MovieFiles.MovieDetails;
import com.example.patel.moviemashup.R;
import com.example.patel.moviemashup.TVFiles.TVDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import static com.example.patel.moviemashup.GeneralFiles.TmdbStrings.*;
public class YourWatchList extends AppCompatActivity {

    ListView listView;
    DatabaseReference watchlist;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    List<WatchDetails> watchDetailsArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_watch_list);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(YourWatchList.this,watchDetailsArrayList.get(i).getType(),Toast.LENGTH_SHORT).show();
                if(watchDetailsArrayList.get(i).getType().equals(MOVIE)){
                    Intent intent = new Intent(YourWatchList.this,MovieDetails.class);
                    intent.putExtra("MovieID",watchDetailsArrayList.get(i).getIdd());
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(YourWatchList.this,TVDetails.class);
                    intent.putExtra("TVID",watchDetailsArrayList.get(i).getIdd());
                    startActivity(intent);
                }
            }
        });
        String email = user.getEmail();
        String newEmail = "";
        for(int i=0;i<email.length();i++){
            if(email.charAt(i) == '@')
                break;
            newEmail += email.charAt(i);
        }
        watchlist = FirebaseDatabase.getInstance().getReference(newEmail);
        watchDetailsArrayList = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        watchlist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                watchDetailsArrayList.clear();
                for(DataSnapshot watch : dataSnapshot.getChildren()){
                    WatchDetails w = watch.getValue(WatchDetails.class);
                    watchDetailsArrayList.add(w);
                }
                WatchAdapter adapter = new WatchAdapter(YourWatchList.this, watchDetailsArrayList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(YourWatchList.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
