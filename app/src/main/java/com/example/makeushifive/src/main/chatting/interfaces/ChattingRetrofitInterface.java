package com.example.makeushifive.src.main.chatting.interfaces;

import com.example.makeushifive.src.main.chatting.models.ChatUserResponse;
import com.example.makeushifive.src.main.chatting.models.ChattingHistoryResponse;
import com.example.makeushifive.src.main.chatting.models.ChattingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ChattingRetrofitInterface {

    @GET("/task/{taskNo}")
    Call<ChattingResponse> getScheduleDetail(
            @Path("taskNo") int taskNo
    );



    @GET("/chatuser")
    Call<ChatUserResponse> getChatUser(@Query("room") int room);


    @GET("/chat")
    Call<ChattingHistoryResponse> getChatHistory(@Query("room") int room);


    @GET("/user")
    Call<ChattingResponse> getUser(@Query("nickname") String nickname);


}
