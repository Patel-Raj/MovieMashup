package com.example.patel.moviemashup.GeneralFiles;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patel.moviemashup.GeneralFiles.LoginFragment;
import com.example.patel.moviemashup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignupFragment extends Fragment implements View.OnClickListener{

    private EditText name,emailId,mobileNumber,password,confirmPassword;
    private Button signupButton;
    private TextView login;
    private CheckBox termsAndConditions;
    private FragmentManager fragmentManager;
    private View view;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    private void initViews()
    {
        name = (EditText) view.findViewById(R.id.fullName);
        emailId = (EditText) view.findViewById(R.id.et_email);
        mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
        password = (EditText) view.findViewById(R.id.et_password);
        confirmPassword = (EditText) view.findViewById(R.id.et_confirm_password);
        signupButton = (Button) view.findViewById(R.id.b_signup);
        termsAndConditions =(CheckBox) view.findViewById(R.id.agreement);
        login = (TextView) view.findViewById(R.id.tv_login);
        progressDialog = new ProgressDialog(getActivity());
        firebaseAuth = FirebaseAuth.getInstance();
    }
    private void setListeners() {
        signupButton.setOnClickListener(this);
        login.setOnClickListener(this);
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    private void jumpToLogin(){
        fragmentManager.beginTransaction().replace(R.id.frameContainer,new LoginFragment()).commit();
    }
    @Override
    public void onClick(View localView) {
        if(localView== signupButton){
            registerUser();
        }
        else if(localView == login){
            jumpToLogin();
        }
    }
    private void registerUser(){
        String fulNameText = name.getText().toString().trim();
        String emailIdText = emailId.getText().toString().trim();
        String mobileText = mobileNumber.getText().toString().trim();
        String passwordText = password.getText().toString().trim();
        String confirmPasswordText = confirmPassword.getText().toString().trim();
        boolean aggreement = termsAndConditions.isChecked();
        if(aggreement == true){
            if(validateName(fulNameText) && validateEmail(emailIdText) && validateMobile(mobileText) && validatePwd(passwordText,confirmPasswordText)) {
                progressDialog.setMessage("Registering User...");
                progressDialog.show();

                // Password must be 6 characters long.
                firebaseAuth.createUserWithEmailAndPassword(emailIdText,passwordText).
                        addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(),"Registered Success!",Toast.LENGTH_SHORT).show();
                                    jumpToLogin();
                                }
                                else{
                                    Toast.makeText(getActivity(),"Registration Failed",Toast.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();
                            }
                        });
            }
        }
        else {
            Toast.makeText(getActivity(), "Please Check T&C", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean validateName(String fullNameText){
        int count = 0;
        if(fullNameText.length() == 0)  count = 3;
        for(int i=0;i<fullNameText.length();i++)
        {
            if(Character.isLetter(fullNameText.charAt(i)) == false){
                if(fullNameText.charAt(i) != ' '){
                    count = 3;
                    break;
                }
                else {
                    count++;
                }
            }
        }
        if(count > 2){
            Toast.makeText(getActivity(),"Please Check Name",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validateMobile(CharSequence mobileText){
        if(!TextUtils.isEmpty(mobileText) && android.text.TextUtils.isDigitsOnly(mobileText) && (mobileText.length()==10)){
            return true;
        }
        Toast.makeText(getActivity(),"Please Check Mobile",Toast.LENGTH_SHORT).show();
        return false;
    }
    private boolean validateEmail(CharSequence target) {
        if( !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()){
            return true;
        }
        Toast.makeText(getActivity(),"Please Check Email",Toast.LENGTH_SHORT).show();
        return false;
    }
    private boolean validatePwd(String str1,String str2){
        if(str1.length() >0 && str2.length()>0 && str1.equals(str2)){
            return true;
        }
        Toast.makeText(getActivity(),"Please Check Password",Toast.LENGTH_SHORT).show();
        return false;
    }
}
