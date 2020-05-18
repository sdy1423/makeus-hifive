package com.example.makeushifive.src.main.home.add.models;

import com.google.gson.annotations.SerializedName;

public class AddResponse {
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
