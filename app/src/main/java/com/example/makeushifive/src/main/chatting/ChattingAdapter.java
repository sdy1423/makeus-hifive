package com.example.makeushifive.src.main.chatting;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.makeushifive.R;

import java.util.ArrayList;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.ViewHolder> {

    Context context;
    ArrayList<Message> chatlist = new ArrayList<Message>();

    int CHAT_MINE = 0;
    int CHAT_PARTNER = 1;
    int USER_JOIN = 2;
    int USER_LEAVE = 3;

    public ChattingAdapter(Context context, ArrayList<Message> chatlist) {
        this.context = context;
        this.chatlist = chatlist;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=null;
        switch (viewType){
            case 0:
                itemView = LayoutInflater.from(context).inflate(R.layout.row_chat_user,parent,false);
                break;
            case 1:
                itemView = LayoutInflater.from(context).inflate(R.layout.row_chat_partner,parent,false);
                break;
            case 2:
                itemView = LayoutInflater.from(context).inflate(R.layout.chat_into_notification,parent,false);
                break;
            case 3:
                itemView = LayoutInflater.from(context).inflate(R.layout.chat_into_notification,parent,false);
                break;
            default:
                break;
        }
        assert itemView != null;
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String UserName = chatlist.get(position).getUserName();
        String content = chatlist.get(position).getMessageContent();
        int viewType = chatlist.get(position).getViewType();

        switch (viewType){
            case 0:
                holder.mTvMessage.setText(content);
                break;
            case 1:
                holder.mTvUserName.setText(UserName);
                holder.mTvMessage.setText(content);
                //프사
                Glide.with(context)
                        .load(chatlist.get(position).getProfileUrl())
                        .centerCrop()
                        .into(holder.mIvProfile);

                holder.mIvProfile.setBackground(new ShapeDrawable(new OvalShape()));
                holder.mIvProfile.setClipToOutline(true);
                break;
            case 2:
                String text="";
                text+=UserName;
                text+="님이 들어왔습니다";
                holder.mTvText.setText(text);
                break;
            case 3:
                String text1 = "";
                text1+=UserName;
                text1+="님이 떠났습니다.";
                holder.mTvText.setText(text1);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    @Override
    public int getItemViewType(int position){
        return chatlist.get(position).getViewType();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTvUserName,mTvMessage,mTvText;
        ImageView mIvProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvUserName = itemView.findViewById(R.id.username);
            mTvMessage = itemView.findViewById(R.id.message);
            mTvText = itemView.findViewById(R.id.text);
            mIvProfile = itemView.findViewById(R.id.profile);
        }
    }
}
