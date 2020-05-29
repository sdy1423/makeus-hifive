package com.example.makeushifive.src.main.chatting.share;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.Image;
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

public class PickedRecyclerAdapter extends RecyclerView.Adapter<PickedRecyclerAdapter.ViewHolder> {
    private ArrayList<SharedUser> sharedUsers =null;
    Context context;

    public interface OnItemClickListener{
        void onItemClick(View v, int pos,int userNo);
    }
    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
    public PickedRecyclerAdapter(ArrayList<SharedUser> sharedUsers, Context context) {
        this.sharedUsers = sharedUsers;
        this.context = context;
    }

    @NonNull
    @Override
    public PickedRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picked_friends,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PickedRecyclerAdapter.ViewHolder holder, int position) {
        holder.mTvUserName.setText(sharedUsers.get(position).getNickname());
        //프사
        Glide.with(context)
                .load(sharedUsers.get(position).getProfileUrl())
                .centerCrop()
                .into(holder.mIvProfile);
        //rounded imageview
        holder.mIvProfile.setBackground(new ShapeDrawable(new OvalShape()));
        holder.mIvProfile.setClipToOutline(true);
    }

    @Override
    public int getItemCount() {
        return sharedUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mIvProfile;
        TextView mTvUserName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mIvProfile = itemView.findViewById(R.id.picked_iv_profile);
            mTvUserName = itemView.findViewById(R.id.picked_tv_user_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        int UserNo = sharedUsers.get(pos).getSharedUserNo();

                        mListener.onItemClick(v, pos,UserNo);

                        sharedUsers.remove(pos);
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), sharedUsers.size());

//                        notifyItemChanged(pos);
                    }
                }
            });
        }
    }
}
