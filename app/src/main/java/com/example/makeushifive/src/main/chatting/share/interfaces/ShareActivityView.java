package com.example.makeushifive.src.main.chatting.share.interfaces;

import com.example.makeushifive.src.main.chatting.share.models.SearchUserResponse;
import com.example.makeushifive.src.main.chatting.share.models.SharedUserResponse;

import java.util.ArrayList;

public interface ShareActivityView {
    void getSearchUserSuccess(ArrayList<SearchUserResponse.Result> result);
    void getSearchUserFail();

    void getRecentShareSuccess(ArrayList<SharedUserResponse.Result> result);
    void getRecentShareFail();

    void postShareSuccess(int code, String message);
    void postShareFail();

}
