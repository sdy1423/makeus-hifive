package com.example.makeushifive.src.main.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.makeushifive.R;

import java.util.Calendar;
import java.util.Objects;

public class YearMonthPickerDialog extends DialogFragment {

    private static final int MAX_YEAR =2099;
    private static final int MIN_YEAR = 1980;

    private DatePickerDialog.OnDateSetListener listener;
    public Calendar calendar = Calendar.getInstance();

    public void setListener(DatePickerDialog.OnDateSetListener listener){
        this.listener = listener;
    }
    ImageView mIvClose;
    Button btnConfirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getDialog().getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View rootview = inflater.inflate(R.layout.year_month_picker,container,false);

        mIvClose=rootview.findViewById(R.id.home_year_month_close);
        btnConfirm=rootview.findViewById(R.id.home_year_month_btn);

        final NumberPicker yearPicker = (NumberPicker)rootview.findViewById(R.id.home_year_picker);
        final NumberPicker monthPicker =(NumberPicker)rootview.findViewById(R.id.home_month_picker);
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDateSet(null,yearPicker.getValue(),monthPicker.getValue(),0);
                YearMonthPickerDialog.this.getDialog().cancel();
            }
        });
        int this_month = calendar.get(Calendar.MONTH);

        monthPicker.setMinValue(this_month+1);
        monthPicker.setMaxValue(this_month+4);
        monthPicker.setValue(calendar.get(Calendar.MONTH)+1);
        int this_year = calendar.get(Calendar.YEAR);

        //일단 올해로 고정
        yearPicker.setMinValue(this_year);
        yearPicker.setMaxValue(this_year);
        yearPicker.setValue(this_year);




        return rootview;
    }
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View dialog =inflater.inflate(R.layout.year_month_picker,null);
//
////        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
////        Objects.requireNonNull(getDialog().getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//
//
//        btnConfirm=dialog.findViewById(R.id.home_year_month_btn);
//
//        final NumberPicker yearPicker = (NumberPicker)dialog.findViewById(R.id.home_year_picker);
//        final NumberPicker monthPicker =(NumberPicker)dialog.findViewById(R.id.home_month_picker);
//
//        btnConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onDateSet(null,yearPicker.getValue(),monthPicker.getValue(),0);
//                YearMonthPickerDialog.this.getDialog().cancel();
//            }
//        });
//        monthPicker.setMinValue(1);
//        monthPicker.setMaxValue(12);
//        monthPicker.setValue(calendar.get(Calendar.MONTH)+1);
//        int year = calendar.get(Calendar.YEAR);
//        yearPicker.setMinValue(MIN_YEAR);
//        yearPicker.setMaxValue(MAX_YEAR);
//        yearPicker.setValue(year);
//        builder.setView(dialog);
//        return builder.create();
////        return super.onCreateDialog(savedInstanceState);
//    }

    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.year_month_pick_dialog_width);
        int height = getResources().getDimensionPixelSize(R.dimen.year_month_pick_dialog_height);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setLayout(width,height);
    }

}
