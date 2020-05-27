package com.example.makeushifive.src.main.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeushifive.R;
import com.example.makeushifive.src.main.feed.FeedRecyclerAdapter;

import java.util.ArrayList;

//RecyclerView.Adapter<FeedRecyclerAdapterr.ViewHolder>

public class AddScheduleRecyclerviewAdapter extends RecyclerView.Adapter<AddScheduleRecyclerviewAdapter.ViewHolder>{

    private ArrayList<PickedDayTasks> pickedDayTasks = null;

    public AddScheduleRecyclerviewAdapter(ArrayList<PickedDayTasks> pickedDayTasks) {
        this.pickedDayTasks = pickedDayTasks;
    }

    @NonNull
    @Override
    public AddScheduleRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_calendar_today_schedule,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddScheduleRecyclerviewAdapter.ViewHolder holder, int position)  {
        int color = pickedDayTasks.get(position).getColor();
        for(int i=0;i<8;i++){
            if(color-1==i){
                holder.mIvColor[i].setVisibility(View.VISIBLE);
            }else{
                holder.mIvColor[i].setVisibility(View.INVISIBLE);
            }
        }
        holder.mTvTitle.setText(pickedDayTasks.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return pickedDayTasks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTvTitle;
        int[] colors ={R.id.item_home_picked_schedule_iv_color_one,R.id.item_home_picked_schedule_iv_color_two,R.id.item_home_picked_schedule_iv_color_three,R.id.item_home_picked_schedule_iv_color_four,
                R.id.item_home_picked_schedule_iv_color_five,R.id.item_home_picked_schedule_iv_color_six,R.id.item_home_picked_schedule_iv_color_seven,R.id.item_home_picked_schedule_iv_color_eight};
        ImageView[] mIvColor =new ImageView[8];
        ImageView mIvChange;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            for(int i=0;i<8;i++){
                mIvColor[i]=itemView.findViewById(colors[i]);
            }
            mTvTitle=itemView.findViewById(R.id.item_home_picked_schedule_tv_title);
            mIvChange=itemView.findViewById(R.id.item_home_picked_schedule_iv_change);
            mIvChange.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.item_home_picked_schedule_iv_change:
                    //TODO 일정정보 변경하기


                    break;
            }

        }
    }
}