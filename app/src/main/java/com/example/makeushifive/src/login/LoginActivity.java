package com.example.makeushifive.src.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseActivity;
import com.example.makeushifive.src.login.interfaces.LoginActivityView;
import com.example.makeushifive.src.login.models.LoginResponse;
import com.example.makeushifive.src.main.MainActivity;
import com.example.makeushifive.src.signup.SignupActivity;

import org.json.JSONException;

import java.util.Objects;

import static com.example.makeushifive.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.example.makeushifive.src.ApplicationClass.sSharedPreferences;

public class LoginActivity extends BaseActivity implements LoginActivityView {

    EditText mEdtEmail,mEdtPwd;
    Button mBtnLogIn,mBtnSignUp;
    TextView mTvFeedBack,mTvFindPwd;
    int mUserNo;
    String mJwtToken;
    private FindPwdDialog mFindPwdDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEdtEmail=findViewById(R.id.login_edt_email);
        mEdtPwd=findViewById(R.id.login_edt_pwd);
        mBtnLogIn=findViewById(R.id.login_btn_login);
        mBtnSignUp=findViewById(R.id.login_btn_sign_up);
        mTvFeedBack=findViewById(R.id.login_tv_log_in_feedback);
        mTvFindPwd=findViewById(R.id.login_tv_find_pwd);

        //비밀번호 찾기 밑줄
        SpannableString content = new SpannableString(mTvFindPwd.getText().toString());
        content.setSpan(new UnderlineSpan(),0,content.length(),0);
        mTvFindPwd.setText(content);

        //TODO 로그인
        mBtnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserEmail = mEdtEmail.getText().toString();
                String UserPwd = mEdtPwd.getText().toString();
                //아무것도 입력 안했을 경우
                if(UserEmail.equals("") || UserPwd.equals("")){
                    ShowLoginFail();
                }
                else{
                    //이메일 패턴이 아닐 경우
                    if(!Patterns.EMAIL_ADDRESS.matcher(UserEmail).matches()){
                        ShowLoginFail();
                    }else{
                        TryPostLogin(UserEmail,UserPwd);
                    }
                }
            }
        });

        //TODO 회원가입
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        //TODO 비번찾기
        mTvFindPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogFindPwd();
            }
        });



    }
    public void ShowDialogFindPwd(){
        mFindPwdDialog = new FindPwdDialog(this,transferListener,cancelListener);
        WindowManager.LayoutParams layoutParams =   Objects.requireNonNull(mFindPwdDialog.getWindow()).getAttributes();
//        Window window = mFindPwdDialog.getWindow();
//        assert window != null;
//        window.setBackgroundDrawable(R.drawable.login_custom_dialog_border); //배경

//        layoutParams.screenBrightness= WindowManager.LayoutParams.DIM_AMOUNT_CHANGED;
//        layoutParams.width= WindowManager.LayoutParams.WRAP_CONTENT; //폭
//        layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT; //높이
        layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND; //배경 흐리게

        mFindPwdDialog.show();
    }

    public void TryPostLogin(String UserEmail,String UserPwd){
        LogInService logInService = new LogInService(this); //오류 뜨면 onCreate밖으로 보낼것.
        try {
            logInService.postLogin(UserEmail,UserPwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    //로그인 성공
    @Override
    public void LogInSuccess(LoginResponse.Result result) {
        mJwtToken = result.getJwt();
        mUserNo = result.getUserNo();
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putString(X_ACCESS_TOKEN, mJwtToken);
        editor.putInt("userNo",mUserNo);
        editor.apply();

        Log.e("jwt",""+mJwtToken);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish(); //로딩페이지를 액티비티 스택에서 제거거

    }

    //로그인 실패
    @Override
    public void LoginFail() {
        ShowLoginFail();

    }


    public void ShowLoginFail(){
        mTvFeedBack.setText("이메일 또는 비밀번호가 틀렸습니다.");
    }








    private View.OnClickListener transferListener = new View.OnClickListener() {
        public void onClick(View v) {
            //전송버튼누를때
            EditText edtEmail;
            edtEmail=findViewById(R.id.login_custom_dialog_edt_email);
            String UserEmail = edtEmail.getText().toString();

            if(UserEmail.equals("")){
                ShowFindPwdFail();
            }
            else{
                //이메일 패턴이 아닐 경우
                if(!Patterns.EMAIL_ADDRESS.matcher(UserEmail).matches()){
                    ShowFindPwdFail();
                }else{
                    //todo API 받아오기

                }
            }



            mFindPwdDialog.dismiss();
        }
    };

    public void ShowFindPwdFail(){
        mTvFeedBack.setText("해당 이메일로 가입된 계정이 없습니다.");
    }

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        public void onClick(View v) {
            //취소버튼
            mFindPwdDialog.dismiss();
        }
    };

    @Override
    public void FindPwdSuccess() {
        //성공할경우
        //TODO 12번 1초정도 띄우고 사라지게
    }

    @Override
    public void FindPwdFail() {
        //실패할경우
        ShowFindPwdFail();
    }

}


