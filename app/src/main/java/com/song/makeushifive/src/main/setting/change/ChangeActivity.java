package com.song.makeushifive.src.main.setting.change;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.song.makeushifive.R;
import com.song.makeushifive.src.BaseActivity;
import com.song.makeushifive.src.main.setting.change.interfaces.ChangeActivityView;
import com.song.makeushifive.src.splash.SplashActivity;

import org.json.JSONException;

import java.util.Objects;

import static com.song.makeushifive.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.song.makeushifive.src.ApplicationClass.sSharedPreferences;

public class ChangeActivity extends BaseActivity implements ChangeActivityView {

    String ProfileUrl, Email, mNewUserName, mNewPwd;
    ImageView mIvProfile;
    TextView mTvEmail, mTvSave, mTvUserNameCheck, mTvPwdCheck, mTvChangeImg;
    EditText mEdtUserName, mEdtPwd, mEdtPwdAgain;
    boolean UserNameFlag = false, PwdFlag = false;
    Drawable img1, img2;
    private final int GET_GALLERY_IMAGE = 200;
    Button mBtnDelete;

    Bundle mBundle;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            mIvProfile.setImageURI(selectedImageUri);
//            ProfileUrl=selectedImageUri.toString();
            Log.e("바뀐 프로필 URI",""+selectedImageUri);
            ProfileUrl = getRealPathFromURI(selectedImageUri);
            Log.e("바뀐 프로필 URL",""+ProfileUrl);
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBundle = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        mBtnDelete = findViewById(R.id.change_btn_bye);
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
        //TODO 프사 변경은 밖으로 빼라
//        mTvChangeImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO 프로필 사진 변경 dialog ㄱㄱ
//
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                startActivityForResult(intent, GET_GALLERY_IMAGE);
//            }
//        });

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
                if (!hasFocus) {
                    mNewUserName = mEdtUserName.getText().toString();
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

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 탈퇴하시겠습니까?
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
            changeService.patchUserInfoChange(mNewPwd, mNewUserName);
        }
    }

    @Override
    public void patchUserInfoChangeSuccess(int code) {
        if (code == 100) {
            //회원 정보 수정 성공(통신 성공)
            //todo sharedpreference에 있는 내용 지운다.
            SharedPreferences.Editor editor = sSharedPreferences.edit();
            editor.remove(X_ACCESS_TOKEN);
            editor.remove("profileUrl");
            editor.remove("nickname");
            editor.apply();
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        } else if (code == 200) {
            //todo 유효하지 않은 토큰입니다.
            showCustomToast("미구현 기능입니다.");
        } else if (code == 201) {
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

    @Override
    public void postImageSuccess() {

    }

    @Override
    public void postImageFail() {

    }

    @Override
    public void getUserSuccess() {
        Log.e("onCreate","ChangeActivity");
        onCreate(mBundle);
    }

    @Override
    public void getUserFail() {

    }

    @Override
    public void deleteUserSuccess() {
        //TODO 탈퇴성공하면? 스플래시로 가야될듯
        //todo sharedpreference에 있는 내용 지운다.
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.remove(X_ACCESS_TOKEN);
        editor.remove("profileUrl");
        editor.remove("nickname");
        editor.apply();
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
    }

    @Override
    public void deleteUserFail() {

    }


    @Override
    protected void onResume() {
        String nickname = sSharedPreferences.getString("nickname",null);
        Log.e("onResume","ChangeActivity"+nickname);
        ChangeService changeService = new ChangeService(this);
        changeService.getUser(nickname);
        super.onResume();
    }
}
