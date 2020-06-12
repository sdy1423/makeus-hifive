package com.song.makeushifive.src.main.chatting.share.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchUserResponse {

    @SerializedName("result")
    public ArrayList<Result> result;
    public class Result{

        @SerializedName("userNo")
        public int userNo;

        @SerializedName("profileUrl")
        public String profileUrl;

        @SerializedName("email")
        public String email;

        @SerializedName("nickname")
        public String nickname;

        public int getUserNo() {
            return userNo;
        }

        public String getProfileUrl() {
            return profileUrl;
        }

        public String getEmail() {
            return email;
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
