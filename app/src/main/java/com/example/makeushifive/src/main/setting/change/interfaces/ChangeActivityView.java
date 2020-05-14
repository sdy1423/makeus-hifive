package com.example.makeushifive.src.main.setting.change.interfaces;


public interface ChangeActivityView {

    void patchUserInfoChangeSuccess(int code);
    void patchUserInfoChangeFail();

    void postOverlapUserNameSuccess(int code,String message);
    void postOverlapUserNameFail();

}
