package com.devdroid.screenshotapp;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;

import java.io.File;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btnShowScreenshot, btnTakeScreenshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        btnShowScreenshot = findViewById(R.id.btnShowScreenshot);
        btnTakeScreenshot = findViewById(R.id.btnTakeScreenshot);

        // Show the latest screenshot from the gallery
        btnShowScreenshot.setOnClickListener(view -> showLatestScreenshot());

        // Capture the screenshot of the current activity and display it
        btnTakeScreenshot.setOnClickListener(view -> captureScreenshot());

        // Register the receiver to listen for image update broadcast
        registerReceiver(imageUpdateReceiver, new IntentFilter("UPDATE_IMAGE_VIEW"));

        // Start the screenshot service
        startService(new Intent(this, ScreenshotService.class));
    }

    private BroadcastReceiver imageUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String imagePath = intent.getStringExtra("imagePath");
            if (imagePath != null) {
                // Update the ImageView with the latest screenshot
                imageView.setImageURI(Uri.fromFile(new File(imagePath)));
            }
        }
    };

    private void showLatestScreenshot() {
        // Ensure we have permissions before accessing gallery
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                // If permission is not granted, request it
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
                return; // Exit if permission is not granted
            }
        }

        // Log and setup query
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
        Log.d("MainActivity", "No screenshots found in the gallery. projection: " + Arrays.toString(projection));
        Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Log.d("MainActivity", "No screenshots found in the gallery. contentUri: " + contentUri);
        ContentResolver resolver = getContentResolver();
        Log.d("MainActivity", "No screenshots found in the gallery. resolver: " + resolver);
        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC"; // Sort by date added, most recent first
        Log.d("MainActivity", "No screenshots found in the gallery. sortOrder: " + sortOrder);

        // Query media storage for images
        Cursor cursor = resolver.query(contentUri, projection, null, null, sortOrder);
        Log.d("MainActivity", "No screenshots found in the gallery. cursor: " + cursor);

        if (cursor != null && cursor.moveToFirst()) {
            // Get the path of the most recent screenshot
            int dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            String imagePath = cursor.getString(dataColumn);
            cursor.close();

            Log.d("MainActivity", "Latest Screenshot Path: " + imagePath); // Debugging log

            if (imagePath != null && imagePath.contains("Screenshots")) {
                // Update the ImageView if the screenshot path is valid
                imageView.setImageURI(Uri.fromFile(new File(imagePath)));
            } else {
                Log.d("MainActivity", "No screenshots found in the gallery.");
            }
        } else {
            Log.d("MainActivity", "No screenshots found in the gallery.");
        }
    }


    private void captureScreenshot() {
        try {
            View rootView = getWindow().getDecorView().getRootView();
            Bitmap bitmap = Bitmap.createBitmap(rootView.getWidth(), rootView.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            rootView.draw(canvas);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(imageUpdateReceiver); // Unregister receiver when activity is destroyed
    }
}
