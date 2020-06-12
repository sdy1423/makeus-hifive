package com.song.makeushifive.src.main.home.search;

import android.util.Log;

import com.song.makeushifive.src.main.home.search.interfaces.SearchActivityView;
import com.song.makeushifive.src.main.home.search.interfaces.SearchRetrofitInterface;
import com.song.makeushifive.src.main.home.search.models.SearchResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.song.makeushifive.src.ApplicationClass.getRetrofit;

public class SearchService {
    SearchActivityView searchActivityView;

    public SearchService(SearchActivityView searchActivityView) {
        this.searchActivityView = searchActivityView;
    }

    void getSearchTitle(String title) {

        SearchRetrofitInterface searchRetrofitInterface = getRetrofit().
                create(SearchRetrofitInterface.class);
        searchRetrofitInterface.getSearchTitle(title).enqueue(new Callback<SearchResponse>(){
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if(response.body()!=null){
                    try{
                        if(response.body().code==100){
                            Log.e("검색성공",""+response.body().getResult());
                            searchActivityView.getSearchSuccess(response.body().getResult());
                        }else{
                            searchActivityView.getSearchFail();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                searchActivityView.getSearchFail();
            }
        });
    }
    void getSearchTag(String tag) {
        SearchRetrofitInterface searchRetrofitInterface = getRetrofit().
                create(SearchRetrofitInterface.class);
        searchRetrofitInterface.getSearchTitle(tag).enqueue(new Callback<SearchResponse>(){
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if(response.body()!=null){
                    if(response.body().code==100){
                        searchActivityView.getSearchSuccess(response.body().getResult());
                    }else{
                        searchActivityView.getSearchFail();
                    }
                }
            }
            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                searchActivityView.getSearchFail();
            }
        });
    }

}
