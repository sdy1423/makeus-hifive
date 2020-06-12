package com.song.makeushifive.src.login.interfaces;

import com.song.makeushifive.src.login.models.LoginResponse;

public interface LoginActivityView {

    void LogInSuccess(LoginResponse.Result result);
    void LoginFail();

    void postOverlapSuccess();
    void possOverlapFail();


}

