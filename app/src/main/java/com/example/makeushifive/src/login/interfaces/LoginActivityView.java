package com.example.makeushifive.src.login.interfaces;

import com.example.makeushifive.src.login.models.LoginResponse;

public interface LoginActivityView {

    void LogInSuccess(LoginResponse.Result result);
    void LoginFail();

    void FindPwdSuccess();
    void FindPwdFail();
}

