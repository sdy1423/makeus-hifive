package com.example.makeushifive.src.main.home;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarUtils;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseFragment;
import com.example.makeushifive.src.main.home.calendar.CalendarAdapter;
import com.example.makeushifive.src.main.home.calendar.DATA;
import com.example.makeushifive.src.main.home.calendar.Keys;
import com.example.makeushifive.src.main.home.calendar.TileItem;
import com.example.makeushifive.src.main.home.interfaces.HomeFragmentView;
import com.example.makeushifive.src.main.home.models.HomeResponse;
import com.example.makeushifive.src.main.home.models.HomeTodayResponse;
import com.example.makeushifive.src.main.home.search.SearchActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import static com.example.makeushifive.src.ApplicationClass.CALENDAR_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.DATE_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.DAY;
import static com.example.makeushifive.src.ApplicationClass.KOREAN_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.MONTH;
import static com.example.makeushifive.src.ApplicationClass.YEAR;

public class HomeFragment extends BaseFragment implements HomeFragmentView {

    int CurrentYear=0, CurrentMonth=0; //캘린더를 set할때 설정한다.
    ArrayList<TileItem> tileItems = new ArrayList<>();

    Context mContext;
    private ImageView mIvSearch,mIvAlarm;
    TextView mTvCurrentDate;
    HomeService homeService;
    AddScheduleDialog addScheduleDialog;
    ArrayList<CalendarItem> calendarItems = new ArrayList<>();

    int taskNo,color,count,week;
    String title;
    Date day;

    RecyclerView TodayRecyclerView;
    TextView mTvTodayNoSchedule;

    //TODO 직접 구현한 캘린더
    public int mCenterPosition;
    public ArrayList<Object> mCalendarList = new ArrayList<>();
    public TextView textView;
    public RecyclerView recyclerView;
    private CalendarAdapter mAdapter;
    private StaggeredGridLayoutManager manager;
    ArrayList<DATA> datas = new ArrayList<>();


    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            CurrentYear=year;
            CurrentMonth=month-1;

            ShowScheduleInfo(true);
//            initCalendarList(year,month-1,true);
//            setRecycler();

        }
    };


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        GregorianCalendar cal = new GregorianCalendar();
        CurrentYear = cal.get(Calendar.YEAR);
        CurrentMonth = cal.get(Calendar.MONTH);

        FindViewById(rootView);


        mContext = getContext();
        mIvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        mTvTodayNoSchedule.setVisibility(View.VISIBLE);
        TodayRecyclerView.setVisibility(View.INVISIBLE);
