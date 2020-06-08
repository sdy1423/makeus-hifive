package com.example.makeushifive.src.main.home.add;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.makeushifive.R;
import com.github.jjobes.slidedatetimepicker.CustomDatePicker;
import com.github.jjobes.slidedatetimepicker.CustomTimePicker;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static com.example.makeushifive.src.ApplicationClass.DATE_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.DAY;
import static com.example.makeushifive.src.ApplicationClass.MONTH;
import static com.example.makeushifive.src.ApplicationClass.YEAR;

public class AddTimeDialog extends Dialog {

    int startDay;
    private DatePickerDialog.OnDateSetListener startDateListener;
    private DatePickerDialog.OnDateSetListener endDateListener;
    private TimePickerDialog.OnTimeSetListener startTimeListener;
    private TimePickerDialog.OnTimeSetListener endTimeListener;
    public Calendar calendar = Calendar.getInstance();
    private Context context;
    Date pickedDay;
    int setYear,setMonth,setDay;


    public AddTimeDialog(@NonNull Context context,
                         TimePickerDialog.OnTimeSetListener startTimeListener,
                         TimePickerDialog.OnTimeSetListener endTimeListener,Date pickedDay,
                         DatePickerDialog.OnDateSetListener startDateListener,
                         DatePickerDialog.OnDateSetListener endDateListener) {
        super(context);
        this.context=context;
        this.startTimeListener = startTimeListener;
        this.endTimeListener=endTimeListener;
        this.pickedDay=pickedDay;
        this.startDateListener = startDateListener;
        this.endDateListener = endDateListener;
    }

    private TextView mTvLeftBlur,mTvLeftBlack,mTvRightBlur,mTvRightBlack;
    private LinearLayout mLlStartPick,mLlEndPick;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_custom_dialog_time);

        WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount=0.8f;
        layoutParams.height = dpToPx(290,context);
        layoutParams.width = dpToPx(300,context);
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

        DatePicker startDate = findViewById(R.id.date_picker_start_date);
        DatePicker endDate = findViewById(R.id.date_picker_end_date);
        TimePicker startTime = findViewById(R.id.time_picker_start_time);
        TimePicker endTime = findViewById(R.id.time_picker_end_time);

        Objects.requireNonNull(startDate).setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
//        startDate.setOutlineSpotShadowColor(Color.parseColor("#F87BAB"));


        endDate.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        startTime.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        endTime.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        setYear=Integer.parseInt(YEAR.format(pickedDay));
        setMonth=Integer.parseInt(MONTH.format(pickedDay));
        setDay = Integer.parseInt(DAY.format(pickedDay));

        final int[] pickedStartYear = new int[1];
        pickedStartYear[0]=setYear;
        final int[] pickedStartMonth = new int[1];
        pickedStartMonth[0]=setMonth;
        final int[] pickedStartDay = new int[1];
        pickedStartDay[0]=setDay;
        final int[] pickedEndYear = new int[1];
        pickedEndYear[0]=-1;
        final int[] pickedEndMonth = new int[1];
        pickedEndMonth[0]=-1;
        final int[] pickedEndDay = new int[1];
        pickedEndDay[0]=-1;

        startDate.init(setYear, setMonth-1, setDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = year + "/" + monthOfYear + "/" + dayOfMonth;
                Log.e("startdate year",""+date);
                pickedStartYear[0] =year;
                pickedStartMonth[0] =monthOfYear;
                pickedStartDay[0] =dayOfMonth;

            }
        });

        endDate.init(setYear, setMonth-1, setDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = year + "/" + monthOfYear + "/" + dayOfMonth;
                Log.e("enddate year",""+date);
                pickedEndYear[0] =year;
                pickedEndMonth[0] =monthOfYear;
                pickedEndDay[0] =dayOfMonth;

            }
        });


        startDate.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        endDate.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

//        startDate.setIndicatorColor(Color.parseColor("#FF0000"));
//        startDate.setOutlineSpotShadowColor(Color.parseColor("#F87BAB"));



        btnComplete.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Log.e("시작date",""+startDate.getYear()+" "+startDate.getMonth()+" "+startDate.getDayOfMonth());
                Log.e("종료date",""+endDate.getMonth()+" "+endDate.getMonth()+" "+endDate.getDayOfMonth());
                Log.e("시작time",""+startTime.getHour()+" "+startTime.getMinute());
                Log.e("종료time",""+endTime.getHour()+" "+endTime.getMinute());

                startDateListener.onDateSet(null,startDate.getYear(),startDate.getMonth()+1,startDate.getDayOfMonth());
                endDateListener.onDateSet(null,endDate.getYear(),endDate.getMonth()+1,endDate.getDayOfMonth());
                endTimeListener.onTimeSet(null,endTime.getHour(),endTime.getMinute());
                startTimeListener.onTimeSet(null,startTime.getHour(),startTime.getMinute());

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

//        startHour.setMinValue(0);
//        startHour.setMaxValue(23);
//
//        endHour.setMinValue(0);
//        endHour.setMaxValue(23);
//
//        startMin.setMinValue(0);
//        startMin.setMaxValue(59);
//
//        endMin.setMinValue(0);
//        endMin.setMaxValue(59);

        mTvLeftBlur.setText("일정시작");
        mTvLeftBlack.setText("일정시작");

        mTvRightBlur.setText("일정종료");
        mTvRightBlack.setText("일정종료");


//        startHour.setValue(12);
//        startMin.setValue(0);
//
//        endHour.setValue(12);
//        endMin.setValue(0);


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

    public Date MakeDateForm(int year,int month,int day) throws ParseException {
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
