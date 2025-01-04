package com.devdroid.ad_09_recycle_view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<ContactModel > arrContact = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerContact);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        for (int i = 0; i < 26; i++) {
            char name = (char) ('A' + i); // Generate characters from 'A' to 'Z'
            String phoneNumber = "123456" + String.format("%04d", i); // Generate unique numbers
            arrContact.add(new ContactModel(R.drawable.ic_launcher_background, String.valueOf(name), phoneNumber));
        }


        RecycerContactAdapter adapter = new RecycerContactAdapter(this,arrContact);
        recyclerView.setAdapter(adapter);




    }
}