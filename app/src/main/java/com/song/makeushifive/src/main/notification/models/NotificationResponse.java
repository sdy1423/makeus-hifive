package com.song.makeushifive.src.main.notification.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationResponse {

    @SerializedName("result")
    ArrayList<Result> results;

    public ArrayList<Result> getResults() {
        return results;
    }
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
        @SerializedName("week")
        public int week;

        public int getWeek() {
            return week;
        }

        @SerializedName("time")
        public String time;

        public String getTime() {
            return time;
        }

        @SerializedName("nickname")
        public String nickname;

        public String getNickname() {
            return nickname;
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
