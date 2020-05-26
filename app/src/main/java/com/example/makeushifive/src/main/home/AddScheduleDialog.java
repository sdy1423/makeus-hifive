package com.example.makeushifive.src.main.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeushifive.R;
import com.example.makeushifive.src.main.home.add.AddActivity;
import com.example.makeushifive.src.main.home.models.HomeTodayResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static com.example.makeushifive.src.ApplicationClass.DATE_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.DAY;
import static com.example.makeushifive.src.ApplicationClass.DOT_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.MONTH;
import static com.example.makeushifive.src.ApplicationClass.YEAR;

public class AddScheduleDialog extends DialogFragment implements AddScheduleView{

    private Activity activity;
    private Context mContext;
    TextView mTvToday;
    private ImageView mIvAddSchedule;
    private Fragment fragment;
    ArrayList<PickedDayTasks> tasks = new ArrayList<>();
    RecyclerView recyclerView;


    public AddScheduleDialog(Activity activity) {
        this.activity = activity;
    }


    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.day_pick_dialog_width);
        int height = getResources().getDimensionPixelSize(R.dimen.day_pick_dialog_height);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setLayout(width,height);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getDialog().getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View rootview = inflater.inflate(R.layout.home_custom_dialog_add_schedule, container, false);

        Bundle bundle =savedInstanceState !=null ? savedInstanceState :getArguments();
        assert bundle != null;
        Date ShowDate = (Date)bundle.getSerializable("date");
        assert ShowDate != null;

        String dialogFormDate = DOT_FORMAT.format(ShowDate); //dialog에 표시할 날짜 형식
        String getScheduleFormat = DATE_FORMAT.format(ShowDate);
        AddScheduleService addScheduleService = new AddScheduleService(this);
        addScheduleService.getPickedDaySchedule(getScheduleFormat);

        //addActivity로 보낼것들
        String year = YEAR.format(ShowDate);
        String month = MONTH.format(ShowDate);
        String day = DAY.format(ShowDate);


        //TODO 오늘의 일정 보여주는 Recyclerview만들기 (오늘의 일정 get API 엮어서 보여주자)

        fragment = Objects.requireNonNull(getActivity()).getSupportFragmentManager().findFragmentByTag("tag");
        mTvToday=rootview.findViewById(R.id.home_custom_dialog_day);
        mTvToday.setText(dialogFormDate);

        mIvAddSchedule=rootview.findViewById(R.id.home_custom_dialog_iv_plus_button);
        mIvAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, AddActivity.class);
                intent.putExtra("date",getScheduleFormat);
//                intent.putExtra("year", year);
//                intent.putExtra("month",month);
//                intent.putExtra("day",day);
                startActivity(intent);

                DialogFragment dialogFragment = (DialogFragment) fragment;
                dialogFragment.dismiss();

            }
        });

        recyclerView=rootview.findViewById(R.id.home_dialog_add_schedule_recyclerview);
        //TODO 리사이클러뷰 만들기


        return rootview;
    }


    @Override
    public void getPickedScheduleSuccess(ArrayList<HomeTodayResponse.Result> result) {
        //TODO 리사이클러뷰 만들기

        String title,location,time;
        int color;
        try {
            if(!result.isEmpty()){
                for(int i=0;i<result.size();i++){
                    title=result.get(i).getTitle();
                    location=result.get(i).getLocation();
                    time=result.get(i).getTime();
                    color=result.get(i).getColor();
                    PickedDayTasks task = new PickedDayTasks(title,location,color,time);
                    tasks.add(task);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        feedRecyclerAdapter = new FeedRecyclerAdapter(tasks);
//        mRecyclerView.setAdapter(feedRecyclerAdapter);




    }

    @Override
    public void getPickedScheduleFail() {

    }
}
