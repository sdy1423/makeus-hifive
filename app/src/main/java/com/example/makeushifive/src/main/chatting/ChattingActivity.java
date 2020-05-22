package com.example.makeushifive.src.main.chatting;

import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseActivity;
import com.example.makeushifive.src.main.chatting.interfaces.ChattingActivityView;
import com.example.makeushifive.src.main.chatting.models.ChattingResponse;

import java.util.ArrayList;

public class ChattingActivity extends BaseActivity implements ChattingActivityView {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        drawerLayout=findViewById(R.id.chatting_drawer);
        if(drawerLayout.isDrawerOpen(Gravity.RIGHT)){
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        }else{
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }



    }

    @Override
    public void getScheduleDetailSuccess(ArrayList<ChattingResponse.Result> result) {

    }

    @Override
    public void getScheduleDetailFail() {

    }
}
