package com.example.makeushifive.src.main.interfaces;

import com.example.makeushifive.src.main.MainService;
import com.example.makeushifive.src.main.models.MainResponse;
import com.example.makeushifive.src.main.models.ProfileResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Query;

public interface MainRetrofitInterface {


    @PATCH("/user/profileUrl")
    Call<ProfileResponse>
    PatchProfileUrl(
            @Body RequestBody params
    );

    @GET("/user")
    Call<MainResponse> getUser(@Query("nickname") String nickname);


}
