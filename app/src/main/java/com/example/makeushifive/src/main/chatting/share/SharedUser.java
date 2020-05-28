package com.example.makeushifive.src.main.chatting.share;

public class SharedUser {
    int sharedUserNo;
    String profileUrl;
    String nickname;

    public SharedUser(int sharedUserNo, String profileUrl, String nickname) {
        this.sharedUserNo = sharedUserNo;
        this.profileUrl = profileUrl;
        this.nickname = nickname;
    }

    public void setSharedUserNo(int sharedUserNo) {
        this.sharedUserNo = sharedUserNo;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSharedUserNo() {
        return sharedUserNo;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getNickname() {
        return nickname;
    }
}
