package com.example.makeushifive.src.main.home;

public class PickedDayTasks {
    private String title;
    private String location;
    private int color;
    private String time;

    public PickedDayTasks(String title, String location, int color, String time) {
        this.title = title;
        this.location = location;
        this.color = color;
        this.time = time;
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
