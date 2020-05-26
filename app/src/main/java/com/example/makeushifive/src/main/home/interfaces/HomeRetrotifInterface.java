package com.example.makeushifive.src.main.home.interfaces;

import com.bumptech.glide.load.model.Model;
import com.example.makeushifive.src.main.home.models.HomeResponse;
import com.example.makeushifive.src.main.home.models.HomeTodayResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HomeRetrotifInterface {

    @GET("/task")
    Call<HomeResponse> getSchedule();

//    @GET("/task?day={date}")
//    Call<HomeTodayResponse> getTodaySchedule(
//            @Path("date") String date
//    );
//
    @GET("/task")
    Call<HomeTodayResponse> getTodaySchedule(@Query("day") String date);

}
