package com.example.makeushifive.src.main.home;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarUtils;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseFragment;
import com.example.makeushifive.src.main.home.add.AddActivity;
import com.example.makeushifive.src.main.home.interfaces.HomeFragmentView;
import com.example.makeushifive.src.main.home.models.HomeResponse;
import com.example.makeushifive.src.main.home.search.SearchActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.example.makeushifive.src.ApplicationClass.CALENDAR_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.DATE_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.DAY;
import static com.example.makeushifive.src.ApplicationClass.DOT_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.KOREAN_FORMAT;

public class HomeFragment extends BaseFragment implements HomeFragmentView {

    Context mContext;
    private ImageView mIvSearch,mIvAlarm;
    TextView mTvCurrentDate;
    private Calendar calendar;
    CalendarView calendarView;
    HomeService homeService;

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Log.e("받아온거",""+year+" "+month+" "+dayOfMonth);
            calendar.set(year, month, 1);
            try {
                calendarView.setDate(calendar);
            } catch (OutOfDateRangeException e) {
                e.printStackTrace();
            }
            String showMonth="";
            if(month<10){
                showMonth="0";
            }
            showMonth+=String.valueOf(month);
            String ShowDate = String.valueOf(year);
            ShowDate+=". ";
            ShowDate+=showMonth;
            ShowDate+=" ";
            mTvCurrentDate.setText(ShowDate);
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mContext = getContext();
        mIvSearch=rootView.findViewById(R.id.home_toolbar_search);
        mIvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        mIvAlarm=rootView.findViewById(R.id.home_toolbar_alarm);

        List<EventDay> events = new ArrayList<>();
        for(int i=0;i<3;i++){
            calendar = Calendar.getInstance();
            int year,month,day;
            year=2020;
            month=5;
            day=20+i;
            calendar.set(year,month-1,day);
            TextDrawable textDrawable = new TextDrawable("song");
            events.add(new EventDay(calendar,textDrawable));
        }


        calendarView = rootView.findViewById(R.id.home_calendarView);
        calendarView.setEvents(events);

        //상단에 년월 표시 안보이게
        calendarView.setHeaderVisibility(View.INVISIBLE);



        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                //TODO DATE_FORMAT으로 보내고 필요할때 바꾸자.

                //오늘날짜
                String pickedDate = DAY.format(eventDay.getCalendar().getTime()); //dialog에 표시할 날짜 형식
                int pickedNum = Integer.parseInt(pickedDate);
//                Log.e("picked",""+pickedNum);
                String today = DAY.format(Calendar.getInstance().getTime());
                int todayNum = Integer.parseInt(today);
//                Log.e("today",""+todayNum);
                if(todayNum<=pickedNum){

                    //TODO dialog내부 recyclerview에 해당 날짜 일정 쏴주기
                    AddScheduleDialog addScheduleDialog = new AddScheduleDialog(getActivity());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("date",eventDay.getCalendar().getTime());
//                    Log.e("클릭한 날짜(dialog으로 보낼 날짜)",""+eventDay.getCalendar().getTime());
                    addScheduleDialog.setArguments(bundle);
                    addScheduleDialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),"tag");
                }
            }
        });

        //상단 날짜 표시
        String date = CALENDAR_FORMAT.format(calendarView.getCurrentPageDate().getTime());
        mTvCurrentDate=rootView.findViewById(R.id.home_toolbar_tv_today);
        mTvCurrentDate.setText(date);

        mTvCurrentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YearMonthPickerDialog yd = new YearMonthPickerDialog();
                yd.setListener(d);
                yd.show(Objects.requireNonNull(getFragmentManager()),"YearMonthPicker");
            }
        });


        homeService = new HomeService(this);
        homeService.getSchedule();



        return rootView;
    }

    @Override
    public void getScheduleSuccess(ArrayList<HomeResponse.Result> result) {

        Log.e("home에서 show",""+result);
        for(int i=0;i<result.size();i++){
            //TODO 달력에 쏴준다.

            //TODO 일정정보 배열을 만들어서 저장 -> 필요할때 taskNo로 꺼내 쓸 수 있도록
        }
    }

    @Override
    public void getScheduleFail() {

    }
}
