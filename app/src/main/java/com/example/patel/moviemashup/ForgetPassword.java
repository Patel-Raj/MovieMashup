package com.example.patel.moviemashup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class ForgetPassword extends Fragment implements View.OnClickListener{

    private static EditText emailId;
    private static Button submit,back;
    private static FragmentManager fragmentManager;
    private static View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgetpassword_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    private void initViews()
    {
        emailId = (EditText) view.findViewById(R.id.et_email);
        submit = (Button) view.findViewById(R.id.submit);
        back = (Button) view.findViewById(R.id.back);
    }
    private void setListeners() {
        emailId.setOnClickListener(this);
        submit.setOnClickListener(this);
        back.setOnClickListener(this);
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public void onClick(View localView) {
        if(localView == submit){
            // Do Task here.
        }
        else if(localView == back){
            fragmentManager.beginTransaction().replace(R.id.frameContainer,new LoginFragment()).commit();
        }
    }
}
