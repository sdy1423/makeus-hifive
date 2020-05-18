package com.example.makeushifive.src.signup;

import android.util.Log;

import com.example.makeushifive.src.login.LoginActivity;
import com.example.makeushifive.src.signup.interfaces.SignupActivityView;
import com.example.makeushifive.src.signup.interfaces.SignupRetrofitInterface;
import com.example.makeushifive.src.signup.models.OverlapResponse;
import com.example.makeushifive.src.signup.models.SignupResponse;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.jar.JarEntry;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makeushifive.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.example.makeushifive.src.ApplicationClass.getRetrofit;

public class SignupService {
    private final SignupActivityView mSignupActivityView;

    public SignupService(SignupActivityView mSignupActivityView) {
        this.mSignupActivityView = mSignupActivityView;
    }

    public void PostOverlapUserName(final String nickname) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("nickname", nickname);

        final SignupRetrofitInterface signupRetrofitInterface = getRetrofit().create(SignupRetrofitInterface.class);
        signupRetrofitInterface.PostOverlap(RequestBody.create(params.toString(), MEDIA_TYPE_JSON)).enqueue(new Callback<OverlapResponse>() {
            @Override
            public void onResponse(Call<OverlapResponse> call, Response<OverlapResponse> response) {
                int code =0;
                if (response.body() != null) {
                    code = response.body().getCode();
                }
                String message;
                message=response.body().getMessage();
                Log.e("username",code+" "+message);
                mSignupActivityView.OverlapUserNameSuccess(code,message);
            }

            @Override
            public void onFailure(Call<OverlapResponse> call, Throwable t) {
                Log.e("username","fail");

            }
        });

    }
    public void PostOverlapEmail(String email) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("email", email);
        final SignupRetrofitInterface signupRetrofitInterface = getRetrofit().create(SignupRetrofitInterface.class);
        signupRetrofitInterface.PostOverlap(RequestBody.create(params.toString(), MEDIA_TYPE_JSON)).enqueue(new Callback<OverlapResponse>() {
            @Override
            public void onResponse(Call<OverlapResponse> call, @NotNull Response<OverlapResponse> response) {
                int code = 0;
                if (response.body() != null) {
                    code = response.body().getCode();
                }
                String message = response.body().getMessage();
                Log.e("통신 성공",code+" "+message);
                mSignupActivityView.OverlapEmailSuccess(code,message);
            }

            @Override
            public void onFailure(Call<OverlapResponse> call, Throwable t) {
                Log.e("통신 실패","fail");

            }
        });

    }
    public void PostSignUp(String email,String pw,String username) throws JSONException{
        JSONObject params = new JSONObject();
        params.put("email", email);
        params.put("pw",pw);
        params.put("nickname",username);
        final SignupRetrofitInterface signupRetrofitInterface = getRetrofit().create(SignupRetrofitInterface.class);
        signupRetrofitInterface.PostSignUp(RequestBody.create(params.toString(),MEDIA_TYPE_JSON)).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                int code =0;
                if(response.body()!=null){
                    code = response.body().getCode();
                    Log.e("code: ", String.valueOf(code));
                    if(code==100 || code==200 || code==201){
                        mSignupActivityView.SignUpSuccess(code);
                    }else{
                        mSignupActivityView.SignUpFail();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {

            }
        });
    }

}
