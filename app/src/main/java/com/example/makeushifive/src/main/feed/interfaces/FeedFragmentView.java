package com.example.makeushifive.src.main.feed.interfaces;

import com.example.makeushifive.src.main.feed.models.FeedResponse;

public interface FeedFragmentView {

    void getScheduleSuccess(FeedResponse result);
    void getScheduleFail();

}
