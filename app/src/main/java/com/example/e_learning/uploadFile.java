package com.example.e_learning;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class uploadFile extends AppCompatActivity {

    Button selectFile, upload;
    TextView notification;
    FirebaseStorage storage;
    FirebaseDatabase database;

    Uri pdfUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);

        selectFile = findViewById(R.id.selectFile);
        upload = findViewById(R.id.upload);
        notification = findViewById(R.id.indicateText);

        storage = FirebaseStorage.getInstance(); //Store file
        database = FirebaseDatabase.getInstance(); //Store URi

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(uploadFile.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPdf();
                } else {
                    ActivityCompat.requestPermissions(uploadFile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pdfUri!=null){
                    uploadFile(pdfUri);
                }else{
                    Toast.makeText(uploadFile.this, "Select a file",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectPdf();
        }else{
            Toast.makeText(uploadFile.this, "please provide permission!", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPdf() {
        //Select a file from the users' device

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);

    }

    private void uploadFile(Uri pdfUri){
        StorageReference storageReference = storage.getReference();

        storageReference.child("Uploads")
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //check whether user has selected a file or not
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfUri=data.getData();//Return uri
        }else{
            Toast.makeText(uploadFile.this, "Please provide select a file", Toast.LENGTH_SHORT).show();
        }
    }
}