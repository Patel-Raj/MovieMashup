package com.example.patel.moviemashup.TVFiles;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.patel.moviemashup.GeneralFiles.BaseActivity;
import com.example.patel.moviemashup.R;

public class TvActivity extends BaseActivity {

    ViewPager viewPager = null;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_tv, null, false);
        mDrawerLayout.addView(contentView, 0);


        viewPager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new MyTvAdapter(fm));

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
class MyTvAdapter extends FragmentStatePagerAdapter {
    public MyTvAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment tempFragment = null;
        if(position == 0)
            tempFragment = new FragmentOnAir();
        else if(position == 1)
            tempFragment = new FragmentTvTopRated();
        else if(position == 2)
            tempFragment = new FragmentTvPopular();
        return tempFragment;
    }
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String tempTitle = new String();
        if(position == 0)
            tempTitle = "On Air";
        else if(position == 1)
            tempTitle = "Top Rated";
        else if(position == 2)
            tempTitle = "Most Popular";
        return tempTitle;
    }
}