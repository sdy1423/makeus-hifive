package com.song.makeushifive.src.main.setting.change.interfaces;


public interface ChangeActivityView {

    void patchUserInfoChangeSuccess(int code);
    void patchUserInfoChangeFail();

    void postOverlapUserNameSuccess(int code,String message);
    void postOverlapUserNameFail();

    void postImageSuccess();
    void postImageFail();

    void getUserSuccess();
    void getUserFail();

    void deleteUserSuccess();
    void deleteUserFail();

}
