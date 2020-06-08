package com.example.makeushifive.src.main.home.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseActivity;
import com.example.makeushifive.src.main.feed.UserInfo;
import com.example.makeushifive.src.main.home.search.interfaces.SearchActivityView;
import com.example.makeushifive.src.main.home.search.models.SearchResponse;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
            private Timer timer = new Timer();
            private final long DELAY = 1000;

            @Override
            public void afterTextChanged(Editable s) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                //TODO 글자 입력할때마다 API로 쏜다
                                searchText = mEdtSearch.getText().toString();
                                try {
                                    if(searchText.length()>1){
                                        if(searchText.charAt(0)=='#'){
                                            Log.e("태그검색 만들기",searchText);
                                            searchService.getSearchTag(searchText);
                                            searchService.getSearchTag(searchText);
                                        }
                                        else{
                                            Log.e("타이틀 검색",searchText);
                                            searchService.getSearchTitle(searchText);
                                            searchService.getSearchTitle(searchText);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        },DELAY
                );
            }
        });



    }

    @Override
    public void getSearchSuccess(ArrayList<SearchResponse.Result> result) {
        //TODO 리사이클러뷰 만든다. (FeedFragment랑 똑같이)
        try {
            String title,day;
            int color,taskNo = 0,week,count,mastertaskNo=0;
            items.clear();
            if(!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    try {
                        taskNo = result.get(i).getTaskNo();
                        Log.e("taskNo: ",""+taskNo);
                        mastertaskNo = result.get(i).getMastertaskNo();
                        Log.e("mastertaskNo: ",""+mastertaskNo);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("튕김: ","");
                    }
                    if(taskNo==0 && mastertaskNo>0){
                        taskNo=mastertaskNo;
                    }
                    title = result.get(i).getTitle();
                    color = result.get(i).getColor();
                    day = result.get(i).getDay();
                    week = result.get(i).getWeek();
                    count = result.get(i).getCount();
                    ArrayList<UserInfo> userInfos = new ArrayList<>();
                    try {
                        if (!result.get(i).getUserInfo().isEmpty()) {
                            for (int j = 0; j < result.get(i).getUserInfo().size(); j++) {
                                int userno = result.get(i).getUserInfo().get(j).getUserNo();
                                String profileurl = result.get(i).getUserInfo().get(j).getProfileUrl();
                                UserInfo userInfo = new UserInfo(userno, profileurl);
                                userInfos.add(userInfo);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ITEM item = new ITEM(taskNo, title, color, day, week, count, userInfos);
                    items.add(item);
                    //TODO 배열에 담는다.
                }
            }
            //TODO recyclerview 만들어
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            SearchRecyclerAdapter searchRecyclerAdapter = new SearchRecyclerAdapter(items, getApplicationContext());
            recyclerView.setAdapter(searchRecyclerAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getSearchFail() {

    }
}
