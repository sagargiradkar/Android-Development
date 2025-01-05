package com.devdroid.ad_24_sqllite_db;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        MyDBHelper dbHelper = new MyDBHelper(this);

//        dbHelper.addContact("John Doe", "123-456-7890");
//        dbHelper.addContact("Jane Smith", "987-654-3210");

        ArrayList<ContactModel> contacts = dbHelper.getAllContacts();
        for (ContactModel contact : contacts) {
            Log.d("Contacts", "---------------------------------");
            Log.d("Contacts", contact.toString());
            Log.d("Contacts", contact.name);
            Log.d("Contacts", contact.phone_no);
            Log.d("Contacts", String.valueOf(contact.id));
            Log.d("Contacts", "---------------------------------");
        }

        ContactModel model = new ContactModel();
        model.id = 1;
        model.name = "John Doe";
        model.phone_no = "9923621676";
        dbHelper.updateContact(model);

        dbHelper.deleteContact(1);

        for (ContactModel contact : contacts) {
            Log.d("Contacts", "---------------------------------");
            Log.d("Contacts", contact.toString());
            Log.d("Contacts", contact.name);
            Log.d("Contacts", contact.phone_no);
            Log.d("Contacts", String.valueOf(contact.id));
            Log.d("Contacts", "---------------------------------");
        }
        dbHelper.getAllContacts();

        ArrayList<ContactModel> contacts1 = dbHelper.getAllContacts();
        for (ContactModel contact : contacts1) {
            Log.d("Contacts", "---------------------------------");
            Log.d("Contacts", contact.toString());
            Log.d("Contacts", contact.name);
            Log.d("Contacts", contact.phone_no);
            Log.d("Contacts", String.valueOf(contact.id));
            Log.d("Contacts", "---------------------------------");

        }

    }
}