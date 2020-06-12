package com.song.makeushifive.src.signup.models;

import com.google.gson.annotations.SerializedName;

public class OverlapResponse {

    @SerializedName("isSuccess")
    public boolean isSuccess;


    public boolean isSuccess() {
        return isSuccess;
    }

    @SerializedName("code")
    public int code;
    public int getCode() {
        return code;
    }

    @SerializedName("message")
    public String message;
    public String getMessage() {
        return message;
    }

}
