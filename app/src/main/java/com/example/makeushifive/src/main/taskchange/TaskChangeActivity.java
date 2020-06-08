package com.example.makeushifive.src.main.taskchange;

import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseActivity;
import com.example.makeushifive.src.main.MainActivity;
import com.example.makeushifive.src.main.home.add.AddAlarmDialog;
import com.example.makeushifive.src.main.home.add.AddTimeDialog;
import com.example.makeushifive.src.main.taskchange.interfaces.TaskChangeActivityView;
import com.example.makeushifive.src.main.taskchange.models.TaskChangeResponse;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.example.makeushifive.src.ApplicationClass.DATE_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.DAY;
import static com.example.makeushifive.src.ApplicationClass.KOREAN_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.MONTH;
import static com.example.makeushifive.src.ApplicationClass.YEAR;

public class TaskChangeActivity extends BaseActivity implements TaskChangeActivityView {
    SHOWTASK showtasks;

    //TIME/////////////////////////////////
    ValueAnimator mTimeAni;
    ImageView mIvTimeRemove,mIvTimeAdd;
    ///////////////////////////////////////

    //LOCATION/////////////////////////////////
    ValueAnimator mlocationAni;
    ImageView mIvLocationRemove,mIvLocationAdd;
    ///////////////////////////////////////

    //REPEAT/////////////////////////////////
    ValueAnimator SelectDayWeekMonthAni,SelectMonToSunAni;
    ImageView mIvRepeatAdd,mIvRepeatRemove;
    ///////////////////////////////////////

    //ALARM/////////////////////////////////
    ValueAnimator mAlarmAni;
    ImageView mIvAlarmRemove,mIvAlarmAdd;
    ///////////////////////////////////////

    //TAG/////////////////////////////////
    EditText mEdtLocation,mEdtTag;
    ImageView mIvTagAdd,mIvTagRemove;
    ValueAnimator mTagAni;
    ///////////////////////////////////////
    TextView mTvRepeatEverdayBlur,mTvRepeatEverdayBlack,mTvRepeatEveryWeekBlack,mTvRepeatEveryWeekBlur,
            mTvMonBlack,mTvMonBlur,mTvTueBlack,mTvTueBlur,mTvWedBlack,mTvWedBlur,mTvThuBlack,mTvThuBlur,mTvFriBlack,mTvFriBlur,mTvSatBlack,mTvSatBlur,mTvSunBlack,mTvSunBlur,mTvStartDate,mTvEndDate
            ,mTvStartTime,mTvEndTime,mTvAlarmSelected,mTvAddCompleteRed;

    int speed = 500;
    //TODO 1번 컬러 디폴트로 설정, 타이틀만 추가해도 일정 추가 가능하도록 ㄱㄱ
    int color=1;
    EditText mEdtTitle;
    int location_height = 150,time_height=110,location_heightt=110,tag_height=110;
    LinearLayout mLlRepeatSelect,mLlRepeatSelectDay,mLlEndTime;
    ImageView mIvShowColor1,mIvShowColor2,mIvShowColor3,mIvShowColor4,mIvShowColor5,mIvShowColor6,mIvShowColor7,mIvShowColor8;


    //월,화,수,목,금,토,일 클릭 여부
    boolean DayFlag[]={false,false,false,false,false,false,false},LocationFlag=false,SelectDayWeekMonthFlag=false,EveryDayFlag=false,EveryWeekFlag=false,TimeFlag=false,AlarmFlag=false
            ,TagFlag=false;

    int PickedStartYear=-1,PickedStartMonth=-1,PickedStartDay=-1,PickedStartHour=-1,PickedStartMin=-1,
            PickedEndYear=-1,PickedEndMonth=-1,PickedEndDay=-1,PickedEndHour=-1,PickedEndMin=-1;

    FrameLayout mFlComplete;
    String title="";
    String location = "";
    String tag="";
    int taskNoFromIntent=0;

    //클릭해서 들어온 날짜
    String pickedDate="";
    Date pickedDay;

    Bundle mBundele;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_change);


        mBundele =savedInstanceState;
        ImageView mIvClose = findViewById(R.id.task_change_ex);
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FindViewById();
        SetVisibility();

