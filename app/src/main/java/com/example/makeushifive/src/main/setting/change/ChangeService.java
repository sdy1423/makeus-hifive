package com.example.makeushifive.src.main.setting.change;

import android.util.Log;

import com.example.makeushifive.src.main.chatting.interfaces.ChattingRetrofitInterface;
import com.example.makeushifive.src.main.chatting.models.ChattingResponse;
import com.example.makeushifive.src.main.setting.change.interfaces.ChangeActivityView;
import com.example.makeushifive.src.main.setting.change.interfaces.ChangeRetrofitInterface;
import com.example.makeushifive.src.main.setting.change.models.ChangeResponse;
import com.example.makeushifive.src.main.setting.change.models.ImageResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Part;

import static com.example.makeushifive.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.example.makeushifive.src.ApplicationClass.getRetrofit;

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
                    code = response.body().getCode();
                    changeActivityView.patchUserInfoChangeSuccess(code);
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
                code = response.body().getCode();
                String message;
                message = response.body().getMessage();
                changeActivityView.postOverlapUserNameSuccess(code, message);
            }

            @Override
            public void onFailure(Call<ChangeResponse> call, Throwable t) {
                changeActivityView.postOverlapUserNameFail();
            }

        });

    }



}