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
import com.example.makeushifive.src.main.home.add.interfaces.AddActivityView;
import com.example.makeushifive.src.main.home.add.models.AddResponse;
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

public class AddActivity extends BaseActivity implements AddActivityView {

    int speed = 500;
    //TODO 1번 컬러 디폴트로 설정, 타이틀만 추가해도 일정 추가 가능하도록 ㄱㄱ
    int color=1;
    ValueAnimator mlocationAni,SelectDayWeekMonthAni,SelectMonToSunAni,mTimeAni,mAlarmAni,mTagAni;
    EditText mEdtLocation,mEdtTag,mEdtTitle;
    int location_height = 150,time_height=110,location_heightt=110,tag_height=110;
    LinearLayout mLlRepeatSelect,mLlRepeatSelectDay,mLlEndTime;
    TextView mTvRepeatEverdayBlur,mTvRepeatEverdayBlack,mTvRepeatEverMonthBlur,mTvRepeatEverMonthBlack,mTvRepeatEveryWeekBlack,mTvRepeatEveryWeekBlur,
    mTvMonBlack,mTvMonBlur,mTvTueBlack,mTvTueBlur,mTvWedBlack,mTvWedBlur,mTvThuBlack,mTvThuBlur,mTvFriBlack,mTvFriBlur,mTvSatBlack,mTvSatBlur,mTvSunBlack,mTvSunBlur,mTvStartDate,mTvEndDate
            ,mTvStartTime,mTvEndTime,mTvAlarmSelected,mTvAddCompleteRed,mTvAddCompleteBlack;
    ImageView mIvShowColor1,mIvShowColor2,mIvShowColor3,mIvShowColor4,mIvShowColor5,mIvShowColor6,mIvShowColor7,
            mIvShowColor8,mIvLocationAdd,mIvLocationRemove,mIvRepeatAdd,mIvRepeatRemove,mIvTimeAdd,mIvTimeRemove,mIvAlarmAdd,mIvAlarmRemove,mIvTagAdd,mIvTagRemove;
    //월,화,수,목,금,토,일 클릭 여부
    boolean DayFlag[]={false,false,false,false,false,false,false},LocationFlag=false,SelectDayWeekMonthFlag=false,EveryDayFlag=false,EveryWeekFlag=false,EveryMonthFlag=false,TimeFlag=false,AlarmFlag=false
            ,TagFlag=false;


    int PickedStartMonth=-1,PickedStartDay=-1,PickedStartHour=-1,PickedStartMin=-1,
            PickedEndMonth=-1,PickedEndDay=-1,PickedEndHour=-1,PickedEndMin=-1,PickedStartYear=-1;

    FrameLayout mFlComplete;
    String FirstShowDate;
//    ArrayList<DAYS> days = new ArrayList<>();
    String title="";
    String location = "";
    String tag="";
    boolean titleFlag=false;

    String pickedDate="";
    Date pickedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

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



        mFlComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titleFlag){
                    //TODO 일정등록
                    location = mEdtLocation.getText().toString();
                    tag = mEdtTag.getText().toString();

                    JsonObject jsonObject = new JsonObject();
                    JsonArray jsonArray = new JsonArray();

                    jsonObject.addProperty("title",title);
                    jsonObject.addProperty("location",location);
                    jsonObject.addProperty("tag",tag);
                    jsonObject.addProperty("color",color);

                    JsonObject dayInfo = new JsonObject();
                    //TODO 시간추가에서 days 넣기
                    if(PickedEndDay==-1){
                        //TODO 종료 시간 설정 안했을 경우
                        dayInfo.addProperty("day",pickedDate);
                        String time="";
                        if(PickedStartMin==-1){
                            time = "00:00";
                        }
                        dayInfo.addProperty("time",time);
                        jsonArray.add(dayInfo);

                    }

                    else if(PickedStartMonth==PickedEndMonth){//시작과 끝달이 같을 때
                        //2020-05-14 , 10:00
                        int s = PickedStartDay;
                        int e = PickedEndDay;
                        int sub = e-s;
                        String tempDate = "";
                        tempDate+= String.valueOf(PickedStartYear);
                        tempDate+= "-";
                        if(PickedStartMonth<10){
                            tempDate+="0";
                        }
                        tempDate+=String.valueOf(PickedStartMonth);
                        tempDate+="-";
                        String sendDate;
                        for(int i=s;i<=s+sub;i++){
                            sendDate=tempDate;
                            if(i<10){
                                sendDate+="0";
                            }
                            sendDate+=String.valueOf(i);
                            String time = "";
                            if(i<s+sub){
                                if(PickedStartHour<10){
                                    time+="0";
                                }
                                time += String.valueOf(PickedStartHour);
                                time+=":";
                                if(PickedStartMin<10){
                                    time+="0";
                                }
                                time+= String.valueOf(PickedStartMin);
                            }else{
                                if(PickedEndHour<10){
                                    time+="0";
                                }
                                time+=String.valueOf(PickedEndHour);
                                time+=":";
                                if(PickedEndMin<10){
                                    time+="0";
                                }
                                time+=String.valueOf(PickedEndMin);
                            }
                            dayInfo.addProperty("day",sendDate);
                            dayInfo.addProperty("time",time);
                            jsonArray.add(dayInfo);
                        }
                    }else{
                        //TODO 시작월, 끝 월 다를 경우 만들기


                    }
                    jsonObject.add("days",jsonArray);
                    try {
                        Log.e("jsonarray",""+jsonArray);
                        Log.e("jsonObject",""+jsonObject);
                        PostAddSchedule(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public void PostAddSchedule(JsonObject jsonObject) throws JSONException {
        AddService addService = new AddService(this);
        addService.PostAddSchedule(jsonObject);
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

        mTvRepeatEverMonthBlack.setVisibility(View.INVISIBLE);
        mTvRepeatEverMonthBlur.setVisibility(View.VISIBLE);

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
            AddTimeDialog addTimeDialog = new AddTimeDialog(this,startDateListener,startTimeListener,endDateListener,endTimeListener,pickedDay);
            Objects.requireNonNull(addTimeDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            addTimeDialog.show();
        }else{
            TimeFlag=false;
            ClosePickedTime();
        }
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
//            Log.e("starttime",""+hourOfDay+" "+minute);
            PickedStartHour = hourOfDay;
            PickedStartMin = minute;

        }
    };
    DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//            Log.e("enddate",""+year+" "+month+" "+dayOfMonth);
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
            OpenPickedTime();
        }
    };

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

        String ShowStartDate="",ShowStartTime="",ShowEndDate="",ShowEndTime="";

        //시작(몇월몇일)
        ShowStartDate+=String.valueOf(PickedStartMonth);
        ShowStartDate+="월 ";
        ShowStartDate+=String.valueOf(PickedStartDay);
        ShowStartDate+=String.valueOf("일");

        //끝(몇월및일)
        ShowEndDate+=String.valueOf(PickedEndMonth);
        ShowEndDate+="월 ";
        ShowEndDate+=String.valueOf(PickedEndDay);
        ShowEndDate+=String.valueOf("일");

        //시작(시간)
        if(PickedStartHour<10){
            ShowStartTime+="0";
        }
        ShowStartTime+=PickedStartHour;
        ShowStartTime+=":";
        if(PickedStartMin<10){
            ShowStartTime+="0";
        }
        ShowStartTime+=PickedStartMin;

        //끝(시간)
        if(PickedEndHour<10){
            ShowEndTime+="0";
        }
        ShowEndTime+=PickedEndHour;
        ShowEndTime+=":";
        if(PickedEndMin<10){
            ShowEndTime+="0";
        }
        ShowEndTime+=PickedEndMin;

        mTvStartDate.setText(ShowStartDate);
        mTvStartTime.setText(ShowStartTime);
        mTvEndDate.setText(ShowEndDate);
        mTvEndTime.setText(ShowEndTime);

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
        mTvStartDate.setText(FirstShowDate);

        mTvStartTime.setText("");
        mTvEndDate.setText("");
        mTvEndTime.setText("");



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
                //매월 닫기
                if(EveryMonthFlag){
                    EveryMonthFlag=false;
                    mTvRepeatEverMonthBlack.setVisibility(View.INVISIBLE);
                    mTvRepeatEverMonthBlur.setVisibility(View.VISIBLE);
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
                if(EveryMonthFlag){
                    EveryMonthFlag=false;
                    mTvRepeatEverMonthBlack.setVisibility(View.INVISIBLE);
                    mTvRepeatEverMonthBlur.setVisibility(View.VISIBLE);
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

                if(EveryDayFlag){
                    EveryDayFlag=false;
                    mTvRepeatEverdayBlack.setVisibility(View.INVISIBLE);
                    mTvRepeatEverdayBlur.setVisibility(View.VISIBLE);
                }
                if(EveryWeekFlag){
                    EveryWeekFlag=false;
                    CloseSelectMonToSun();
                    mTvRepeatEveryWeekBlack.setVisibility(View.INVISIBLE);
                    mTvRepeatEveryWeekBlur.setVisibility(View.VISIBLE);
                }




                break;
            case R.id.add_fl_repeat_select_day_sun:
                if(!DayFlag[0]){
                    mTvSunBlack.setVisibility(View.VISIBLE);
                    mTvSunBlack.setVisibility(View.INVISIBLE);
                    DayFlag[0]=true;
                }else{
                    mTvSunBlack.setVisibility(View.INVISIBLE);
                    mTvSunBlack.setVisibility(View.VISIBLE);
                    DayFlag[0]=false;
                }
                break;
            case R.id.add_fl_repeat_select_day_mon:
                if(!DayFlag[1]){
                    mTvMonBlack.setVisibility(View.VISIBLE);
                    mTvMonBlur.setVisibility(View.INVISIBLE);
                    DayFlag[1]=true;
                }else{
                    mTvMonBlack.setVisibility(View.INVISIBLE);
                    mTvMonBlur.setVisibility(View.VISIBLE);
                    DayFlag[1]=false;
                }
                break;
            case R.id.add_fl_repeat_select_day_tue:
                if(!DayFlag[2]){
                    mTvTueBlack.setVisibility(View.VISIBLE);
                    mTvTueBlur.setVisibility(View.INVISIBLE);
                    DayFlag[2]=true;
                }else{
                    mTvTueBlack.setVisibility(View.INVISIBLE);
                    mTvTueBlur.setVisibility(View.VISIBLE);
                    DayFlag[2]=false;
                }
                break;
            case R.id.add_fl_repeat_select_day_wed:
                if(!DayFlag[3]){
                    mTvWedBlack.setVisibility(View.VISIBLE);
                    mTvWedBlur.setVisibility(View.INVISIBLE);
                    DayFlag[3]=true;
                }else{
                    mTvWedBlack.setVisibility(View.INVISIBLE);
                    mTvWedBlur.setVisibility(View.VISIBLE);
                    DayFlag[3]=false;
                }
                break;
            case R.id.add_fl_repeat_select_day_thu:
                if(!DayFlag[4]){
                    mTvThuBlack.setVisibility(View.VISIBLE);
                    mTvThuBlur.setVisibility(View.INVISIBLE);
                    DayFlag[4]=true;
                }else{
                    mTvThuBlack.setVisibility(View.INVISIBLE);
                    mTvThuBlur.setVisibility(View.VISIBLE);
                    DayFlag[4]=false;
                }
                break;
            case R.id.add_fl_repeat_select_day_fri:
                if(!DayFlag[5]){
                    mTvFriBlack.setVisibility(View.VISIBLE);
                    mTvFriBlur.setVisibility(View.INVISIBLE);
                    DayFlag[5]=true;
                }else {
                    mTvFriBlack.setVisibility(View.INVISIBLE);
                    mTvFriBlur.setVisibility(View.VISIBLE);
                    DayFlag[5] = false;
                    break;
                }
            case R.id.add_fl_repeat_select_day_sat:
                if(!DayFlag[6]){
                    mTvSatBlack.setVisibility(View.VISIBLE);
                    mTvSatBlur.setVisibility(View.INVISIBLE);
                    DayFlag[6]=true;
                }else {
                    mTvSatBlack.setVisibility(View.INVISIBLE);
                    mTvSatBlur.setVisibility(View.VISIBLE);
                    DayFlag[6] = false;
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

        if(!EveryDayFlag && !EveryWeekFlag && !EveryMonthFlag){
            // TODO 그냥 뒤로 가기! 반복할 일정이 없다.
            showCustomToast("일정이 등록되었습니다.");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            AddActivity.this.finish(); //로딩페이지를 액티비티 스택에서 제거거
        }else{
            //TODO 반복 존재
             int taskNo = result.getTaskNo();
            JsonObject jsonObject = new JsonObject(); //Post 할 jsonobject
            jsonObject.addProperty("taskNo",taskNo);
            JsonArray jsonArray = new JsonArray(); //배열
            JsonObject object = new JsonObject(); //배열에 넣을 것

            if(EveryDayFlag){
                //TODO 매일반복
                object.addProperty("repeatweek",1);
                object.addProperty("repeatweek",2);
                object.addProperty("repeatweek",3);
                object.addProperty("repeatweek",4);
                object.addProperty("repeatweek",5);
                object.addProperty("repeatweek",6);
                object.addProperty("repeatweek",7);

            }else if(EveryWeekFlag) {
                //TODO 매주반복
                for(int i=0;i<7;i++){
                    if(DayFlag[i]){
                        object.addProperty("repeatweek",i+1);
                    }
                }
            }else if(EveryMonthFlag) {
                //TODO 매월반복
                String DayOfWeek= DAYOFWEEK.format(pickedDay);
                switch (DayOfWeek){
                    case "일":
                        object.addProperty("repeatweek",1);
                        break;
                    case "월":
                        object.addProperty("repeatweek",2);
                        break;
                    case "화":
                        object.addProperty("repeatweek",3);
                        break;
                    case "수":
                        object.addProperty("repeatweek",4);
                        break;
                    case "목":
                        object.addProperty("repeatweek",5);
                        break;
                    case "금":
                        object.addProperty("repeatweek",6);
                        break;
                    case "토":
                        object.addProperty("repeatweek",7);
                        break;
                    default:
                        break;
                }
            }
            jsonArray.add(object);
            jsonObject.add("repeatweek",jsonArray);
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
            showCustomToast("일정이 등록되었습니다.");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            AddActivity.this.finish(); //로딩페이지를 액티비티 스택에서 제거거
    }

    @Override
    public void postAddTaskRepeatFail() {
        //TODO 일정반복 등록 성공

    }
}
