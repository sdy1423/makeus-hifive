package com.example.makeushifive.src.main.home;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseFragment;
import com.example.makeushifive.src.main.home.interfaces.HomeFragmentView;
import com.example.makeushifive.src.main.home.models.HomeResponse;
import com.example.makeushifive.src.main.home.models.HomeTodayResponse;
import com.example.makeushifive.src.main.home.search.SearchActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.example.makeushifive.src.ApplicationClass.CALENDAR_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.DATE_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.DAY;
import static com.example.makeushifive.src.ApplicationClass.KOREAN_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.MONTH;
import static com.example.makeushifive.src.ApplicationClass.YEAR;

public class HomeFragment extends BaseFragment implements HomeFragmentView {

    Context mContext;
    private ImageView mIvSearch,mIvAlarm;
    TextView mTvCurrentDate;
    private Calendar calendar;
    CalendarView calendarView;
    HomeService homeService;
    AddScheduleDialog addScheduleDialog;
    ArrayList<CalendarItem> calendarItems = new ArrayList<>();

    int taskNo,color,count,week;
    String title;
    Date day;


    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


            calendar.set(year, month,dayOfMonth);
            try {
                calendarView.setDate(calendar);
            } catch (OutOfDateRangeException e) {
                e.printStackTrace();
            }
            ShowScheduleInfo();

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


        calendarView = rootView.findViewById(R.id.home_calendarView);

        //        //상단에 년월 표시 안보이게
//        calendarView.setHeaderVisibility(View.INVISIBLE);



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
                    addScheduleDialog = new AddScheduleDialog(getActivity());

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

        Date CurrentDate = Calendar.getInstance().getTime();
        String Today =DATE_FORMAT.format(CurrentDate);

        HomeService homeService1 = new HomeService(this);
        homeService1.getTodaySchedule(Today);
        return rootView;
    }

    @Override
    public void getScheduleSuccess(ArrayList<HomeResponse.Result> result) throws ParseException {

        for(int i=0;i<result.size();i++){
            //TODO 달력에 쏴준다.
            //TODO 일정정보 배열을 만들어서 저장 -> 필요할때 taskNo로 꺼내 쓸 수 있도록
            //TODO 오늘날짜랑 같으면 오늘의 일정으로 뺀다 (필요한것:
            taskNo=result.get(i).getTaskNo();
            title=result.get(i).getTitle();
            color=result.get(i).getCount();
            count=result.get(i).getCount();
            day = DATE_FORMAT.parse(result.get(i).getDay());

            CalendarItem calendarItem = new CalendarItem(taskNo,title,color,day,count);
            calendarItems.add(calendarItem);
            }
            ShowScheduleInfo();
    }

    private void ShowScheduleInfo() {
        String ThisMonth = MONTH.format(calendarView.getCurrentPageDate().getTime());
        Log.e("thismonth",""+calendarView.getCurrentPageDate().getTime());
        List<EventDay> events = new ArrayList<>();
        int year_int,month_int,day_int;
        String year_st,month_st,day_st;
        for(int i=0;i<calendarItems.size();i++){
            month_st = MONTH.format(calendarItems.get(i).getDay());
            if(month_st.equals(ThisMonth)){//이번달이면
                year_st=YEAR.format(calendarItems.get(i).getDay());
                year_int=Integer.parseInt(year_st);
                month_int=Integer.parseInt(month_st);
                day_st=DAY.format(calendarItems.get(i).getDay());
                day_int=Integer.parseInt(day_st);
                calendar=Calendar.getInstance();

                calendar.set(year_int,month_int-1,day_int);
                events.add(new EventDay(calendar, R.drawable.ic_hifive_icon));
//                TextDrawable textDrawable = new TextDrawable("song");
//                    events.add(new EventDay(calendar,textDrawable));
                calendarView.setEvents(events);
            }else{
                continue;
            }
        }


    }

    @Override
    public void getScheduleFail() {

    }

    @Override
    public void getTodayScheduleSuccess(ArrayList<HomeTodayResponse.Result> result) {
        //TODO 오늘에 일정에도 보여주고 dialog로도 보내야 한다.

        //TODO result.size =0 오늘의 일정이 없다. !=0 오늘 일정 있다.
        if(result.size()==0){
            //TODO 오늘일정 존재

        }else if(result.size()==1){
            //TODO 1개

        }else if(result.size()==2){
            //TODO 2개

        }


    }

    @Override
    public void getTodayScheduleFail() {

    }
}
