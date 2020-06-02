package com.example.makeushifive.src.main.chatting.interfaces;

import com.example.makeushifive.src.main.chatting.models.ChatUserResponse;
import com.example.makeushifive.src.main.chatting.models.ChattingHistoryResponse;
import com.example.makeushifive.src.main.chatting.models.ChattingResponse;

import java.text.ParseException;
import java.util.ArrayList;

public interface ChattingActivityView {

    void getScheduleDetailSuccess(ArrayList<ChattingResponse.Result> result) throws ParseException;
    void getScheduleDetailFail();

    void getChatUserSuccess(ArrayList<ChatUserResponse.Result> result);
    void getChatUserFail();

    void getChatHistorySuccess(ArrayList<ChattingHistoryResponse.Result> result);
    void getChatHistoryFail();

    void getUserSuccess();
    void getUserFail();
}
