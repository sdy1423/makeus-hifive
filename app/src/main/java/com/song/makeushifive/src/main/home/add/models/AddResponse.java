package com.song.makeushifive.src.main.home.add.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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

        @SerializedName("title")
        public int title;
        public int getTitle() {
            return title;
        }

        @SerializedName("location")
        public int location;
        public int getLocation() {
            return location;
        }

        @SerializedName("tag")
        public int tag;
        public int getTag() {
            return tag;
        }

        @SerializedName("color")
        public int color;
        public int getColor() {
            return color;
        }

        @SerializedName("days")
        public ArrayList days;
        public ArrayList getDays () {
            return days;
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
