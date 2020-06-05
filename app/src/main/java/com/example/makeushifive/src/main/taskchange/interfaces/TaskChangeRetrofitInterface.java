package com.example.makeushifive.src.main.taskchange.interfaces;

import com.example.makeushifive.src.main.home.add.models.AddResponse;
import com.example.makeushifive.src.main.home.models.HomeResponse;
import com.example.makeushifive.src.main.home.models.HomeTodayResponse;
import com.example.makeushifive.src.main.taskchange.models.TaskChangeResponse;
import com.example.makeushifive.src.signup.models.OverlapResponse;
import com.example.makeushifive.src.signup.models.SignupResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TaskChangeRetrofitInterface {

//    @GET("/task")
//    Call<HomeResponse> getSchedule();
//
//    @GET("/task")
//    Call<HomeTodayResponse> getTodaySchedule(@Query("day") String date);

    @GET("/task/info")
    Call<TaskChangeResponse> getTaskInfo(
            @Body RequestBody params
    );



    @PATCH("/task")
    Call<TaskChangeResponse> patchTaskInfo(
            @Body RequestBody requestBody
    );

    @POST("/taskrepeat")
    Call<TaskChangeResponse> postAddTaskRepeat(
            @Body RequestBody params
    );


}
