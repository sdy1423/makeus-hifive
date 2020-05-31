package com.example.makeushifive.src.main.home.add;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
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
    private DatePickerDialog.OnDateSetListener startDateListener;
    private TimePickerDialog.OnTimeSetListener startTimeListener;
    private DatePickerDialog.OnDateSetListener endDateListener;
    private TimePickerDialog.OnTimeSetListener endTimeListener;
    public Calendar calendar = Calendar.getInstance();
    private Context context;
    private Date PickedDate;


    public AddTimeDialog(@NonNull Context context, DatePickerDialog.OnDateSetListener startDateListener,
                         TimePickerDialog.OnTimeSetListener startTimeListener,
                         DatePickerDialog.OnDateSetListener endDateListener,
                         TimePickerDialog.OnTimeSetListener endTimeListener,
                         Date pickedDate) {
        super(context);
        this.context=context;
        this.startDateListener = startDateListener;
        this.startTimeListener = startTimeListener;
        this.endDateListener=endDateListener;
        this.endTimeListener=endTimeListener;
        this.PickedDate=pickedDate;
    }

    Button btnComplete;
    FrameLayout mFlLeftText,mFlRightText;
    TextView mTvLeftBlur,mTvLeftBlack,mTvRightBlur,mTvRightBlack;
    LinearLayout mLlStartPick,mLlEndPick;
    ImageView mIvClose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_custom_dialog_time);

        WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount=0.8f;
        layoutParams.height = dpToPx(800,context);
        layoutParams.width = dpToPx(400,context);
        Objects.requireNonNull(getWindow()).setAttributes(layoutParams);

        startDay= Integer.parseInt(DAY.format(PickedDate));



        btnComplete = findViewById(R.id.add_time_dialog_btn_complete);

        //TODO 클릭이벤트
        mFlLeftText = findViewById(R.id.add_dialog_time_fl_left);
        mFlRightText = findViewById(R.id.add_dialog_time_fl_right);

        mTvLeftBlur = findViewById(R.id.add_dialog_time_tv_left_blur);
        mTvLeftBlack=findViewById(R.id.add_dialog_time_tv_left_black);

        mTvRightBlur=findViewById(R.id.add_dialog_time_tv_right_blur);
        mTvRightBlack=findViewById(R.id.add_dialog_time_ll_right_black);

        mLlStartPick=findViewById(R.id.add_dialog_time_ll_start);
        mLlEndPick=findViewById(R.id.add_dialog_time_ll_end);

        mIvClose = findViewById(R.id.add_dialog_time_iv_close);
        //TODO 처음 오픈하면 LEFT=BLACK, RIGHT=BLUR,
        StartVisible();

        final NumberPicker startDayPicker =findViewById(R.id.add_dialog_time_np_start_day);
        final NumberPicker startHourPicker =findViewById(R.id.add_dialog_time_np_start_hour);
        final NumberPicker startMinPicker = findViewById(R.id.add_dialog_time_np_start_min);
        final NumberPicker endDayPicker=findViewById(R.id.add_dialog_time_np_end_day);
        final NumberPicker endHourPicker =findViewById(R.id.add_dialog_time_np_end_hour);
        final NumberPicker endMinPicker = findViewById(R.id.add_dialog_time_np_end_min);

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateListener.onDateSet(null,2020,calendar.get(Calendar.MONTH)+1,startDayPicker.getValue());
                startTimeListener.onTimeSet(null,startHourPicker.getValue(),startMinPicker.getValue());
                endDateListener.onDateSet(null,2020,calendar.get(Calendar.MONTH)+1,endDayPicker.getValue());
                endTimeListener.onTimeSet(null,endHourPicker.getValue(),endMinPicker.getValue());
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

        //시작날짜 고정
        startDayPicker.setMinValue(startDay);
        startDayPicker.setMaxValue(startDay);

        startHourPicker.setMinValue(0);
        startHourPicker.setMaxValue(23);

        startMinPicker.setMinValue(0);
        startMinPicker.setMaxValue(59);

        endDayPicker.setMinValue(startDay);
        endDayPicker.setMaxValue(calendar.getMaximum(Calendar.DAY_OF_MONTH));

        endHourPicker.setMinValue(0);
        endHourPicker.setMaxValue(23);

        endMinPicker.setMinValue(0);
        endMinPicker.setMaxValue(59);


        startDayPicker.setValue(startDay);
        endDayPicker.setValue(startDay);

        startHourPicker.setValue(calendar.get(Calendar.HOUR_OF_DAY));
        endHourPicker.setValue(calendar.get(Calendar.HOUR_OF_DAY));

        startMinPicker.setValue(calendar.get(Calendar.MINUTE));
        endMinPicker.setValue(calendar.get(Calendar.MINUTE));

        mTvLeftBlur.setText("시작날짜");
        mTvLeftBlack.setText("시작날짜");

        mTvRightBlur.setText("종료날짜");
        mTvRightBlack.setText("종료날짜");


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
