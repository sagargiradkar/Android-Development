package com.devdroid.ad_40_fake_news;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;

public class ScreenshotService extends Service {

    public ScreenshotService() {}

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Monitor screenshots continuously
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // Show the latest screenshot
                    showLatestScreenshot();
                    try {
                        Thread.sleep(5000); // Sleep for 5 seconds before checking again
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return START_STICKY;
    }

    private void showLatestScreenshot() {
        ContentResolver resolver = getContentResolver();
        Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";

        Cursor cursor = resolver.query(contentUri, projection, null, null, sortOrder);

        if (cursor != null && cursor.moveToFirst()) {
            int dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            String imagePath = cursor.getString(dataColumn);
            cursor.close();

            if (imagePath != null && imagePath.contains("Screenshots")) {
                // Update the ImageView with the latest screenshot
                Intent updateIntent = new Intent("UPDATE_IMAGE_VIEW");
                updateIntent.putExtra("imagePath", imagePath);
                sendBroadcast(updateIntent);  // Send broadcast to MainActivity to update ImageView
            }
        }
    }

}
