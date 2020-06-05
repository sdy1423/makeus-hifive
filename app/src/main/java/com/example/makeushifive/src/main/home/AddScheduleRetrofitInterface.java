package com.example.makeushifive.src.main.home;

import com.example.makeushifive.src.main.home.models.HomeTodayResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

public interface AddScheduleRetrofitInterface {

    @GET("/task")
    Call<HomeTodayResponse> getTodaySchedule(@Query("day") String date);


    @HTTP(method = "DELETE", path = "/task", hasBody = true)
    Call<HomeTodayResponse> deleteTask(@Body RequestBody requestBody);

}
