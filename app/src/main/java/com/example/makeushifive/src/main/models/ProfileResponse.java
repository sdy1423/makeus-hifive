package com.example.makeushifive.src.main.models;

import com.google.gson.annotations.SerializedName;

public class ProfileResponse {


    @SerializedName("result")
    public String result;
    public String result() {
        return result;
    }

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
