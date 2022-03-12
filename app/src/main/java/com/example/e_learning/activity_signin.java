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
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_signin extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";

    private EditText editUserNameSI, editPasswordSI;
    private ImageView signinBG;
    private TextView forgotPassSI, signinTextView, usernameWarnSI, passWarnSI;
    private Button  loginBtnSI, signupBtnSI, backtoMain;
    private ConstraintLayout childSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sign in content view
        setContentView(R.layout.activity_signin);

        //Init firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        forgotPassSI = (TextView) findViewById(R.id.forgotPass);
        forgotPassSI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_signin.this, forgot_password.class));

            }
        });

        signinView();

        backtoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.backtomain:
                        Log.d(TAG, "backtoMain: Started");
                        Intent backMain = new Intent(activity_signin.this, MainActivity.class);
                        startActivity(backMain);
                        break;

                }
            }
        });

        signupBtnSI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(activity_signin.this,activity_signup.class);
                startActivity(signUp);
            }
        });

        loginBtnSI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initLoginSI();
            }
        });


    }

    private void initLoginSI(){
        Log.d(TAG, "loginViewSI: Started");
        if (validateData()){

        }
    }

    private boolean validateData(){
        Log.d(TAG,"validateData: Started");
        if ((editUserNameSI.getText().toString().equals("")) && (editPasswordSI.getText().toString().equals(""))){
            usernameWarnSI.setVisibility(View.VISIBLE);
            passWarnSI.setVisibility(View.VISIBLE);
            return false;
        }
        else if (!(editUserNameSI.getText().toString().equals("")) && (editPasswordSI.getText().toString().equals(""))){
            passWarnSI.setVisibility(View.VISIBLE);
            return false;
        }
        else if ((editUserNameSI.getText().toString().equals("")) && !(editPasswordSI.getText().toString().equals(""))){
            usernameWarnSI.setVisibility(View.VISIBLE);
            return false;
        }


        return true;
    }

    private void signinView(){
        Log.d(TAG,"signinView: Started");
        editUserNameSI = findViewById(R.id.editUserName);
        editPasswordSI = findViewById(R.id.editPassword);
        forgotPassSI = findViewById(R.id.forgotPass);

        signinTextView = findViewById(R.id.signinTextView);
        signinBG = findViewById(R.id.bg_signin);

        backtoMain = findViewById(R.id.backtomain);
        loginBtnSI = findViewById(R.id.loginBtn);
        signupBtnSI = findViewById(R.id.signupBtn);

        usernameWarnSI = findViewById(R.id.usernameWarn);
        passWarnSI = findViewById(R.id.passWarn);
        childSignIn = findViewById(R.id.childSignin);

    }

    @Override
    public void onClick(View view) {
    }
}

