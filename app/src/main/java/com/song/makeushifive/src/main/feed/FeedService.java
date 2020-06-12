package com.song.makeushifive.src.main.feed;

import com.song.makeushifive.src.main.feed.interfaces.FeedFragmentView;
import com.song.makeushifive.src.main.feed.interfaces.FeedRetrofitInterface;
import com.song.makeushifive.src.main.feed.models.FeedResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.song.makeushifive.src.ApplicationClass.getRetrofit;

public class FeedService {
    FeedFragmentView feedFragmentView;

    public FeedService(FeedFragmentView feedFragmentView) {
        this.feedFragmentView = feedFragmentView;
    }

    public void getSchedule(){
        FeedRetrofitInterface feedRetrofitInterface =
                getRetrofit().create(FeedRetrofitInterface.class);
        feedRetrofitInterface.getSchedule().enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                if (response.body() != null) {
                    if(response.body().getCode()==100) {
                        feedFragmentView.getScheduleSuccess(response.body().getResult());
                    }else{
                        feedFragmentView.getScheduleFail();
                    }
                }else{
                    feedFragmentView.getScheduleFail();
                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                feedFragmentView.getScheduleFail();
            }
        });
    }
}
