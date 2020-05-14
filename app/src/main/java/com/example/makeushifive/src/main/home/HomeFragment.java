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
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import com.example.makeushifive.src.main.home.search.SearchActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.example.makeushifive.src.ApplicationClass.CALENDAR_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.DATE_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.DOT_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.KOREAN_FORMAT;

public class HomeFragment extends BaseFragment implements HomeFragmentView {

    Context mContext;
    private ImageView mIvSearch,mIvAlarm;
    TextView mTvCurrentDate;
    private Calendar calendar;
    CalendarView calendarView;

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
        calendar = Calendar.getInstance();
        int year,month,day;
        year=2020;
        month=5;
        day=20;
        calendar.set(year,month-1,day);
        events.add(new EventDay(calendar,R.drawable.ic_account_circle_24px));

        Calendar calendar1=Calendar.getInstance();
        year=2020;
        month=5;
        day=13;
        calendar1.set(year,month-1,day);
        events.add(new EventDay(calendar1,R.drawable.ic_hifive_icon));

//        CalendarUtils.getDrawableText(mContext,"song",Typeface.defaultFromStyle(R.style.TabLayoutStyle),    Color.parseColor("#FFFFFF"),10);
//        events.add(new EventDay(calendar, new Drawable() {
//            @Override
//            public void draw(@NonNull Canvas canvas) {
//
//            }
//
//            @Override
//            public void setAlpha(int alpha) {
//
//            }
//
//            @Override
//            public void setColorFilter(@Nullable ColorFilter colorFilter) {
//                String text="song";
//                Typeface typeface = null;
//                int size = 10;
//                CalendarUtils.getDrawableText(mContext,text, typeface, Color.parseColor("#228B22"), size);
//
//            }
//
//            @Override
//            public int getOpacity() {
//                return 0;
//            }
//        }));

        calendarView = rootView.findViewById(R.id.home_calendarView);
        calendarView.setEvents(events);

        //상단에 년월 표시 안보이게
        calendarView.setHeaderVisibility(View.INVISIBLE);



        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                 String korean = KOREAN_FORMAT.format(eventDay.getCalendar().getTime());
                 String dot = DOT_FORMAT.format(eventDay.getCalendar().getTime());

                AddScheduleDialog addScheduleDialog = new AddScheduleDialog(getActivity());
                Bundle args = new Bundle();
                args.putString("korean", korean);
                args.putString("dot",dot);
                addScheduleDialog.setArguments(args);
                addScheduleDialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),"tag");
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




        return rootView;
    }

}
