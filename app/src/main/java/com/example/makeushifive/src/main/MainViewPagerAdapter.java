package com.example.makeushifive.src.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.makeushifive.src.main.feed.FeedFragment;
import com.example.makeushifive.src.main.home.HomeFragment;
import com.example.makeushifive.src.main.setting.SettingFragment;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    private int pageCount;

    public MainViewPagerAdapter(@NonNull FragmentManager fm,int pageCount) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.pageCount=pageCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new FeedFragment();
            case 2:
                return new SettingFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }



}







