package com.devdroid.ad_27_media_player;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btnPause,btnPlay,btnStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnPause = findViewById(R.id.btnPause);
        btnPlay = findViewById(R.id.btnPlay);
        btnStop = findViewById(R.id.btnStop);

        MediaPlayer mp = new MediaPlayer();

        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

        String aPath = "android.resourse://"+getPackageName()+"/raw/android";
        String onlineAudioPath = "";
        Uri onlineURI = Uri.parse(onlineAudioPath);
        try {
            Uri audioURI = Uri.parse(aPath);
            mp.setDataSource(this,audioURI);
            mp.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.pause();

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mp.pause();
                mp.seekTo(0);

            }
        });


    }
}