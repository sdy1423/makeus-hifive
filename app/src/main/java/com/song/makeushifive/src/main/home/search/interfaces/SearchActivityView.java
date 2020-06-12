package com.song.makeushifive.src.main.home.search.interfaces;

import com.song.makeushifive.src.main.home.search.models.SearchResponse;

import java.util.ArrayList;

public interface SearchActivityView {
    void getSearchSuccess(ArrayList<SearchResponse.Result> result);
    void getSearchFail();

}
