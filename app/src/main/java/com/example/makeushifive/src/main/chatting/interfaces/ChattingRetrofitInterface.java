package com.example.makeushifive.src.main.chatting.interfaces;

import com.example.makeushifive.src.main.chatting.models.ChattingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChattingRetrofitInterface {

    @GET("/task/{taskNo}")
    Call<ChattingResponse> getScheduleDetail(
            @Path("taskNo") int taskNo
    );



}
