package com.example.makeushifive.src.main.home.add;

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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseActivity;
import com.example.makeushifive.src.main.MainActivity;
import com.example.makeushifive.src.main.MainService;
import com.example.makeushifive.src.main.home.add.interfaces.AddActivityView;
import com.example.makeushifive.src.main.home.add.models.AddResponse;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.DoubleDateAndTimePickerDialog;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

import static com.example.makeushifive.src.ApplicationClass.DATE_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.DAY;
import static com.example.makeushifive.src.ApplicationClass.DAYOFWEEK;
import static com.example.makeushifive.src.ApplicationClass.KOREAN_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.MONTH;
import static com.example.makeushifive.src.ApplicationClass.YEAR;
import static com.example.makeushifive.src.ApplicationClass.sSharedPreferences;

public class AddActivity extends BaseActivity implements AddActivityView {

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
            ,mTvStartTime,mTvEndTime,mTvAlarmSelected,mTvAddCompleteRed,mTvAddCompleteBlack;

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


    int PickedStartMonth=-1,PickedStartDay=-1,PickedStartHour=-1,PickedStartMin=-1,
            PickedEndMonth=-1,PickedEndDay=-1,PickedEndHour=-1,PickedEndMin=-1,PickedStartYear=-1;

    FrameLayout mFlComplete;
    String title="";
    String location = "";
    String tag="";
    boolean titleFlag=false;

    //클릭해서 들어온 날짜
    String pickedDate="";
    Date pickedDay;

    Bundle mBundele;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mBundele =savedInstanceState;
        ImageView mIvClose = findViewById(R.id.add_iv_ex);
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FindViewById();
        SetVisibility();

