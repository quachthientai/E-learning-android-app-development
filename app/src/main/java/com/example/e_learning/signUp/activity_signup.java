package com.example.e_learning.signUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_learning.R;
import com.example.e_learning.signIn.activity_signin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class activity_signup extends AppCompatActivity implements View.OnClickListener  {

    private static final String TAG = "\n" +
            "public class activity_signup extends AppCompatActivity implement SignUpActivity";

    private EditText editFirstNameSU, editLastNameSU, editEmailSU, editPassSU, editRePassSU;
    private CheckBox isTeacherBox, isStudentBox;
    private Button signupBtnSU;
    private String userType;
    private ImageView signupBG;
    private TextView signupTextViewSU, haveAccountTextViewSU, loginSU;
    private ConstraintLayout childSignUp;
    private TextView register, forgotPassword;
    private ProgressBar progressBarSU;

    //Declare firebase authentication to project
    private FirebaseAuth ELearning2Auth;
    private FirebaseFirestore Elearning2Store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initialize firebase authentication
        ELearning2Auth = FirebaseAuth.getInstance();

    }

    //Initialize the private variable in the sign up view
    private void signupView(){
        Log.d(TAG,"signupView: Started");
        editFirstNameSU = findViewById(R.id.editFirstName_SU);
        editLastNameSU = findViewById(R.id.editLastName_SU);
        editEmailSU = findViewById(R.id.editTextEmailAddress_SU);
        editPassSU = findViewById(R.id.editTextPassword_SU);
        editRePassSU = findViewById(R.id.editTextrePass_SU);
        isTeacherBox = findViewById(R.id.isTeacher);
        isStudentBox = findViewById(R.id.isStudent);
        signupBtnSU = findViewById(R.id.signupBtn_SU);
        signupBtnSU.setOnClickListener(this);
        signupBG = findViewById(R.id.bg_signup);
        signupTextViewSU = findViewById(R.id.signupTextView_SU);
        haveAccountTextViewSU = findViewById(R.id.haveAccount_SU);
        loginSU = findViewById(R.id.loginView_SU);
        progressBarSU = (ProgressBar) findViewById(R.id.progressBarSU);

        isStudentBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    isTeacherBox.setChecked(false);
                    userType = "isStudent";
                }
            }
        });

        isTeacherBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    isStudentBox.setChecked(false);
                    userType = "isTeacher";
                }
            }
        });
    }

    //method onClick of signup activity
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signupBtn_SU: //onClick the sign up button
                registerUserSU();
                break;
            case R.id.loginView_SU: //onClick log in if user has an account
                Intent login = new Intent(activity_signup.this, activity_signin.class);
                startActivity(login);
                break;
        }
    }

    //method registerUser to register user
    private void registerUserSU() {
        String firstName = editFirstNameSU.getText().toString();
        String lastName = editLastNameSU.getText().toString();
        String email = editEmailSU.getText().toString();
        String password;


        //Validate the string is empty or not, setError will show error icon
        if (firstName.isEmpty()){
            editFirstNameSU.setError("First name is required!");
            editFirstNameSU.requestFocus();
            return;
        }

        if (lastName.isEmpty()){
            editLastNameSU.setError("Last name is required!");
            editLastNameSU.requestFocus();
            return;
        }

        if (email.isEmpty()){
            editEmailSU.setError("Email is required!");
            editEmailSU.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmailSU.setError("Please provide valid email!");
            editEmailSU.requestFocus();
            return;
        }

        if(editPassSU.getText().toString().equals(editRePassSU.getText().toString())){
            password = editPassSU.getText().toString();

            if(password.isEmpty()){
                editPassSU.setError("Passwords is required!");
                editPassSU.requestFocus();
                return;
            }

            if(password.length() < 6){
                editPassSU.setError("Passwords length should be 6 characters!");
                editPassSU.requestFocus();
            }

        }else{
            editRePassSU.setError("Password do not match!");
            editRePassSU.requestFocus();
            return;
        }

        if (!(isStudentBox.isChecked() || isTeacherBox.isChecked())){
            isTeacherBox.setError("");
            isStudentBox.setError("");
            Toast.makeText(this, "Please select account type!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBarSU.setVisibility(View.VISIBLE);
        //create user and add data to firebase
        ELearning2Auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if the task is successful
                        if(task.isSuccessful()){
                            //create the obj of UserInfo class hold the firstname, lastname and email
                            UserInfo user = new UserInfo(firstName, lastName, email, userType);
                            //make a child of database tree (firebase)
                            FirebaseDatabase.getInstance().getReference("Users")
                                    //get database token
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    //then start to set value to user obj
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //if task successful
                                    if (task.isSuccessful()){
                                        progressBarSU.setVisibility(View.GONE);
                                        //show message "successfully"
                                        Toast.makeText(activity_signup.this, "User has been registered successfully!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        progressBarSU.setVisibility(View.GONE);
                                        //if not, show "failed"
                                        Toast.makeText(activity_signup.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(activity_signup.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}