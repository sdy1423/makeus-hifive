package com.song.makeushifive.src.main.home.add;
import android.util.Log;

import com.song.makeushifive.src.main.home.add.interfaces.AddActivityView;
import com.song.makeushifive.src.main.home.add.interfaces.AddRetrofitInterface;
import com.song.makeushifive.src.main.home.add.models.AddResponse;
import com.song.makeushifive.src.main.models.MainResponse;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.song.makeushifive.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.song.makeushifive.src.ApplicationClass.getRetrofit;


public class AddService {
    private final AddActivityView addActivityView;

    public AddService(AddActivityView addActivityView) {
        this.addActivityView = addActivityView;
    }

    public void PostAddSchedule(final JSONObject jsonObject) throws JSONException{
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

    public void getUser(String nickname){
        Log.e("sned getUser",""+nickname);
        AddRetrofitInterface addRetrofitInterface =getRetrofit().create(AddRetrofitInterface.class);
        addRetrofitInterface.getUser(nickname).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                try{

                    if(response.body()!=null){
                        if(response.body().getCode()==100){
                            addActivityView.getUserSuccess();
                        }else{
                            addActivityView.getUserFail();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                addActivityView.getUserFail();
            }
        });

    }

}
