package com.example.makeushifive.src.main.setting;

import com.example.makeushifive.src.main.setting.interfaces.SettingFragmentView;
import com.example.makeushifive.src.main.setting.interfaces.SettingRetrofitInterface;
import com.example.makeushifive.src.main.setting.models.SettingResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makeushifive.src.ApplicationClass.getRetrofit;

public class SettingService {

    SettingFragmentView mSettingFragmentView;

    public SettingService(SettingFragmentView mSettingFragmentView) {
        this.mSettingFragmentView = mSettingFragmentView;
    }

    public void getUserInfoDetail(int userNo){
        SettingRetrofitInterface settingRetrofitInterface = getRetrofit().create(SettingRetrofitInterface.class);
        settingRetrofitInterface.getUserInfoDetail(userNo).enqueue(new Callback<SettingResponse>() {
            @Override
            public void onResponse(Call<SettingResponse> call, Response<SettingResponse> response) {
                int code=0;
                if (response.body() != null) {
                    code=response.body().getCode();
                    if(code==100){
                        mSettingFragmentView.getUserInfoDetailSuccess(response.body().getResult());
                    }else{
                        mSettingFragmentView.getUserInfoDetailFail();
                    }
                }else{
                    mSettingFragmentView.getUserInfoDetailFail();
                }
            }

            @Override
            public void onFailure(Call<SettingResponse> call, Throwable t) {

                mSettingFragmentView.getUserInfoDetailFail();
            }
        });
    }
}
