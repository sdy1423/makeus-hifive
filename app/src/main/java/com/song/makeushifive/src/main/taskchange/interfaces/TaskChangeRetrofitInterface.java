package com.song.makeushifive.src.main.taskchange.interfaces;

import com.song.makeushifive.src.main.taskchange.models.TaskChangeResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface TaskChangeRetrofitInterface {

//    @GET("/task")
//    Call<HomeResponse> getSchedule();
//
//    @GET("/task")
//    Call<HomeTodayResponse> getTodaySchedule(@Query("day") String date);
//
//    @GET("/task/info")
//    Call<TaskChangeResponse> getTaskInfo(
//            @Body RequestBody params
//    );

    @HTTP(method = "GET", path = "/task/info", hasBody = true)
    Call<TaskChangeResponse> getTaskInfo(@Body RequestBody requestBody);


    @PATCH("/task")
    Call<TaskChangeResponse> patchTaskInfo(
            @Body RequestBody requestBody
    );

    @POST("/taskrepeat")
    Call<TaskChangeResponse> postAddTaskRepeat(
            @Body RequestBody params
    );


}
