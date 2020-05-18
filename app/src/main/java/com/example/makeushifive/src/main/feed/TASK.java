package com.example.makeushifive.src.main.feed;

public class TASK {
    private int taskNo;
    private String title;
    private String location;
    private String day;
    private String time;
    private String count;

    public TASK(int taskNo, String title, String location, String day, String time, String count) {
        this.taskNo = taskNo;
        this.title = title;
        this.location = location;
        this.day = day;
        this.time = time;
        this.count = count;
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

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getCount() {
        return count;
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

    public void setDay(String day) {
        this.day = day;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCount(String count) {
        this.count = count;
    }
}


//package com.example.makeushifive.src.main.home.add;
//
//public class DAYS {
//    private String day;
//    private String time;
//
//    public DAYS(String day,String time){
//        this.day=day;
//        this.time=time;
//    }
//
//    public String getDay() {
//        return day;
//    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public void setDay(String day) {
//        this.day = day;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
//}
