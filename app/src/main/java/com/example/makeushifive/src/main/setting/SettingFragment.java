package com.example.makeushifive.src.main.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseFragment;
import com.example.makeushifive.src.main.setting.change.ChangeActivity;
import com.example.makeushifive.src.main.setting.interfaces.SettingFragmentView;
import com.example.makeushifive.src.main.setting.models.SettingResponse;
import com.example.makeushifive.src.splash.SplashActivity;

import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.example.makeushifive.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.example.makeushifive.src.ApplicationClass.sSharedPreferences;

public class SettingFragment extends BaseFragment implements SettingFragmentView {

    ImageView mIvProfileImg;
    TextView mTvUserName,mTvChangeUserInfo,mTvShareFriendSetting,mTvLogOut;
    int UserNo;
    private String ProfileUrl, NickName, Email;
    private final int GET_GALLERY_IMAGE = 200;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        mIvProfileImg = (rootView).findViewById(R.id.setting_iv_profile_img);
        mTvUserName = (rootView).findViewById(R.id.setting_tv_username);

        mIvProfileImg.setBackground(new ShapeDrawable(new OvalShape()));
        mIvProfileImg.setClipToOutline(true);

        UserNo=sSharedPreferences.getInt("userNo",0);

        SettingService settingService = new SettingService(this);
        settingService.getUserInfoDetail(UserNo);

        mTvLogOut=rootView.findViewById(R.id.setting_tv_log_out);
        mTvChangeUserInfo=rootView.findViewById(R.id.setting_tv_change_user_info);
        mTvShareFriendSetting=rootView.findViewById(R.id.setting_tv_share_friend);

        mTvShareFriendSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomToast("미 구현 기능입니다.");
            }
        });

        mTvChangeUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ChangeActivity.class);
                intent.putExtra("email",Email);
                Log.e("이멜",Email);
                intent.putExtra("profileUrl",ProfileUrl);
                startActivity(intent);
            }
        });

        mTvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sSharedPreferences.edit();
                editor.remove(X_ACCESS_TOKEN);
                editor.remove("userNo");
                editor.remove("profileUrl");
                editor.remove("nickname");
                editor.apply();
                Intent intent = new Intent(getActivity(), SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        mIvProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);

                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                Objects.requireNonNull(getActivity()).startActivityForResult(intent, GET_GALLERY_IMAGE);
                //TODO MainActivity로 가서 이미지 처리하라.
            }
        });

        return rootView;
    }

    @Override
    public void getUserInfoDetailSuccess(ArrayList<SettingResponse.Result> result) {
        ProfileUrl = result.get(0).getProfileUrl();
        NickName = result.get(0).getNickname();
        Email = result.get(0).getEmail();

        Glide.with(this)
                .load(ProfileUrl)
                .centerCrop()
                .into(mIvProfileImg);
        mTvUserName.setText(NickName);


    }

    @Override
    public void getUserInfoDetailFail() {

    }
}