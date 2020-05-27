package com.example.makeushifive.src.main.home.add.models;

import com.google.gson.annotations.SerializedName;

public class AddResponse {

    @SerializedName("result")
    public Result result;
    public Result getResult(){
        return result;
    }

    public class Result {

        @SerializedName("taskNo")
        public int taskNo;
        public int getTaskNo() {
            return taskNo;
        }
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
