package com.example.patel.moviemashup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.support.v4.app.FragmentManager;
import android.widget.Button;
import android.widget.CheckBox;


/**
 * Created by patel on 24-Feb-17.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {

    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static FragmentManager fragmentManager;
    private static View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        initViews();
        setListeners();
        return view;
    }
    private void initViews()
    {
        emailid = (EditText) view.findViewById(R.id.et_email);
        password = (EditText) view.findViewById(R.id.et_password);
        loginButton = (Button) view.findViewById(R.id.b_login);
        forgotPassword = (TextView) view.findViewById(R.id.forgotPassword);
        signUp = (TextView) view.findViewById(R.id.tv_signup);
        show_hide_password =(CheckBox) view.findViewById(R.id.showPassword);
    }
    private void setListeners()
    {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public void onClick(View localView) {
        if(localView == signUp){
            fragmentManager.beginTransaction().replace(R.id.frameContainer,new SignupFragment()).commit();
        }
        else if(localView == loginButton){
            startActivity(new Intent(getContext(),MoviePage.class));
        }
        else if(localView == forgotPassword){
            fragmentManager.beginTransaction().replace(R.id.frameContainer,new ForgetPassword()).commit();
        }
    }
}