//        Intent intent = getIntent();
//        pickedDate = Objects.requireNonNull(intent.getExtras()).getString("date");
//        taskNoFromIntent= Objects.requireNonNull(intent.getExtras()).getInt("taskNo");

        //TODO 기존 일정 정보 뿌려주기 위함
        TaskChangeService taskChangeService = new TaskChangeService(this);
        try {
            taskChangeService.getTaskInfo(taskNoFromIntent);
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        pickedDay = null;
//        try {
//            assert pickedDate != null;
//            pickedDay = DATE_FORMAT.parse(pickedDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        assert pickedDay != null;
//        PickedStartMonth = Integer.parseInt(MONTH.format(pickedDay));
//        PickedStartDay = Integer.parseInt(DAY.format(pickedDay));
//        PickedStartYear = Integer.parseInt(YEAR.format(pickedDay));
//
//        mTvStartDate.setText(KOREAN_FORMAT.format(pickedDay));
//        mTvEndDate.setText(KOREAN_FORMAT.format(pickedDay));


        mEdtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                title = mEdtTitle.getText().toString();

            }
        });


        mTvAddCompleteRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 일정 수정
            }
        });
    }

    private void SetVisibility() {
        //장소선택 +,-버튼
        mIvLocationRemove.setVisibility(View.INVISIBLE);
        mIvLocationAdd.setVisibility(View.VISIBLE);
        //일정반복 +,-버튼
        mIvRepeatAdd.setVisibility(View.VISIBLE);
        mIvRepeatRemove.setVisibility(View.INVISIBLE);

        //타이틀 옆부분 칼라
        mIvShowColor1.setVisibility(View.VISIBLE);
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
        mIvTimeAdd.setVisibility(View.VISIBLE);
        mIvTimeRemove.setVisibility(View.INVISIBLE);

        mIvAlarmAdd.setVisibility(View.VISIBLE);
        mIvAlarmRemove.setVisibility(View.INVISIBLE);

        mIvTagAdd.setVisibility(View.VISIBLE);
        mIvTagRemove.setVisibility(View.INVISIBLE);


    }
    private void FindViewById() {
        //장소선택
        mEdtLocation = findViewById(R.id.task_change_edt_location);
        mIvLocationAdd = findViewById(R.id.task_change_iv_location_add);
        mIvLocationRemove = findViewById(R.id.task_change_iv_location_remove);
        //일정 반복
        //선택
        mLlRepeatSelect = findViewById(R.id.task_change_ll_repeat_select);
        //요일선택
        mLlRepeatSelectDay=findViewById(R.id.task_change_ll_repeat_select_day);
        //매일
        mTvRepeatEverdayBlur=findViewById(R.id.task_change_tv_repeat_everyday_blur);
        mTvRepeatEverdayBlack=findViewById(R.id.task_change_tv_repeat_everyday_black);
        //매주
        mTvRepeatEveryWeekBlack=findViewById(R.id.task_change_tv_repeat_every_week_black);
        mTvRepeatEveryWeekBlur=findViewById(R.id.task_change_tv_repeat_every_week_blur);
        //매일 매주 매월 선택 오픈,클로즈
        mIvRepeatAdd=findViewById(R.id.task_change_iv_repeat_add);
        mIvRepeatRemove=findViewById(R.id.task_change_iv_repeat_remove);
        //요일 버튼
        mTvSunBlack=findViewById(R.id.task_change_tv_repeat_select_day_sun_black);
        mTvSunBlur=findViewById(R.id.task_change_tv_repeat_select_day_sun_blur);
        mTvMonBlack=findViewById(R.id.task_change_tv_repeat_select_day_mon_black);
        mTvMonBlur=findViewById(R.id.task_change_tv_repeat_select_day_mon_blur);
        mTvTueBlack=findViewById(R.id.task_change_tv_repeat_select_day_tue_black);
        mTvTueBlur=findViewById(R.id.task_change_tv_repeat_select_day_tue_blur);
        mTvWedBlack=findViewById(R.id.task_change_tv_repeat_select_day_wed_black);
        mTvWedBlur=findViewById(R.id.task_change_tv_repeat_select_day_wed_blur);
        mTvThuBlack=findViewById(R.id.task_change_tv_repeat_select_day_thu_black);
        mTvThuBlur=findViewById(R.id.task_change_tv_repeat_select_day_thu_blur);
        mTvFriBlack=findViewById(R.id.task_change_tv_repeat_select_day_fri_black);
        mTvFriBlur=findViewById(R.id.task_change_tv_repeat_select_day_fri_blur);
        mTvSatBlack=findViewById(R.id.task_change_tv_repeat_select_day_sat_black);
        mTvSatBlur=findViewById(R.id.task_change_tv_repeat_select_day_sat_blur);
        mIvShowColor1 = findViewById(R.id.task_change_iv_show_color1);
        mIvShowColor2 = findViewById(R.id.task_change_iv_show_color2);
        mIvShowColor3 = findViewById(R.id.task_change_iv_show_color3);
        mIvShowColor4 = findViewById(R.id.task_change_iv_show_color4);
        mIvShowColor5 = findViewById(R.id.task_change_iv_show_color5);
        mIvShowColor6 = findViewById(R.id.task_change_iv_show_color6);
        mIvShowColor7 = findViewById(R.id.task_change_iv_show_color7);
        mIvShowColor8 = findViewById(R.id.task_change_iv_show_color8);
        mTvStartDate =findViewById(R.id.task_change_tv_start_date);
        mTvStartTime =findViewById(R.id.task_change_tv_start_time);
        mTvEndDate = findViewById(R.id.task_change_tv_end_date);
        mTvEndTime =findViewById(R.id.task_change_tv_end_time);
        mIvTimeAdd=findViewById(R.id.task_change_iv_time_add);
        mIvTimeRemove=findViewById(R.id.task_change_iv_time_remove);
        mLlEndTime=findViewById(R.id.task_change_ll_end_time);
        mIvAlarmAdd=findViewById(R.id.task_change_iv_alarm_add);
        mIvAlarmRemove=findViewById(R.id.task_change_iv_alarm_remove);
        mTvAlarmSelected=findViewById(R.id.task_change_tv_alarm_what);
        mIvTagAdd=findViewById(R.id.task_change_iv_tag_add);
        mIvTagRemove=findViewById(R.id.task_change_iv_tag_remove);
        mEdtTag=findViewById(R.id.task_change_edt_tag);

        mTvAddCompleteRed=findViewById(R.id.task_change_tv_complete_red);
        mEdtTitle=findViewById(R.id.task_change_edt_title);
    }


    public void TaskChangeTagClick(View view) {
        if(!TagFlag){
            OpenTag();
            TagFlag=true;
        }else{
            CloseTag();
            TagFlag=false;
        }
    }
    public void OpenTag(){
        mTagAni=ValueAnimator.ofArgb(0,tag_height);
        mTagAni.setDuration(speed);
        mTagAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                mEdtTag.getLayoutParams().height=value;
                mEdtTag.requestLayout();
            }
        });
        mTagAni.start();

        mIvTagAdd.setVisibility(View.INVISIBLE);
        mIvTagRemove.setVisibility(View.VISIBLE);
    }
    public void CloseTag(){
        mTagAni=ValueAnimator.ofArgb(tag_height,0);
        mTagAni.setDuration(speed);
        mTagAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                mEdtTag.getLayoutParams().height=value;
                mEdtTag.requestLayout();
            }
        });
        mTagAni.start();
        mIvTagAdd.setVisibility(View.VISIBLE);
        mIvTagRemove.setVisibility(View.INVISIBLE);
    }




    public void TaskChangeAlarmClick(View view){
        if(!AlarmFlag){
            //DIALOG띄워
            final AddAlarmDialog addAlarmDialog = new AddAlarmDialog(this, new AddAlarmDialog.ICustomDialogEventListener() {
                @Override
                public void customDialogEvent(int valueYouWantToSendBackToTheActivity) {
                    mAlarmAni=ValueAnimator.ofArgb(0,location_height);
                    mAlarmAni.setDuration(speed);
                    mAlarmAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            Integer value = (Integer)animation.getAnimatedValue();
                            mTvAlarmSelected.getLayoutParams().height=value;
                            mTvAlarmSelected.requestLayout();
                        }
                    });
                    mAlarmAni.start();
                    mIvAlarmAdd.setVisibility(View.INVISIBLE);
                    mIvAlarmRemove.setVisibility(View.VISIBLE);
                    AlarmFlag=true;
                    Log.e("30분부터 1번: ",""+valueYouWantToSendBackToTheActivity);
                    ShowSelectedAlarm(valueYouWantToSendBackToTheActivity);
                }
            });
            Objects.requireNonNull(addAlarmDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            addAlarmDialog.show();

        }else{
            AlarmFlag=false;
            mAlarmAni=ValueAnimator.ofArgb(location_height,0);
            mAlarmAni.setDuration(speed);
            mAlarmAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Integer value = (Integer)animation.getAnimatedValue();
                    mTvAlarmSelected.getLayoutParams().height=value;
                    mTvAlarmSelected.requestLayout();
                }
            });
            mAlarmAni.start();
            mIvAlarmAdd.setVisibility(View.VISIBLE);
            mIvAlarmRemove.setVisibility(View.INVISIBLE);
        }
    }
    public void ShowSelectedAlarm(int num){
        switch (num){
            case 1:
                mTvAlarmSelected.setText("30분 전");
                break;
            case 2:
                mTvAlarmSelected.setText("1시간 전");
                break;
            case 3:
                mTvAlarmSelected.setText("2시간 전");
                break;
            case 4:
                mTvAlarmSelected.setText("하루 전");
                break;
            case 5:
                mTvAlarmSelected.setText("일주일 전");
                break;
        }
    }


    public void TaskChangeTimeClick(View view){
        //AddTimeDialog띄우기
        if(!TimeFlag){
            TimeFlag=true;
            AddTimeDialog addTimeDialog = new AddTimeDialog(this,startTimeListener,endTimeListener,pickedDay,startDateListener,endDateListener);
            Objects.requireNonNull(addTimeDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            addTimeDialog.show();
        }else{
            TimeFlag=false;
            ClosePickedTime();
        }
    }
    TimePickerDialog.OnTimeSetListener startTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            PickedStartHour = hourOfDay;
            PickedStartMin = minute;
            mTvStartTime.setText(MakeSetText(hourOfDay,minute));
        }
    };

    TimePickerDialog.OnTimeSetListener endTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            PickedEndHour=hourOfDay;
            PickedEndMin=minute;
            mTvEndTime.setText(MakeSetText(hourOfDay,minute));
            OpenPickedTime();
        }
    };
    DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            PickedStartYear=year;
            PickedStartMonth = month;
            PickedStartDay=dayOfMonth;
            Date StartDate = null;
            try {
                StartDate = MakeDateForm(PickedStartYear,PickedStartMonth,PickedStartDay);
                Log.e("startDate",""+StartDate);
                String date = KOREAN_FORMAT.format(StartDate);
                mTvStartDate.setText(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };
    DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            PickedEndYear=year;
            PickedEndMonth = month;
            PickedEndDay = dayOfMonth;

            Date EndDate = null;
            try {
                EndDate = MakeDateForm(PickedEndYear,PickedEndMonth,PickedEndDay);
                Log.e("EndDate",""+EndDate);
                String date = KOREAN_FORMAT.format(EndDate);
                mTvEndDate.setText(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };


    private String MakeSetText(int hourOfDay, int minute) {
        String text ="";
        if(hourOfDay<10){
            text+="0";
        }
        text+=String.valueOf(hourOfDay);
        text+=":";
        if(minute<10){
            text+="0";
        }
        text+=String.valueOf(minute);
        return text;
    }

    public void OpenPickedTime(){
        //TODO 1. 애니메이션으로 도착시간 보여주는 부분 내려오게 하기
        mTimeAni = ValueAnimator.ofArgb(0,time_height);
        mTimeAni.setDuration(speed);
        mTimeAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer)animation.getAnimatedValue();
                mLlEndTime.getLayoutParams().height=value;
                mLlEndTime.requestLayout();
            }
        });
        mTimeAni.start();
        //TODO 2. setText로 보여주기 (시작시간, 끝나는 시간)
        mIvTimeAdd.setVisibility(View.INVISIBLE);
        mIvTimeRemove.setVisibility(View.VISIBLE);

    }
    public void ClosePickedTime(){
        mTimeAni = ValueAnimator.ofArgb(time_height,0);
        mTimeAni.setDuration(speed);
        mTimeAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer)animation.getAnimatedValue();
                mLlEndTime.getLayoutParams().height=value;
                mLlEndTime.requestLayout();
            }
        });
        mTimeAni.start();
        mIvTimeAdd.setVisibility(View.VISIBLE);
        mIvTimeRemove.setVisibility(View.INVISIBLE);
    }



    public void TaskChangeLocationClick(View view) {
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
        mlocationAni = ValueAnimator.ofArgb(0,location_heightt);
        mlocationAni.setDuration(speed);
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
        mlocationAni = ValueAnimator.ofArgb(location_heightt,0);
        mlocationAni.setDuration(speed);
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


    public void TaskChangeDayClick(View view) {
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

                //매주 닫기
                if(EveryWeekFlag){
                    EveryWeekFlag=false;
                    CloseSelectMonToSun();
                    mTvRepeatEveryWeekBlack.setVisibility(View.INVISIBLE);
                    mTvRepeatEveryWeekBlur.setVisibility(View.VISIBLE);
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

                if(EveryDayFlag){
                    EveryDayFlag=false;
                    mTvRepeatEverdayBlack.setVisibility(View.INVISIBLE);
                    mTvRepeatEverdayBlur.setVisibility(View.VISIBLE);
                }



                break;
            case R.id.add_fl_repeat_select_day_sun:
                if(!DayFlag[0]){
                    mTvSunBlack.setVisibility(View.VISIBLE);
                    mTvSunBlack.setVisibility(View.INVISIBLE);
                    DayFlag[0]=true;
                    Log.e("DAY","FLAG "+DayFlag[0]);
                }else{
                    mTvSunBlack.setVisibility(View.INVISIBLE);
                    mTvSunBlack.setVisibility(View.VISIBLE);
                    DayFlag[0]=false;
                    Log.e("DAY","FLAG "+DayFlag[0]);
                }
                break;
            case R.id.add_fl_repeat_select_day_mon:
                if(!DayFlag[1]){
                    mTvMonBlack.setVisibility(View.VISIBLE);
                    mTvMonBlur.setVisibility(View.INVISIBLE);
                    DayFlag[1]=true;
                    Log.e("DAY","FLAG "+DayFlag[1]);
                }else{
                    mTvMonBlack.setVisibility(View.INVISIBLE);
                    mTvMonBlur.setVisibility(View.VISIBLE);
                    DayFlag[1]=false;
                    Log.e("DAY","FLAG "+DayFlag[1]);
                }
                break;
            case R.id.add_fl_repeat_select_day_tue:
                if(!DayFlag[2]){
                    mTvTueBlack.setVisibility(View.VISIBLE);
                    mTvTueBlur.setVisibility(View.INVISIBLE);
                    DayFlag[2]=true;
                    Log.e("DAY","FLAG "+DayFlag[2]);
                }else{
                    mTvTueBlack.setVisibility(View.INVISIBLE);
                    mTvTueBlur.setVisibility(View.VISIBLE);
                    DayFlag[2]=false;
                    Log.e("DAY","FLAG "+DayFlag[2]);
                }
                break;
            case R.id.add_fl_repeat_select_day_wed:
                if(!DayFlag[3]){
                    mTvWedBlack.setVisibility(View.VISIBLE);
                    mTvWedBlur.setVisibility(View.INVISIBLE);
                    DayFlag[3]=true;
                    Log.e("DAY","FLAG "+DayFlag[3]);
                }else{
                    mTvWedBlack.setVisibility(View.INVISIBLE);
                    mTvWedBlur.setVisibility(View.VISIBLE);
                    DayFlag[3]=false;
                    Log.e("DAY","FLAG "+DayFlag[3]);
                }
                break;
            case R.id.add_fl_repeat_select_day_thu:
                if(!DayFlag[4]){
                    mTvThuBlack.setVisibility(View.VISIBLE);
                    mTvThuBlur.setVisibility(View.INVISIBLE);
                    DayFlag[4]=true;
                    Log.e("DAY","FLAG "+DayFlag[4]);
                }else{
                    mTvThuBlack.setVisibility(View.INVISIBLE);
                    mTvThuBlur.setVisibility(View.VISIBLE);
                    DayFlag[4]=false;
                    Log.e("DAY","FLAG "+DayFlag[4]);
                }
                break;
            case R.id.add_fl_repeat_select_day_fri:
                if(!DayFlag[5]){
                    mTvFriBlack.setVisibility(View.VISIBLE);
                    mTvFriBlur.setVisibility(View.INVISIBLE);
                    DayFlag[5]=true;
                    Log.e("DAY","FLAG "+DayFlag[5]);
                }else {
                    mTvFriBlack.setVisibility(View.INVISIBLE);
                    mTvFriBlur.setVisibility(View.VISIBLE);
                    DayFlag[5] = false;
                    Log.e("DAY","FLAG "+DayFlag[5]);
                }
                break;
            case R.id.add_fl_repeat_select_day_sat:
                if(!DayFlag[6]){
                    mTvSatBlack.setVisibility(View.VISIBLE);
                    mTvSatBlur.setVisibility(View.INVISIBLE);
                    DayFlag[6]=true;
                    Log.e("DAY","FLAG "+DayFlag[6]);
                }else {
                    mTvSatBlack.setVisibility(View.INVISIBLE);
                    mTvSatBlur.setVisibility(View.VISIBLE);
                    DayFlag[6] = false;
                    Log.e("DAY","FLAG "+DayFlag[6]);
                    break;
                }
                break;
        }
    }

    //요일선택창 오픈
    private void OpenSelectMonToSun() {
        SelectMonToSunAni = ValueAnimator.ofArgb(0,location_height);
        SelectMonToSunAni.setDuration(speed);
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
        SelectMonToSunAni.setDuration(speed);
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
        SelectDayWeekMonthAni.setDuration(speed);
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
        SelectDayWeekMonthAni.setDuration(speed);
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
    public void TaskChangeColorClick(View view) {
        switch (view.getId()){
            case R.id.add_iv_color1:
                color=1;
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
                color=2;
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
                color=3;
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
                color=4;
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
                color=5;
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
                color=6;
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
                color=7;
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
                color=8;
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





    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void patchTaskChangeSuccess() {
        //TODO 일정반복 등록 성공
        Log.e("일정반복 등록 성공","일정반복 등록 성공");
        showCustomToast("일정이 등록되었습니다.");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        TaskChangeActivity.this.finish(); //로딩페이지를 액티비티 스택에서 제거거

    }

    @Override
    public void patchTaskChangeFail() {

    }

    @Override
    public void getTaskSuccess(TaskChangeResponse.Result result) {
        int getTaskNo = result.getTaskNo();
        String getTitle = result.getTitle();
        String getLocation = result.getLocation();
        String getTag = result.getTag();
        int getColor = result.getColor();
        ArrayList<repeatweek> repeatweeks = null;
        ArrayList<days> day = null;
        try {
            if(!result.getRepeatweek().isEmpty()){
                for(int i=0;i<result.getRepeatweek().size();i++){
                    repeatweek repeatweek = new repeatweek(result.getRepeatweek().get(i).getRepeatweek());
                    repeatweeks.add(repeatweek);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if(!result.getDays().isEmpty()){
                for(int i=0;i<result.getDays().size();i++){
                    int getdayno = result.getDays().get(i).getDayNo();
                    String getday = result.getDays().get(i).getDay();
                    String time= result.getDays().get(i).getTime();
                    days days = new days(getdayno,getday,time);
                    day.add(days);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        showtasks = new SHOWTASK(getTaskNo,getTitle,getLocation,getTag,getColor,repeatweeks,day);
        //TODO 뿌려줘라.
        ShowTaskChangeInitialize(showtasks);
    }

    private void ShowTaskChangeInitialize(SHOWTASK showtasks) {
    //TODO 일정변경에 진입했을 때 값을 넣어주는 역할
        //TODO 타이틀
        mEdtTitle.setText(showtasks.getTitle(),TextView.BufferType.EDITABLE);
        //TODO 시간
        try {
            if(!showtasks.getDays().isEmpty()){
                int max = showtasks.getDays().size()-1;
                String startDatee = showtasks.getDays().get(0).getDay();
                String endDatee = showtasks.getDays().get(max).getDay();
                Date StartDate = null,EndDate=null;
                StartDate = DATE_FORMAT.parse(startDatee);
                EndDate = DATE_FORMAT.parse(endDatee);
                assert StartDate != null;
                String ShowStartDate = KOREAN_FORMAT.format(StartDate);
                assert EndDate != null;
                String ShowEndDate = KOREAN_FORMAT.format(EndDate);
                mTvStartDate.setText(ShowStartDate);
                mTvEndDate.setText(ShowEndDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mEdtLocation.setText(showtasks.getLocation(),TextView.BufferType.EDITABLE);
        //TODO 마저 하라...

    }

    @Override
    public void getTaskFail() {

    }

    @Override
    public void postTaskRepeatSuccess() {

    }

    @Override
    public void postTaskRepeatFail() {

    }
    public Date MakeDateForm(int year,int month,int day) throws ParseException {
//        month+=1;
        String stringDate="";
        stringDate+=String.valueOf(year);
        stringDate+="-";
        stringDate+=String.valueOf(month);
        stringDate+="-";
        stringDate+=String.valueOf(day);
        Date date = DATE_FORMAT.parse(stringDate);
        return date;
    }

    private static List<String> getDates(String dateString1, String dateString2)
    {
        ArrayList<String> dates = new ArrayList<String>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            dates.add(DATE_FORMAT.format(cal1.getTime()));
            cal1.add(Calendar.DATE, 1);




        }
        Log.e("showdates",dates.toString());
        return dates;
    }

}
