package com.example.e_learning.signIn;

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

import com.example.e_learning.R;
import com.example.e_learning.signUp.activity_signup;
import com.example.e_learning.forgotPassword.forgot_password;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_signin extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";

    private EditText editEmailSI, editPasswordSI;
    private ImageView signinBG;
    private TextView forgotPassSI, signinTextViewSI;
    private Button  loginBtnSI, signupBtnSI;
    private ConstraintLayout childSignIn;
    private FirebaseAuth ELearning2;
    private ProgressBar progressBarSI;
    public String usertype = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sign in content view
        setContentView(R.layout.activity_signin);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                Intent signUp = new Intent(activity_signin.this, activity_signup.class);
                startActivity(signUp);
                break;
            case R.id.forgotPassSI:
                Log.d(TAG,"forgotPassSI: Started");
                Intent forgotPass = new Intent(activity_signin.this, forgot_password.class);
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

        progressBarSI.setVisibility(View.VISIBLE);
        ELearning2.signInWithEmailAndPassword(emailSI,passwordSI).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    progressBarSI.setVisibility(View.GONE);
                    String uid = task.getResult().getUser().getUid();
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    firebaseDatabase.getReference().child("Users").child(uid).child("userType").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            usertype = snapshot.getValue(String.class);
                            if(usertype.equals("isTeacher")){
                                Intent intent = new Intent(activity_signin.this,activity_teacherDashboard.class);
                                startActivity(intent);
                            }
                            if(usertype.equals("isStudent")){
                                Intent intent = new Intent(activity_signin.this,activity_studentDashboard.class);
                                startActivity(intent);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(activity_signin.this,"Fail",Toast.LENGTH_LONG).show();
                        }
                    });


                }else{
                    progressBarSI.setVisibility(View.GONE);
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

        loginBtnSI = findViewById(R.id.loginBtnSI);
        loginBtnSI.setOnClickListener(this);

        signupBtnSI = findViewById(R.id.signupBtnSI);
        signupBtnSI.setOnClickListener(this);

        childSignIn = findViewById(R.id.childSignin);

        progressBarSI = (ProgressBar) findViewById(R.id.progressBarSI);
    }
}

