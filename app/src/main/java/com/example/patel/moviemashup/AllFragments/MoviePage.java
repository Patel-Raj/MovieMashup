package com.example.patel.moviemashup.AllFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.example.patel.moviemashup.GeneralFiles.BaseActivity;
import com.example.patel.moviemashup.R;

public class MoviePage extends BaseActivity {

    ViewPager viewPager = null;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_movie_page, null, false);
        mDrawerLayout.addView(contentView, 0);


        viewPager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new MyMovieAdapter(fm));

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
class MyMovieAdapter extends FragmentStatePagerAdapter{
    public MyMovieAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment tempFragment = null;
        if(position == 0)
            tempFragment = new FragmentNowPlaying();
        else if(position == 1)
            tempFragment = new FragmentUpComing();
        else if(position == 2)
            tempFragment = new FragmentTopRated();
        else if(position == 3)
            tempFragment = new FragmentPopular();
        return tempFragment;
    }
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String tempTitle = new String();
        if(position == 0)
            tempTitle = "Now Playing";
        else if(position == 1)
            tempTitle = "Up Coming";
        else if(position == 2)
            tempTitle = "Top Rated";
        else if(position == 3)
            tempTitle = "Most Popular";
        return tempTitle;
    }
}