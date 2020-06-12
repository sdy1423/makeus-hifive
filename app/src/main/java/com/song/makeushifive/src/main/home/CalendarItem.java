package com.song.makeushifive.src.main.home;

import java.util.Date;

public class CalendarItem{
    private int taskNo;
    private String title;
    private int color;
    private Date day;
    private int count;

    public CalendarItem(int taskNo, String title,int color
            , Date day,int count) {
        this.taskNo = taskNo;
        this.title = title;
        this.color = color;
        this.day = day;
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

    public void setDay(Date day) {
        this.day = day;
    }

    public Date getDay() {
        return day;
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



    public int getCount() {
        return count;
    }

}


