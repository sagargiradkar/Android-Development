package com.devdroid.ad_25_room_library;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
EditText edtTitle,edtAmount;
Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        edtTitle = findViewById(R.id.edtTitle);
        edtAmount = findViewById(R.id.edtAmount);
        btnAdd = findViewById(R.id.btnAdd);

        DatabaseHelper databaseHelper = DatabaseHelper.getDb(this);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                String amount = edtAmount.getText().toString();

                databaseHelper.expenseDAO().addTx(
                        new Expense(title,amount)
                );

                ArrayList<Expense> arrExpnse = (ArrayList<Expense>) databaseHelper.expenseDAO().getAllExpenses();

                for(int i=0; i<arrExpnse.size(); i++){
                    Log.d("Data ::","Title"+arrExpnse.get(i)+"Amount :: "+arrExpnse.get(i).getAmount());

                }
            }
        });
        databaseHelper.expenseDAO().addTx(
                new Expense("100","Food")
        );

    }
}