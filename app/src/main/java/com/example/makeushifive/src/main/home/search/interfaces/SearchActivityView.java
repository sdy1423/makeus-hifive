package com.example.makeushifive.src.main.home.search.interfaces;

import com.example.makeushifive.src.main.home.search.models.SearchResponse;

import java.util.ArrayList;

public interface SearchActivityView {
    void getSearchSuccess(ArrayList<SearchResponse.Result> result);
    void getSearchFail();

}
