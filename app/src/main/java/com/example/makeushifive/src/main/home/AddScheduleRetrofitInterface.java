package com.example.makeushifive.src.main.home;

import com.example.makeushifive.src.main.home.models.HomeTodayResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AddScheduleRetrofitInterface {

    @GET("/task")
    Call<HomeTodayResponse> getTodaySchedule(@Query("day") String date);

}
