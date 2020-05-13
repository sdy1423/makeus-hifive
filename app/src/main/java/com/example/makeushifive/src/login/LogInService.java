package com.example.makeushifive.src.login;

import android.util.Log;
import android.view.View;

import com.example.makeushifive.src.login.interfaces.LoginActivityView;
import com.example.makeushifive.src.login.interfaces.LoginRetrofitInterface;
import com.example.makeushifive.src.login.models.LoginResponse;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makeushifive.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.example.makeushifive.src.ApplicationClass.getRetrofit;

public class LogInService {
    private final LoginActivityView mloginActivityView;

    public LogInService(final LoginActivityView loginActivityView) {
        this.mloginActivityView = loginActivityView;
    }

    public void postLogin(String email,String pw) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("email",email);
        params.put("pw",pw);

        final LoginRetrofitInterface loginRetrofitInterface = getRetrofit().create(LoginRetrofitInterface.class);

        loginRetrofitInterface.PostLogIn(RequestBody.create(params.toString(),MEDIA_TYPE_JSON)).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse != null) {
                    int code = loginResponse.getCode();
                    if(code==100){
                        mloginActivityView.LogInSuccess(loginResponse.getResult());
                    }else{
                        mloginActivityView.LoginFail();
                    }
                }else{
                    mloginActivityView.LoginFail();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

}
