package com.example.makeushifive.src.main.home.interfaces;

import com.example.makeushifive.src.main.home.models.HomeResponse;
import com.example.makeushifive.src.main.home.models.HomeTodayResponse;

import java.text.ParseException;
import java.util.ArrayList;

public interface HomeFragmentView {

    void getScheduleSuccess(ArrayList<HomeResponse.Result> result) throws ParseException;
    void getScheduleFail();

    void getTodayScheduleSuccess(ArrayList<HomeTodayResponse.Result> result);
    void getTodayScheduleFail();


}
