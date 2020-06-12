package com.song.makeushifive.src.main.feed.interfaces;

import com.song.makeushifive.src.main.feed.models.FeedResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FeedRetrofitInterface {

    @GET("/task")
    Call<FeedResponse> getSchedule();

}
