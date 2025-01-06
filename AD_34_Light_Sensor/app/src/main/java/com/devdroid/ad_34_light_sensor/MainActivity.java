package com.devdroid.ad_34_light_sensor;

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

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Get the light sensor
        if(sensorManager != null){
            Sensor lightSensior = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

            if(lightSensior != null)
                sensorManager.registerListener((SensorEventListener) this, lightSensior, SensorManager.SENSOR_DELAY_NORMAL);
            else
                Toast.makeText(this, "Light sensor not found", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Sensor not found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT){
            TextView txtView = findViewById(R.id.txtView);
            txtView.setText("Light: " + sensorEvent.values[0] + " lx");
            txtView.setTextSize(34);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}