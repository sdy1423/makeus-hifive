package com.example.makeushifive.src.main.feed;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseFragment;
import com.example.makeushifive.src.main.feed.interfaces.FeedFragmentView;
import com.example.makeushifive.src.main.feed.models.FeedResponse;
import com.google.firebase.auth.FederatedAuthProvider;

import java.util.ArrayList;

import okhttp3.internal.concurrent.Task;

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
    public void getScheduleSuccess(ArrayList<FeedResponse.Result> result) {
        //TODO RecyclerView만들기
        int taskNo = 0;
        String title = null,location=null,day=null,time=null,count=null;
        for(int i=0;i<result.size();i++){
            TASK task = new TASK(taskNo,title,location,day,time,count);
            tasks.add(task);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        feedRecyclerAdapter = new FeedRecyclerAdapter(tasks);
        mRecyclerView.setAdapter(feedRecyclerAdapter);
    }

    @Override
    public void getScheduleFail() {

    }
}
