package com.example.patel.moviemashup.CelebritiesFiles;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.RequestQueue;
import com.example.patel.moviemashup.GeneralFiles.BaseActivity;
import com.example.patel.moviemashup.GeneralFiles.VolleySingelton;
import com.example.patel.moviemashup.R;


public class CelebritiesActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private VolleySingelton volleySingelton;
    private RequestQueue requestQueue;

    ViewPager viewPager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_celebrities, null, false);
        mDrawerLayout.addView(contentView, 0);

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new MyCelebAdapter(fm));
    }
}
class MyCelebAdapter extends FragmentStatePagerAdapter {
    public MyCelebAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment tempFragment = null;
        if(position == 0)
            tempFragment = new FragmentCeleb();
        return tempFragment;
    }
    @Override
    public int getCount() {
        return 1;
    }
}
