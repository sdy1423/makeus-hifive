package com.example.makeushifive.src.main.setting.change.interfaces;

import com.example.makeushifive.src.main.setting.change.models.ChangeResponse;
import com.example.makeushifive.src.main.setting.change.models.ImageResponse;

import java.lang.annotation.Documented;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ChangeRetrofitInterface {

    @PATCH("/user")
    Call<ChangeResponse>
    PatchChangeInfo(
            @Body RequestBody params
    );

    @POST("/usertest")
    Call<ChangeResponse> PostOverlap(
            @Body RequestBody params
    );

    @Multipart
    @POST("/upload")
    Call<ImageResponse> uploadImage(
            @Part MultipartBody.Part file,
            @Part("name") RequestBody requestBody
    );

    @GET("/user")
    Call<ChangeResponse> getUser(@Query("nickname") String nickname);


    @DELETE("/user")
    Call<ChangeResponse> deleteUser();

}
