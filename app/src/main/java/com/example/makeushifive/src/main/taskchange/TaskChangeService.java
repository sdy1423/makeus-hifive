package com.example.makeushifive.src.main.taskchange;

import android.util.Log;

import com.example.makeushifive.src.main.home.add.interfaces.AddRetrofitInterface;
import com.example.makeushifive.src.main.home.add.models.AddResponse;
import com.example.makeushifive.src.main.taskchange.interfaces.TaskChangeActivityView;
import com.example.makeushifive.src.main.taskchange.interfaces.TaskChangeRetrofitInterface;
import com.example.makeushifive.src.main.taskchange.models.TaskChangeResponse;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makeushifive.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.example.makeushifive.src.ApplicationClass.getRetrofit;

public class TaskChangeService {
    TaskChangeActivityView taskChangeActivityView;

    public TaskChangeService(TaskChangeActivityView taskChangeActivityView) {
        this.taskChangeActivityView = taskChangeActivityView;
    }

    public void getTaskInfo(int  taskNo) throws JSONException {
        final TaskChangeRetrofitInterface taskChangeRetrofitInterface=getRetrofit().create(TaskChangeRetrofitInterface.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskNo",taskNo);
        taskChangeRetrofitInterface.getTaskInfo(RequestBody.create(jsonObject.toString(),MEDIA_TYPE_JSON)).enqueue(new Callback<TaskChangeResponse>() {
            @Override
            public void onResponse(Call<TaskChangeResponse> call, Response<TaskChangeResponse> response) {
                try{
                    assert response.body() != null;
                    if(response.body().getCode()==100){
                        taskChangeActivityView.getTaskSuccess(response.body().getResult());

                    }else{
                        taskChangeActivityView.getTaskFail();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaskChangeResponse> call, Throwable t) {
                taskChangeActivityView.getTaskFail();

            }


        });
    }

    public void PostTaskRepeat(final JSONObject jsonObject) throws  JSONException{
//        Log.e("추가하는 반복일정",""+jsonObject);
        final TaskChangeRetrofitInterface taskChangeRetrofitInterface=getRetrofit().create(TaskChangeRetrofitInterface.class);
        taskChangeRetrofitInterface.postAddTaskRepeat(RequestBody.create(jsonObject.toString(),MEDIA_TYPE_JSON)).enqueue(new Callback<TaskChangeResponse>() {
            @Override
            public void onResponse(Call<TaskChangeResponse> call, Response<TaskChangeResponse> response) {
                try{
                    if(response.body().getCode()==100){
                        taskChangeActivityView.postTaskRepeatSuccess();
                    }else{
                        taskChangeActivityView.postTaskRepeatFail();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<TaskChangeResponse> call, Throwable t) {
                taskChangeActivityView.postTaskRepeatFail();
            }
    });
    }


    void patchTaskChange(final JSONObject jsonObject){
        TaskChangeRetrofitInterface taskChangeRetrofitInterface = getRetrofit().create(TaskChangeRetrofitInterface.class);
        taskChangeRetrofitInterface.patchTaskInfo(RequestBody.create(jsonObject.toString(),MEDIA_TYPE_JSON)).enqueue(new Callback<TaskChangeResponse>() {
            @Override
            public void onResponse(Call<TaskChangeResponse> call, Response<TaskChangeResponse> response) {
                try {
                    if(response.body().getCode()==100){
                        taskChangeActivityView.patchTaskChangeSuccess();

                    }
                    else {
                        taskChangeActivityView.postTaskRepeatFail();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaskChangeResponse> call, Throwable t) {

                taskChangeActivityView.postTaskRepeatFail();
            }
        });


    }

}
