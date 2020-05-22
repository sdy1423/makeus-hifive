package com.example.makeushifive.src.main.home;

import android.util.Log;

import com.example.makeushifive.src.main.home.interfaces.HomeFragmentView;
import com.example.makeushifive.src.main.home.interfaces.HomeRetrotifInterface;
import com.example.makeushifive.src.main.home.models.HomeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makeushifive.src.ApplicationClass.getRetrofit;

public class HomeService {
    HomeFragmentView homeFragmentView;

    public HomeService(HomeFragmentView homeFragmentView) {
        this.homeFragmentView = homeFragmentView;
    }

    public void getSchedule(){
        HomeRetrotifInterface homeRetrotifInterface =
                getRetrofit().create(HomeRetrotifInterface.class);
        homeRetrotifInterface.getSchedule().enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                if (response.body() != null) {
                    Log.e("show",""+response.body().getResult());
                    homeFragmentView.getScheduleSuccess(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                homeFragmentView.getScheduleFail();
            }
        });
    }
}
