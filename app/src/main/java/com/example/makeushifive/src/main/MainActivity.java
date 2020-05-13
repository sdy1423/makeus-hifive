package com.example.makeushifive.src.main;

import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseActivity;
import com.example.makeushifive.src.main.interfaces.MainActivityView;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MainActivity extends BaseActivity implements MainActivityView {

    int mUserNo;
    TabLayout mTlTabLayout;
    ViewPager mVpViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTlTabLayout=findViewById(R.id.main_tl_tabs);
        mVpViewPager=findViewById(R.id.main_vp_view_pager);
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(),3);
        mVpViewPager.setAdapter(mainViewPagerAdapter);



        mVpViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTlTabLayout));

        Objects.requireNonNull(mTlTabLayout.getTabAt(0)).setIcon(R.drawable.ic_today_24px);
        Objects.requireNonNull(mTlTabLayout.getTabAt(1)).setIcon(R.drawable.ic_calendar_view_day_24px);
        Objects.requireNonNull(mTlTabLayout.getTabAt(2)).setIcon(R.drawable.ic_person_outline_24px);

        mTlTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mVpViewPager));
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







        //인텐트 수신
        Intent intent = getIntent();
        mUserNo=intent.getExtras().getInt("userNo");
        Log.e("userno",""+mUserNo);

    }

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



}
