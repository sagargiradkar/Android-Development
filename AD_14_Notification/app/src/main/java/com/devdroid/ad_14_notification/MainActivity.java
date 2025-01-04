package com.devdroid.ad_14_notification;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "My_Channel";
    private static final int NOTIFICATION_ID = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Lifecycle", "onCreate called");

        // Enable Edge-to-Edge content
        EdgeToEdge.enable(this);
        Log.d("EdgeToEdge", "EdgeToEdge enabled");

        setContentView(R.layout.activity_main);
        Log.d("UI", "Layout set to activity_main");

        // Adjust insets for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            Log.d("WindowInsets", "Insets received: " + systemBars);

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            Log.d("UI", "Padding applied to the view");
            return insets;
        });

        // Check and request notification permissions (for Android 13 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.d("Permissions", "Requesting notification permissions");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1001);
            }
        }

        // Load drawable resource
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.user, null);
        if (drawable == null) {
            Log.e("Drawable", "Failed to load drawable resource");
            return;
        }
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Log.d("Bitmap", "Bitmap created: " + bitmap);

        // Initialize NotificationManager
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (nm == null) {
            Log.e("NotificationManager", "Failed to initialize NotificationManager");
            return;
        }

        // Create Notification
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create NotificationChannel
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "New Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Description of the notification channel");
            nm.createNotificationChannel(channel);
            Log.d("NotificationChannel", "Channel created: " + CHANNEL_ID);

            // Build Notification
            notification = new Notification.Builder(this, CHANNEL_ID)
                    .setLargeIcon(bitmap)
                    .setSmallIcon(R.drawable.user)
                    .setContentTitle("Notification")
                    .setSubText("Notification From Sagar")
                    .setContentText("This is a notification")
                    .build();
            Log.d("Notification", "Notification created for API >= 26");
        } else {
            // Build Notification for API < 26
            notification = new Notification.Builder(this)
                    .setLargeIcon(bitmap)
                    .setSmallIcon(R.drawable.user)
                    .setContentTitle("Notification")
                    .setSubText("Notification From Sagar")
                    .setContentText("This is a notification")
                    .build();
            Log.d("Notification", "Notification created for API < 26");
        }

        // Send the Notification
        nm.notify(NOTIFICATION_ID, notification);
        Log.d("Notification", "Notification sent successfully");
    }
}
