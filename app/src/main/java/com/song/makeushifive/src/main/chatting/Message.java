package com.song.makeushifive.src.main.chatting;

public class Message {
    String userName;
    String messageContent;
    String roomName;
    String profileUrl;
    int viewType;

    public Message(String userName, String messageContent, String roomName, int viewType,String profileUrl) {
        this.userName = userName;
        this.messageContent = messageContent;
        this.roomName = roomName;
        this.viewType = viewType;
        this.profileUrl = profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getViewType() {
        return viewType;
    }
}
