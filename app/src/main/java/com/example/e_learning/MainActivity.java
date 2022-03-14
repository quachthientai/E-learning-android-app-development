package com.example.e_learning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{
    private static final String TAG = "MainActivity";
    private Button signinBtnMain, signupBtnMain;
    private ConstraintLayout parent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViews();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.signinBtn:
                Log.d(TAG,"signinViews: Started");
                Intent signIn = new Intent(MainActivity.this,activity_signin.class);
                startActivity(signIn);
                break;
            case R.id.signupBtn:
                Log.d(TAG,"signunViews: Started");
                Intent signUp = new Intent(MainActivity.this,activity_signup.class);
                startActivity(signUp);
                break;
        }
    }

    //Initialize method, initialize all the button of home screen
    private void mainViews() {
        Log.d(TAG,"mainViews: Started");
        signinBtnMain = findViewById(R.id.signinBtn);
        signinBtnMain.setOnClickListener(this);
        signupBtnMain = findViewById(R.id.signupBtn);
        signupBtnMain.setOnClickListener(this);
    }
}