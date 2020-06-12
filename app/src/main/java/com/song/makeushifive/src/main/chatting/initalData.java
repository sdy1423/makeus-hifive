package com.song.makeushifive.src.main.chatting;

public class initalData {
    String userName;
    String roomName;

    public initalData(String userName, String roomName) {
        this.userName = userName;
        this.roomName = roomName;
    }

    public String getUserName() {
        return userName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
