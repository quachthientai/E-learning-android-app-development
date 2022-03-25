package com.example.e_learning.signIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.e_learning.R;

public class student extends AppCompatActivity {

    RecyclerView recyclerView;
    String s1[], s2[];
    int images[] = {R.drawable.classes, R.drawable.assignments, R.drawable.due_date, R.drawable.events};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        recyclerView = findViewById(R.id.recyclerView);
        s1 = getResources().getStringArray(R.array.student_view);
        s2 = getResources().getStringArray(R.array.description);

        Student_rview_adapter myAdapter = new Student_rview_adapter(this, s1, s2, images);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


}