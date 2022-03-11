package com.example.e_learning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_signin extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";

    private EditText editUserNameSI, editPasswordSI;
    private ImageView signinBG;
    private TextView forgotPassSI, signinTextViewSI;
    private Button  loginBtnSI, signupBtnSI, backtoMainSI;
    private ConstraintLayout childSignIn;
    private FirebaseAuth ELearning2;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sign in content view
        setContentView(R.layout.activity_signin);

        //Init firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        signinView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginBtn:
                authUserSI();
                break;
            case R.id.signupBtn:
                Log.d(TAG,"signUpView: Started");
                Intent signUp = new Intent(activity_signin.this,activity_signup.class);
                startActivity(signUp);
                break;
            case R.id.mainBtn:
                Log.d(TAG, "backtoMain: Started");
                Intent backMain = new Intent(activity_signin.this,MainActivity.class);
                startActivity(backMain);
                break;
            case R.id.forgotPass:
                Log.d(TAG,"forgotPassSI: Started");
                Intent forgotPass = new Intent(activity_signin.this,activity_signup.class);
                startActivity(forgotPass);
                break;
        }
    }

    private void authUserSI(){

    }

    private void signinView(){
        Log.d(TAG,"signinView: Started");
        editUserNameSI = findViewById(R.id.editUserName);
        editPasswordSI = findViewById(R.id.editPassword);
        forgotPassSI = findViewById(R.id.forgotPass);

        signinTextViewSI = findViewById(R.id.signinTextView);
        signinBG = findViewById(R.id.bg_signin);

        backtoMainSI = findViewById(R.id.mainBtn);
        backtoMainSI.setOnClickListener(this);

        loginBtnSI = findViewById(R.id.loginBtn);
        loginBtnSI.setOnClickListener(this);

        signupBtnSI = findViewById(R.id.signupBtn);
        signupBtnSI.setOnClickListener(this);


        childSignIn = findViewById(R.id.childSignin);

    }


}

