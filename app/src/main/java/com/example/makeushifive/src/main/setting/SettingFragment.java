package com.example.makeushifive.src.main.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseFragment;
import com.example.makeushifive.src.main.setting.change.ChangeActivity;
import com.example.makeushifive.src.main.setting.interfaces.SettingFragmentView;
import com.example.makeushifive.src.main.setting.models.SettingResponse;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.example.makeushifive.src.ApplicationClass.X_ACCESS_TOKEN;

public class SettingFragment extends BaseFragment implements SettingFragmentView {

    ImageView mIvProfileImg;
    TextView mTvUserName;
    int UserNo;
    private String ProfileUrl, NickName, Email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        mIvProfileImg = (rootView).findViewById(R.id.setting_iv_profile_img);
        mTvUserName = (rootView).findViewById(R.id.setting_tv_username);

        //TODO API로 엮어서 프로필 이미지 ㄱㄱ
        mIvProfileImg.setBackground(new ShapeDrawable(new OvalShape()));
        mIvProfileImg.setClipToOutline(true);

        //TODO 유저 상세 조회
        SharedPreferences prefs = Objects.requireNonNull(getContext()).getSharedPreferences(X_ACCESS_TOKEN, MODE_PRIVATE);
        UserNo = prefs.getInt("userNo", 0); //키값, 디폴트값
        SettingService settingService = new SettingService(this);
        settingService.getUserInfoDetail(UserNo);


        return rootView;
    }

    @Override
    public void getUserInfoDetailSuccess(ArrayList<SettingResponse.Result> result) {
        ProfileUrl = result.get(0).getProfileUrl();
        NickName = result.get(0).getNickname();
        Email = result.get(0).getEmail();

        //TODO URL 띄우기
//        mIvProfileImg.setImageResource();
        mTvUserName.setText(NickName);
    }

    @Override
    public void getUserInfoDetailFail() {

    }

    public void SettingClick(View view) {
        switch (view.getId()){
            case R.id.setting_tv_change_user_info:
                Objects.requireNonNull(getActivity()).getIntent().putExtra("email",Email);
                getActivity().getIntent().putExtra("profileUrl",ProfileUrl);
                Intent intent1 = new Intent(getActivity(), ChangeActivity.class);
                startActivity(intent1);
                break;
            case R.id.setting_tv_share_friend:

                break;
            case R.id.setting_tv_theme_setting:

                break;
        }
    }
}