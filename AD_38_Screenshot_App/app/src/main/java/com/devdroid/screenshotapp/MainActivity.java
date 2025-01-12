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

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btnShowScreenshot, btnTakeScreenshot;
    private Button btnUploadToFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "onCreate: Activity created");

        imageView = findViewById(R.id.imageView);
        btnShowScreenshot = findViewById(R.id.btnShowScreenshot);
        btnUploadToFirebase = findViewById(R.id.btnUploadToFirebase);

        // Show the latest screenshot from the gallery
        btnShowScreenshot.setOnClickListener(view -> {
            Log.d("MainActivity", "onCreate: Show screenshot button clicked");
            showLatestScreenshot();
        });


        // Upload the latest screenshot to Firebase
        btnUploadToFirebase.setOnClickListener(view -> {
            Log.d("MainActivity", "onCreate: Upload to Cloudinary button clicked");
            uploadScreenshotToCloudinary();
        });


        // Register the receiver to listen for image update broadcast
        registerReceiver(imageUpdateReceiver, new IntentFilter("UPDATE_IMAGE_VIEW"));
        Log.d("MainActivity", "onCreate: Receiver registered");

        // Start the screenshot service
        startService(new Intent(this, ScreenshotService.class));
        Log.d("MainActivity", "onCreate: Screenshot service started");
    }

    private void uploadScreenshotToCloudinary() {
        if (imageView.getDrawable() == null) {
            Log.d("MainActivity", "uploadScreenshotToCloudinary: No image to upload.");
            return;
        }

        // Path to the latest screenshot
        String screenshotPath = getLatestScreenshotPath();
        if (screenshotPath == null) {
            Log.d("MainActivity", "uploadScreenshotToCloudinary: No valid screenshot path found.");
            return;
        }

        File screenshotFile = new File(screenshotPath);
        if (!screenshotFile.exists()) {
            Log.d("MainActivity", "uploadScreenshotToCloudinary: Screenshot file does not exist.");
            return;
        }

        // Initialize Cloudinary
        Map config = new HashMap();
        config.put("cloud_name", "your_cloud_name"); // Replace with your Cloud Name
        config.put("api_key", "your_api_key"); // Replace with your API Key
        config.put("api_secret", "your_api_secret"); // Replace with your API Secret
        Cloudinary cloudinary = new Cloudinary(config);

        // Upload file
        new Thread(() -> {
            try {
                Map uploadResult = cloudinary.uploader().upload(screenshotFile, ObjectUtils.emptyMap());
                Log.d("MainActivity", "uploadScreenshotToCloudinary: Upload successful: " + uploadResult);
            } catch (Exception e) {
                Log.d("MainActivity", "uploadScreenshotToCloudinary: Upload failed: " + e.getMessage());
            }
        }).start();
    }


    private String getLatestScreenshotPath() {
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
        Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";

        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, sortOrder);
        Log.d("MainActivity", "getLatestScreenshotPath: Query executed");

        if (cursor != null && cursor.moveToFirst()) {
            int dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            String imagePath = cursor.getString(dataColumn);
            cursor.close();

            Log.d("MainActivity", "getLatestScreenshotPath: Latest Screenshot Path: " + imagePath); // Debugging log

            if (imagePath != null && imagePath.contains("Screenshots")) {
                return imagePath;
            }
        }
        return null;
    }

    private BroadcastReceiver imageUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String imagePath = intent.getStringExtra("imagePath");
            Log.d("MainActivity", "imageUpdateReceiver: Image path received: " + imagePath);
            if (imagePath != null) {
                // Update the ImageView with the latest screenshot
                imageView.setImageURI(Uri.fromFile(new File(imagePath)));
                Log.d("MainActivity", "imageUpdateReceiver: ImageView updated with new image.");
            }
        }
    };

    private void showLatestScreenshot() {
        // Ensure we have permissions before accessing gallery
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                // If permission is not granted, request it
                Log.d("MainActivity", "showLatestScreenshot: Permission not granted, requesting...");
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
                return; // Exit if permission is not granted
            }
        }

        // Log and setup query
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
        Log.d("MainActivity", "showLatestScreenshot: Projection: " + Arrays.toString(projection));
        Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Log.d("MainActivity", "showLatestScreenshot: contentUri: " + contentUri);
        ContentResolver resolver = getContentResolver();
        Log.d("MainActivity", "showLatestScreenshot: resolver: " + resolver);
        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC"; // Sort by date added, most recent first
        Log.d("MainActivity", "showLatestScreenshot: sortOrder: " + sortOrder);

        // Query media storage for images
        Cursor cursor = resolver.query(contentUri, projection, null, null, sortOrder);
        Log.d("MainActivity", "showLatestScreenshot: cursor: " + cursor);

        if (cursor != null && cursor.moveToFirst()) {
            // Get the path of the most recent screenshot
            int dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            String imagePath = cursor.getString(dataColumn);
            cursor.close();

            Log.d("MainActivity", "showLatestScreenshot: Latest Screenshot Path: " + imagePath); // Debugging log

            if (imagePath != null && imagePath.contains("Screenshots")) {
                // Update the ImageView if the screenshot path is valid
                imageView.setImageURI(Uri.fromFile(new File(imagePath)));
                Log.d("MainActivity", "showLatestScreenshot: ImageView updated with latest screenshot.");
            } else {
                Log.d("MainActivity", "showLatestScreenshot: No screenshots found in the gallery.");
            }
        } else {
            Log.d("MainActivity", "showLatestScreenshot: No screenshots found in the gallery.");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(imageUpdateReceiver); // Unregister receiver when activity is destroyed
        Log.d("MainActivity", "onDestroy: Receiver unregistered");
    }
}
