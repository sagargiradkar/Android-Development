package com.devdroid.ad_19_bottom_navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {
    private static final String ARG1 = "argument1";
    private static final String ARG2 = "argument2";
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance(String value1, int value2){
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG1,value1);
        bundle.putInt(ARG2,value2);

        homeFragment.setArguments(bundle);
        return new HomeFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(getArguments() != null){
            String name = getArguments().getString(ARG1);
            int rollNo = getArguments().getInt(ARG2);
            Log.d("Value from Act","Name is :: "+name+" RollNo is :: "+rollNo);

        }
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        return v;
    }
}