package com.example.makeushifive.src.main.home.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HomeResponse {
    @SerializedName("result")
    public ArrayList<Result> result;
    public class Result{
        @SerializedName("taskNo")
        public int taskNo;
        public int getTaskNo() {
            return taskNo;
        }
        @SerializedName("title")
        public String title;
        public String getTitle() {
            return title;
        }
        @SerializedName("color")
        public int color;
        public int getColor() {
            return color;
        }
        @SerializedName("day")
        public String day;
        public String getDay() {
            return day;
        }
        @SerializedName("time")
        public String time;
        public String getTime() {
            return time;
        }
        @SerializedName("count")
        public int count;

        public int getCount() {
            return count;
        }
        @SerializedName("userInfo")
        public ArrayList<UserInfo> userInfo;
        public class UserInfo{
            @SerializedName("userNo")
            public int userNo;

            public int getUserNo() {
                return userNo;
            }
            @SerializedName("profileUrl")
            public String profileUrl;

            public String getProfileUrl() {
                return profileUrl;
            }

        }

        public ArrayList<UserInfo> getUserInfo() {
            return userInfo;
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

