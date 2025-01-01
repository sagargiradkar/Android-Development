package com.devdroid.ad_01_bmi_app;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etHeight = findViewById(R.id.etHeight);
        EditText etWeight = findViewById(R.id.etWeight);
        Button btnCalculate = findViewById(R.id.btnCalculate);
        TextView tvResult = findViewById(R.id.tvResult);

        btnCalculate.setOnClickListener(view -> {
            // Get height and weight values
            String heightStr = etHeight.getText().toString().trim();
            String weightStr = etWeight.getText().toString().trim();

            // Validate inputs
            if (TextUtils.isEmpty(heightStr)) {
                etHeight.setError("Height is required");
                return;
            }
            if (TextUtils.isEmpty(weightStr)) {
                etWeight.setError("Weight is required");
                return;
            }

            try {
                double height = Double.parseDouble(heightStr) / 100; // Convert cm to meters
                double weight = Double.parseDouble(weightStr);

                if (height <= 0) {
                    etHeight.setError("Enter a valid height");
                    return;
                }
                if (weight <= 0) {
                    etWeight.setError("Enter a valid weight");
                    return;
                }

                // Calculate BMI
                double bmi = weight / (height * height);

                // Display BMI category
                String bmiCategory;
                if (bmi < 18.5) {
                    bmiCategory = "Underweight";
                } else if (bmi < 24.9) {
                    bmiCategory = "Normal weight";
                } else if (bmi < 29.9) {
                    bmiCategory = "Overweight";
                } else {
                    bmiCategory = "Obesity";
                }

                String result = String.format("BMI: %.2f\nCategory: %s", bmi, bmiCategory);
                tvResult.setText(result);
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Invalid input. Please enter numbers only.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
