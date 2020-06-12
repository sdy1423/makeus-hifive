package com.song.makeushifive.src.main.home.interfaces;

import com.song.makeushifive.src.main.home.models.HomeResponse;
import com.song.makeushifive.src.main.home.models.HomeTodayResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeRetrotifInterface {

    @GET("/task")
    Call<HomeResponse> getSchedule();

    @GET("/task")
    Call<HomeTodayResponse> getTodaySchedule(@Query("day") String date);

}
