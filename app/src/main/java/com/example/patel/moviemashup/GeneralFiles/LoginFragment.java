package com.example.patel.moviemashup.GeneralFiles;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.support.v4.app.FragmentManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.patel.moviemashup.MovieFiles.MoviePage;
import com.example.patel.moviemashup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by patel on 24-Feb-17.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText emailid, password;
    private Button loginButton;
    private TextView signUp;
    private CheckBox show_hide_password;
    private FragmentManager fragmentManager;
    private View view;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        initViews();
        setListeners();
        if(firebaseAuth.getCurrentUser() != null){
            getActivity().finish();
            startActivity(new Intent(getActivity(),MoviePage.class));
        }
        return view;
    }
    private void initViews()
    {
        emailid = (EditText) view.findViewById(R.id.et_email);
        password = (EditText) view.findViewById(R.id.et_password);
        loginButton = (Button) view.findViewById(R.id.b_login);
        signUp = (TextView) view.findViewById(R.id.tv_signup);
        show_hide_password =(CheckBox) view.findViewById(R.id.showPassword);
        progressDialog = new ProgressDialog(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();
    }
    private void setListeners()
    {
        loginButton.setOnClickListener(this);
        signUp.setOnClickListener(this);
        show_hide_password.setOnClickListener(this);
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    private void jumpToSignup(){
        fragmentManager.beginTransaction().replace(R.id.frameContainer,new SignupFragment()).commit();
    }
    @Override
    public void onClick(View localView) {
        if(localView == signUp){
            jumpToSignup();
        }
        else if(localView == loginButton){
            loginUser();
        }
        else if(localView == show_hide_password){
            if(show_hide_password.isChecked()){
                password.setTransformationMethod(null);
            }
            else{
                password.setTransformationMethod(new PasswordTransformationMethod());
            }
            password.setSelection(password.getText().length());
        }
    }
    private boolean validateEmail(CharSequence target) {
        if( !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()){
            return true;
        }
        Toast.makeText(getActivity(),"Please Check Email",Toast.LENGTH_SHORT).show();
        return false;
    }
    private boolean validatePwd(String str1){
        if(str1.length() > 0 ){
            return true;
        }
        Toast.makeText(getActivity(),"Please Check Password",Toast.LENGTH_SHORT).show();
        return false;
    }
    private void loginUser(){
        String emailText = emailid.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        if(validateEmail(emailText) && validatePwd(passwordText)){
            progressDialog.setMessage("Logging In...");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(emailText,passwordText)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                getActivity().finish();
                                startActivity(new Intent(getActivity(),MoviePage.class));
                            }
                            else{
                                Toast.makeText(getActivity(),"Login Failed",Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
        }
    }
}