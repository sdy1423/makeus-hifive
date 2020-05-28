package com.example.makeushifive.src.main.chatting;

public class GetMessage {
    int taskNo;
    String name;
    String msg;
    int room;
    String profileUrl;

    public GetMessage(int taskNo, String name, String msg, int room, String profileUrl) {
        this.taskNo = taskNo;
        this.name = name;
        this.msg = msg;
        this.room = room;
        this.profileUrl = profileUrl;
    }

    public void setTaskNo(int taskNo) {
        this.taskNo = taskNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public int getTaskNo() {
        return taskNo;
    }

    public String getName() {
        return name;
    }

    public String getMsg() {
        return msg;
    }

    public int getRoom() {
        return room;
    }

    public String getProfileUrl() {
        return profileUrl;
    }
}
