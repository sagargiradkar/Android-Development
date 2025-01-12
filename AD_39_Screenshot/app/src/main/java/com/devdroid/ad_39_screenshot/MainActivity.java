package com.devdroid.ad_39_screenshot;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.widget.TextView;


import com.devdroid.ad_39_screenshot.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btnShowScreenshot, btnTakeScreenshot;
    private Button btnUploadToFirebase;
    private TextView textViewResponse,textViewSummary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check for notification permissions (Android 13 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 102);
            }
        }
        initCloudinary();
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "onCreate: Activity created");

        imageView = findViewById(R.id.imageView);
        btnShowScreenshot = findViewById(R.id.btnShowScreenshot);
        btnUploadToFirebase = findViewById(R.id.btnUploadToFirebase);
        textViewResponse = findViewById(R.id.textViewResponse); // TextView to show API response
        textViewSummary = findViewById(R.id.textViewSummary);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 102) { // Match the notification permission request code
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("MainActivity", "Notification permission granted.");
            } else {
                Log.d("MainActivity", "Notification permission denied.");
            }
        }
    }

    private void uploadScreenshotToCloudinary() {
        Log.d("MainActivity", "uploadScreenshotToCloudinary: Uploading screenshot to Cloudinary");

        String screenshotPath = getLatestScreenshotPath();

        if (screenshotPath != null) {
            Log.d("MainActivity", "Screenshot path: " + screenshotPath);

            MediaManager.get().upload(screenshotPath)
                    .option("resource_type", "image")
                    .callback(new UploadCallback() {
                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            String uploadedUrl = resultData.get("url").toString();
                            Log.d("MainActivity", "Uploaded Image URL: " + uploadedUrl);

                            // Send the URL to the API
                            sendUrlToApi(uploadedUrl);
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {
                            Log.e("MainActivity", "Upload error: " + error.getDescription());
                        }

                        @Override
                        public void onStart(String requestId) {}
                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {}
                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {}
                    })
                    .dispatch();
        } else {
            Log.d("MainActivity", "No screenshot found to upload.");
        }
    }

    private void sendUrlToApi(String image_url) {
        String apiUrl = "https://api.runtimetheory.com/flask_app/checkNews"; // Replace with your API endpoint
        Log.d("MainActivity", "sendUrlToApi: API endpoint: " + apiUrl);
        Log.d("MainActivity", "sendUrlToApi: Uploaded URL: " + image_url);

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("image_url", image_url); // Add Cloudinary URL as JSON parameter
            Log.d("MainActivity", "sendUrlToApi: JSON body created: " + jsonBody.toString());
        } catch (JSONException e) {
            Log.e("MainActivity", "sendUrlToApi: JSON Error: " + e.toString());
            textViewResponse.setText("JSON Error: " + e.toString());
            return; // Exit if JSON creation fails
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, apiUrl, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("MainActivity", "sendUrlToApi: API Response: " + response.toString());
                        try {
                            // Get the response as a string and clean up the markdown code block
                            String jsonResponseStr = response.getString("response");
                            jsonResponseStr = jsonResponseStr.replace("```json\n", "").replace("```", ""); // Clean up markdown

                            // Parse the cleaned-up string as JSON
                            JSONObject jsonResponse = new JSONObject(jsonResponseStr);

                            // Extract values from the response
                            boolean booleanValue = jsonResponse.getBoolean("boolean_value");
                            String summary = jsonResponse.getString("summary");

                            // Set the appropriate message on the TextView
                            if (booleanValue) {
                                textViewResponse.setText("News is Fake");
                                sendNotification("Fake News", summary);
                            } else {
                                textViewResponse.setText("News is Real");
                                sendNotification("Real News", summary);
                            }

                            // Show the summary in the TextView
                            textViewSummary.setText(summary);

                        } catch (JSONException e) {
                            Log.e("MainActivity", "Error parsing API response: " + e.toString());
                            textViewResponse.setText("Error parsing response: " + e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MainActivity", "sendUrlToApi: API Error: " + error.toString());
                        if (error.networkResponse != null) {
                            Log.e("MainActivity", "sendUrlToApi: Error Status Code: " + error.networkResponse.statusCode);
                            Log.e("MainActivity", "sendUrlToApi: Error Response Data: " + new String(error.networkResponse.data));
                        }
                        textViewResponse.setText("API Error: " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json"); // Set Content-Type to JSON
                Log.d("MainActivity", "sendUrlToApi: Headers: " + headers.toString());
                return headers;
            }
        };

        // Add retry policy with longer timeout
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,  // 15 seconds timeout
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        // Add the request to the Volley queue
        queue.add(jsonRequest);
        Log.d("MainActivity", "sendUrlToApi: Request added to Volley queue");
    }


    private void sendNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "api_response_channel";

        // Create the notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "API Response Notifications", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Channel for API response notifications");
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background) // Replace with your app's icon
                .setContentTitle(title) // Title of the notification
                .setContentText(message) // Short content for collapsed view
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message)) // Detailed message for expanded view
                .setPriority(NotificationCompat.PRIORITY_HIGH) // High priority
                .setAutoCancel(true); // Automatically cancel when tapped

// Show the notification
        notificationManager.notify(1, builder.build());


    }



    private void initCloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "disl8qg3k");
        config.put("api_key", "243662671413975");
        config.put("api_secret", "Z2R6qXeCxbTz8Puwo_0id5tk6tc");
        MediaManager.init(this, config);
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
                uploadScreenshotToCloudinary();

                Log.d("MainActivity", "imageUpdateReceiver: ImageView updated with new image. ::uploadScreenshotToCloudinary");
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
