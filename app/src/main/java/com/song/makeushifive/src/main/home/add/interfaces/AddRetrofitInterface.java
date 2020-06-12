package com.song.makeushifive.src.main.home.add.interfaces;

import com.song.makeushifive.src.main.home.add.models.AddResponse;
import com.song.makeushifive.src.main.models.MainResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AddRetrofitInterface {


    @POST("/task")
    Call<AddResponse> PostAddSchedule(
            @Body RequestBody params
    );

    @POST("/taskrepeat")
    Call<AddResponse> PostAddTaskRepeat(
            @Body RequestBody params
    );

    @GET("/user")
    Call<MainResponse> getUser(@Query("nickname") String nickname);



}
