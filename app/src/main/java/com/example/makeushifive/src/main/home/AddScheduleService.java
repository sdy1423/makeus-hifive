package com.example.makeushifive.src.main.home;

import com.example.makeushifive.src.main.home.models.HomeTodayResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makeushifive.src.ApplicationClass.getRetrofit;

public class AddScheduleService {
    AddScheduleView addScheduleVIew;

    public AddScheduleService(AddScheduleView addScheduleVIew) {
        this.addScheduleVIew = addScheduleVIew;
    }
    public void getPickedDaySchedule(String date){
        AddScheduleRetrofitInterface addScheduleRetrofitInterface=getRetrofit().
                create(AddScheduleRetrofitInterface.class);
        addScheduleRetrofitInterface.getTodaySchedule(date).enqueue(
                new Callback<HomeTodayResponse>() {
                    @Override
                    public void onResponse(Call<HomeTodayResponse> call, Response<HomeTodayResponse> response) {
                        if(response.body()!=null){
                            addScheduleVIew.getPickedScheduleSuccess(response.body().getResult());
                        }else{
                            addScheduleVIew.getPickedScheduleFail();
                        }
                    }

                    @Override
                    public void onFailure(Call<HomeTodayResponse> call, Throwable t) {
                        addScheduleVIew.getPickedScheduleFail();
                    }
                }
        );
    }
}
