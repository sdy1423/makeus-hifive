package com.example.makeushifive.src;

import android.app.ProgressDialog;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    public ProgressDialog mProgressDialog;

    public void showCustomToast(final String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }

    public void showProgressDialog(){ //널이면 새로운 애를 할당하고 로딩중입니다 생성
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("로딩중입니다.");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false); //화면 클릭했을때 안꺼진다.
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }


}
