package com.example.makeushifive.src.main.home.search;

import com.example.makeushifive.src.main.feed.UserInfo;

import java.util.ArrayList;

public class ITEM {
    int taskNo;
    String title;
    int color;
    String day;
    int week;
    int count;
    private ArrayList<UserInfo> userInfos;

    //TODO UserInfo


    public ITEM(int taskNo, String title, int color, String day, int week, int count,ArrayList<UserInfo> userInfos) {
        this.taskNo = taskNo;
        this.title = title;
        this.color = color;
        this.day = day;
        this.week = week;
        this.count = count;
        this.userInfos = userInfos;
    }

    public void setUserInfos(ArrayList<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public ArrayList<UserInfo> getUserInfos() {
        return userInfos;
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
