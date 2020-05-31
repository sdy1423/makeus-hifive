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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.e("onCreate","onStart");
        mTlTabLayout=findViewById(R.id.main_tl_tabs);
        mVpViewPager=findViewById(R.id.main_vp_view_pager);
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(),3);
        mVpViewPager.setAdapter(mainViewPagerAdapter);

        mVpViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTlTabLayout));

//        mTlTabLayout.addTab(mTlTabLayout.newTab().setText("캘린더"));
//        mTlTabLayout.addTab(mTlTabLayout.newTab().setText("일정 피드"));
//        mTlTabLayout.addTab(mTlTabLayout.newTab().setText("프로필"));
        Objects.requireNonNull(mTlTabLayout.getTabAt(0)).setIcon(R.drawable.ic_today_24px);
        Objects.requireNonNull(mTlTabLayout.getTabAt(1)).setIcon(R.drawable.ic_calendar_view_day_24px);
        Objects.requireNonNull(mTlTabLayout.getTabAt(2)).setIcon(R.drawable.ic_person_outline_24px);

        mTlTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mVpViewPager));


//        mTlTabLayout.addTab(mTlTabLayout.newTab().setCustomView(createTabView("캘린더",1)));
//        mTlTabLayout.addTab(mTlTabLayout.newTab().setCustomView(createTabView("일정 피드",2)));
//        mTlTabLayout.addTab(mTlTabLayout.newTab().setCustomView(createTabView("프로필",3)));

        mTlTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                changeView(pos);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });







    }
//    private View createTabView(String tabName,int num) {
//        View tabView = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
////        TextView txt_name = (TextView) tabView.findViewById(R.id.custom_tab_tv);
//        txt_name.setText(tabName);
////        ImageView IvIcon=findViewById(R.id.custom_tab_iv);
//        if(num==1){
//            IvIcon.setImageResource(R.drawable.ic_today_24px_black);
//        }else if(num==2){
//            IvIcon.setImageResource(R.drawable.ic_calendar_view_day_24px);
//        }else if(num==3){
//            IvIcon.setImageResource(R.drawable.ic_person_outline_24px);
//        }
//        return tabView;
//    }
    public void changeView(int index){
        if(index==0){
            Objects.requireNonNull(mTlTabLayout.getTabAt(0)).setIcon(R.drawable.ic_today_24px);
            Objects.requireNonNull(mTlTabLayout.getTabAt(1)).setIcon(R.drawable.ic_calendar_view_day_24px);
            Objects.requireNonNull(mTlTabLayout.getTabAt(2)).setIcon(R.drawable.ic_person_outline_24px);
        }else if(index==1){
            Objects.requireNonNull(mTlTabLayout.getTabAt(0)).setIcon(R.drawable.ic_today_24px_black);
            Objects.requireNonNull(mTlTabLayout.getTabAt(1)).setIcon(R.drawable.ic_calendar_view_day_24px_red);
            Objects.requireNonNull(mTlTabLayout.getTabAt(2)).setIcon(R.drawable.ic_person_outline_24px);
        }else if(index==2){
            Objects.requireNonNull(mTlTabLayout.getTabAt(0)).setIcon(R.drawable.ic_today_24px_black);
            Objects.requireNonNull(mTlTabLayout.getTabAt(1)).setIcon(R.drawable.ic_calendar_view_day_24px);
            Objects.requireNonNull(mTlTabLayout.getTabAt(2)).setIcon(R.drawable.ic_person_outline_24px_red);
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
