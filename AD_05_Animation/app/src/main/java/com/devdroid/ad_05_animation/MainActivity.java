package com.devdroid.ad_05_animation;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
TextView txtAnim;
Button btnTranslate, btnAlpha ,btnRotate, btnScale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtAnim = findViewById(R.id.txtAnim);

        Animation move = AnimationUtils.loadAnimation(this, R.anim.move);

        btnTranslate = findViewById(R.id.btnTranslate);
        btnAlpha= findViewById(R.id.btnAlpha);
        btnRotate = findViewById(R.id.btnRotate);
        btnScale = findViewById(R.id.btnScale);
        btnTranslate.setOnClickListener(view -> txtAnim.startAnimation(move));
        btnAlpha.setOnClickListener(view -> txtAnim.startAnimation(move));
        btnRotate.setOnClickListener(view -> txtAnim.startAnimation(move));
        btnScale.setOnClickListener(view -> txtAnim.startAnimation(move));

        btnAlpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation alpha = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha);
                txtAnim.startAnimation(alpha);

            }
        });

        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation rotate = AnimationUtils.loadAnimation(MainActivity.this,R.anim.rotation);
                txtAnim.startAnimation(rotate);
            }
        });

        btnScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation scale = AnimationUtils.loadAnimation(MainActivity.this,R.anim.scale);
                txtAnim.startAnimation(scale);
            }
        });
    }
}