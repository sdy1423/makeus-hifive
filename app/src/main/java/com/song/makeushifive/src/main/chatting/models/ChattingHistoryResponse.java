package com.song.makeushifive.src.main.chatting.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChattingHistoryResponse {

    @SerializedName("result")
    public ArrayList<Result> result;
    public class Result{
        @SerializedName("userNo")
        public int userNo;

        @SerializedName("profileUrl")
        public String profileUrl;

        @SerializedName("nickname")
        public String nickname;

        @SerializedName("msg")
        public String msg;

        public String getMsg() {
            return msg;
        }

        public int getUserNo() {
            return userNo;
        }

        public String getProfileUrl() {
            return profileUrl;
        }

        public String getNickname() {
            return nickname;
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
