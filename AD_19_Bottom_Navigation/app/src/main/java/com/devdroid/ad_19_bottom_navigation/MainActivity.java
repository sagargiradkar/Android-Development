package com.devdroid.ad_19_bottom_navigation;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
BottomNavigationView bnView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bnView = findViewById(R.id.bnView);

        bnView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.nav_home){
                   loadFrag(new HomeFragment(),true);
                }else if(id == R.id.nav_Search){
                    loadFrag(new SearchFragment(),false);
                }else if(id == R.id.nav_Settings){
                    loadFrag(new SettingsFragment(),false);
                }else if(id == R.id.nav_Utilities){
                    loadFrag(new UtilityFragment(),false);
                }else {
                    loadFrag(new ContactUsFragment(),false);
                }
                return true;
            }
        });

        bnView.setSelectedItemId(R.id.nav_home);
    }

    public void loadFrag(Fragment fragment,boolean flag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

       //
//        Bundle bundle = new Bundle();
//        bundle.putString("Arg1","Raman");
//        bundle.putInt("Arg2",7);
//
//        fragment.setArguments(bundle);



        if(flag)
            ft.add(R.id.container, fragment);
        else
            ft.replace(R.id.container, fragment);
        ft.commit();
    }

}