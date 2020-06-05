package com.example.makeushifive.src.main.notification;

import android.app.Notification;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeushifive.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static com.example.makeushifive.src.ApplicationClass.Chatting;
import static com.example.makeushifive.src.ApplicationClass.DATE_FORMAT;
import static com.example.makeushifive.src.ApplicationClass.DOT_FORMAT;

public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.ViewHolder> {

    ArrayList<NotificationInfo> notificationInfos=null;
    Context mContext;

    public NotificationRecyclerAdapter(ArrayList<NotificationInfo> notificationInfos, Context mContext) {
        this.notificationInfos = notificationInfos;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public NotificationRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_recycler,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationRecyclerAdapter.ViewHolder holder, int position) {
        holder.mIvColor1.setVisibility(View.INVISIBLE);
        holder.mIvColor2.setVisibility(View.INVISIBLE);
        holder.mIvColor3.setVisibility(View.INVISIBLE);
        holder.mIvColor4.setVisibility(View.INVISIBLE);
        holder.mIvColor5.setVisibility(View.INVISIBLE);
        holder.mIvColor6.setVisibility(View.INVISIBLE);
        holder.mIvColor7.setVisibility(View.INVISIBLE);
        holder.mIvColor8.setVisibility(View.INVISIBLE);
        int color = notificationInfos.get(position).getColor();
        switch (color){
            case 1:
                holder.mIvColor1.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.mIvColor2.setVisibility(View.VISIBLE);
                break;
            case 3:
                holder.mIvColor3.setVisibility(View.VISIBLE);
                break;
            case 4:
                holder.mIvColor4.setVisibility(View.VISIBLE);
                break;
            case 5:
                holder.mIvColor5.setVisibility(View.VISIBLE);
                break;
            case 6:
                holder.mIvColor6.setVisibility(View.VISIBLE);
                break;
            case 7:
                holder.mIvColor7.setVisibility(View.VISIBLE);
                break;
            case 8:
                holder.mIvColor8.setVisibility(View.VISIBLE);
                break;
        }
//        "taskNo": 145,
//                "title": "일정등록테스트",
//                "color": 7,
//                "day": "2020-06-05",
//                "week": 6,
//                "time": "12:00:00",
//                "nickname": "deok"
        holder.mTvTitle.setText(notificationInfos.get(position).getTitle());
        Date date1 = null;
        try {
            date1 = DATE_FORMAT.parse(notificationInfos.get(position).getDay());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String date = Chatting.format(Objects.requireNonNull(date1));

        String time = getTimeForm(notificationInfos.get(position).getTime());
        String ShowDateTime = "";
        ShowDateTime+=date;
        ShowDateTime+=" / ";
        ShowDateTime+=time;
        holder.mTvDateTime.setText(ShowDateTime);
        String nickname = notificationInfos.get(position).getNickname();
        holder.mTvMessage.setText(nickname+"님이 전공 스터디 일정을 공유하였습니다.");
        Log.e("알림","알림"+holder.mTvMessage);
    }

    private String getTimeForm(String time) {
        String returnString="";
        if(time.length()==17){
            try{
                returnString+=time.substring(0,5);
                returnString+=time.substring(8,14);
//                Log.e("ShowTime",""+time.length());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            try{
                returnString+=time.substring(0,5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnString;
    }

    @Override
    public int getItemCount() {
        return notificationInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mIvColor1,mIvColor2,mIvColor3,mIvColor4,mIvColor5,mIvColor6,mIvColor7,mIvColor8;
        TextView mTvTitle,mTvDateTime,mTvMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mIvColor1=itemView.findViewById(R.id.notification_item_iv_color_1);
            mIvColor2=itemView.findViewById(R.id.notification_item_iv_color_2);
            mIvColor3=itemView.findViewById(R.id.notification_item_iv_color_3);
            mIvColor4=itemView.findViewById(R.id.notification_item_iv_color_4);
            mIvColor5=itemView.findViewById(R.id.notification_item_iv_color_5);
            mIvColor6=itemView.findViewById(R.id.notification_item_iv_color_6);
            mIvColor7=itemView.findViewById(R.id.notification_item_iv_color_7);
            mIvColor8=itemView.findViewById(R.id.notification_item_iv_color_8);
            mTvTitle = itemView.findViewById(R.id.notification_item_tv_title);
            mTvDateTime = itemView.findViewById(R.id.notification_item_tv_day_time);
            mTvMessage=itemView.findViewById(R.id.notification_item_tv_message);
        }
    }

}
