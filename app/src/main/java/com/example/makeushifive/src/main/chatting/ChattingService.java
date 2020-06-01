package com.example.makeushifive.src.main.chatting;

import android.util.Log;

import com.example.makeushifive.src.main.chatting.interfaces.ChattingActivityView;
import com.example.makeushifive.src.main.chatting.interfaces.ChattingRetrofitInterface;
import com.example.makeushifive.src.main.chatting.models.ChatUserResponse;
import com.example.makeushifive.src.main.chatting.models.ChattingHistoryResponse;
import com.example.makeushifive.src.main.chatting.models.ChattingResponse;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makeushifive.src.ApplicationClass.getRetrofit;

public class ChattingService {
    ChattingActivityView chattingActivityView;

    public ChattingService(final ChattingActivityView chattingActivityView) {
        this.chattingActivityView = chattingActivityView;
    }

    public void getDetailSchedule(int taskNo){
        ChattingRetrofitInterface chattingRetrofitInterface = getRetrofit()
        .create(ChattingRetrofitInterface.class);
        chattingRetrofitInterface.getScheduleDetail(taskNo).enqueue(new Callback<ChattingResponse>() {
            @Override
            public void onResponse(Call<ChattingResponse> call, Response<ChattingResponse> response) {
                assert response.body() != null;
                if(response.body().getCode()==100){
                    try {
                        chattingActivityView.getScheduleDetailSuccess(response.body().getResult());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                    chattingActivityView.getScheduleDetailFail();
                }
            }

            @Override
            public void onFailure(Call<ChattingResponse> call, Throwable t) {
                chattingActivityView.getScheduleDetailFail();
            }
        });
    }


    public void getChatUser(int taskNo){
        final ChattingRetrofitInterface chattingRetrofitInterface = getRetrofit().create(ChattingRetrofitInterface.class);
        chattingRetrofitInterface.getChatUser(taskNo).enqueue(new Callback<ChatUserResponse>() {
            @Override
            public void onResponse(Call<ChatUserResponse> call, Response<ChatUserResponse> response) {
                Log.e("채팅방 유저정보","받아오기 성공");
                if(response.body()!=null){
                    Log.e("CHAT","why"+response.body().getResult());

                    chattingActivityView.getChatUserSuccess(response.body().getResult());
                }else{
                    chattingActivityView.getChatUserFail();

                }
            }

            @Override
            public void onFailure(Call<ChatUserResponse> call, Throwable t) {
                chattingActivityView.getChatUserFail();
                Log.e("채팅방 유저정보","받아오기 실패");


            }
        });
    }

    public void getChatHistory(int taskNo){
        final ChattingRetrofitInterface chattingRetrofitInterface = getRetrofit().create(ChattingRetrofitInterface.class);
        chattingRetrofitInterface.getChatHistory(taskNo).enqueue(new Callback<ChattingHistoryResponse>() {
            @Override
            public void onResponse(Call<ChattingHistoryResponse> call, Response<ChattingHistoryResponse> response) {
                if(response.body()!=null){
                    chattingActivityView.getChatHistorySuccess(response.body().getResult());
                }else{
                    chattingActivityView.getChatUserFail();
                }

            }

            @Override
            public void onFailure(Call<ChattingHistoryResponse> call, Throwable t) {
                chattingActivityView.getChatHistoryFail();

            }
        });
    }

}
