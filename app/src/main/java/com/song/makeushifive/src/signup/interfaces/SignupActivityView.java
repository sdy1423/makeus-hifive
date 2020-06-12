package com.song.makeushifive.src.signup.interfaces;

public interface SignupActivityView {

    void OverlapUserNameSuccess(int code,String message);
    void OverlapUserNameFail(int code,String message);

    void OverlapEmailSuccess(int code,String message);
    void OverlapEmailFail(int code,String message);

    void SignUpSuccess(int code);
    void SignUpFail();

}
