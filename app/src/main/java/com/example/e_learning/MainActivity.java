package com.example.e_learning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button signinBtnMain, signupBtnMain;
    private ConstraintLayout parent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViews();
        signinBtnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"signinViews: Started");
                Intent signIn = new Intent(MainActivity.this,activity_signin.class);
                startActivity(signIn);
            }
        });

        signupBtnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"signunViews: Started");
                Intent signUp = new Intent(MainActivity.this,activity_signup.class);
                startActivity(signUp);
            }
        });
    }

    //Initialize method, initialize all the button of home screen
    private void mainViews() {
        Log.d(TAG,"mainViews: Started");
        signinBtnMain = findViewById(R.id.signinBtn);
        signupBtnMain = findViewById(R.id.signupBtnSI);
    }
}