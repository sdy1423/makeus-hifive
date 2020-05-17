package com.example.makeushifive.src.main.feed;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.makeushifive.R;
import com.example.makeushifive.src.BaseFragment;
import com.example.makeushifive.src.main.feed.interfaces.FeedFragmentView;
import com.example.makeushifive.src.main.feed.models.FeedResponse;
import com.google.firebase.auth.FederatedAuthProvider;

import java.util.ArrayList;

public class FeedFragment extends BaseFragment implements FeedFragmentView {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        FeedService feedService = new FeedService(this);
        feedService.getSchedule();



        return rootView;
    }

    @Override
    public void getScheduleSuccess(ArrayList<FeedResponse.Result> result) {
        //TODO RecyclerView만들기

    }

    @Override
    public void getScheduleFail() {

    }
}
