package com.example.e_learning.signIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_learning.MainActivity;
import com.example.e_learning.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class activity_studentDashboard extends AppCompatActivity implements View.OnClickListener {

    private Button logoutBtn, editprofileBtn;
    private CardView personaldataCard, gradeCard, assignmentCard, attendanceCard, coursesCard, settingCard;
    private TextView userName;
    private FirebaseUser user;
    private DatabaseReference reference;

    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        studentdbView();

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        uid = user.getUid();

        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInfo UserInfo = snapshot.getValue(UserInfo.class);
                String fn = UserInfo.firstnameInfo;
                String ln = UserInfo.lastnameInfo;
                userName.setText("Welcome, " + fn + " " + ln + "!");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logOutBtn:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(activity_studentDashboard.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "User logout successfully!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void studentdbView(){
        //Dashboard button
        logoutBtn = findViewById(R.id.logOutBtn);
        logoutBtn.setOnClickListener(this);
        editprofileBtn = findViewById(R.id.editProfileBtn);
        editprofileBtn.setOnClickListener(this);

        //Dashboard card view
        personaldataCard =  findViewById(R.id.personalDataCard);
        personaldataCard.setOnClickListener(this);
        gradeCard = findViewById(R.id.gradeCard);
        gradeCard.setOnClickListener(this);
        assignmentCard = findViewById(R.id.assignmentCard);
        assignmentCard.setOnClickListener(this);
        attendanceCard = findViewById(R.id.attendanceCard);
        attendanceCard.setOnClickListener(this);
        coursesCard = findViewById(R.id.courseCard);
        coursesCard.setOnClickListener(this);
        settingCard = findViewById(R.id.settingCard);
        settingCard.setOnClickListener(this);

        //Text view
        userName = findViewById(R.id.welcome_TextView);
    }


}