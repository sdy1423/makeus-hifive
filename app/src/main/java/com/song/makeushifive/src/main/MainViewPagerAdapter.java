package com.song.makeushifive.src.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.song.makeushifive.src.main.feed.FeedFragment;
import com.song.makeushifive.src.main.home.HomeFragment;
import com.song.makeushifive.src.main.setting.SettingFragment;

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
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                FeedFragment feedFragment = new FeedFragment();
                return feedFragment;
            case 2:
                SettingFragment settingFragment = new SettingFragment();
                return settingFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }



}







