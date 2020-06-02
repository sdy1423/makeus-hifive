package com.example.makeushifive.src.main.chatting.share.interfaces;

import com.example.makeushifive.src.main.chatting.share.models.SearchUserResponse;
import com.example.makeushifive.src.main.chatting.share.models.SharedUserResponse;
import com.example.makeushifive.src.main.chatting.share.models.TaskShareResponse;
import com.example.makeushifive.src.main.models.MainResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ShareRetrofitInterface {

    @POST("/taskshare")
    Call<TaskShareResponse> postTaskShare(
            @Body RequestBody params
    );
    @GET("/shareduser")
    Call<SharedUserResponse> getRecentSharedUser();

    @GET("/user")
    Call<SearchUserResponse> getSearchUser(@Query("nickname") String nickname);

    @GET("/user")
    Call<MainResponse> getUser(@Query("nickname") String nickname);




}
