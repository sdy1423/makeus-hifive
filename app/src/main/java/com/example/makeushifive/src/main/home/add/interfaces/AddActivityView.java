package com.example.makeushifive.src.main.home.add.interfaces;

import com.example.makeushifive.src.main.home.add.models.AddResponse;

import org.json.JSONException;

public interface AddActivityView {

    void postAddSuccess(AddResponse.Result result) throws JSONException;
    void postAddFail();

    void postAddTaskRepeatSuccess();
    void postAddTaskRepeatFail();

}
