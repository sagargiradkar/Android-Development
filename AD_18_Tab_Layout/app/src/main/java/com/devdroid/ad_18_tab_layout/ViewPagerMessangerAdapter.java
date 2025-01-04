package com.devdroid.ad_18_tab_layout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerMessangerAdapter extends FragmentPagerAdapter {

    public ViewPagerMessangerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new ChatFragment();//position 0
        }else if(position == 1){
            return new StatusFragment();//position 1
        }else{
            return new CallFragment(); //position 2
        }
    }

    @Override
    public int getCount() {
        return 3; //no of tabs
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "Chats";
        }else if(position == 1){
            return "Status";
        }else {
            return "Calls";
        }
    }
}
