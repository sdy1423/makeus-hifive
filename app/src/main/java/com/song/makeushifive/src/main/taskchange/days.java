package com.song.makeushifive.src.main.taskchange;

class days {
    int dayNo;
    String day;
    String time;

    public days(int dayNo, String day, String time) {
        this.dayNo = dayNo;
        this.day = day;
        this.time = time;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDayNo(int dayNo) {
        this.dayNo = dayNo;
    }

    public int getDayNo() {
        return dayNo;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }
}
