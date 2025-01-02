package com.devdroid.ad_07_list_examples;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ListView listView;
Spinner spinner;
AutoCompleteTextView autoCompleteTextView;
ArrayList<String> arrayNames = new ArrayList<>();
ArrayList<String> arrIds = new ArrayList<>();
ArrayList<String> arrLanguage = new ArrayList<>();
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

        listView = findViewById(R.id.listView);
        spinner = findViewById(R.id.spinner);
        autoCompleteTextView = findViewById(R.id.actxtView);


        arrayNames.add("Sagar");
        arrayNames.add("Paavan");
        arrayNames.add("Raju");
        arrayNames.add("Pathil");
        arrayNames.add("Parth");
        arrayNames.add("Pavar");
        arrayNames.add("Ashish");
        arrayNames.add("Ram");
        arrayNames.add("Sham");
        arrayNames.add("Goa");
        arrayNames.add("Gita");
        arrayNames.add("Sagar");
        arrayNames.add("Paavan");
        arrayNames.add("Raju");
        arrayNames.add("Pathil");
        arrayNames.add("Parth");
        arrayNames.add("Pavar");
        arrayNames.add("Ashish");
        arrayNames.add("Ram");
        arrayNames.add("Sham");
        arrayNames.add("Goa");
        arrayNames.add("Gita");
        arrayNames.add("Sagar");
        arrayNames.add("Paavan");
        arrayNames.add("Raju");
        arrayNames.add("Pathil");
        arrayNames.add("Parth");
        arrayNames.add("Pavar");
        arrayNames.add("Ashish");
        arrayNames.add("Ram");
        arrayNames.add("Sham");
        arrayNames.add("Goa");
        arrayNames.add("Gita");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    Toast.makeText(MainActivity.this,"Clicked First item",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Spinner

        arrIds.add("Adhar Card");
        arrIds.add("PAN card");
        arrIds.add("Leaving Certificate");
        arrIds.add("Identy card");
        arrIds.add("Driving Licence");
        arrIds.add("10th Marksheet");
        arrIds.add("12th Marksheet");
        arrIds.add("Ration Card");

        ArrayAdapter<String> spinnerAdaptor = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,arrIds);
        spinner.setAdapter(spinnerAdaptor);

        //AutoComplete Text View
        arrLanguage.add("C");
        arrLanguage.add("C++");
        arrLanguage.add("PHP");
        arrLanguage.add("Java");
        ArrayAdapter<String> actvAddapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrLanguage);
        autoCompleteTextView.setAdapter(actvAddapter);
        autoCompleteTextView.setThreshold(1);

     }
}