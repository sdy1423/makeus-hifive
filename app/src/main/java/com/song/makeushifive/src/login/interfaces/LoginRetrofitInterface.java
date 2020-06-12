package com.song.makeushifive.src.login.interfaces;

import com.song.makeushifive.src.login.models.LoginResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginRetrofitInterface {
    @POST("/jwt")
    Call<LoginResponse> PostLogIn(
            @Body RequestBody params
            );

    @POST("/usertest")
    Call<LoginResponse> PostOverlap(
            @Body RequestBody params
    );

}
