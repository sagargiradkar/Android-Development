package com.devdroid.ad_10_tool_bar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
Toolbar toolbar;
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

        toolbar = findViewById(R.id.toolbar);

        //step 1: set the toolbar as the action bar
        setSupportActionBar(toolbar);

        //step 2: enable the up button

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Toolbar");
        }
        toolbar.setSubtitle("Sub Title");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.opt_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       int itemId = item.getItemId();

       if(itemId == R.id.opt_new){
           Toast.makeText(this, "Create New File", Toast.LENGTH_SHORT).show();
       }else if(itemId == R.id.opt_open){
           Toast.makeText(this, "Open File", Toast.LENGTH_SHORT).show();
       }else if(itemId == R.id.opt_save){
           Toast.makeText(this, "Save File", Toast.LENGTH_SHORT).show();
       }else{
           //Back button
           Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show();
           super.onBackPressed();
       }

        return super.onOptionsItemSelected(item);
    }
}