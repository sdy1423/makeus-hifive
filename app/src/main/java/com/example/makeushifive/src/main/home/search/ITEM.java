package com.example.makeushifive.src.main.home.search;

public class ITEM {
    int taskNo;
    String title;
    int color;
    String day;
    int week;
    int count;
    //TODO UserInfo


    public ITEM(int taskNo, String title, int color, String day, int week, int count) {
        this.taskNo = taskNo;
        this.title = title;
        this.color = color;
        this.day = day;
        this.week = week;
        this.count = count;
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

    public void setCount(int count) {
        this.count = count;
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

    public int getCount() {
        return count;
    }
}
