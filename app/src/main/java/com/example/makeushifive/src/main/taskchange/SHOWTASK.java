package com.example.makeushifive.src.main.taskchange;

import java.util.ArrayList;

public class SHOWTASK {
    int taskNo;
    String title;
    String location;
    String tag;
    int color;
    ArrayList<repeatweek> repeatweek;
    ArrayList<days> days;

    public SHOWTASK(int taskNo, String title, String location, String tag, int color,ArrayList<repeatweek> repeatweek,ArrayList<days> days) {
        this.taskNo = taskNo;
        this.title = title;
        this.location = location;
        this.tag = tag;
        this.color = color;
        this.repeatweek = repeatweek;
        this.days= days;
    }

    public void setTaskNo(int taskNo) {
        this.taskNo = taskNo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getTaskNo() {
        return taskNo;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getTag() {
        return tag;
    }

    public int getColor() {
        return color;
    }

    public void setRepeatweek(ArrayList<com.example.makeushifive.src.main.taskchange.repeatweek> repeatweek) {
        this.repeatweek = repeatweek;
    }

    public void setDays(ArrayList<com.example.makeushifive.src.main.taskchange.days> days) {
        this.days = days;
    }

    public ArrayList<com.example.makeushifive.src.main.taskchange.repeatweek> getRepeatweek() {
        return repeatweek;
    }

    public ArrayList<com.example.makeushifive.src.main.taskchange.days> getDays() {
        return days;
    }
}
