package com.song.makeushifive.src.login.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("result")
    public Result result;
    public Result getResult(){
        return result;
    }
    public class Result{
        @SerializedName("jwt")
        public String jwt;
        public String getJwt(){return jwt;}
        @SerializedName("userNo")
        public int userNo;
        public int getUserNo(){return userNo;}
        @SerializedName("profileUrl")
        public String profileUrl;
        public String getProfileUrl() {
            return profileUrl;
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
