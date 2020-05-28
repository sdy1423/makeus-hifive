package com.example.makeushifive.src.main.chatting;

public class SendMessage {

    int taskNo;
    int name;
    String msg;
    int room;

    public SendMessage(int taskNo, int name, String msg, int room) {
        this.taskNo = taskNo;
        this.name = name;
        this.msg = msg;
        this.room = room;
    }

    public void setTaskNo(int taskNo) {
        this.taskNo = taskNo;
    }

    public void setName(int name) {
        this.name = name;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getTaskNo() {
        return taskNo;
    }

    public int getName() {
        return name;
    }

    public String getMsg() {
        return msg;
    }

    public int getRoom() {
        return room;
    }
}
