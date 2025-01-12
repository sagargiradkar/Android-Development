package com.devdroid.ad_39_screenshot;

import android.app.Service;
import android.content.Intent;
import android.os.FileObserver;
import android.os.IBinder;
import android.util.Log;

import java.io.File;

public class ScreenshotService extends Service {

    private ScreenshotDetector screenshotDetector;

    public ScreenshotService() {}

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Initialize ScreenshotDetector and start watching for screenshots
        screenshotDetector = new ScreenshotDetector();
        screenshotDetector.startWatching();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (screenshotDetector != null) {
            screenshotDetector.stopWatching();  // Stop watching when service is destroyed
        }
    }

    // ScreenshotDetector class to monitor for new screenshots
    private class ScreenshotDetector {

        private FileObserver fileObserver;

        public void startWatching() {
            String screenshotsPath = "/storage/emulated/0/Pictures/Screenshots/"; // Common path for screenshots
            File screenshotsDir = new File(screenshotsPath);

            if (!screenshotsDir.exists()) {
                Log.e("ScreenshotDetector", "Screenshot directory does not exist.");
                return;
            }

            fileObserver = new FileObserver(screenshotsPath, FileObserver.CREATE) {
                @Override
                public void onEvent(int event, String path) {
                    if (path != null) {
                        Log.d("ScreenshotDetector", "Screenshot taken: " + path);
                        // You can add logic here to handle the screenshot event,
                        // such as sending the file path to an Activity or BroadcastReceiver
                        updateImageView(path);
                    }
                }
            };
            fileObserver.startWatching();
        }

        public void stopWatching() {
            if (fileObserver != null) {
                fileObserver.stopWatching();
            }
        }

        private void updateImageView(String imagePath) {
            // Update the ImageView with the latest screenshot
            Intent updateIntent = new Intent("UPDATE_IMAGE_VIEW");
            updateIntent.putExtra("imagePath", imagePath);
            sendBroadcast(updateIntent);  // Send broadcast to MainActivity to update ImageView
        }
    }
}
