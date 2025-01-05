package com.devdroid.ad_17_fragment_in_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private static final String ROOT_FRAGMENT_TAG = "root_fragment";
    Button btnFragA, btnFragB, btnFragC;
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFragA = findViewById(R.id.btnFragA);
        btnFragB = findViewById(R.id.btnFragB);
        btnFragC = findViewById(R.id.btnFragC);
        container = findViewById(R.id.container);

        // Load default fragment (BFragment) as root
        loadFrag(new BFragment(), true);

        btnFragA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFrag(new AFragment(), false);
            }
        });

        btnFragB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFrag(new BFragment(), false);
            }
        });

        btnFragC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFrag(new CFragment(), false);
            }
        });
    }

    /**
     * Method to load a fragment into the container
     *
     * @param fragment Fragment to load
     * @param isRoot   If true, the fragment is treated as the root
     */
    public void loadFrag(Fragment fragment, boolean isRoot) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        if (isRoot) {
            // Set the fragment as the root
            fragmentManager.popBackStack(ROOT_FRAGMENT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.replace(R.id.container, fragment);
            ft.addToBackStack(ROOT_FRAGMENT_TAG);
        } else {
            // Replace the current fragment
            ft.replace(R.id.container, fragment);
            ft.addToBackStack(null);
        }

        ft.commit();
    }
}
