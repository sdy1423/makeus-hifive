package com.song.makeushifive.src.main.chatting.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChattingResponse {

    @SerializedName("result")
    public ArrayList<Result> result;
    public class Result{
        @SerializedName("taskNo")
        public int taskNo;

        @SerializedName("title")
        public String title;

        @SerializedName("location")
        public String location;

        @SerializedName("day")
        public String day;

        @SerializedName("time")
        public String time;

        @SerializedName("repeatweek")
        public int repeatweek;

        public int getRepeatweek() {
            return repeatweek;
        }

        public int getTaskNo() {
            return taskNo;
        }

        public String getTitle() {
            return title;
        }

        public String getLocation() {
            return location;
        }

        public String getDay() {
            return day;
        }

        public String getTime() {
            return time;
        }
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
