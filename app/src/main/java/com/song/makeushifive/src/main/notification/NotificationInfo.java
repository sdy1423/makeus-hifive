package com.song.makeushifive.src.main.notification;

public class NotificationInfo {
    int taskNo;
    String title;
    int color;
    String day;
    int week;
    String time;
    String nickname;

    public NotificationInfo(int taskNo, String title, int color, String day, int week, String time, String nickname) {
        this.taskNo = taskNo;
        this.title = title;
        this.color = color;
        this.day = day;
        this.week = week;
        this.time = time;
        this.nickname = nickname;
    }

    public void setTaskNo(int taskNo) {
        this.taskNo = taskNo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getTaskNo() {
        return taskNo;
    }

    public String getTitle() {
        return title;
    }

    public int getColor() {
        return color;
    }

    public String getDay() {
        return day;
    }

    public int getWeek() {
        return week;
    }

    public String getTime() {
        return time;
    }

    public String getNickname() {
        return nickname;
    }
}
