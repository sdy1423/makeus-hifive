package com.example.makeushifive.src.main.feed;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseFragment;
import com.example.makeushifive.src.main.NotificationDialogAdapter;
import com.example.makeushifive.src.main.feed.interfaces.FeedFragmentView;
import com.example.makeushifive.src.main.feed.models.FeedResponse;
import com.example.makeushifive.src.main.home.search.SearchActivity;

import java.util.ArrayList;

public class FeedFragment extends BaseFragment implements FeedFragmentView {

    ArrayList<TASK> tasks = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private FeedRecyclerAdapter feedRecyclerAdapter;

    ImageView mIvSearch,mIvNoti;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        FeedService feedService = new FeedService(this);
        feedService.getSchedule();

        mRecyclerView=rootView.findViewById(R.id.feed_rv_recycler);

        mIvNoti = rootView.findViewById(R.id.feed_iv_alarm);
        mIvNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationDialogAdapter adapter = new NotificationDialogAdapter(getContext());
                assert getFragmentManager() != null;
                adapter.show(getFragmentManager(),"notification");
            }
        });


        mIvSearch = rootView.findViewById(R.id.feed_iv_search);
        mIvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void getScheduleSuccess(ArrayList<FeedResponse.Result> result) {
        //TODO
        int taskNo = 0,color=0,week=0,count=0;
        String title = null, location = null, day = null, time = null;
        for (int i = 0; i < result.size(); i++) {
            taskNo=result.get(i).getTaskNo();
            title=result.get(i).getTitle();
            color=result.get(i).getColor();
            day=result.get(i).getDay();
            week=result.get(i).getWeek();
            count=result.get(i).getCount();
            TASK task = new TASK(taskNo, title, color, day, week, count);
            tasks.add(task);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        feedRecyclerAdapter = new FeedRecyclerAdapter(tasks);
        mRecyclerView.setAdapter(feedRecyclerAdapter);

    }

    @Override
    public void getScheduleFail() {

    }
}
