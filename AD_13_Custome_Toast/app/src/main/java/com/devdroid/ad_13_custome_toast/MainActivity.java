package com.devdroid.ad_13_custome_toast;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
Button btnToast;
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

        //Default Toast
       // Toast.makeText(getApplicationContext(),"This is my first Toast",Toast.LENGTH_LONG).show();
     btnToast.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             btnToast = findViewById(R.id.btnToast);
             //Custome Toast
             Toast toast = new Toast(getApplicationContext());
             View view = getLayoutInflater().inflate(R.layout.custom_toast_layout,(ViewGroup) findViewById(R.id.viewContainer));
             toast.setView(view);

             TextView txtMsg = view.findViewById(R.id.txtMsg);

             txtMsg.setText("Message sent Successfully !");
             toast.setDuration(Toast.LENGTH_LONG);
             toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
             toast.show();
         }
     });
    }
}