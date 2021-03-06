package com.song.makeushifive.src.main.home;

import com.song.makeushifive.src.main.home.interfaces.HomeFragmentView;
import com.song.makeushifive.src.main.home.interfaces.HomeRetrotifInterface;
import com.song.makeushifive.src.main.home.models.HomeResponse;
import com.song.makeushifive.src.main.home.models.HomeTodayResponse;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.song.makeushifive.src.ApplicationClass.getRetrofit;

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
                    try {
                        if(response.body().getCode()==100){
                            homeFragmentView.getScheduleSuccess(response.body().getResult());
                        }else{
                            homeFragmentView.getScheduleFail();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                homeFragmentView.getScheduleFail();
            }
        });
    }
    public void getTodaySchedule(String date){
        HomeRetrotifInterface homeRetrotifInterface = getRetrofit()
                .create(HomeRetrotifInterface.class);
        homeRetrotifInterface.getTodaySchedule(date).enqueue(new Callback<HomeTodayResponse>() {
            @Override
            public void onResponse(Call<HomeTodayResponse> call, Response<HomeTodayResponse> response) {
                if(response.body()!=null){
                    homeFragmentView.getTodayScheduleSuccess(response.body().getResult());
                }else{
                    homeFragmentView.getTodayScheduleFail();
                }

            }

            @Override
            public void onFailure(Call<HomeTodayResponse> call, Throwable t) {
                homeFragmentView.getTodayScheduleFail();
            }
        });
    }
}
