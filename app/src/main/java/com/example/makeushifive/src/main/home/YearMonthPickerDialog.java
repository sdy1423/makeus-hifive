package com.example.makeushifive.src.main.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.makeushifive.R;

import java.util.Calendar;

public class YearMonthPickerDialog extends DialogFragment {

    private static final int MAX_YEAR =2099;
    private static final int MIN_YEAR = 1980;

    private DatePickerDialog.OnDateSetListener listener;
    public Calendar calendar = Calendar.getInstance();

    public void setListener(DatePickerDialog.OnDateSetListener listener){
        this.listener = listener;
    }

    Button btnConfirm;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialog =inflater.inflate(R.layout.year_month_picker,null);

        btnConfirm=dialog.findViewById(R.id.home_year_month_btn);

        final NumberPicker yearPicker = (NumberPicker)dialog.findViewById(R.id.home_year_picker);
        final NumberPicker monthPicker =(NumberPicker)dialog.findViewById(R.id.home_month_picker);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDateSet(null,yearPicker.getValue(),monthPicker.getValue(),0);
                YearMonthPickerDialog.this.getDialog().cancel();
            }
        });
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(calendar.get(Calendar.MONTH)+1);
        int year = calendar.get(Calendar.YEAR);
        yearPicker.setMinValue(MIN_YEAR);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(year);
        builder.setView(dialog);
        return builder.create();
//        return super.onCreateDialog(savedInstanceState);
    }
}
