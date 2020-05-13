package com.example.makeushifive.src.main.setting.interfaces;

import com.example.makeushifive.src.main.setting.models.SettingResponse;

import java.util.ArrayList;

public interface SettingFragmentView {

    void getUserInfoDetailSuccess(ArrayList<SettingResponse.Result> result);
    void getUserInfoDetailFail();
}
