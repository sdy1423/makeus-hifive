package com.example.makeushifive.src.main.interfaces;

import com.example.makeushifive.src.main.models.ProfileResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;

public interface MainRetrofitInterface {


    @PATCH("/user/profileUrl")
    Call<ProfileResponse>
    PatchProfileUrl(
            @Body RequestBody params
    );

}
