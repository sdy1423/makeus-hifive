package com.example.makeushifive.src.main.home.interfaces;

import com.example.makeushifive.src.main.home.models.HomeResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HomeRetrotifInterface {

    @GET("/task")
    Call<HomeResponse> getSchedule();

}
