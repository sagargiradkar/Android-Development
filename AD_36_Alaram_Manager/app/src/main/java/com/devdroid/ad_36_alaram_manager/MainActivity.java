package com.devdroid.ad_36_alaram_manager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTime = findViewById(R.id.editTime);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        findViewById(R.id.btnSet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int time = Integer.parseInt(editTime.getText().toString());

                    Intent intent = new Intent(MainActivity.this, MyReceiver.class);
                    intent.putExtra("time", time);
                    intent.setAction("com.devdroid.ad_36_alaram_manager");

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            MainActivity.this,
                            0,
                            intent,
                            PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
                    );

                    // Cancel any existing alarms with the same PendingIntent
                    alarmManager.cancel(pendingIntent);

                    // Set the new alarm
                    alarmManager.set(
                            AlarmManager.RTC_WAKEUP,
                            System.currentTimeMillis() + (time * 1000L),
                            pendingIntent
                    );
                } catch (NumberFormatException e) {
                    editTime.setError("Invalid time entered");
                }
            }
        });
    }
}
