package com.example.patel.moviemashup.GeneralFiles;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.patel.moviemashup.MovieFiles.MoviePage;
import com.example.patel.moviemashup.CelebritiesFiles.CelebritiesActivity;
import com.example.patel.moviemashup.R;
import com.example.patel.moviemashup.TVFiles.TvActivity;
import com.example.patel.moviemashup.WatchList.YourWatchList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BaseActivity extends AppCompatActivity {

    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle actionBarDrawerToggle; // Toggle Button for Navigation drawer.
    protected FirebaseAuth firebaseAuth;
    protected FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.baseActivity);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
        // Getting current firebase user.
        user = firebaseAuth.getCurrentUser();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
    public void menuClickHandling(MenuItem item){
        switch (item.getItemId()){
            case R.id.movies:
                finish();
                startActivity(new Intent(this,MoviePage.class));
                break;
            case R.id.TV:
                finish();
                startActivity(new Intent(this,TvActivity.class));
                break;
            case R.id.celebrities:
                finish();
                startActivity(new Intent(this,CelebritiesActivity.class));
                break;
            case R.id.watchlist:
                startActivity(new Intent(this,YourWatchList.class));
                break;
            case R.id.logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this,MainActivity.class));
            break;
        }

    }
}
