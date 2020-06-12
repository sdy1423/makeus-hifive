package com.song.makeushifive.src.main.setting.change.models;

import com.google.gson.annotations.SerializedName;

public class ChangeResponse {

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
