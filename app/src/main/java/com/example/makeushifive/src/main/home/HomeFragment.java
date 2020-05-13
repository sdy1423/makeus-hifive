package com.example.makeushifive.src.main.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseFragment;
import com.example.makeushifive.src.main.home.add.AddActivity;
import com.example.makeushifive.src.main.home.interfaces.HomeFragmentView;
import com.example.makeushifive.src.main.home.search.SearchActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.example.makeushifive.src.ApplicationClass.DATE_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.DOT_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.KOREAN_FORMAT;

public class HomeFragment extends BaseFragment implements HomeFragmentView {

    Context mContext = getContext();
    private ImageView mIvSearch,mIvAlarm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

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
        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar,R.drawable.ic_account_circle_24px));
        CalendarView calendarView = rootView.findViewById(R.id.home_calendarView);
        calendarView.setEvents(events);

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
        return rootView;
    }


}
