/*
* This is the First Page of the App and it contains the login, signup and Forget Password fragments.*/
package com.example.patel.moviemashup.GeneralFiles;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.patel.moviemashup.R;

public class MainActivity extends AppCompatActivity {

    private static FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().replace(R.id.frameContainer,new LoginFragment()).commit();
        }
    }
}
