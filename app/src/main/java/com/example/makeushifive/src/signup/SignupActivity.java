
package com.example.makeushifive.src.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseActivity;
import com.example.makeushifive.src.signup.interfaces.SignupActivityView;

import org.json.JSONException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.makeushifive.R.id.sign_up_edt_email;

public class SignupActivity extends BaseActivity implements SignupActivityView {

    EditText mEdtEmail,mEdtUserName,mEdtPwd,mEdtPwdCheck;
    TextView mTvPossible1,mTvPossible2,mTvPwdCheckText;
    Button mBtnJoin;
    String mPwd="",mEmail="",mUserName="";
    Drawable img1,img2;
    SignupService mSignupService;
    boolean EmailFlag=false,UserNameFlag=false,PasswordFlag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mEdtEmail=findViewById(R.id.sign_up_edt_email);
        mEdtUserName=findViewById(R.id.sign_up_edt_user_name);
        mEdtPwd=findViewById(R.id.sign_up_edt_pwd);
        mEdtPwdCheck=findViewById(R.id.sign_up_edt_pwd_check);
        mBtnJoin=findViewById(R.id.sign_up_btn_join);
        img1 = getApplicationContext().getResources().getDrawable(R.drawable.ic_check_circle_outline_24px);
        img2 = getApplicationContext().getResources().getDrawable(R.drawable.ic_cancel_24px);
        img1.setBounds( 0, 0, 60, 60 );
        img2.setBounds( 0, 0, 60, 60 );

        mTvPossible1=findViewById(R.id.sign_up_tv_possible1);
        mTvPossible2=findViewById(R.id.sign_up_tv_possible2);
        mTvPwdCheckText=findViewById(R.id.sign_up_tv_pwd_check_text);
        mTvPwdCheckText.setVisibility(View.INVISIBLE);

        mSignupService = new SignupService(this);

        mEdtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                }else{
                    mEmail = mEdtEmail.getText().toString();
                    if(!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
                        Log.e("매치안함", " " + mEmail);

//                    ShowImPossibleEmail("다른 이메일을 사용해주세요.");
                        EmailFlag=false;
                    }else{
                        Log.e("매치완료", " " + mEmail);
                        //이메일 중복체크
                        PostOverLapEmail(mEmail);
                    }
                }
            }
        });

        mEdtUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                }else{
                    mUserName=mEdtUserName.getText().toString();
                    Log.e("유저네임",""+mUserName);
                    if(mUserName.equals("")){
//                        ShowImPossibleUserName("다른 유저 네임을 사용해주세요.");
                        UserNameFlag=false;
                    }
                    else{
                        PostOverLapUserName(mUserName);

                    }
                }
            }
        });

        mEdtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPwd = mEdtPwd.getText().toString();
//                Log.e("입력완료"," "+mPwd);
            }
        });

        mEdtPwdCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals(mPwd)){
//                    Log.e("equal","equal");
//                    Log.e(" equal mPwd",""+mPwd);
//                    Log.e(" equal s",s.toString());
                    mEdtPwdCheck.setCompoundDrawables(null,null,img1,null);
                    mTvPwdCheckText.setVisibility(View.INVISIBLE);

                    PasswordFlag=true;
                }else{
//                    Log.e("not equal","not equal");
//                    Log.e("not equal mPwd",""+mPwd);
//                    Log.e("not equal s",s.toString());

                    mEdtPwdCheck.setCompoundDrawables(null,null,img2,null);
                    mTvPwdCheckText.setVisibility(View.VISIBLE);
                    PasswordFlag=false;
                }
            }
        });

        //가입버튼
        mBtnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //버튼 클릭
                try {
//                    Log.e("emailflag",""+EmailFlag);
//                    Log.e("usernameflag",""+UserNameFlag);
//                    Log.e("passwordflag",""+PasswordFlag);
                    if(EmailFlag && UserNameFlag && PasswordFlag){
//                        Log.e("inin","inin");
                        mSignupService.PostSignUp(mEmail,mPwd,mUserName);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void PostOverLapEmail(String email){
        try {
            mSignupService.PostOverlapEmail(email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void PostOverLapUserName(String nickname){
        SignupService signupService=new SignupService(this);
        try {
            signupService.PostOverlapUserName(nickname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void OverlapUserNameSuccess(int code, String message) {

        if(code==101){//유저네임 가능

            ShowPossibleUserName(message);
            UserNameFlag=true;
        }else if(code==201){ //유저네임 불가능
            UserNameFlag=false;
            ShowImPossibleUserName(message);
        }
    }

    public void ShowPossibleUserName(String message){
        //사용 가능한 유저 네임입니다.
        mEdtUserName.setCompoundDrawables(null,null,img1,null);
        mTvPossible2.setText(message);
        mTvPossible2.setTextColor(Color.parseColor("#13CB37"));
    }
    public void ShowImPossibleUserName(String message){
        //다른 유저 네임을 사용해주세요.
        mEdtUserName.setCompoundDrawables(null,null,img2,null);
        mTvPossible2.setText(message);
        mTvPossible2.setTextColor(Color.parseColor("#FF7979"));


    }

    @Override
    public void OverlapUserNameFail(int code, String message) {
        UserNameFlag=false;

    }


    @Override
    public void OverlapEmailSuccess(int code, String message) {
        if(code==100){//이메일가능
            ShowPossibleEmail(message);
            EmailFlag=true;
        }else if(code==200){ //이메일 불가능
            ShowImPossibleEmail(message);
            EmailFlag=false;
        }
    }
    public void ShowPossibleEmail(String message){
        //사용 가능한 이메일입니다.
        mEdtEmail.setCompoundDrawables(null,null,img1,null);
        mTvPossible1.setText(message);
        mTvPossible1.setTextColor(Color.parseColor("#13CB37"));
    }
    public void ShowImPossibleEmail(String message){
        //다른 이메일을 사용해주세요.
        mEdtEmail.setCompoundDrawables(null,null,img2,null);
        mTvPossible1.setText(message);
        mTvPossible1.setTextColor(Color.parseColor("#FF7979"));
    }

    @Override
    public void OverlapEmailFail(int code, String message) {
        EmailFlag=false;
    }

    @Override
    public void SignUpSuccess(int code) {
        if(code==100){
            //성공
            showCustomToast("회원가입에 성공했습니다.");
            onBackPressed();
        }else if(code==200){
            //이미 등록된 이메일
            ShowImPossibleEmail("다른 이메일을 사용해주세요.");
        }else if(code==201){
            //다른 유저네임
            ShowImPossibleUserName("다른 유저 네임을 사용해주세요.");

        }else{
            //실패
        }
    }

    @Override
    public void SignUpFail() {
        //회원가입 실패
    }
}
