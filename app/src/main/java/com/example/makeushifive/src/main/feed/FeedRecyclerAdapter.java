package com.example.makeushifive.src.main.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeushifive.R;

import java.util.ArrayList;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.ViewHolder> {

    private ArrayList<TASK> tasks = null;

    public FeedRecyclerAdapter(ArrayList<TASK> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_recycler,parent,false);
        ViewHolder viewHolder=new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //TODO 화면에 표시할 것들 (CGV에서 CHART참고하기)
        String title = tasks.get(position).getTitle();
        holder.mTvTitle.setText(title);

        String day = tasks.get(position).getDay();
        holder.mTvDay.setText(day);//TODO 수정요함

        String numofpeople = tasks.get(position).getCount();
        holder.mTvNumOfPeople.setText(numofpeople);

    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTvTitle,mTvDay,mTvNumOfPeople;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //TODO 클릭이벤트들 정의하기 IvMainImg = itemView.findViewById(R.id.chart_iv_mainImg);
            mTvTitle = itemView.findViewById(R.id.item_feed_tv_title);
            mTvDay=itemView.findViewById(R.id.item_feed_tv_date);
            mTvNumOfPeople=itemView.findViewById(R.id.item_feed_tv_num_of_people);

        }

        @Override
        public void onClick(View v) {
            //TODO 피드를 클릭하면 채팅창으로 간다.
            switch (v.getId()){

            }
        }
    }
}
