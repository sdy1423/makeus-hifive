package com.song.makeushifive.src.main.chatting.share;

public class SharedUser {
    int sharedUserNo;
    String profileUrl;
    String nickname;
    boolean picked;

    public SharedUser(int sharedUserNo, String profileUrl, String nickname,Boolean picked) {
        this.sharedUserNo = sharedUserNo;
        this.profileUrl = profileUrl;
        this.nickname = nickname;
        this.picked = picked;
    }

    public void setPicked(boolean picked) {
        this.picked = picked;
    }

    public boolean isPicked() {
        return picked;
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
