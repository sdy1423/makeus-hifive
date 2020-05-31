package com.example.makeushifive.src.main.chatting.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChatUserResponse {

    @SerializedName("result")
    public ArrayList<Result> result;
    public class Result{
        @SerializedName("userNo")
        public int userNo;

        @SerializedName("profileUrl")
        public String profileUrl;

        @SerializedName("nickname")
        public String nickname;

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
