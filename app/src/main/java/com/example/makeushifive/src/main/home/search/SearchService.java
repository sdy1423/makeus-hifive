package com.example.makeushifive.src.main.home.search;

import com.example.makeushifive.src.main.home.search.interfaces.SearchActivityView;
import com.example.makeushifive.src.main.home.search.interfaces.SearchRetrofitInterface;
import com.example.makeushifive.src.main.home.search.models.SearchResponse;
import com.example.makeushifive.src.main.setting.interfaces.SettingRetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makeushifive.src.ApplicationClass.getRetrofit;

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
