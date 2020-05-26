package com.example.makeushifive.src.main.chatting;

import com.example.makeushifive.src.main.chatting.interfaces.ChattingActivityView;
import com.example.makeushifive.src.main.chatting.interfaces.ChattingRetrofitInterface;
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


}
