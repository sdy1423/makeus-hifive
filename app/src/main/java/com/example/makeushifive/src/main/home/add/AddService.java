package com.example.makeushifive.src.main.home.add;
import android.util.Log;

import com.example.makeushifive.src.main.home.add.interfaces.AddActivityView;
import com.example.makeushifive.src.main.home.add.interfaces.AddRetrofitInterface;
import com.example.makeushifive.src.main.home.add.models.AddResponse;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makeushifive.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.example.makeushifive.src.ApplicationClass.getRetrofit;


public class AddService {
    private final AddActivityView addActivityView;

    public AddService(AddActivityView addActivityView) {
        this.addActivityView = addActivityView;
    }

    public void PostAddSchedule(final JsonObject jsonObject) throws JSONException{
        Log.e("추가하는 일정",jsonObject.toString());
        final AddRetrofitInterface addRetrofitInterface=getRetrofit().create(AddRetrofitInterface.class);
        addRetrofitInterface.PostAddSchedule(RequestBody.create(jsonObject.toString(),MEDIA_TYPE_JSON)).enqueue(new Callback<AddResponse>() {
            @Override
            public void onResponse(Call<AddResponse> call, Response<AddResponse> response) {
                assert response.body() != null;
                if(response.body().getCode()==100){
                    try {
                        addActivityView.postAddSuccess(response.body().getResult());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    addActivityView.postAddFail();
                }
            }

            @Override
            public void onFailure(Call<AddResponse> call, Throwable t) {
                addActivityView.postAddFail();
            }
        });
    }

    public void PostAddTaskRepeat(final JsonObject jsonObject) throws  JSONException{
        Log.e("추가하는 반복일정",""+jsonObject);
        final AddRetrofitInterface addRetrofitInterface=getRetrofit().create(AddRetrofitInterface.class);
        addRetrofitInterface.PostAddTaskRepeat(RequestBody.create(jsonObject.toString(),MEDIA_TYPE_JSON)).enqueue(new Callback<AddResponse>() {
            @Override
            public void onResponse(Call<AddResponse> call, Response<AddResponse> response) {
                assert response.body() != null;
                if(response.body().getCode()==100){
                    addActivityView.postAddTaskRepeatSuccess();
                }else{
                    addActivityView.postAddTaskRepeatFail();
                }
            }

            @Override
            public void onFailure(Call<AddResponse> call, Throwable t) {
                addActivityView.postAddTaskRepeatFail();

            }
        });
    }
}
