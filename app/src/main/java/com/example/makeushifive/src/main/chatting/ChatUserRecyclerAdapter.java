package com.example.makeushifive.src.main.chatting;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.makeushifive.R;

import java.util.ArrayList;

public class ChatUserRecyclerAdapter extends RecyclerView.Adapter<ChatUserRecyclerAdapter.ViewHolder> {

    ArrayList<ChatUser> chatUsers = null;
    Context context;

    public ChatUserRecyclerAdapter(ArrayList<ChatUser> chatUsers, Context context) {
        this.chatUsers = chatUsers;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatUserRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_chat_user_list,parent,false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatUserRecyclerAdapter.ViewHolder holder, int position) {
            holder.mTvUserName.setText(chatUsers.get(position).getNickname());


        //프사
        Glide.with(context)
                .load(chatUsers.get(position).getProfileUrl())
                .centerCrop()
                .into(holder.mIvProfile);

        //rounded imageview
        holder.mIvProfile.setBackground(new ShapeDrawable(new OvalShape()));
        holder.mIvProfile.setClipToOutline(true);
    }

    @Override
    public int getItemCount() {
        return chatUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mIvProfile;
        TextView mTvUserName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mIvProfile = itemView.findViewById(R.id.chatting_drawer_chat_user_profile);
            mTvUserName = itemView.findViewById(R.id.chatting_drawer_chat_user_username);
        }
    }
}
