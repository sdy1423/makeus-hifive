package com.song.makeushifive.src.main.setting.change;

import android.util.Log;

import com.song.makeushifive.src.main.setting.change.interfaces.ChangeActivityView;
import com.song.makeushifive.src.main.setting.change.interfaces.ChangeRetrofitInterface;
import com.song.makeushifive.src.main.setting.change.models.ChangeResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.song.makeushifive.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.song.makeushifive.src.ApplicationClass.getRetrofit;

public class ChangeService {

    ChangeActivityView changeActivityView;

    public ChangeService(ChangeActivityView changeActivityView) {
        this.changeActivityView = changeActivityView;
    }

    //유저 정보 수정 API
    public void patchUserInfoChange(String pw, String nickname) throws JSONException {
        JSONObject params = new JSONObject();
//        params.put("email", email);
//        params.put("profileUrl", profileUrl);
        params.put("pw", pw);
        params.put("nickname", nickname);

        Log.e("sned params",""+params);

        ChangeRetrofitInterface changeRetrofitInterface = getRetrofit().create(ChangeRetrofitInterface.class);
        changeRetrofitInterface.PatchChangeInfo(RequestBody.create(params.toString(),MEDIA_TYPE_JSON)).
                enqueue(new Callback<ChangeResponse>() {
            @Override
            public void onResponse(Call<ChangeResponse> call, Response<ChangeResponse> response) {
                int code =0;
                if(response.body() != null){
                    try {
                        code = response.body().getCode();
                        changeActivityView.patchUserInfoChangeSuccess(code);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    changeActivityView.patchUserInfoChangeFail();
                }
            }

            @Override
            public void onFailure(Call<ChangeResponse> call, Throwable t) {
                changeActivityView.patchUserInfoChangeFail();
            }
        });

    }

    public void postOverlapUserName(final String nickname) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("nickname", nickname);

        final ChangeRetrofitInterface changeRetrofitInterface = getRetrofit().create(ChangeRetrofitInterface.class);
        changeRetrofitInterface.PostOverlap(RequestBody.create(params.toString(), MEDIA_TYPE_JSON)).enqueue(new Callback<ChangeResponse>() {
            @Override
            public void onResponse(Call<ChangeResponse> call, Response<ChangeResponse> response) {
                int code = 0;
                assert response.body() != null;
                try{
                    code = response.body().getCode();
                    String message;
                    message = response.body().getMessage();
                    changeActivityView.postOverlapUserNameSuccess(code, message);
                } catch (Exception e) {
                    e.printStackTrace();
                    changeActivityView.postOverlapUserNameFail();
                }
            }

            @Override
            public void onFailure(Call<ChangeResponse> call, Throwable t) {
                changeActivityView.postOverlapUserNameFail();
            }

        });

    }

    void getUser(String nickname){
        ChangeRetrofitInterface changeRetrofitInterface = getRetrofit().create(ChangeRetrofitInterface.class);
        changeRetrofitInterface.getUser(nickname).enqueue(new Callback<ChangeResponse>() {
            @Override
            public void onResponse(Call<ChangeResponse> call, Response<ChangeResponse> response) {
                try {
                    if(response.body().getCode()==100){

                        changeActivityView.getUserSuccess();
                    }else{
                        changeActivityView.getUserFail();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    changeActivityView.getUserFail();
                }
            }

            @Override
            public void onFailure(Call<ChangeResponse> call, Throwable t) {
                changeActivityView.getUserFail();
            }
        });
    }
    void deleteUser(){
        ChangeRetrofitInterface changeRetrofitInterface = getRetrofit().create(ChangeRetrofitInterface.class);
        changeRetrofitInterface.deleteUser().enqueue(new Callback<ChangeResponse>() {
            @Override
            public void onResponse(Call<ChangeResponse> call, Response<ChangeResponse> response) {
                try {
                    if(Objects.requireNonNull(response.body()).getCode()==100){
                        changeActivityView.deleteUserSuccess();
                    }else{
                        changeActivityView.deleteUserFail();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    changeActivityView.deleteUserFail();
                }
            }

            @Override
            public void onFailure(Call<ChangeResponse> call, Throwable t) {
                try {

                    changeActivityView.deleteUserFail();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



}