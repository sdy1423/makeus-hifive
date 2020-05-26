package com.example.makeushifive.src.main.chatting;

import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseActivity;
import com.example.makeushifive.src.main.chatting.interfaces.ChattingActivityView;
import com.example.makeushifive.src.main.chatting.models.ChattingResponse;
import com.example.makeushifive.src.main.setting.change.ChangeService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.example.makeushifive.src.ApplicationClass.Chatting;
import static com.example.makeushifive.src.ApplicationClass.DATE_FORMAT;

public class ChattingActivity extends BaseActivity implements ChattingActivityView {

    DrawerLayout drawerLayout;
    int taskNo=0,color=0;
    String day,location,time,title;
    TextView mTvLocation,mTvDay,mTvTime,mTvTitle;
    ImageView[] imageView=new ImageView[8];
    ImageView mIvColor1,mIvColor2,mIvColor3,mIvColor4,mIvColor5,mIvColor6,mIvColor7,mIvColor8;
    int Colors[] = {R.id.chatting_iv_one,R.id.chatting_iv_two,R.id.chatting_iv_three,R.id.chatting_iv_four,R.id.chatting_iv_five,R.id.chatting_iv_six,R.id.chatting_iv_seven,R.id.chatting_iv_eight};

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

        Intent intent = getIntent();
        taskNo = Objects.requireNonNull(intent.getExtras()).getInt("taskNo");
        color = intent.getExtras().getInt("color");
        ChattingService chattingService = new ChattingService(this);
        chattingService.getDetailSchedule(taskNo);

        mTvLocation= findViewById(R.id.chatting_tv_location);
        mTvDay=findViewById(R.id.chatting_tv_day);
        mTvTime=findViewById(R.id.chatting_tv_time);
        mTvTitle=findViewById(R.id.chatting_tv_title);

        imageView= new ImageView[]{mIvColor1, mIvColor2, mIvColor3, mIvColor4, mIvColor5, mIvColor6, mIvColor7, mIvColor8};
        for(int i=0;i<8;i++){
            imageView[i] = findViewById(Colors[i]);
            if(i==color-1){
                imageView[i].setVisibility(View.VISIBLE);
            }else{
                imageView[i].setVisibility(View.INVISIBLE);
            }
        }





    }


    @Override
    public void getScheduleDetailSuccess(ArrayList<ChattingResponse.Result> result) throws ParseException {

        location = result.get(0).getLocation();
        time = result.get(0).getTime();
        title = result.get(0).getTitle();

        day = result.get(0).getDay();
        Date dateformat;
        dateformat = DATE_FORMAT.parse(day); //string to date
        assert dateformat != null;
        String today = Chatting.format(dateformat); //date to string

        mTvLocation.setText(location);
        mTvDay.setText(today);
        mTvTime.setText(time);
        mTvTitle.setText(title);





    }

    @Override
    public void getScheduleDetailFail() {

    }
}
