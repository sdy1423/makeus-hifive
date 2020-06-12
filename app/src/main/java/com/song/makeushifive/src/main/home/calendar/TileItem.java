package com.song.makeushifive.src.main.home.calendar;

public class TileItem {
    int year;
    int month;
    int day;
    int color;
    String title;

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }

    public TileItem(int year, int month, int day, int color, String title) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.color = color;
        this.title = title;
    }
}
