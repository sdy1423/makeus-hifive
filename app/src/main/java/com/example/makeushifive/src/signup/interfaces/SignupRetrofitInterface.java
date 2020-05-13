package com.example.makeushifive.src.signup.interfaces;

import com.example.makeushifive.src.signup.models.OverlapResponse;
import com.example.makeushifive.src.signup.models.SignupResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignupRetrofitInterface {

    @POST("/usertest")
    Call<OverlapResponse> PostOverlap(
            @Body RequestBody params
    );

    @POST("/user")
    Call<SignupResponse> PostSignUp(
            @Body RequestBody requestBody
    );
}
