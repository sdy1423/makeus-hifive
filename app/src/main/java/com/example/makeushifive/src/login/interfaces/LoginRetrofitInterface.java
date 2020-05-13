package com.example.makeushifive.src.login.interfaces;

import com.example.makeushifive.src.login.models.LoginResponse;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginRetrofitInterface {
    @POST("/jwt")
    Call<LoginResponse> PostLogIn(
            @Body RequestBody params
            );
}
