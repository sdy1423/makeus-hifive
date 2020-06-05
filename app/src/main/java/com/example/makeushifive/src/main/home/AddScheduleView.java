package com.example.makeushifive.src.main.home;

import com.example.makeushifive.src.main.home.models.HomeTodayResponse;

import java.util.ArrayList;

public interface AddScheduleView {
    void getPickedScheduleSuccess(ArrayList<HomeTodayResponse.Result> result);
    void getPickedScheduleFail();

    void deleteTaskSuccess();
    void deleteTaskFail();
}
