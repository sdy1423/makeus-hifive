package com.example.makeushifive.src.main.home.add;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.makeushifive.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static com.example.makeushifive.src.ApplicationClass.DAY;

public class AddTimeDialog extends Dialog {

    int startDay;
    private TimePickerDialog.OnTimeSetListener startTimeListener;
    private TimePickerDialog.OnTimeSetListener endTimeListener;
    public Calendar calendar = Calendar.getInstance();
    private Context context;


    public AddTimeDialog(@NonNull Context context,
                         TimePickerDialog.OnTimeSetListener startTimeListener,
                         TimePickerDialog.OnTimeSetListener endTimeListener) {
        super(context);
        this.context=context;
        this.startTimeListener = startTimeListener;
        this.endTimeListener=endTimeListener;
    }

    private TextView mTvLeftBlur,mTvLeftBlack,mTvRightBlur,mTvRightBlack;
    private LinearLayout mLlStartPick,mLlEndPick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_custom_dialog_time);

        WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount=0.8f;
        layoutParams.height = dpToPx(370,context);
        layoutParams.width = dpToPx(276,context);
        Objects.requireNonNull(getWindow()).setAttributes(layoutParams);

        Button btnComplete = findViewById(R.id.add_time_dialog_btn_complete);

        //TODO 클릭이벤트
        FrameLayout mFlLeftText = findViewById(R.id.add_dialog_time_fl_left);
        FrameLayout mFlRightText = findViewById(R.id.add_dialog_time_fl_right);

        mTvLeftBlur = findViewById(R.id.add_dialog_time_tv_left_blur);
        mTvLeftBlack=findViewById(R.id.add_dialog_time_tv_left_black);

        mTvRightBlur=findViewById(R.id.add_dialog_time_tv_right_blur);
        mTvRightBlack=findViewById(R.id.add_dialog_time_ll_right_black);

        mLlStartPick=findViewById(R.id.add_dialog_time_ll_start);
        mLlEndPick=findViewById(R.id.add_dialog_time_ll_end);

        ImageView mIvClose = findViewById(R.id.add_dialog_time_iv_close);
        //TODO 처음 오픈하면 LEFT=BLACK, RIGHT=BLUR,
        StartVisible();

        NumberPicker startHour = findViewById(R.id.time_picker_start_hour);
        NumberPicker startMin = findViewById(R.id.time_picker_start_min);
        NumberPicker endHour = findViewById(R.id.time_picker_end_hour);
        NumberPicker endMin = findViewById(R.id.time_picker_end_min);
        startHour.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        startMin.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        endHour.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        endMin.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("선택안했을 경우!시작",""+startHour.getValue()+" "+startMin.getValue());
                Log.e("선택안했을 경우!종료",""+endHour.getValue()+" "+endMin.getValue());
                startTimeListener.onTimeSet(null,startHour.getValue(),startMin.getValue());
                endTimeListener.onTimeSet(null,endHour.getValue(),endMin.getValue());
                dismiss();
            }
        });

        mFlLeftText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartVisible();
            }
        });

        mFlRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EndVisible();
            }
        });

        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        startHour.setMinValue(0);
        startHour.setMaxValue(23);

        endHour.setMinValue(0);
        endHour.setMaxValue(23);

        startMin.setMinValue(0);
        startMin.setMaxValue(59);

        endMin.setMinValue(0);
        endMin.setMaxValue(59);

        mTvLeftBlur.setText("시작시간");
        mTvLeftBlack.setText("시작시간");

        mTvRightBlur.setText("종료시간");
        mTvRightBlack.setText("종료시간");

        startHour.setValue(12);
        startMin.setValue(0);

        endHour.setValue(12);
        endMin.setValue(0);


    }

    public void StartVisible(){

        mLlStartPick.setVisibility(View.VISIBLE);
        mLlEndPick.setVisibility(View.INVISIBLE);

        mTvLeftBlur.setVisibility(View.INVISIBLE);
        mTvLeftBlack.setVisibility(View.VISIBLE);
        mTvRightBlur.setVisibility(View.VISIBLE);
        mTvRightBlack.setVisibility(View.INVISIBLE);

    }
    public void EndVisible(){
        mLlStartPick.setVisibility(View.INVISIBLE);
        mLlEndPick.setVisibility(View.VISIBLE);

        mTvLeftBlur.setVisibility(View.VISIBLE);
        mTvLeftBlack.setVisibility(View.INVISIBLE);
        mTvRightBlur.setVisibility(View.INVISIBLE);
        mTvRightBlack.setVisibility(View.VISIBLE);
    }

    public int dpToPx(int dp,Context context) {
        float density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }
}
