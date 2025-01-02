package com.devdroid.ad_03_tictactoe_basic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent fromMain = getIntent();
        String title = fromMain.getStringExtra("title");
        String studentName = fromMain.getStringExtra("Student Name");
        String greetMessage = fromMain.getStringExtra("message");
        int rollNo = fromMain.getIntExtra("Roll No",0);

        TextView txtStudentInfo = findViewById(R.id.txtStudentInfo);
//        getSupportActionBar().setTitle(title);
        txtStudentInfo.setText(title + "\nMessage ::" + greetMessage +"\nStudent Name ::"+ studentName + "\nRoll No ::" + rollNo);




    }
}