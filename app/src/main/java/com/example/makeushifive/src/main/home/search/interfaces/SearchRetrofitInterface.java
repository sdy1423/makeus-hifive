package com.example.makeushifive.src.main.home.search.interfaces;

import com.example.makeushifive.src.main.home.search.models.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchRetrofitInterface {


    @GET("/task")
    Call<SearchResponse> getSearchTitle(@Query("title") String title);

    @GET("/task")
    Call<SearchResponse> getTagTitle(@Query("tag") String tag);

}
