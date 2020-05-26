package com.example.makeushifive.src.main.home.add;
import com.example.makeushifive.src.main.home.add.interfaces.AddActivityView;
import com.example.makeushifive.src.main.home.add.interfaces.AddRetrofitInterface;
import com.example.makeushifive.src.main.home.add.models.AddResponse;
import com.google.gson.JsonObject;

import org.json.JSONException;

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
        final AddRetrofitInterface addRetrofitInterface=getRetrofit().create(AddRetrofitInterface.class);
        addRetrofitInterface.PostAddSchedule(RequestBody.create(jsonObject.toString(),MEDIA_TYPE_JSON)).enqueue(new Callback<AddResponse>() {
            @Override
            public void onResponse(Call<AddResponse> call, Response<AddResponse> response) {
                assert response.body() != null;
                if(response.body().getCode()==100){
                    addActivityView.postAddSuccess();
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
}
