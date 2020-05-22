package com.example.makeushifive.src.main.feed;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseFragment;
import com.example.makeushifive.src.main.feed.interfaces.FeedFragmentView;
import com.example.makeushifive.src.main.feed.models.FeedResponse;

import java.util.ArrayList;

public class FeedFragment extends BaseFragment implements FeedFragmentView {

    ArrayList<TASK> tasks = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private FeedRecyclerAdapter feedRecyclerAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        FeedService feedService = new FeedService(this);
        feedService.getSchedule();

        mRecyclerView=rootView.findViewById(R.id.feed_rv_recycler);


        return rootView;
    }

    @Override
    public void getScheduleSuccess(FeedResponse result) {
        //TODO
        if(result.getCode()==100){
            int taskNo = 0;
            String title = null,location=null,day=null,time=null,count=null;
            for(int i=0;i<result.getResult().size();i++){
                TASK task = new TASK(taskNo,title,location,day,time,count);
                tasks.add(task);
            }
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            feedRecyclerAdapter = new FeedRecyclerAdapter(tasks);
            mRecyclerView.setAdapter(feedRecyclerAdapter);
        }else if(result.getCode()==201){
            //일정이 없다
        }else if(result.getCode()==200){
            //유효하지 않은 토큰
        }
    }

    @Override
    public void getScheduleFail() {

    }
}
