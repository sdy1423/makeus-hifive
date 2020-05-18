package com.example.makeushifive.src.main.home.add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.example.makeushifive.R;
import com.example.makeushifive.src.main.home.AddScheduleDialog;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static com.example.makeushifive.src.ApplicationClass.KOREAN_FORMAT;

public class AddActivity extends AppCompatActivity {

    ValueAnimator mlocationAni,SelectDayWeekMonthAni,SelectMonToSunAni;
    EditText mEdtLocation;
    int location_height = 150;
    LinearLayout mLlRepeatSelect,mLlRepeatSelectDay;
    TextView mTvRepeatEverdayBlur,mTvRepeatEverdayBlack,mTvRepeatEverMonthBlur,mTvRepeatEverMonthBlack,mTvRepeatEveryWeekBlack,mTvRepeatEveryWeekBlur,
    mTvMonBlack,mTvMonBlur,mTvTueBlack,mTvTueBlur,mTvWedBlack,mTvWedBlur,mTvThuBlack,mTvThuBlur,mTvFriBlack,mTvFriBlur,mTvSatBlack,mTvSatBlur,mTvSunBlack,mTvSunBlur,mTvStartDate,mTvEndDate
            ,mTvStartTime,mTvEndTime;
    ImageView mIvShowColor1,mIvShowColor2,mIvShowColor3,mIvShowColor4,mIvShowColor5,mIvShowColor6,mIvShowColor7,
            mIvShowColor8,mIvLocationAdd,mIvLocationRemove,mIvRepeatAdd,mIvRepeatRemove,mIvTimeAdd,mIvTimeRemove;
    //월,화,수,목,금,토,일 클릭 여부
    boolean DayFlag[]={false,false,false,false,false,false,false},LocationFlag=false,SelectDayWeekMonthFlag=false,EveryDayFlag=false,EveryWeekFlag=false,EveryMonthFlag=false,TimeFlag=false;

    int getYear,getMonth,getDay;

    int PickedStartMonth,PickedStartDay,PickedStartHour,PickedStartMin,
            PickedEndMonth,PickedEndDay,PickedEndHour,PickedEndMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        FindViewById();
        SetVisibility();

        //TODO 인텐트로 day받으면 터지는거 해결ㄱㄱ
        Intent intent = getIntent();
        String year = intent.getExtras().getString("year");
        String month = intent.getExtras().getString("month");
        String day = intent.getExtras().getString("day");
        assert year != null;
        getYear=Integer.parseInt(year);
        assert month != null;
        getMonth=Integer.parseInt(month);
        assert day != null;
        getDay=Integer.parseInt(day);


        Log.e("받은것들: ",""+getYear+" "+getMonth+" "+getDay);

