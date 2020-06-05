package com.example.makeushifive.src.main.home;

import android.util.Log;

import com.example.makeushifive.src.main.home.models.HomeTodayResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makeushifive.src.ApplicationClass.MEDIA_TYPE_JSON;
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
    void deleteTask(int taskNo) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskNo",taskNo);
        AddScheduleRetrofitInterface addScheduleRetrofitInterface =
                getRetrofit().create(AddScheduleRetrofitInterface.class);
        addScheduleRetrofitInterface.deleteTask(RequestBody.create(jsonObject.toString(),MEDIA_TYPE_JSON)).enqueue(new Callback<HomeTodayResponse>() {
            @Override
            public void onResponse(Call<HomeTodayResponse> call, Response<HomeTodayResponse> response) {
                try {
                    if(Objects.requireNonNull(response.body()).getCode()==100){
                        Log.e("일정삭제 성공","성공");
                        addScheduleVIew.deleteTaskSuccess();
                    }else{
                        addScheduleVIew.deleteTaskFail();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    addScheduleVIew.deleteTaskFail();
                }
            }

            @Override
            public void onFailure(Call<HomeTodayResponse> call, Throwable t) {
                addScheduleVIew.deleteTaskFail();
            }
        });
    }



}
