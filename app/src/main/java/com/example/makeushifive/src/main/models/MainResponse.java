package com.example.makeushifive.src.main.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainResponse {
    @SerializedName("result")
    public ArrayList<Result> result;
    public class Result{
    }
    public ArrayList<Result> getResult(){
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
