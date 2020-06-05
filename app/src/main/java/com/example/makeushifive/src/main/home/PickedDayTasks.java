package com.example.makeushifive.src.main.home;

public class PickedDayTasks {
    private String title;
    private String location;
    private int color;
    private String time;
    private int taskNo;

    public PickedDayTasks(String title, String location, int color, String time,int taskNo) {
        this.title = title;
        this.location = location;
        this.color = color;
        this.time = time;
        this.taskNo=taskNo;

    }

    public void setTaskNo(int taskNo) {
        this.taskNo = taskNo;
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

    public int getColor() {
        return color;
    }

    public String getTime() {
        return time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
