package com.devdroid.mad_26_taking_pictures;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageView imgCamera;
    Button btnCamera;
    private static final int REQUEST_CAMERA_CAPTURE_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        btnCamera = findViewById(R.id.btnCamera);
        imgCamera = findViewById(R.id.imgCamera);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(iCamera, REQUEST_CAMERA_CAPTURE_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) { // Check the resultCode instead of requestCode
            if (requestCode == REQUEST_CAMERA_CAPTURE_CODE) {
                // Retrieve the bitmap from the camera's result
                Bitmap img = (Bitmap) data.getExtras().get("data");
                imgCamera.setImageBitmap(img); // Set the image in the ImageView
            }
        }
    }

}