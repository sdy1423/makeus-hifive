package com.example.makeushifive.src.main.chatting.share;

import android.util.Log;

import com.example.makeushifive.src.main.chatting.share.interfaces.ShareActivityView;
import com.example.makeushifive.src.main.chatting.share.interfaces.ShareRetrofitInterface;
import com.example.makeushifive.src.main.chatting.share.models.SearchUserResponse;
import com.example.makeushifive.src.main.chatting.share.models.SharedUserResponse;
import com.example.makeushifive.src.main.chatting.share.models.TaskShareResponse;
import com.example.makeushifive.src.main.home.search.interfaces.SearchRetrofitInterface;
import com.google.gson.JsonObject;

import org.json.JSONException;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makeushifive.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.example.makeushifive.src.ApplicationClass.getRetrofit;

public class ShareService {
    ShareActivityView shareActivityView;

    public ShareService(ShareActivityView shareActivityView) {
        this.shareActivityView = shareActivityView;
    }

    public void postTaskShare(final JsonObject jsonObject)throws JSONException {
        Log.e("send object",jsonObject.toString());
        ShareRetrofitInterface shareRetrofitInterface = getRetrofit()
                .create(ShareRetrofitInterface.class);
        shareRetrofitInterface.postTaskShare(RequestBody.create(jsonObject.toString(),MEDIA_TYPE_JSON))
                .enqueue(new Callback<TaskShareResponse>() {
            @Override
            public void onResponse(Call<TaskShareResponse> call, Response<TaskShareResponse> response) {
                if(response.body()!=null){
                    int code =response.body().getCode();
                    String message =response.body().getMessage();
                    if(code==100 ||code==201 || code==202){
                        shareActivityView.postShareSuccess(code,message);
                    }else{
                        shareActivityView.postShareFail();
                    }
                }

            }

            @Override
            public void onFailure(Call<TaskShareResponse> call, Throwable t) {
                shareActivityView.postShareFail();
            }
        });

    }

    public void getRecentSharedUser(){
        ShareRetrofitInterface shareRetrofitInterface = getRetrofit()
                .create(ShareRetrofitInterface.class);
        shareRetrofitInterface.getRecentSharedUser().enqueue(new Callback<SharedUserResponse>() {
            @Override
            public void onResponse(Call<SharedUserResponse> call, Response<SharedUserResponse> response) {
                if (response.body() != null) {
                    shareActivityView.getRecentShareSuccess(response.body().getResult());
                }else{
                    shareActivityView.getRecentShareFail();
                }

            }

            @Override
            public void onFailure(Call<SharedUserResponse> call, Throwable t) {
                shareActivityView.getRecentShareFail();
            }
        });

    }

    public void getSearchUser(String nickname){
        ShareRetrofitInterface shareRetrofitInterface = getRetrofit()
                .create(ShareRetrofitInterface.class);
        shareRetrofitInterface.getSearchUser(nickname).enqueue(new Callback<SearchUserResponse>() {
            @Override
            public void onResponse(Call<SearchUserResponse> call, Response<SearchUserResponse> response) {

                if(response.body()!=null){
                    shareActivityView.getSearchUserSuccess(response.body().getResult());
                }else{
                    shareActivityView.getSearchUserFail();
                }
            }

            @Override
            public void onFailure(Call<SearchUserResponse> call, Throwable t) {
                shareActivityView.getSearchUserFail();
            }
        });

    }
}
