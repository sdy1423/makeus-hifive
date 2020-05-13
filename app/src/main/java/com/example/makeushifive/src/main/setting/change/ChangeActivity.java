package com.example.makeushifive.src.main.setting.change;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseActivity;
import com.example.makeushifive.src.main.setting.change.interfaces.ChangeActivityView;

import java.util.Objects;

public class ChangeActivity extends BaseActivity implements ChangeActivityView {

    String ProfileUrl,Email;
    ImageView mTvProfile;
    TextView mTvEmail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);


        //TODO Setting Fragment에서 프사, 이메일 받아오기
        Intent intent=getIntent();
        ProfileUrl= Objects.requireNonNull(intent.getExtras()).getString("profileUrl");
        Email=intent.getExtras().getString("email");

        mTvProfile=findViewById(R.id.change_iv_profile_img);
        mTvEmail =findViewById(R.id.change_tv_email);

    }
}
