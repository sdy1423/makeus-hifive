package com.example.makeushifive.src.main.setting.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SettingResponse {


    @SerializedName("result")
    public ArrayList<Result> result;

    public class Result{
        @SerializedName("profileUrl")
        public String profileUrl;
        public String getProfileUrl() {
            return profileUrl;
        }

        @SerializedName("nickname")
        public String nickname;
        public String getNickname(){
            return  nickname;
        }

        @SerializedName("email")
        public String email;
        public String getEmail() {
            return email;
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
