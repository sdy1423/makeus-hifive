package com.example.makeushifive.src.main.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.example.makeushifive.R;
import com.example.makeushifive.src.main.feed.FeedRecyclerAdapter;
import com.example.makeushifive.src.main.taskchange.TaskChangeActivity;

import java.text.ParseException;
import java.util.ArrayList;

//RecyclerView.Adapter<FeedRecyclerAdapterr.ViewHolder>

public class AddScheduleRecyclerviewAdapter extends RecyclerView.Adapter<AddScheduleRecyclerviewAdapter.ViewHolder>{

    private ArrayList<PickedDayTasks> pickedDayTasks = null;
    public AddScheduleRecyclerviewAdapter(ArrayList<PickedDayTasks> pickedDayTasks) {
        this.pickedDayTasks = pickedDayTasks;
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int pos,int taskNo,String time) throws ParseException;
        void onDeleteClick(View v,int pos,int taskNo);
        void onTitleClick(View v,int pos,int taskNo,int color);
    }
    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
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
        if (pickedDayTasks.size() == 1 && pickedDayTasks.get(0).isEmptyFlag()) {
            for (int i = 0; i < 8; i++) {
                holder.mIvColor[i].setVisibility(View.INVISIBLE);
            }
            holder.mTvTitle.setText(pickedDayTasks.get(0).getTitle());
            holder.mIvColor9.setVisibility(View.VISIBLE);
        } else {
            holder.mIvColor9.setVisibility(View.INVISIBLE);
            for (int i = 0; i < 8; i++) {
                if (color - 1 == i) {
                    holder.mIvColor[i].setVisibility(View.VISIBLE);
                } else {
                    holder.mIvColor[i].setVisibility(View.INVISIBLE);
                }
            }
            holder.mIvColor9.setVisibility(View.INVISIBLE);
            holder.mTvTitle.setText(pickedDayTasks.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return pickedDayTasks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTvTitle;
        int[] colors ={R.id.item_home_picked_schedule_iv_color_one,R.id.item_home_picked_schedule_iv_color_two,R.id.item_home_picked_schedule_iv_color_three,R.id.item_home_picked_schedule_iv_color_four,
                R.id.item_home_picked_schedule_iv_color_five,R.id.item_home_picked_schedule_iv_color_six,R.id.item_home_picked_schedule_iv_color_seven,R.id.item_home_picked_schedule_iv_color_eight};
        ImageView[] mIvColor =new ImageView[8];
        ImageView mIvColor9;
        ImageView mIvChange,mIvDelete;
        SwipeLayout swipeLayout;
        LinearLayout topWrapper;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            for(int i=0;i<8;i++){
                mIvColor[i]=itemView.findViewById(colors[i]);
            }
            mIvColor9=itemView.findViewById(R.id.item_home_picked_schedule_iv_color_nine);
            mTvTitle=itemView.findViewById(R.id.item_home_picked_schedule_tv_title);
            mIvChange=itemView.findViewById(R.id.item_home_picked_schedule_iv_change);
            mIvChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        if(!pickedDayTasks.isEmpty()){
                            int taskNo = pickedDayTasks.get(pos).getTaskNo();
                            String time = pickedDayTasks.get(pos).getTime();
                            try {
    //                            Log.e("타일",""+year+" "+month+" "+day);
                                //TODO 리스터 달고 밖에서 일정정보 수정으로 이동(인텐트로 taskNo도 같이 전송)
                                mListener.onItemClick(v,pos,taskNo,time);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            });

            topWrapper = itemView.findViewById(R.id.top_wrapper);
            swipeLayout=itemView.findViewById(R.id.swipe_layout);
            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);





            swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onStartOpen(SwipeLayout layout) {

                }

                @Override
                public void onOpen(SwipeLayout layout) {

                }

                @Override
                public void onStartClose(SwipeLayout layout) {

                }

                @Override
                public void onClose(SwipeLayout layout) {

                }

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

                }

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

                }
            });

            mIvDelete=itemView.findViewById(R.id.item_home_picked_schedule_iv_delete);
            mIvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        Log.e("deleteclick","deleteclick");
                        try{
                            if(!pickedDayTasks.isEmpty()){
                                int taskNo = pickedDayTasks.get(pos).getTaskNo();
                                mListener.onDeleteClick(v,pos,taskNo);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            mTvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        try{
                            if(!pickedDayTasks.isEmpty()){
                                int taskNo = pickedDayTasks.get(pos).getTaskNo();
                                int color = pickedDayTasks.get(pos).getColor();
                                mListener.onTitleClick(v,pos,taskNo,color);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }


    }
}