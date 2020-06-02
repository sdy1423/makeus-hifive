package com.example.makeushifive.src.main.home.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseActivity;
import com.example.makeushifive.src.main.home.search.interfaces.SearchActivityView;
import com.example.makeushifive.src.main.home.search.models.SearchResponse;

import java.util.ArrayList;

public class SearchActivity extends BaseActivity implements SearchActivityView {

    ImageView mIvClose;
    EditText mEdtSearch;
    String searchText;
    SearchService searchService;
    RecyclerView recyclerView;
    ArrayList<ITEM> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mIvClose= findViewById(R.id.search_iv_close);
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = findViewById(R.id.search_recyclerview);
        mEdtSearch= findViewById(R.id.search_edt_search);
        searchService = new SearchService(this);
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //TODO 글자 입력할때마다 API로 쏜다
                searchText = mEdtSearch.getText().toString();
                searchService.getSearchTitle(searchText);
                searchService.getSearchTitle(searchText);
            }
        });



    }

    @Override
    public void getSearchSuccess(ArrayList<SearchResponse.Result> result) {
        //TODO 리사이클러뷰 만든다. (FeedFragment랑 똑같이)
        try {
            String title,day;
            int color,taskNo,week,count;
            for(int i=0;i<result.size();i++){
                taskNo = result.get(i).getTaskNo();
                title = result.get(i).getTitle();
                color = result.get(i).getColor();
                day = result.get(i).getDay();
                week = result.get(i).getWeek();
                count = result.get(i).getCount();
                ITEM item = new ITEM(taskNo,title,color,day,week,count);
                items.add(item);
                //TODO 배열에 담는다.
            }
            //TODO recyclerview 만들어
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            SearchRecyclerAdapter searchRecyclerAdapter = new SearchRecyclerAdapter(items);
            recyclerView.setAdapter(searchRecyclerAdapter);



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getSearchFail() {

    }
}
