package com.devdroid.ad_33_proximity_sensors;

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

        TextView textView = findViewById(R.id.txtView);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if(sensorManager != null){
            Sensor proxySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

            if(proxySensor != null){
                sensorManager.registerListener(this,proxySensor,SensorManager.SENSOR_DELAY_NORMAL);
                textView.setText("Proximity Sensor Available");
            }else{
                textView.setText("Proximity Sensor Not Available");
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY){
            TextView textView = findViewById(R.id.txtView);
            textView.setText(String.valueOf(sensorEvent.values[0]));
            if(sensorEvent.values[0] == 0){
                Toast toast = Toast.makeText(this,"Close",Toast.LENGTH_SHORT);
                textView.setText("Close");
            }else{
                Toast toast = Toast.makeText(this,"Far",Toast.LENGTH_SHORT);
                textView.setText("Far");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}