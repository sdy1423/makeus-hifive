package com.song.makeushifive.src.main.taskchange.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TaskChangeResponse {

    @SerializedName("result")
    public Result result;
    public class Result {
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

        @SerializedName("location")
        public String location;

        public String getLocation() {
            return location;
        }

        @SerializedName("tag")
        public String tag;

        public String getTag() {
            return tag;
        }

        @SerializedName("color")
        public int color;

        public int getColor() {
            return color;
        }

        @SerializedName("repeatweek")
        ArrayList<Repeatweek> repeatweek;

        public ArrayList<Repeatweek> getRepeatweek() {
            return repeatweek;
        }

        public class Repeatweek {
            @SerializedName("repeatweek")
            public int repeatweek;

            public int getRepeatweek() {
                return repeatweek;
            }
        }

        @SerializedName("days")
        ArrayList<Days> days;

        public ArrayList<Days> getDays() {
            return days;
        }

        public class Days {
            @SerializedName("dayNo")
            public int dayNo;

            public int getDayNo() {
                return dayNo;
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
        }
    }

    public Result getResult() {
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
