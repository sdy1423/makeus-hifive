package com.song.makeushifive.src.main.feed;

public class UserInfo {
    int userNo;
    String profileUrl;

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public int getUserNo() {
        return userNo;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public UserInfo(int userNo, String profileUrl) {
        this.userNo = userNo;
        this.profileUrl = profileUrl;
    }
}
