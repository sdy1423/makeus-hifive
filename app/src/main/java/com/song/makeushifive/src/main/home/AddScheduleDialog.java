package com.song.makeushifive.src.main.home;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.song.makeushifive.R;
import com.song.makeushifive.src.main.chatting.ChattingActivity;
import com.song.makeushifive.src.main.home.add.AddActivity;
import com.song.makeushifive.src.main.home.models.HomeTodayResponse;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static com.song.makeushifive.src.ApplicationClass.DATE_FORMAT;
import static com.song.makeushifive.src.ApplicationClass.DAY;
import static com.song.makeushifive.src.ApplicationClass.DOT_FORMAT;
import static com.song.makeushifive.src.ApplicationClass.MONTH;
import static com.song.makeushifive.src.ApplicationClass.YEAR;

public class AddScheduleDialog extends DialogFragment implements AddScheduleView {

    private Activity activity;
    private Context mContext;
    TextView mTvToday;
    private ImageView mIvAddSchedule;
    private Fragment fragment;
    ArrayList<PickedDayTasks> tasks = new ArrayList<>();
    RecyclerView recyclerView;
    String getScheduleFormat;

    public AddScheduleDialog(Activity activity) {
        this.activity = activity;
    }

    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(@NotNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.day_pick_dialog_width);
        int height = getResources().getDimensionPixelSize(R.dimen.day_pick_dialog_height);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setLayout(width, height);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getDialog().getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View rootview = inflater.inflate(R.layout.home_custom_dialog_add_schedule, container, false);

        Bundle bundle = savedInstanceState != null ? savedInstanceState : getArguments();
        assert bundle != null;
        String getDate = bundle.getString("date");
        Date getDateDate = null;
        try {
            assert getDate != null;
            getDateDate = DATE_FORMAT.parse(getDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        assert getDateDate != null;
        String dialogFormDate = DOT_FORMAT.format(getDateDate); //dialog에 표시할 날짜 형식
        getScheduleFormat = DATE_FORMAT.format(getDateDate);
        AddScheduleService addScheduleService = new AddScheduleService(this);
        addScheduleService.getPickedDaySchedule(getScheduleFormat);

        //addActivity로 보낼것들
        String year = YEAR.format(getDateDate);
        String month = MONTH.format(getDateDate);
        String day = DAY.format(getDateDate);


        //TODO 오늘의 일정 보여주는 Recyclerview만들기 (오늘의 일정 get API 엮어서 보여주자)

        fragment = Objects.requireNonNull(getActivity()).getSupportFragmentManager().findFragmentByTag("tag");
        mTvToday = rootview.findViewById(R.id.home_custom_dialog_day);
        mTvToday.setText(dialogFormDate);

        mIvAddSchedule = rootview.findViewById(R.id.home_custom_dialog_iv_plus_button);
        mIvAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, AddActivity.class);
                intent.putExtra("date", getScheduleFormat);
                startActivity(intent);

                DialogFragment dialogFragment = (DialogFragment) fragment;
                dialogFragment.dismiss();

            }
        });

        recyclerView = rootview.findViewById(R.id.home_dialog_add_schedule_recyclerview);
        //TODO 리사이클러뷰 만들기


        return rootview;
    }


    @Override
    public void getPickedScheduleSuccess(ArrayList<HomeTodayResponse.Result> result) {
        //TODO 리사이클러뷰 만들기
        Log.e("선택한날 일정 받아옴", "성공");

        String title, location, time;
        int color, taskNo;
        tasks.clear();
        try {
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    title = result.get(i).getTitle();
                    location = result.get(i).getLocation();
                    time = result.get(i).getTime();
                    color = result.get(i).getColor();
                    taskNo = result.get(i).getTaskNo();
                    PickedDayTasks task = new PickedDayTasks(title, location, color, time, taskNo, false);
                    boolean flag=false;
                    try {
                        if(!tasks.isEmpty()){
                            for(int j=0;j<tasks.size();j++){
                                if(tasks.get(j).getTaskNo()==task.getTaskNo()){
                                    flag=true;
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(!flag){
                        tasks.add(task);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        AddScheduleRecyclerviewAdapter addScheduleRecyclerviewAdapter = new AddScheduleRecyclerviewAdapter(tasks);
        recyclerView.setAdapter(addScheduleRecyclerviewAdapter);

        addScheduleRecyclerviewAdapter.setOnItemClickListener(new AddScheduleRecyclerviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos, int taskNo, String time) throws ParseException {
//                Intent intent = new Intent(activity, TaskChangeActivity.class);
//                intent.putExtra("taskNo", taskNo);
//                intent.putExtra("date", time);
//                startActivity(intent);
                Toast.makeText(getContext(), "서비스 준비중 입니다.", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDeleteClick(View v, int pos, int taskNo) {
                try {
                    deleteTask(taskNo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTitleClick(View v, int pos, int taskNo, int color) {
                try {
                    Intent intent = new Intent(getContext(), ChattingActivity.class);
                    intent.putExtra("taskNo",taskNo);
                    intent.putExtra("color",color);
                    Log.e("addschedule taskNo",""+taskNo);
                    Log.e("addschedule color",""+color);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void deleteTask(int taskNo) throws JSONException {
        AddScheduleService addScheduleService = new AddScheduleService(this);
        addScheduleService.deleteTask(taskNo);
    }

    @Override
    public void getPickedScheduleFail() {

    }

    @Override
    public void deleteTaskSuccess() {
        Log.e("일정 삭제 성공", "성공");
        AddScheduleService addScheduleService = new AddScheduleService(this);
        addScheduleService.getPickedDaySchedule(getScheduleFormat);
    }

    @Override
    public void deleteTaskFail() {
        Toast.makeText(getContext(), "일정 삭제를 실패했습니다.", Toast.LENGTH_SHORT).show();
    }
}
