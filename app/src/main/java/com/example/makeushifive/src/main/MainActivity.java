package com.example.makeushifive.src.main;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseActivity;
import com.example.makeushifive.src.main.interfaces.MainActivityView;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MainActivity extends BaseActivity implements MainActivityView {

    TabLayout mTlTabLayout;
    ViewPager mVpViewPager;

    View tabView1,tabView2,tabView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabView1 = LayoutInflater.from(this).inflate(R.layout.custom_tab_calendar, null);
        tabView2 = LayoutInflater.from(this).inflate(R.layout.custom_tab_feed, null);
        tabView3 = LayoutInflater.from(this).inflate(R.layout.custom_tab_profile, null);

        mTlTabLayout=findViewById(R.id.main_tl_tabs);
        mVpViewPager=findViewById(R.id.main_vp_view_pager);

        Log.e("onCreate","onStart");
        mTlTabLayout.addTab(mTlTabLayout.newTab().setCustomView(changeSelectedTabView(0)));
        mTlTabLayout.addTab(mTlTabLayout.newTab().setCustomView(changeUnSelectedTabView(1)));
        mTlTabLayout.addTab(mTlTabLayout.newTab().setCustomView(changeUnSelectedTabView(2)));

//        mTlTabLayout.addTab(mTlTabLayout.newTab().setIcon(R.drawable.calendar_red_tab2));
//        mTlTabLayout.addTab(mTlTabLayout.newTab().setIcon(R.drawable.feed_tab2_black));
//        mTlTabLayout.addTab(mTlTabLayout.newTab().setIcon(R.drawable.profile_tab2_black));
        mTlTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(),mTlTabLayout.getTabCount());
        mVpViewPager.setAdapter(mainViewPagerAdapter);
        mVpViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTlTabLayout));

        mTlTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mVpViewPager));

        mTlTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                changeView(pos);
                mVpViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private View changeSelectedTabView(int index) {
        if(index==0){
            tabView1.findViewById(R.id.custom_tab_iv).setBackgroundResource(R.drawable.calendar_red_tab2);
            return tabView1;
        }else if(index==1){
            tabView2.findViewById(R.id.custom_tab_iv).setBackgroundResource(R.drawable.feed_tab2_red);
            return tabView2;
        }else if(index==2){
            tabView3.findViewById(R.id.custom_tab_iv).setBackgroundResource(R.drawable.profile_tab2_red);
            return tabView3;
        }
        return tabView1;
    }
    private View changeUnSelectedTabView(int index) {
//        View tabView = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        if(index==0){
            tabView1.findViewById(R.id.custom_tab_iv).setBackgroundResource(R.drawable.calendar_tab2_black);
            return tabView1;
        }else if(index==1){
            tabView2.findViewById(R.id.custom_tab_iv).setBackgroundResource(R.drawable.feed_tab2_black);
            return tabView2;
        }else if(index==2){
            tabView3.findViewById(R.id.custom_tab_iv).setBackgroundResource(R.drawable.profile_tab2_black);
            return tabView3;
        }
        return tabView1;
    }

    public void changeView(int index){
        if(index==0){
            Objects.requireNonNull(mTlTabLayout.getTabAt(0)).setCustomView(changeSelectedTabView(0));
            Objects.requireNonNull(mTlTabLayout.getTabAt(1)).setCustomView(changeUnSelectedTabView(1));
            Objects.requireNonNull(mTlTabLayout.getTabAt(2)).setCustomView(changeUnSelectedTabView(2));
        }else if(index==1){
            Objects.requireNonNull(mTlTabLayout.getTabAt(0)).setCustomView(changeUnSelectedTabView(0));
            Objects.requireNonNull(mTlTabLayout.getTabAt(1)).setCustomView(changeSelectedTabView(1));
            Objects.requireNonNull(mTlTabLayout.getTabAt(2)).setCustomView(changeUnSelectedTabView(2));
        }else if(index==2){
            Objects.requireNonNull(mTlTabLayout.getTabAt(0)).setCustomView(changeUnSelectedTabView(0));
            Objects.requireNonNull(mTlTabLayout.getTabAt(1)).setCustomView(changeUnSelectedTabView(1));
            Objects.requireNonNull(mTlTabLayout.getTabAt(2)).setCustomView(changeSelectedTabView(2));
        }
    }

    @Override
    protected void onResume() {
        Log.e("onResume","onStart");

        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause","onStart");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy","onStart");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("onDestroy","onStart");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("onStop","onStart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart","onStart");
    }
}
