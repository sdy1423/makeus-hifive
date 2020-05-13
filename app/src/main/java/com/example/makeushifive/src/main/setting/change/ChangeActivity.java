package com.example.makeushifive.src.main.setting.change;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseActivity;
import com.example.makeushifive.src.main.setting.change.interfaces.ChangeActivityView;

public class ChangeActivity extends BaseActivity implements ChangeActivityView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);


        //TODO Setting Fragment에서 프사, 이메일 받아오기

    }
}
