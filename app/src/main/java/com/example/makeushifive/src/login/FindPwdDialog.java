package com.example.makeushifive.src.login;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.makeushifive.R;

import java.util.Objects;

public class FindPwdDialog extends Dialog {
    private ImageView mIvClose;
    private Button mBtnTransfer;
    private View.OnClickListener mTransferListener,mCancelListener;

    public FindPwdDialog(@NonNull Context context,View.OnClickListener transferListener,View.OnClickListener cancelListener) {
        super(context);
        this.mTransferListener=transferListener;
        this.mCancelListener=cancelListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_custom_dialog_find_pwd);
        mIvClose=findViewById(R.id.login_custom_dialog_iv_close);
        mBtnTransfer=findViewById(R.id.login_custom_dialog_btn_transfer);
        mIvClose.setOnClickListener(mCancelListener);
        mBtnTransfer.setOnClickListener(mTransferListener);

    }

}




