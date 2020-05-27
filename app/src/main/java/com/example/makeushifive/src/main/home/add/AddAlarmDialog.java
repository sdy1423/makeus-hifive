package com.example.makeushifive.src.main.home.add;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.makeushifive.R;

import java.util.Objects;

public class AddAlarmDialog extends Dialog {


    ImageView mIvClose;
    FrameLayout mFlThirtyMin,mFlOneHour,mFlTwoHour,mFlOneDay,mFlOneWeek;
    TextView mTvThrityMinBlur,mTvThrityMinBlack,mTvOneHourBlur,mTvOneHourBlack,mTvTwoHourBlur,mTvTwoHourBlack,mTvOneDayBlur,mTvOneDayBlack,
    mTvOneWeekBlur,mTvOneWeekBlack;
    Button mBtnComplete;
    int Picked=0;
    Context context;

    public interface ICustomDialogEventListener {
        void customDialogEvent(int valueYouWantToSendBackToTheActivity);
    }

    private ICustomDialogEventListener onCustomDialogEventListener;




    public AddAlarmDialog(@NonNull Context context,ICustomDialogEventListener iCustomDialogEventListener) {
        super(context);
        this.onCustomDialogEventListener=iCustomDialogEventListener;
        this.context=context;
    }

    public static int dpToPx(int dp,Context context) {
        float density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_custom_dialog_alarm);



        mIvClose=findViewById(R.id.add_dialog_alarm_iv_close);
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mFlThirtyMin=findViewById(R.id.add_dialog_fl_30min);
        mFlOneHour=findViewById(R.id.add_dialog_fl_1hour);
        mFlTwoHour=findViewById(R.id.add_dialog_fl_2hour);
        mFlOneDay=findViewById(R.id.add_dialog_fl_1_day);
        mFlOneWeek=findViewById(R.id.add_dialog_fl_1_week);

        mTvThrityMinBlur=findViewById(R.id.add_dialog_alarm_tv_30min_blur);
        mTvThrityMinBlack=findViewById(R.id.add_dialog_alarm_tv_30min_black);
        mTvOneHourBlur=findViewById(R.id.add_dialog_alarm_tv_1hour_blur);
        mTvOneHourBlack=findViewById(R.id.add_dialog_alarm_tv_1hour_black);
        mTvTwoHourBlur=findViewById(R.id.add_dialog_alarm_tv_2hour_blur);
        mTvTwoHourBlack=findViewById(R.id.add_dialog_alarm_tv_2hour_black);
        mTvOneDayBlur=findViewById(R.id.add_dialog_alarm_tv_1day_blur);
        mTvOneDayBlack=findViewById(R.id.add_dialog_alarm_tv_1day_black);
        mTvOneWeekBlur=findViewById(R.id.add_dialog_alarm_tv_1week_blur);
        mTvOneWeekBlack=findViewById(R.id.add_dialog_alarm_tv_1week_black);

        mBtnComplete=findViewById(R.id.add_dialog_alarm_btn_complete);

        mTvThrityMinBlack.setVisibility(View.INVISIBLE);
        mTvThrityMinBlur.setVisibility(View.VISIBLE);

        mTvOneHourBlack.setVisibility(View.INVISIBLE);
        mTvOneHourBlur.setVisibility(View.VISIBLE);

        mTvTwoHourBlack.setVisibility(View.INVISIBLE);
        mTvTwoHourBlur.setVisibility(View.VISIBLE);

        mTvOneDayBlack.setVisibility(View.INVISIBLE);
        mTvOneDayBlur.setVisibility(View.VISIBLE);

        mTvOneWeekBlack.setVisibility(View.INVISIBLE);
        mTvOneWeekBlur.setVisibility(View.VISIBLE);


        mFlThirtyMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickThirtyMin();
            }


        });
        mFlOneHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickOneHour();
            }
        });
        mFlTwoHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickTwoHour();
            }
        });
        mFlOneDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickOneDay();
            }
        });
        mFlOneWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickOneWeek();
            }
        });

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags=WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount=0.8f;
        layoutParams.height = dpToPx(320,context);
        layoutParams.width = dpToPx(220,context);

        Objects.requireNonNull(getWindow()).setAttributes(layoutParams);



        mBtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Picked != 0){
                    onCustomDialogEventListener.customDialogEvent(Picked);
                }
                dismiss();
            }
        });
    }
    private void PickThirtyMin(){
        mTvThrityMinBlack.setVisibility(View.VISIBLE);
        mTvThrityMinBlur.setVisibility(View.INVISIBLE);

        mTvOneHourBlack.setVisibility(View.INVISIBLE);
        mTvOneHourBlur.setVisibility(View.VISIBLE);

        mTvTwoHourBlack.setVisibility(View.INVISIBLE);
        mTvTwoHourBlur.setVisibility(View.VISIBLE);

        mTvOneDayBlack.setVisibility(View.INVISIBLE);
        mTvOneDayBlur.setVisibility(View.VISIBLE);

        mTvOneWeekBlack.setVisibility(View.INVISIBLE);
        mTvOneWeekBlur.setVisibility(View.VISIBLE);
        Picked=1;
    }
    private void PickOneHour(){
        mTvThrityMinBlack.setVisibility(View.INVISIBLE);
        mTvThrityMinBlur.setVisibility(View.VISIBLE);

        mTvOneHourBlack.setVisibility(View.VISIBLE);
        mTvOneHourBlur.setVisibility(View.INVISIBLE);

        mTvTwoHourBlack.setVisibility(View.INVISIBLE);
        mTvTwoHourBlur.setVisibility(View.VISIBLE);

        mTvOneDayBlack.setVisibility(View.INVISIBLE);
        mTvOneDayBlur.setVisibility(View.VISIBLE);

        mTvOneWeekBlack.setVisibility(View.INVISIBLE);
        mTvOneWeekBlur.setVisibility(View.VISIBLE);
        Picked=2;
    }
    private void PickTwoHour(){
        mTvThrityMinBlack.setVisibility(View.INVISIBLE);
        mTvThrityMinBlur.setVisibility(View.VISIBLE);

        mTvOneHourBlack.setVisibility(View.INVISIBLE);
        mTvOneHourBlur.setVisibility(View.VISIBLE);

        mTvTwoHourBlack.setVisibility(View.VISIBLE);
        mTvTwoHourBlur.setVisibility(View.INVISIBLE);

        mTvOneDayBlack.setVisibility(View.INVISIBLE);
        mTvOneDayBlur.setVisibility(View.VISIBLE);

        mTvOneWeekBlack.setVisibility(View.INVISIBLE);
        mTvOneWeekBlur.setVisibility(View.VISIBLE);
        Picked=3;
    }
    private void PickOneDay(){
        mTvThrityMinBlack.setVisibility(View.INVISIBLE);
        mTvThrityMinBlur.setVisibility(View.VISIBLE);

        mTvOneHourBlack.setVisibility(View.INVISIBLE);
        mTvOneHourBlur.setVisibility(View.VISIBLE);

        mTvTwoHourBlack.setVisibility(View.INVISIBLE);
        mTvTwoHourBlur.setVisibility(View.VISIBLE);

        mTvOneDayBlack.setVisibility(View.VISIBLE);
        mTvOneDayBlur.setVisibility(View.INVISIBLE);

        mTvOneWeekBlack.setVisibility(View.INVISIBLE);
        mTvOneWeekBlur.setVisibility(View.VISIBLE);
        Picked=4;
    }
    private void PickOneWeek(){
        mTvThrityMinBlack.setVisibility(View.INVISIBLE);
        mTvThrityMinBlur.setVisibility(View.VISIBLE);

        mTvOneHourBlack.setVisibility(View.INVISIBLE);
        mTvOneHourBlur.setVisibility(View.VISIBLE);

        mTvTwoHourBlack.setVisibility(View.INVISIBLE);
        mTvTwoHourBlur.setVisibility(View.VISIBLE);

        mTvOneDayBlack.setVisibility(View.INVISIBLE);
        mTvOneDayBlur.setVisibility(View.VISIBLE);

        mTvOneWeekBlack.setVisibility(View.VISIBLE);
        mTvOneWeekBlur.setVisibility(View.INVISIBLE);
        Picked=5;
    }


}
