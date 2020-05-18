package com.example.makeushifive.src.main.home.add.interfaces;

import com.example.makeushifive.src.main.home.add.models.AddResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AddRetrofitInterface {


    @POST("/task")
    Call<AddResponse> PostAddSchedule(
            @Body RequestBody params
    );



}
