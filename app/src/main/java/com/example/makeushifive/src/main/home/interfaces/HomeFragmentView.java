package com.example.makeushifive.src.main.home.interfaces;

import com.example.makeushifive.src.main.home.models.HomeResponse;

import java.util.ArrayList;

public interface HomeFragmentView {

    void getScheduleSuccess(ArrayList<HomeResponse.Result> result);
    void getScheduleFail();



}