//
//        calendarView.setOnDayClickListener(new OnDayClickListener() {
//            @Override
//            public void onDayClick(EventDay eventDay) {
//                //TODO dialog내부 recyclerview에 해당 날짜 일정 쏴주기
//                addScheduleDialog = new AddScheduleDialog(getActivity());
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("date", eventDay.getCalendar().getTime());
//                addScheduleDialog.setArguments(bundle);
//                addScheduleDialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "tag");
//            }
//        });
        mTvCurrentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YearMonthPickerDialog yd = new YearMonthPickerDialog(CurrentYear,CurrentMonth);
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


        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if(e.getAction()==MotionEvent.ACTION_DOWN){//화면에 손가락 닿았을때
                    try {
                        View child =rv.findChildViewUnder(e.getX(),e.getY());
                        assert child != null;
                        LinearLayout linearLayout = rv.getChildViewHolder(child).itemView.findViewById(R.id.item_layout);
                        linearLayout.setBackgroundColor(Color.parseColor("#F4F4F4"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                if(e.getAction()== MotionEvent.ACTION_UP){
                    try {
                        View child =rv.findChildViewUnder(e.getX(),e.getY());
                        assert child != null;
                        LinearLayout linearLayout = rv.getChildViewHolder(child).itemView.findViewById(R.id.item_layout);
                        linearLayout.setBackgroundResource(R.drawable.calendar_tile_border_second);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        return rootView;
    }
    public void FindViewById(View rootView){
        mIvSearch=rootView.findViewById(R.id.home_toolbar_search);
        mIvAlarm=rootView.findViewById(R.id.home_toolbar_alarm);
        TodayRecyclerView=rootView.findViewById(R.id.home_today_schedule_recycler);
        mTvTodayNoSchedule=rootView.findViewById(R.id.home_today_schedule_tv_no);
        mTvCurrentDate=rootView.findViewById(R.id.home_toolbar_tv_today);
        textView = rootView.findViewById(R.id.title);
        recyclerView = rootView.findViewById(R.id.calendar);
//        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void getScheduleSuccess(ArrayList<HomeResponse.Result> result) throws ParseException {

        try {
            for(int i=0;i<result.size();i++){
                //TODO 달력에 쏴준다.
                //TODO 일정정보 배열을 만들어서 저장 -> 필요할때 taskNo로 꺼내 쓸 수 있도록
                //TODO 오늘날짜랑 같으면 오늘의 일정으로 뺀다 (필요한것:

                //TODO 지금은 모든 일정 다 담아놔야 달력 바꿀때 꺼내 쓸 수 있음
                taskNo=result.get(i).getTaskNo();
                title=result.get(i).getTitle();
                title=result.get(i).getTitle();
                color=result.get(i).getColor();
                count=result.get(i).getCount();
                day = DATE_FORMAT.parse(result.get(i).getDay());

                CalendarItem calendarItem = new CalendarItem(taskNo,title,color,day,count);
                calendarItems.add(calendarItem);
            }
            ShowScheduleInfo(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void ShowScheduleInfo(boolean flag) {
//        String ThisMonth = MONTH.format(calendarView.getCurrentPageDate().getTime());
//        Log.e("thismonth",""+calendarView.getCurrentPageDate().getTime());
//        int year_int,month_int,day_int;
//        String year_st,month_st,day_st;
//        List<EventDay> events = new ArrayList<>();

        if(!flag){
            for(int i=0;i<calendarItems.size();i++){
                //같은 달인지 파싱한다.
                int ItemYear = Integer.parseInt(YEAR.format(calendarItems.get(i).getDay()));
                int ItemMonth = Integer.parseInt(MONTH.format(calendarItems.get(i).getDay()));
                if(ItemYear==CurrentYear && ItemMonth == CurrentMonth+1){ //현재 달력과 년 월이 같다면
                    int ItemDay = Integer.parseInt(DAY.format(calendarItems.get(i).getDay()));
                    int ItemColor = calendarItems.get(i).getColor();
                    String ItemTitle = calendarItems.get(i).getTitle();
                    TileItem item = new TileItem(ItemYear,ItemMonth,ItemDay,ItemColor,ItemTitle);
                    tileItems.add(item);
                }else{
                    continue;
                }
            }
        }else{
            tileItems.clear();
            for(int i=0;i<calendarItems.size();i++){
                //같은 달인지 파싱한다.
                int ItemYear = Integer.parseInt(YEAR.format(calendarItems.get(i).getDay()));
                int ItemMonth = Integer.parseInt(MONTH.format(calendarItems.get(i).getDay()));
                if(ItemYear==CurrentYear && ItemMonth == CurrentMonth+1){ //현재 달력과 년 월이 같다면
                    int ItemDay = Integer.parseInt(DAY.format(calendarItems.get(i).getDay()));
                    int ItemColor = calendarItems.get(i).getColor();
                    String ItemTitle = calendarItems.get(i).getTitle();
                    TileItem item = new TileItem(ItemYear,ItemMonth,ItemDay,ItemColor,ItemTitle);
                    tileItems.add(item);
                }else{
                    continue;
                }
            }
        }

        //타일 안에 들어간 정보를 다 담고 달력 만든다.
//        for(int i=0;i<tileItems.size();i++){
//            int yea = tileItems.get(i).getYear();
//            int mon = tileItems.get(i).getMonth();
//            int day = tileItems.get(i).getDay();
//            String strin = tileItems.get(i).getTitle();
//            int colo = tileItems.get(i).getColor();
////            Log.e("타일 year",""+yea);
////            Log.e("타일 mon",""+mon);
////            Log.e("타일 day",""+day);
////            Log.e("타일 title",""+strin);
////            Log.e("타일 color",""+colo);
//
//        }
        initSet(flag);
        setRecycler();
    }

    @Override
    public void getScheduleFail() {

    }

    @Override
    public void getTodayScheduleSuccess(ArrayList<HomeTodayResponse.Result> result) {
        //TODO 오늘에 일정에도 보여주고 dialog로도 보내야 한다.

        //TODO result.size =0 오늘의 일정이 없다. !=0 오늘 일정 있다.
        try {
            int color=0;
            String location,time,title;

            ArrayList<PickedDayTasks> tasks = new ArrayList<>();
            for(int i=0;i<result.size();i++){
                color = result.get(i).getColor();
                location = result.get(i).getLocation();
                time=result.get(i).getTime();
                title = result.get(i).getTitle();
                PickedDayTasks pickedDayTasks = new PickedDayTasks(title,location,color,time);
                tasks.add(pickedDayTasks);
            }

            if(result.isEmpty()){
                TodayRecyclerView.setVisibility(View.INVISIBLE);
                mTvTodayNoSchedule.setVisibility(View.VISIBLE);
            }else{
                //TODO 오늘의 일정에 뿌리기기
                mTvTodayNoSchedule.setVisibility(View.INVISIBLE);
                TodayRecyclerView.setVisibility(View.VISIBLE);

                //가로 리사이클러
                TodayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                TodayRecyclerAdapter todayRecyclerAdapter = new TodayRecyclerAdapter(tasks);
                TodayRecyclerView.setAdapter(todayRecyclerAdapter);

                TodayRecyclerView.setVisibility(View.VISIBLE);
                mTvTodayNoSchedule.setVisibility(View.INVISIBLE);

            }
        }catch (Exception e) {
            e.printStackTrace();
            //TODO 오늘의 일정 없음 보여주기
            mTvTodayNoSchedule.setVisibility(View.VISIBLE);
            TodayRecyclerView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void getTodayScheduleFail() {

    }


    private void setRecycler() {
        if (mCalendarList == null) {
//            Log.w(TAG, "No Query, not initializing RecyclerView");
        }

        manager = new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new CalendarAdapter(tileItems,datas,mCalendarList,getContext());
        mAdapter.setCalendarList(mCalendarList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CalendarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos, int year, int month, int day) throws ParseException {
                //TODO Dialog 띄운다.
                Log.e("Home",""+year+" "+month+" "+day);

                String date = MakeStringForm(year,month+1,day);
                addScheduleDialog = new AddScheduleDialog(getActivity());
                Bundle bundle = new Bundle();
                bundle.putString("date",date);
//                Log.e("SHOWSHOW",""+date);
//                bundle.putSerializable("date", date.getTime());
                addScheduleDialog.setArguments(bundle);
                addScheduleDialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "tag");
            }
        });

        if (mCenterPosition >= 0) {
            recyclerView.scrollToPosition(mCenterPosition);
        }


    }

    private void initSet(boolean flag) {
        initCalendarList(flag);
    }

    private void initCalendarList(boolean flag) {
        if(!flag){
            GregorianCalendar cal = new GregorianCalendar();
            setCalendarList(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH));
        }else{
            Log.e("initCalendarList",""+CurrentYear+" "+CurrentMonth);
            setCalendarList(CurrentYear,CurrentMonth);
        }
    }
    private void setCalendarList(int year,int month) {
        CurrentYear=year;
        CurrentMonth=month;

        Log.e("setCalendarlist",""+year+" "+month);

        //TODO Month는 미리 +1해서 줄것.

        datas.clear();
        ArrayList<Object> calendarList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            try {
                GregorianCalendar calendar = new GregorianCalendar(year, month, 1, 0, 0, 0);
                if (i == 0) {
                    mCenterPosition = calendarList.size();
                }
                //상단!
                Date ShowDate = MakeDateForm(year,month+1,1);
                String date = CALENDAR_FORMAT.format(ShowDate);
                mTvCurrentDate.setText(date);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; //해당 월에 시작하는 요일 -1 을 하면 빈칸을 구할 수 있겠죠 ?
                int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 해당 월에 마지막 요일

                // EMPTY 생성
                for (int j = 0; j < dayOfWeek; j++) {
                    calendarList.add(Keys.EMPTY);
                    DATA data = new DATA(0, 0, 0);
                    datas.add(data);
                }
                for (int j = 1; j <= max; j++) {
                    calendarList.add(new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), j));
                    DATA data = new DATA(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), j);
                    datas.add(data);
                }
                for(int j = 0;j<(7-(dayOfWeek + max)%7);j++){
                    calendarList.add(Keys.EMPTY);
                    DATA data = new DATA(0, 0, 0);
                    datas.add(data);
                }


                // TODO : 결과값 넣을떄 여기다하면될듯
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mCalendarList = calendarList;
    }

    private void initView(View rootView) {
    }

    public String MakeStringForm(int year,int month,int day) throws ParseException {
//        month+=1;
        String stringDate="";
        stringDate+=String.valueOf(year);
        stringDate+="-";
        stringDate+=String.valueOf(month);
        stringDate+="-";
        stringDate+=String.valueOf(day);
        return stringDate;
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

}