        //TODO mTvStartDate에 날짜 보여주기
        String ShowDate="";
        String ShowMonth = String.valueOf(getMonth);
        String ShowDay = String.valueOf(getDay);
        ShowDate+=ShowMonth;
        ShowDate+="월 ";
        ShowDate+=ShowDay;
        ShowDate+="일";
        mTvStartDate.setText(ShowDate);
    }

    private void SetVisibility() {
        //장소선택 +,-버튼
        mIvLocationRemove.setVisibility(View.INVISIBLE);
        mIvLocationAdd.setVisibility(View.VISIBLE);
        //일정반복 +,-버튼
        mIvRepeatAdd.setVisibility(View.VISIBLE);
        mIvRepeatRemove.setVisibility(View.INVISIBLE);

        //타이틀 옆부분 칼라
        mIvShowColor1.setVisibility(View.INVISIBLE);
        mIvShowColor2.setVisibility(View.INVISIBLE);
        mIvShowColor3.setVisibility(View.INVISIBLE);
        mIvShowColor4.setVisibility(View.INVISIBLE);
        mIvShowColor5.setVisibility(View.INVISIBLE);
        mIvShowColor6.setVisibility(View.INVISIBLE);
        mIvShowColor7.setVisibility(View.INVISIBLE);
        mIvShowColor8.setVisibility(View.INVISIBLE);

        //요일선택
        mTvMonBlack.setVisibility(View.INVISIBLE);
        mTvMonBlur.setVisibility(View.VISIBLE);
        mTvTueBlack.setVisibility(View.INVISIBLE);
        mTvTueBlur.setVisibility(View.VISIBLE);
        mTvWedBlack.setVisibility(View.INVISIBLE);
        mTvWedBlur.setVisibility(View.VISIBLE);
        mTvThuBlack.setVisibility(View.INVISIBLE);
        mTvThuBlur.setVisibility(View.VISIBLE);
        mTvFriBlack.setVisibility(View.INVISIBLE);
        mTvFriBlur.setVisibility(View.VISIBLE);
        mTvSatBlack.setVisibility(View.INVISIBLE);
        mTvSatBlur.setVisibility(View.VISIBLE);
        mTvSunBlack.setVisibility(View.INVISIBLE);
        mTvSunBlur.setVisibility(View.VISIBLE);

        mTvRepeatEverdayBlack.setVisibility(View.INVISIBLE);
        mTvRepeatEverdayBlur.setVisibility(View.VISIBLE);

        mTvRepeatEveryWeekBlack.setVisibility(View.INVISIBLE);
        mTvRepeatEveryWeekBlur.setVisibility(View.VISIBLE);

        mTvRepeatEverMonthBlack.setVisibility(View.INVISIBLE);
        mTvRepeatEverMonthBlur.setVisibility(View.VISIBLE);

        mIvTimeAdd.setVisibility(View.VISIBLE);
        mIvTimeRemove.setVisibility(View.INVISIBLE);


    }

    private void FindViewById() {
        //장소선택
        mEdtLocation = findViewById(R.id.add_edt_location);
        mIvLocationAdd = findViewById(R.id.add_iv_location_add);
        mIvLocationRemove = findViewById(R.id.add_iv_location_remove);
        //일정 반복
        //선택
        mLlRepeatSelect = findViewById(R.id.add_ll_repeat_select);
        //요일선택
        mLlRepeatSelectDay=findViewById(R.id.add_ll_repeat_select_day);
        //매일
        mTvRepeatEverdayBlur=findViewById(R.id.add_tv_repeat_everyday_blur);
        mTvRepeatEverdayBlack=findViewById(R.id.add_tv_repeat_everyday_black);
        //매월
        mTvRepeatEverMonthBlur=findViewById(R.id.add_tv_repeat_every_month_blur);
        mTvRepeatEverMonthBlack=findViewById(R.id.add_tv_repeat_every_month_black);
        //매주
        mTvRepeatEveryWeekBlack=findViewById(R.id.add_tv_repeat_every_week_black);
        mTvRepeatEveryWeekBlur=findViewById(R.id.add_tv_repeat_every_week_blur);

        //매일 매주 매월 선택 오픈,클로즈
        mIvRepeatAdd=findViewById(R.id.add_iv_repeat_add);
        mIvRepeatRemove=findViewById(R.id.add_iv_repeat_remove);

        //요일 버튼
        mTvMonBlack=findViewById(R.id.add_tv_repeat_select_day_mon_black);
        mTvMonBlur=findViewById(R.id.add_tv_repeat_select_day_mon_blur);

        mTvTueBlack=findViewById(R.id.add_tv_repeat_select_day_tue_black);
        mTvTueBlur=findViewById(R.id.add_tv_repeat_select_day_tue_blur);

        mTvWedBlack=findViewById(R.id.add_tv_repeat_select_day_wed_black);
        mTvWedBlur=findViewById(R.id.add_tv_repeat_select_day_wed_blur);

        mTvThuBlack=findViewById(R.id.add_tv_repeat_select_day_thu_black);
        mTvThuBlur=findViewById(R.id.add_tv_repeat_select_day_thu_blur);

        mTvFriBlack=findViewById(R.id.add_tv_repeat_select_day_fri_black);
        mTvFriBlur=findViewById(R.id.add_tv_repeat_select_day_fri_blur);

        mTvSatBlack=findViewById(R.id.add_tv_repeat_select_day_sat_black);
        mTvSatBlur=findViewById(R.id.add_tv_repeat_select_day_sat_blur);

        mTvSunBlack=findViewById(R.id.add_tv_repeat_select_day_sun_black);
        mTvSunBlur=findViewById(R.id.add_tv_repeat_select_day_sun_blur);


        int showColor[] = {R.id.add_iv_show_color1, R.id.add_iv_show_color2, R.id.add_iv_show_color3,
                R.id.add_iv_show_color4, R.id.add_iv_show_color5, R.id.add_iv_show_color6,
                R.id.add_iv_show_color7, R.id.add_iv_show_color8};
        mIvShowColor1=findViewById(showColor[0]);
        mIvShowColor2=findViewById(showColor[1]);
        mIvShowColor3=findViewById(showColor[2]);
        mIvShowColor4=findViewById(showColor[3]);
        mIvShowColor5=findViewById(showColor[4]);
        mIvShowColor6=findViewById(showColor[5]);
        mIvShowColor7=findViewById(showColor[6]);
        mIvShowColor8=findViewById(showColor[7]);

        mTvStartDate =findViewById(R.id.add_tv_start_date);
        mTvStartTime =findViewById(R.id.add_tv_start_time);
        mTvEndDate = findViewById(R.id.add_tv_end_date);
        mTvEndTime =findViewById(R.id.add_tv_end_time);

        mIvTimeAdd=findViewById(R.id.add_iv_time_add);
        mIvTimeRemove=findViewById(R.id.add_iv_time_remove);

    }
    public void TimeClick(View view){
        //AddTimeDialog띄우기
        AddTimeDialog addTimeDialog = new AddTimeDialog(this,startDateListener,startTimeListener,endDateListener,endTimeListener,getYear,getMonth,getDay);
        addTimeDialog.show();
    }


    DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Log.e("startdate",""+year+" "+month+" "+dayOfMonth);
            PickedStartMonth = month;
            PickedStartDay=dayOfMonth;


        }
    };
    TimePickerDialog.OnTimeSetListener startTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Log.e("starttime",""+hourOfDay+" "+minute);
            PickedStartHour = hourOfDay;
            PickedStartMin = minute;

        }
    };
    DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Log.e("enddate",""+year+" "+month+" "+dayOfMonth);
            PickedEndMonth=month;
            PickedEndDay=dayOfMonth;

        }
    };
    TimePickerDialog.OnTimeSetListener endTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Log.e("endtime",""+hourOfDay+" "+minute);
            PickedEndHour=hourOfDay;
            PickedEndMin=minute;
            ShowPickedTime();
        }
    };

    public void ShowPickedTime(){
        //TODO 1. 애니메이션으로 도착시간 보여주는 부분 내려오게 하기
        //TODO 2. setText로 보여주기 (시작시간, 끝나는 시간)

    }



    public void locationClick(View view) {
        switch(view.getId()){
            case R.id.add_fl_location:
                if(!LocationFlag){
                    openLocation();
                }else{
                    closeLocation();
                }
                break;
            default :
                break;
        }
    }
    //장소선택 오픈
    public void openLocation(){
        mlocationAni = ValueAnimator.ofArgb(0,location_height);
        mlocationAni.setDuration(1000);
        mlocationAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                mEdtLocation.getLayoutParams().height = value;
                mEdtLocation.requestLayout();
            }
        });
        mlocationAni.start();
        LocationFlag=true;
        mIvLocationRemove.setVisibility(View.VISIBLE);
        mIvLocationAdd.setVisibility(View.INVISIBLE);

    }
    //장소선택 클로즈
    public void closeLocation(){
        mlocationAni = ValueAnimator.ofArgb(location_height,0);
        mlocationAni.setDuration(1000);
        mlocationAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                mEdtLocation.getLayoutParams().height = value;
                mEdtLocation.requestLayout();
            }
        });
        mlocationAni.start();
        LocationFlag=false;
        mIvLocationRemove.setVisibility(View.INVISIBLE);
        mIvLocationAdd.setVisibility(View.VISIBLE);


    }


    //TODO 요일선택 만들기
    public void dayClick(View view) {
        switch (view.getId()){
            case R.id.add_fl_repeat_btn:
                //TODO 클릭하면 매일,매주,매월 창이 뜬다.
                if(!SelectDayWeekMonthFlag){
                    OpenSelectDayWeekMonth();
                }else{
                    CloseSelectDayWeekMonth();
                }
                break;
            case R.id.add_fl_repeat_everyday:
                //클릭하면 매일 일정반복
                if(!EveryDayFlag){
                    EveryDayFlag=true;
                    mTvRepeatEverdayBlack.setVisibility(View.VISIBLE);
                    mTvRepeatEverdayBlur.setVisibility(View.INVISIBLE);
                }else{
                    EveryDayFlag=false;
                    mTvRepeatEverdayBlack.setVisibility(View.INVISIBLE);
                    mTvRepeatEverdayBlur.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.add_fl_repeat_every_week:
                //클릭하면 매주 일정반복
                if(!EveryWeekFlag){
                    EveryWeekFlag=true;
                    OpenSelectMonToSun();
                    mTvRepeatEveryWeekBlack.setVisibility(View.VISIBLE);
                    mTvRepeatEveryWeekBlur.setVisibility(View.INVISIBLE);
                }else{
                    EveryWeekFlag=false;
                    CloseSelectMonToSun();
                    mTvRepeatEveryWeekBlack.setVisibility(View.INVISIBLE);
                    mTvRepeatEveryWeekBlur.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.add_fl_repeat_every_month:
                //매달 일정반복복
                if(!EveryMonthFlag){
                    EveryMonthFlag=true;
                    mTvRepeatEverMonthBlack.setVisibility(View.VISIBLE);
                    mTvRepeatEverMonthBlur.setVisibility(View.INVISIBLE);
                }else{
                    EveryMonthFlag=false;
                    mTvRepeatEverMonthBlack.setVisibility(View.INVISIBLE);
                    mTvRepeatEverMonthBlur.setVisibility(View.VISIBLE);
                }
               break;
            case R.id.add_fl_repeat_select_day_mon:
                if(!DayFlag[0]){
                    mTvMonBlack.setVisibility(View.VISIBLE);
                    mTvMonBlur.setVisibility(View.INVISIBLE);
                    DayFlag[0]=true;
                }else{
                    mTvMonBlack.setVisibility(View.INVISIBLE);
                    mTvMonBlur.setVisibility(View.VISIBLE);
                    DayFlag[0]=false;
                }
                break;
            case R.id.add_fl_repeat_select_day_tue:
                if(!DayFlag[1]){
                    mTvTueBlack.setVisibility(View.VISIBLE);
                    mTvTueBlur.setVisibility(View.INVISIBLE);
                    DayFlag[1]=true;
                }else{
                    mTvTueBlack.setVisibility(View.INVISIBLE);
                    mTvTueBlur.setVisibility(View.VISIBLE);
                    DayFlag[1]=false;
                }
                break;
            case R.id.add_fl_repeat_select_day_wed:
                if(!DayFlag[2]){
                    mTvWedBlack.setVisibility(View.VISIBLE);
                    mTvWedBlur.setVisibility(View.INVISIBLE);
                    DayFlag[2]=true;
                }else{
                    mTvWedBlack.setVisibility(View.INVISIBLE);
                    mTvWedBlur.setVisibility(View.VISIBLE);
                    DayFlag[2]=false;
                }
                break;
            case R.id.add_fl_repeat_select_day_thu:
                if(!DayFlag[3]){
                    mTvThuBlack.setVisibility(View.VISIBLE);
                    mTvThuBlur.setVisibility(View.INVISIBLE);
                    DayFlag[3]=true;
                }else{
                    mTvThuBlack.setVisibility(View.INVISIBLE);
                    mTvThuBlur.setVisibility(View.VISIBLE);
                    DayFlag[3]=false;
                }
                break;
            case R.id.add_fl_repeat_select_day_fri:
                if(!DayFlag[4]){
                    mTvFriBlack.setVisibility(View.VISIBLE);
                    mTvFriBlur.setVisibility(View.INVISIBLE);
                    DayFlag[4]=true;
                }else{
                    mTvFriBlack.setVisibility(View.INVISIBLE);
                    mTvFriBlur.setVisibility(View.VISIBLE);
                    DayFlag[4]=false;
                }
                break;
            case R.id.add_fl_repeat_select_day_sat:
                if(!DayFlag[5]){
                    mTvSatBlack.setVisibility(View.VISIBLE);
                    mTvSatBlur.setVisibility(View.INVISIBLE);
                    DayFlag[5]=true;
                }else {
                    mTvSatBlack.setVisibility(View.INVISIBLE);
                    mTvSatBlur.setVisibility(View.VISIBLE);
                    DayFlag[5] = false;
                    break;
                }
            case R.id.add_fl_repeat_select_day_sun:
                if(!DayFlag[6]){
                    mTvSunBlack.setVisibility(View.VISIBLE);
                    mTvSunBlur.setVisibility(View.INVISIBLE);
                    DayFlag[6]=true;
                }else {
                    mTvSunBlack.setVisibility(View.INVISIBLE);
                    mTvSunBlur.setVisibility(View.VISIBLE);
                    DayFlag[6] = false;
                    break;
                }
                break;
        }
    }

    //요일선택창 오픈
    private void OpenSelectMonToSun() {
        SelectMonToSunAni = ValueAnimator.ofArgb(0,location_height);
        SelectMonToSunAni.setDuration(1000);
        SelectMonToSunAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                mLlRepeatSelectDay.getLayoutParams().height = value;
                mLlRepeatSelectDay.requestLayout();

            }
        });
        SelectMonToSunAni.start();
    }


    //요일 선택 창 클로즈
    public void CloseSelectMonToSun(){
        SelectMonToSunAni = ValueAnimator.ofArgb(location_height,0);
        SelectMonToSunAni.setDuration(1000);
        SelectMonToSunAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                mLlRepeatSelectDay.getLayoutParams().height = value;
                mLlRepeatSelectDay.requestLayout();
            }
        });
        SelectMonToSunAni.start();

    }

    //매일 매주 매월 선택창 오픈
    public void OpenSelectDayWeekMonth(){
        mIvRepeatAdd.setVisibility(View.INVISIBLE);
        mIvRepeatRemove.setVisibility(View.VISIBLE);

        SelectDayWeekMonthFlag=true;
        SelectDayWeekMonthAni = ValueAnimator.ofArgb(0,location_height);
        SelectDayWeekMonthAni.setDuration(1000);
        SelectDayWeekMonthAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                mLlRepeatSelect.getLayoutParams().height = value;
                mLlRepeatSelect.requestLayout();
            }
        });
        SelectDayWeekMonthAni.start();

    }
    //매일 매주 매월 선택창 클로즈
    public void CloseSelectDayWeekMonth(){
        //요일선택창 열려있으면 같이(먼저) 닫아야 함

        if(EveryWeekFlag){
            CloseSelectMonToSun();
        }
        mIvRepeatAdd.setVisibility(View.VISIBLE);
        mIvRepeatRemove.setVisibility(View.INVISIBLE);
        SelectDayWeekMonthFlag=false;
        SelectDayWeekMonthAni = ValueAnimator.ofArgb(location_height,0);
        SelectDayWeekMonthAni.setDuration(1000);
        SelectDayWeekMonthAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                mLlRepeatSelect.getLayoutParams().height = value;
                mLlRepeatSelect.requestLayout();
            }
        });
        SelectDayWeekMonthAni.start();
    }

    //칼라 선택
    public void ColorClick(View view) {
        switch (view.getId()){
            case R.id.add_iv_color1:
                mIvShowColor1.setVisibility(View.VISIBLE);
                mIvShowColor2.setVisibility(View.INVISIBLE);
                mIvShowColor3.setVisibility(View.INVISIBLE);
                mIvShowColor4.setVisibility(View.INVISIBLE);
                mIvShowColor5.setVisibility(View.INVISIBLE);
                mIvShowColor6.setVisibility(View.INVISIBLE);
                mIvShowColor7.setVisibility(View.INVISIBLE);
                mIvShowColor8.setVisibility(View.INVISIBLE);
                break;
            case R.id.add_iv_color2:
                mIvShowColor1.setVisibility(View.INVISIBLE);
                mIvShowColor2.setVisibility(View.VISIBLE);
                mIvShowColor3.setVisibility(View.INVISIBLE);
                mIvShowColor4.setVisibility(View.INVISIBLE);
                mIvShowColor5.setVisibility(View.INVISIBLE);
                mIvShowColor6.setVisibility(View.INVISIBLE);
                mIvShowColor7.setVisibility(View.INVISIBLE);
                mIvShowColor8.setVisibility(View.INVISIBLE);
                break;
            case R.id.add_iv_color3:
                mIvShowColor1.setVisibility(View.INVISIBLE);
                mIvShowColor2.setVisibility(View.INVISIBLE);
                mIvShowColor3.setVisibility(View.VISIBLE);
                mIvShowColor4.setVisibility(View.INVISIBLE);
                mIvShowColor5.setVisibility(View.INVISIBLE);
                mIvShowColor6.setVisibility(View.INVISIBLE);
                mIvShowColor7.setVisibility(View.INVISIBLE);
                mIvShowColor8.setVisibility(View.INVISIBLE);
                break;
            case R.id.add_iv_color4:
                mIvShowColor1.setVisibility(View.INVISIBLE);
                mIvShowColor2.setVisibility(View.INVISIBLE);
                mIvShowColor3.setVisibility(View.INVISIBLE);
                mIvShowColor4.setVisibility(View.VISIBLE);
                mIvShowColor5.setVisibility(View.INVISIBLE);
                mIvShowColor6.setVisibility(View.INVISIBLE);
                mIvShowColor7.setVisibility(View.INVISIBLE);
                mIvShowColor8.setVisibility(View.INVISIBLE);
                break;
            case R.id.add_iv_color5:
                mIvShowColor1.setVisibility(View.INVISIBLE);
                mIvShowColor2.setVisibility(View.INVISIBLE);
                mIvShowColor3.setVisibility(View.INVISIBLE);
                mIvShowColor4.setVisibility(View.INVISIBLE);
                mIvShowColor5.setVisibility(View.VISIBLE);
                mIvShowColor6.setVisibility(View.INVISIBLE);
                mIvShowColor7.setVisibility(View.INVISIBLE);
                mIvShowColor8.setVisibility(View.INVISIBLE);
                break;
            case R.id.add_iv_color6:
                mIvShowColor1.setVisibility(View.INVISIBLE);
                mIvShowColor2.setVisibility(View.INVISIBLE);
                mIvShowColor3.setVisibility(View.INVISIBLE);
                mIvShowColor4.setVisibility(View.INVISIBLE);
                mIvShowColor5.setVisibility(View.INVISIBLE);
                mIvShowColor6.setVisibility(View.VISIBLE);
                mIvShowColor7.setVisibility(View.INVISIBLE);
                mIvShowColor8.setVisibility(View.INVISIBLE);
                break;
            case R.id.add_iv_color7:
                mIvShowColor1.setVisibility(View.INVISIBLE);
                mIvShowColor2.setVisibility(View.INVISIBLE);
                mIvShowColor3.setVisibility(View.INVISIBLE);
                mIvShowColor4.setVisibility(View.INVISIBLE);
                mIvShowColor5.setVisibility(View.INVISIBLE);
                mIvShowColor6.setVisibility(View.INVISIBLE);
                mIvShowColor7.setVisibility(View.VISIBLE);
                mIvShowColor8.setVisibility(View.INVISIBLE);
                break;
            case R.id.add_iv_color8:
                mIvShowColor1.setVisibility(View.INVISIBLE);
                mIvShowColor2.setVisibility(View.INVISIBLE);
                mIvShowColor3.setVisibility(View.INVISIBLE);
                mIvShowColor4.setVisibility(View.INVISIBLE);
                mIvShowColor5.setVisibility(View.INVISIBLE);
                mIvShowColor6.setVisibility(View.INVISIBLE);
                mIvShowColor7.setVisibility(View.INVISIBLE);
                mIvShowColor8.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}
