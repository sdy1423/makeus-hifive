package com.example.makeushifive.src.main.home.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HomeTodayResponse {
    @SerializedName("result")
    public ArrayList<Result> result;
    public class Result{

        @SerializedName("title")
        public String title;
        public String getTitle() {
            return title;
        }

        @SerializedName("location")
        public String location;
        public String getLocation() {
            return location;
        }
        @SerializedName("color")
        public int color;
        public int getColor() {
            return color;
        }
        @SerializedName("time")
        public String time;
        public String getTime() {
            return time;
        }
    }

    public ArrayList<Result> getResult() {
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
