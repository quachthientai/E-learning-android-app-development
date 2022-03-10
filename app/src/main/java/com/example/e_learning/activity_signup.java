package com.example.e_learning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Patterns;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class activity_signup extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignUpActivity";

    private EditText editFirstNameSU, editLastNameSU, editEmailSU, editPassSU, editRePassSU;
    private Button signupBtnSU;
    private ImageView signupBG;
    private TextView signupTextViewSU, haveAccountTextViewSU, loginSU;
    private ConstraintLayout childSignUp;

    //Declare firebase authentication to project
    private FirebaseAuth ELearning2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupView();

        //Initialize firebase authentication
        ELearning2 = FirebaseAuth.getInstance();
    }

    //Initialize the private variable in the sign up view
    private void signupView(){
        Log.d(TAG,"signupView: Started");
        editFirstNameSU = findViewById(R.id.editFirstName_SU);
        editLastNameSU = findViewById(R.id.editLastName_SU);
        editEmailSU = findViewById(R.id.editTextEmailAddress_SU);
        editPassSU = findViewById(R.id.editTextPassword_SU);
        editRePassSU = findViewById(R.id.editTextrePass_SU);

        signupBtnSU = findViewById(R.id.signupBtn_SU);
        signupBtnSU.setOnClickListener(this);

        signupBG = findViewById(R.id.bg_signup);

        signupTextViewSU = findViewById(R.id.signupTextView_SU);
        haveAccountTextViewSU = findViewById(R.id.haveAccount_SU);
        loginSU = findViewById(R.id.loginView_SU);
    }

    //method onClick of signup activity
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //onClick sign up button case
            case R.id.signupBtn_SU:
                registerUserSU();
                break;
        }
    }

    private void registerUserSU() {
        String firstName = editFirstNameSU.getText().toString();
        String lastName = editLastNameSU.getText().toString();
        String email = editEmailSU.getText().toString();
        String password = editPassSU.getText().toString();
        String rePassword = editRePassSU.getText().toString();

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

        if(password.isEmpty()){
            editPassSU.setError("Passwords is required!");
            editPassSU.requestFocus();
            return;
        }

        if(password.length() < 6){
            editPassSU.setError("Passwords length should be 6 characters!");
            editPassSU.requestFocus();
        }

        //create user and add data to firebase
        ELearning2.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if the task is successful
                        if(task.isSuccessful()){
                            //create the obj of UserInfo class hold the firstname, lastname and email
                            UserInfo user = new UserInfo(firstName, lastName, email);

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
                                        //show message "successfully"
                                        Toast.makeText(activity_signup.this, "User has been registered successfully!", Toast.LENGTH_SHORT).show();
                                    }else{
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