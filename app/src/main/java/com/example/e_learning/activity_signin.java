package com.example.e_learning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_signin extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";

    private EditText editEmailSI, editPasswordSI;
    private ImageView signinBG;
    private TextView forgotPassSI, signinTextViewSI;
    private Button  loginBtnSI, signupBtnSI, backtoMainSI;
    private ConstraintLayout childSignIn;
    private FirebaseAuth ELearning2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sign in content view
        setContentView(R.layout.activity_signin);

        //Init firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        signinView();
        ELearning2 = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginBtnSI:
                userLoginSI();
                break;
            case R.id.signupBtnSI:
                Log.d(TAG,"signUpView: Started");
                Intent signUp = new Intent(activity_signin.this,activity_signup.class);
                startActivity(signUp);
                break;
            case R.id.mainBtnSI:
                Log.d(TAG, "backtoMain: Started");
                Intent backMain = new Intent(activity_signin.this,MainActivity.class);
                startActivity(backMain);
                break;
            case R.id.forgotPassSI:
                Log.d(TAG,"forgotPassSI: Started");
                Intent forgotPass = new Intent(activity_signin.this,forgot_password.class);
                startActivity(forgotPass);
                break;
        }
    }

    private void userLoginSI(){
        String emailSI = editEmailSI.getText().toString();
        String passwordSI = editPasswordSI.getText().toString();

        if (emailSI.isEmpty()){
            editEmailSI.setError("Email is required!");
            editEmailSI.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailSI).matches()){
            editEmailSI.setError("Please provide valid email!");
            editEmailSI.requestFocus();
            return;
        }

        if(passwordSI.isEmpty()){
            editPasswordSI.setError("Password is required!");
            editPasswordSI.requestFocus();
            return;
        }

        if(passwordSI.length() < 6){
            editPasswordSI.setError("Passwords length should be 6 characters!");
            editPasswordSI.requestFocus();
            return;
        }

        ELearning2.signInWithEmailAndPassword(emailSI,passwordSI).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(activity_signin.this,activity_welcomefromsignin.class));
                }else{
                    Toast.makeText(activity_signin.this, "Failed to log in! Please check your email and/or password!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void signinView(){
        Log.d(TAG,"signinView: Started");
        editEmailSI = findViewById(R.id.editEmailSI);
        editPasswordSI = findViewById(R.id.editPasswordSI);
        forgotPassSI = findViewById(R.id.forgotPassSI);

        signinTextViewSI = findViewById(R.id.signinTextView);
        signinBG = findViewById(R.id.bg_signin);

        backtoMainSI = findViewById(R.id.mainBtnSI);
        backtoMainSI.setOnClickListener(this);

        loginBtnSI = findViewById(R.id.loginBtnSI);
        loginBtnSI.setOnClickListener(this);

        signupBtnSI = findViewById(R.id.signupBtnSI);
        signupBtnSI.setOnClickListener(this);


        childSignIn = findViewById(R.id.childSignin);

    }


}

