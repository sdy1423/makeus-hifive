package com.song.makeushifive.src.main.chatting.share;

import android.util.Log;

import com.song.makeushifive.src.main.chatting.share.interfaces.ShareActivityView;
import com.song.makeushifive.src.main.chatting.share.interfaces.ShareRetrofitInterface;
import com.song.makeushifive.src.main.chatting.share.models.SearchUserResponse;
import com.song.makeushifive.src.main.chatting.share.models.SharedUserResponse;
import com.song.makeushifive.src.main.chatting.share.models.TaskShareResponse;
import com.song.makeushifive.src.main.models.MainResponse;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.song.makeushifive.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.song.makeushifive.src.ApplicationClass.getRetrofit;

public class ShareService {
    ShareActivityView shareActivityView;

    public ShareService(ShareActivityView shareActivityView) {
        this.shareActivityView = shareActivityView;
    }

    public void postTaskShare(final JSONObject jsonObject)throws JSONException {
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
        Log.e("in service nickname",""+nickname);
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
    void getUser(String nickname){
        ShareRetrofitInterface shareRetrofitInterface =getRetrofit().create(ShareRetrofitInterface.class);
        shareRetrofitInterface.getUser(nickname).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                try {
                    assert response.body() != null;
                    int code = response.body().getCode();
                    if(code==100){
                        shareActivityView.getUserSuccess();

                    }else{
                        shareActivityView.getSearchUserFail();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    shareActivityView.getSearchUserFail();
                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                shareActivityView.getSearchUserFail();

            }
        });
    }

}
