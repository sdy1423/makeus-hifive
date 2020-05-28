package com.example.makeushifive.src.main.setting.change;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseActivity;
import com.example.makeushifive.src.main.setting.change.interfaces.ChangeActivityView;
import com.example.makeushifive.src.splash.SplashActivity;

import org.json.JSONException;

import java.io.File;
import java.util.Objects;

import static com.example.makeushifive.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.example.makeushifive.src.ApplicationClass.sSharedPreferences;

public class ChangeActivity extends BaseActivity implements ChangeActivityView {

    String ProfileUrl, Email, mNewUserName, mNewPwd;
    ImageView mIvProfile;
    TextView mTvEmail, mTvSave, mTvUserNameCheck, mTvPwdCheck, mTvChangeImg;
    EditText mEdtUserName, mEdtPwd, mEdtPwdAgain;
    boolean UserNameFlag = false, PwdFlag = false;
    Drawable img1, img2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        mIvProfile = findViewById(R.id.change_iv_profile_img);
        mTvEmail = findViewById(R.id.change_tv_email);
        mTvSave = findViewById(R.id.change_tv_save);//저장버튼
        mEdtUserName = findViewById(R.id.change_edt_username);
        mEdtPwd = findViewById(R.id.change_edt_pwd);
        mEdtPwdAgain = findViewById(R.id.change_edt_pwd_check);
        mTvUserNameCheck = findViewById(R.id.change_tv_username_check_message);
        mTvUserNameCheck.setVisibility(View.INVISIBLE);
        mTvPwdCheck = findViewById(R.id.change_tv_pwd_check_message);
        mTvPwdCheck.setVisibility(View.INVISIBLE);
        mTvChangeImg = findViewById(R.id.change_tv_change_profile_img);

        //비밀번호 찾기 밑줄
        SpannableString content = new SpannableString(mTvChangeImg.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        mTvChangeImg.setText(content);
        mTvChangeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 프로필 사진 변경 dialog ㄱㄱ
            }
        });

        img1 = getApplicationContext().getResources().getDrawable(R.drawable.ic_check_circle_outline_24px);
        img2 = getApplicationContext().getResources().getDrawable(R.drawable.ic_cancel_24px);
        img1.setBounds(0, 0, 60, 60);
        img2.setBounds(0, 0, 60, 60);

        //TODO Setting Fragment에서 프사, 이메일 받아오기(아직 이메일 안만들어줌)
        Intent intent = getIntent();
        ProfileUrl = Objects.requireNonNull(intent.getExtras()).getString("profileUrl");
        Email = intent.getExtras().getString("email");
        mTvEmail.setText(Email);

        //프사
        Glide.with(this)
                .load(ProfileUrl)
                .centerCrop()
                .into(mIvProfile);

        //rounded imageview
        mIvProfile.setBackground(new ShapeDrawable(new OvalShape()));
        mIvProfile.setClipToOutline(true);


        //TODO 사용가능한 유저네임인지, 패스워드 체크 맞는지
        mEdtUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mNewUserName = mEdtUserName.getText().toString();
                if (!hasFocus) {
                    if (mNewUserName.equals("")) {
                        ShowImpossibleUserName();

                    } else {
                        //TODO 회원가입 때 사용했던 유저네임 확인 API
                        try {
                            PostOverLapUserName(mNewUserName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        //비밀번호 입력
        mEdtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mNewPwd = mEdtPwd.getText().toString();
            }
        });
        //비밀번호 다시 입력(확인)
        mEdtPwdAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(mNewPwd)) {
                    ShowPwdPossible();
                } else {
                    ShowPwdImPossible();
                }
            }
        });


        mTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 유저네임 맞는지, 비밀번호 맞는지
                try {
                    ChangeUserInfo();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void ShowPwdPossible() {
        //가능한 비밀번호 알려주기
        PwdFlag = true;
        mTvPwdCheck.setVisibility(View.INVISIBLE);
        mEdtPwdAgain.setCompoundDrawables(null, null, img1, null);
    }

    public void ShowPwdImPossible() {
        //불가능한 비밀번호 알려주기
        PwdFlag = false;
        mTvPwdCheck.setVisibility(View.VISIBLE);
        mEdtPwdAgain.setCompoundDrawables(null, null, img2, null);
    }

    public void ShowPossibleUserName() {
        //가능한 유저네임 알려주기
        mTvUserNameCheck.setVisibility(View.INVISIBLE);
        UserNameFlag = true;
        mEdtUserName.setCompoundDrawables(null, null, img1, null);
    }

    public void ShowImpossibleUserName() {
        //불가능한 유저네임 알려주기
        mTvUserNameCheck.setVisibility(View.VISIBLE);
        UserNameFlag = false;
        mEdtUserName.setCompoundDrawables(null, null, img2, null);
    }

    public void ChangeUserInfo() throws JSONException {
        //TODO FLAG 조건문 안에 넣기
        //회원정보 수정 API
        if (PwdFlag && UserNameFlag) {
            ChangeService changeService = new ChangeService(this);
            changeService.patchUserInfoChange(Email, mNewPwd, ProfileUrl, mNewUserName);
        }
    }

    @Override
    public void patchUserInfoChangeSuccess(int code) {
        if (code == 100 || code == 201) {
            //회원 정보 수정 성공(통신 성공)
            //todo sharedpreference에 있는 내용 지운다.
            SharedPreferences.Editor editor = sSharedPreferences.edit();
            editor.remove(X_ACCESS_TOKEN);
            editor.remove("userNo");
            editor.apply();
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        } else if (code == 200) {
            //todo 유효하지 않은 토큰입니다.
            showCustomToast("미구현 기능입니다.");
        } else if (code == 202) {
            //todo 이미 등록된 유저네임
            ShowImpossibleUserName();
        }
    }

    @Override
    public void patchUserInfoChangeFail() {
        //회원 정보 수정
        showCustomToast("다시 입력해 주세요");
    }

    public void PostOverLapUserName(String username) throws JSONException {
        //유저네임 중복 체크 API
        ChangeService changeService = new ChangeService(this);
        changeService.postOverlapUserName(username);
    }

    @Override
    public void postOverlapUserNameSuccess(int code, String message) {
        //유저네임 중복체크
        if (code == 101) {
            //사용가능한 유저네임
            ShowPossibleUserName();
        } else {
            ShowImpossibleUserName();
        }
    }

    @Override
    public void postOverlapUserNameFail() {
        //유저네임 중복체크

    }



}
