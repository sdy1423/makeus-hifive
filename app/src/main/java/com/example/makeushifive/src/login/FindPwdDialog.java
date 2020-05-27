package com.example.makeushifive.src.login;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.makeushifive.R;
import com.example.makeushifive.src.login.interfaces.LoginActivityView;
import com.example.makeushifive.src.login.models.LoginResponse;

import org.json.JSONException;

import java.util.Objects;

public class FindPwdDialog extends Dialog implements LoginActivityView {
    private ImageView mIvClose;
    private Button mBtnTransfer;
    private EditText mEdtEmail;
    private TextView mTvMessage;
    Context context;
    String Email;
    boolean EmailFlag = false;

    public FindPwdDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_custom_dialog_find_pwd);

        WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount=0.8f;
        layoutParams.height = dpToPx(350,context);
        layoutParams.width = dpToPx(271,context);
        Objects.requireNonNull(getWindow()).setAttributes(layoutParams);

        mTvMessage=findViewById(R.id.find_pwd_tv_message);
        mTvMessage.setVisibility(View.INVISIBLE);
        mIvClose=findViewById(R.id.login_custom_dialog_iv_close);
        mBtnTransfer=findViewById(R.id.login_custom_dialog_btn_transfer);
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mEdtEmail=findViewById(R.id.login_custom_dialog_edt_email);

        mBtnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EmailFlag){
                    //TODO API로 보낸다.
                    try {
                        PostOverLap();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.e("아직안됨","");
                }
            }
        });

        mEdtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Email = mEdtEmail.getText().toString();
                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    Log.e("(이멜형식아님)", " " + Email+" "+EmailFlag);
                    EmailFlag=false;
                }else{
                    Log.e("(이멜형식맞음)", " " + Email+" "+EmailFlag);
                    EmailFlag=true;
                }
            }
        });
    }
    public void PostOverLap() throws JSONException {
        LogInService logInService = new LogInService(this);
        logInService.postOverlapEmail(Email);
    }

    public int dpToPx(int dp,Context context) {
        float density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }


    @Override
    public void LogInSuccess(LoginResponse.Result result) {
        //안쓴다.
    }

    @Override
    public void LoginFail() {
        //안쓴다.
    }

    @Override
    public void postOverlapSuccess() {
        //TODO dismiss하고 CustomToast 한다.
        Log.e("돌아옴!","");
        dismiss();

        LayoutInflater inflater = getLayoutInflater();
        View toastDesign = inflater.inflate(R.layout.find_pwd_toast_design, (ViewGroup)findViewById(R.id.find_pwd_toast_design_root)); //toast_design.xml 파일의 toast_design_root 속성을 로드
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0); // CENTER를 기준으로 0, 0 위치에 메시지 출력
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastDesign);
        toast.show();
        //TODO 주변 배경 흐리게

    }

    @Override
    public void possOverlapFail() {
        //TODO 다시 보낸다.
        mTvMessage.setVisibility(View.VISIBLE);
        Log.e("돌아옴!","실패");

    }
}




