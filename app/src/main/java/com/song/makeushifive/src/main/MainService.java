package com.song.makeushifive.src.main;

import android.util.Log;

import com.song.makeushifive.src.main.interfaces.MainActivityView;
import com.song.makeushifive.src.main.interfaces.MainRetrofitInterface;
import com.song.makeushifive.src.main.models.MainResponse;
import com.song.makeushifive.src.main.models.ProfileResponse;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.song.makeushifive.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.song.makeushifive.src.ApplicationClass.getRetrofit;

public class MainService {

    MainActivityView mainActivityView;

    public MainService(MainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    public void patchProfile(String profileUrl) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("profileUrl",profileUrl);
        MainRetrofitInterface mainRetrofitInterface = getRetrofit().create(MainRetrofitInterface.class);
        mainRetrofitInterface.PatchProfileUrl(RequestBody.create(jsonObject.toString(),MEDIA_TYPE_JSON)).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, @NotNull Response<ProfileResponse> response) {

                assert response.body() != null;
                if(response.body().getCode()==100){
                    mainActivityView.ChangeProfileSuccess();
                }else{
                    mainActivityView.ChangeProfileFail();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                mainActivityView.ChangeProfileFail();
            }
        });

    }

    public void getUser(String nickname){
        Log.e("sned getUser",""+nickname);
        MainRetrofitInterface mainRetrofitInterface = getRetrofit()
                .create(MainRetrofitInterface.class);
        mainRetrofitInterface.getUser(nickname).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                try{

                    if(response.body()!=null){
                        if(response.body().getCode()==100){
                            Log.e("success",""+"success");
                            mainActivityView.getUserSuccess();
                        }else{
                            Log.e("fail",""+"100아님");
                            mainActivityView.getUserFail();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                mainActivityView.getUserFail();
            }
        });
    }


}
