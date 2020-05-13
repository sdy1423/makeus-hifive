package com.example.makeushifive.src.main.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.makeushifive.R;
import com.example.makeushifive.src.main.home.add.AddActivity;

import java.util.Objects;

public class AddScheduleDialog extends DialogFragment {

    private Activity activity;
    private Context mContext;
    TextView mTvToday;
    private ImageView mIvAddSchedule;
    private Fragment fragment;
    public AddScheduleDialog(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.home_custom_dialog_add_schedule, container, false);

        Bundle args = getArguments();
        //TODO 날짜 받아오기
        String korean = Objects.requireNonNull(args).getString("korean");
        String dot = Objects.requireNonNull(args).getString("dot");


        fragment = Objects.requireNonNull(getActivity()).getSupportFragmentManager().findFragmentByTag("tag");

        mTvToday=rootview.findViewById(R.id.home_custom_dialog_day);
        mTvToday.setText(dot);

        mIvAddSchedule=rootview.findViewById(R.id.home_custom_dialog_iv_plus_button);
        mIvAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, AddActivity.class);
                //TODO today올바른 형식으로 수정할것.
//                activity.getIntent().putExtra("korean",korean);
                startActivity(intent);

                DialogFragment dialogFragment = (DialogFragment) fragment;
                dialogFragment.dismiss();

            }
        });




        return rootview;
    }


}
