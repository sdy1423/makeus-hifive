package com.example.makeushifive.src.main.feed.interfaces;

import com.example.makeushifive.src.main.feed.models.FeedResponse;

import java.util.ArrayList;

public interface FeedFragmentView {

    void getScheduleSuccess(ArrayList<FeedResponse.Result> result);
    void getScheduleFail();

}