        Intent intent = getIntent();
        pickedDate = Objects.requireNonNull(intent.getExtras()).getString("date");
        pickedDay = null;
        try {
            assert pickedDate != null;
            pickedDay = DATE_FORMAT.parse(pickedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert pickedDay != null;
        PickedStartMonth = Integer.parseInt(MONTH.format(pickedDay));
        PickedStartDay = Integer.parseInt(DAY.format(pickedDay));
        PickedStartYear = Integer.parseInt(YEAR.format(pickedDay));

        mTvStartDate.setText(KOREAN_FORMAT.format(pickedDay));
        mTvEndDate.setText(KOREAN_FORMAT.format(pickedDay));


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
                if(title.equals("")){
                    titleFlag=false;
                    mTvAddCompleteBlack.setVisibility(View.VISIBLE);
                    mTvAddCompleteRed.setVisibility(View.INVISIBLE);


                }else{
                    titleFlag=true;
                    mTvAddCompleteBlack.setVisibility(View.INVISIBLE);
                    mTvAddCompleteRed.setVisibility(View.VISIBLE);

                }

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

        mTvAddCompleteBlack.setVisibility(View.VISIBLE);
        mTvAddCompleteRed.setVisibility(View.INVISIBLE);

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
        //매주
        mTvRepeatEveryWeekBlack=findViewById(R.id.add_tv_repeat_every_week_black);
        mTvRepeatEveryWeekBlur=findViewById(R.id.add_tv_repeat_every_week_blur);
        //매일 매주 매월 선택 오픈,클로즈
        mIvRepeatAdd=findViewById(R.id.add_iv_repeat_add);
        mIvRepeatRemove=findViewById(R.id.add_iv_repeat_remove);
        //요일 버튼
        mTvSunBlack=findViewById(R.id.add_tv_repeat_select_day_sun_black);
        mTvSunBlur=findViewById(R.id.add_tv_repeat_select_day_sun_blur);
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
        mIvShowColor1 = findViewById(R.id.add_iv_show_color1);
        mIvShowColor2 = findViewById(R.id.add_iv_show_color2);
        mIvShowColor3 = findViewById(R.id.add_iv_show_color3);
        mIvShowColor4 = findViewById(R.id.add_iv_show_color4);
        mIvShowColor5 = findViewById(R.id.add_iv_show_color5);
        mIvShowColor6 = findViewById(R.id.add_iv_show_color6);
        mIvShowColor7 = findViewById(R.id.add_iv_show_color7);
        mIvShowColor8 = findViewById(R.id.add_iv_show_color8);
        mTvStartDate =findViewById(R.id.add_tv_start_date);
        mTvStartTime =findViewById(R.id.add_tv_start_time);
        mTvEndDate = findViewById(R.id.add_tv_end_date);
        mTvEndTime =findViewById(R.id.add_tv_end_time);
        mIvTimeAdd=findViewById(R.id.add_iv_time_add);
        mIvTimeRemove=findViewById(R.id.add_iv_time_remove);
        mLlEndTime=findViewById(R.id.add_ll_end_time);
        mIvAlarmAdd=findViewById(R.id.add_iv_alarm_add);
        mIvAlarmRemove=findViewById(R.id.add_iv_alarm_remove);
        mTvAlarmSelected=findViewById(R.id.add_tv_alarm_what);
        mIvTagAdd=findViewById(R.id.add_iv_tag_add);
        mIvTagRemove=findViewById(R.id.add_iv_tag_remove);
        mEdtTag=findViewById(R.id.add_edt_tag);
        mTvAddCompleteRed=findViewById(R.id.add_tv_complete_red);
        mTvAddCompleteBlack=findViewById(R.id.add_tv_complete_black);
        mEdtTitle=findViewById(R.id.add_edt_title);
        mFlComplete = findViewById(R.id.add_fl_complete);
    }

    public void TagClick(View view){
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

    public void AlarmClick(View view){
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


    public void TimeClick(View view){
        //AddTimeDialog띄우기
        if(!TimeFlag){
            TimeFlag=true;
            AddTimeDialog addTimeDialog = new AddTimeDialog(this,startTimeListener,endTimeListener);
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
    public void ColorClick(View view) {
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
    public void postAddSuccess(AddResponse.Result result) throws JSONException {
        //TODO 일정등록 성공했으니까 일정 반복 시도 ㄱㄱㄱ 일정반복도 성공하면 뒤로 넘어간다.
        Log.e("일정등록은 성공","일정반복 할지 말지 ");

        if(!EveryDayFlag && !EveryWeekFlag){
            // TODO 그냥 뒤로 가기! 반복할 일정이 없다.
            showCustomToast("일정이 등록되었습니다.");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            AddActivity.this.finish(); //로딩페이지를 액티비티 스택에서 제거거
            Log.e("반복할 일정이 없다.","반복할 일정이 없다.");
        }else{
            Log.e("반복할 일정이 있다.","반복할 일정이 있다.");
            //TODO 반복 존재
            int taskNo = result.getTaskNo();
            JsonObject jsonObject = new JsonObject(); //Post 할 jsonobject
            jsonObject.addProperty("taskNo",taskNo);
            JsonArray jsonArray = new JsonArray(); //배열

            if(EveryDayFlag && !EveryWeekFlag){
                //TODO 매일반복
                JsonObject object1 = new JsonObject();
                object1.addProperty("repeatweek",1);
                jsonArray.add(object1);

                JsonObject object2 = new JsonObject();
                object2.addProperty("repeatweek",2);
                jsonArray.add(object2);

                JsonObject object3 = new JsonObject();
                object3.addProperty("repeatweek",3);
                jsonArray.add(object3);

                JsonObject object4 = new JsonObject();
                object4.addProperty("repeatweek",4);
                jsonArray.add(object4);

                JsonObject object5 = new JsonObject();
                object5.addProperty("repeatweek",5);
                jsonArray.add(object5);

                JsonObject object6 = new JsonObject();
                object6.addProperty("repeatweek",6);
                jsonArray.add(object6);

                JsonObject object7 = new JsonObject();
                object7.addProperty("repeatweek",7);
                jsonArray.add(object7);

            }else if(!EveryDayFlag && EveryWeekFlag) {
                //TODO 매주반복
                if(DayFlag[0]){
                    JsonObject object1 = new JsonObject();
                    object1.addProperty("repeatweek",1);
                    jsonArray.add(object1);
                }
                if(DayFlag[1]){
                    JsonObject object2 = new JsonObject();
                    object2.addProperty("repeatweek",2);
                    jsonArray.add(object2);
                }
                if(DayFlag[2]){
                    JsonObject object3 = new JsonObject();
                    object3.addProperty("repeatweek",3);
                    jsonArray.add(object3);

                }
                if(DayFlag[3]){
                    JsonObject object4 = new JsonObject();
                    object4.addProperty("repeatweek",4);
                    jsonArray.add(object4);

                }
                if(DayFlag[4]){
                    JsonObject object5 = new JsonObject();
                    object5.addProperty("repeatweek",5);
                    jsonArray.add(object5);

                }
                if(DayFlag[5]){
                    JsonObject object6 = new JsonObject();
                    object6.addProperty("repeatweek",6);
                    jsonArray.add(object6);
                }
                if(DayFlag[6]){
                    JsonObject object7 = new JsonObject();
                    object7.addProperty("repeatweek",7);
                    jsonArray.add(object7);
                }

            }
            jsonObject.add("repeatweek",jsonArray);
            Log.e("반복","."+jsonObject);
            AddService addService = new AddService(this);
            addService.PostAddTaskRepeat(jsonObject);

        }
    }

    @Override
    public void postAddFail() {

    }

    @Override
    public void postAddTaskRepeatSuccess() {
            //TODO 일정반복 등록 성공
        Log.e("일정반복 등록 성공","일정반복 등록 성공");
            showCustomToast("일정이 등록되었습니다.");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            AddActivity.this.finish(); //로딩페이지를 액티비티 스택에서 제거거
    }

    @Override
    public void postAddTaskRepeatFail() {
        //TODO 일정반복 등록 성공

    }

    @Override
    protected void onResume() {
        super.onResume();
        String nickname = sSharedPreferences.getString("nickname",null);
        Log.e("AddActivity","onResume"+nickname);
        AddService mainService = new AddService(this);
        mainService.getUser(nickname);
    }

    @Override
    public void getUserSuccess() {
        Log.e("AddActivity","onCreateㄱㄱ");
        onCreate(mBundele);
    }

    @Override
    public void getUserFail() {

    }

    public void completeClick(View view) {
        //완료 버튼 클릭시 일정 등록
        if(titleFlag){ //최소한 타이틀이 입력되어 있어야 등록 가능
            location = mEdtLocation.getText().toString(); //장소
            tag = mEdtTag.getText().toString(); //태그

            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("title",title); //입력했어야 진입 가능
            jsonObject.addProperty("location",location); //입력 안했으면 ""
            jsonObject.addProperty("tag",tag); //입력 안했으면 ""
            jsonObject.addProperty("color",color); //입력 안했으면 1

            //TODO 1.시작시간X, 종료시간X => 시작시간만 설정
            //TODO 2. 시작시간O , 종료시간X ->없음
            //TODO 3. 시작시간X , 종료시간O ->없음
            //TODO 4. 시작시간O , 종료시간O => 둘 다 설정
            JsonObject dayInfo = new JsonObject(); //추가한 시간들 정보 담기
            dayInfo.addProperty("day",pickedDate);
            if(PickedStartHour==-1){//TODO 1  => 시작시간만 설정
                String time = MakeSetText(12,0);
                dayInfo.addProperty("time",time);
                JsonArray jsonArray = new JsonArray();

                jsonArray.add(dayInfo);

                jsonObject.add("days",jsonArray);

                Log.e("jsonarray",""+jsonArray);
                Log.e("jsonObject",""+jsonObject);
                try {
                    PostAddSchedule(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{ //TODO 4
                JsonArray jsonArray = new JsonArray();
                String time1 = MakeSetText(PickedStartHour,PickedStartMin);
                String time2 =MakeSetText(PickedEndHour,PickedEndMin);

                JsonObject object1 = new JsonObject();
                object1.addProperty("day",pickedDate);
                object1.addProperty("time",time1);
                jsonArray.add(object1);

                JsonObject object2 = new JsonObject();
                object2.addProperty("day",pickedDate);
                object2.addProperty("time",time2);
                jsonArray.add(object2);

                jsonObject.add("days",jsonArray);

                if(PickedStartHour>PickedEndHour || (PickedStartHour==PickedEndHour && PickedStartMin>PickedEndMin)){
                    //TODO 종
                    showCustomToast("시간을 다시 설정해 주세요");
                }else{
                    try {
                        PostAddSchedule(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public void PostAddSchedule(JsonObject jsonObject) throws JSONException {
        AddService addService = new AddService(this);
        addService.PostAddSchedule(jsonObject);
    }

}
