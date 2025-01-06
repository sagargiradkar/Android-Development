package com.devdroid.ad_32_accelerometer_sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.txtValues);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if(sensorManager != null){
            Sensor acclSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if(acclSensor != null){
                sensorManager.registerListener(this, acclSensor, SensorManager.SENSOR_DELAY_NORMAL);

                //SensorManager.registerListener(this, acclSensor, SensorManager.SENSOR_DELAY_NORMAL);
                textView.setText("Accelerometer Sensor Found");
                Toast.makeText(this, "Accelerometer Sensor Found", Toast.LENGTH_LONG).show();
            }
        }else{
            textView.setText("No Sensors");
            Toast.makeText(this, "No Sensors", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            ((TextView)findViewById(R.id.txtValues)).setText("X: " + sensorEvent.values[0] + " Y: " + sensorEvent.values[1] + " Z: " + sensorEvent.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}