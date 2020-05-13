package com.example.makeushifive.src.main.setting.interfaces;

import com.example.makeushifive.src.main.setting.models.SettingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface SettingRetrofitInterface {
    @GET("/user/{userNo}")
    Call<SettingResponse> getUserInfoDetail(
        @Path("userNo") int userNo
    );
}

