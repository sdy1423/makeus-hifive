package com.example.makeushifive.src.main.chatting;

public class ChatUser {
    int userNo;
    String profileUrl;
    String nickname;

    public ChatUser(int userNo, String profileUrl, String nickname) {
        this.userNo = userNo;
        this.profileUrl = profileUrl;
        this.nickname = nickname;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
