package com.song.makeushifive.src.main.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.song.makeushifive.R;

import java.util.ArrayList;

public class TodayRecyclerAdapter extends RecyclerView.Adapter<TodayRecyclerAdapter.ViewHolder>{

    private ArrayList<PickedDayTasks> pickedDayTasks = null;

    public TodayRecyclerAdapter(ArrayList<PickedDayTasks> pickedDayTasks) {
        this.pickedDayTasks = pickedDayTasks;
    }

    @NonNull
    @Override
    public TodayRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_today_schedule,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodayRecyclerAdapter.ViewHolder holder, int position)  {
        holder.mTvTitle.setText(pickedDayTasks.get(position).getTitle());
        String Time = pickedDayTasks.get(position).getTime();
//        show = show.substring(0,5);
//        show+="\n";
//        show+=pickedDayTasks.get(position).getLocation();


        String ShowTime="";
        if(Time.length()==17){
            try{
                ShowTime+=Time.substring(0,5);
                ShowTime+=Time.substring(8,14);
                Log.e("ShowTime",""+Time.length());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            try{
                ShowTime+=Time.substring(0,5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        holder.mTvDetail.setText(ShowTime);
        holder.mTvLocation.setText(pickedDayTasks.get(position).getLocation());
        int color = 0;
        color = pickedDayTasks.get(position).getColor();
        switch (color){
            case 1:
                holder.mTvTitle.setBackgroundResource(R.drawable.today_schedule_border_one);
                break;
            case 2:
                holder.mTvTitle.setBackgroundResource(R.drawable.today_schedule_border_two);
                break;
            case 3:
                holder.mTvTitle.setBackgroundResource(R.drawable.today_schedule_border_three);
                break;
            case 4:
                holder.mTvTitle.setBackgroundResource(R.drawable.today_schedule_border_four);

                break;
            case 5:
                holder.mTvTitle.setBackgroundResource(R.drawable.today_schedule_border_five);
                break;
            case 6:
                holder.mTvTitle.setBackgroundResource(R.drawable.today_schedule_border_six);
                break;
            case 7:
                holder.mTvTitle.setBackgroundResource(R.drawable.today_schedule_border_seven);
                break;
            case 8:
                holder.mTvTitle.setBackgroundResource(R.drawable.today_schedule_border_eight);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return pickedDayTasks.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTvTitle,mTvDetail;

        TextView mTvLocation;  //쓸수도 안쓸수도
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle=itemView.findViewById(R.id.today_tv_title);
            mTvDetail=itemView.findViewById(R.id.today_tv_detail);
            mTvLocation = itemView.findViewById(R.id.today_tv_detail_location);
        }
    }

}