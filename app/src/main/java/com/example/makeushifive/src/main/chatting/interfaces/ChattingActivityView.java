package com.example.makeushifive.src.main.chatting.interfaces;

import com.example.makeushifive.src.main.chatting.models.ChattingResponse;

import java.util.ArrayList;

public interface ChattingActivityView {

    void getScheduleDetailSuccess(ArrayList<ChattingResponse.Result> result);
    void getScheduleDetailFail();

}
