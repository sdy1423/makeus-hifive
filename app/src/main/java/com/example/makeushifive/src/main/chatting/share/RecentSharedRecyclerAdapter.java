package com.example.makeushifive.src.main.chatting.share;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.makeushifive.R;
import com.example.makeushifive.src.main.home.search.SearchRecyclerAdapter;

import java.util.ArrayList;

public class RecentSharedRecyclerAdapter extends RecyclerView.Adapter<RecentSharedRecyclerAdapter.ViewHolder> {

    private ArrayList<SharedUser> sharedUsers = null;
    Context context;

    public interface OnItemClickListener{
        void onItemClick(View v,int pos,boolean flag);
    }
    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public RecentSharedRecyclerAdapter(ArrayList<SharedUser> sharedUsers,Context context) {
        this.sharedUsers = sharedUsers;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_shared,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mTvUserName.setText(sharedUsers.get(position).getNickname());

        //프사
        Glide.with(context)
                .load(sharedUsers.get(position).getProfileUrl())
                .centerCrop()
                .into(holder.mIvProfile);

        //rounded imageview
        holder.mIvProfile.setBackground(new ShapeDrawable(new OvalShape()));
        holder.mIvProfile.setClipToOutline(true);

        //todo 클릭하면 done 등장, 글자 색상 변경, 완료 색갈 변경 하도록

    }


    @Override
    public int getItemCount() {
        return sharedUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mIvProfile,mIvDone;
        TextView mTvUserName;
        boolean flag = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mIvDone = itemView.findViewById(R.id.recent_shared_iv_done);
            mIvProfile = itemView.findViewById(R.id.recent_shared_iv_profile);
            mTvUserName = itemView.findViewById(R.id.recent_shared_tv_username);
            mIvDone.setVisibility(View.INVISIBLE);

            //TODO 리사이클러뷰 외부에서 아이템 클릭 이벤트 처리하는 중
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
//                        if(mListener!=null){
//                            mListener.onItemClick(v,pos);
//                        }
                        if(!flag){
                            flag=true;
                            mIvDone.setVisibility(View.VISIBLE);
                            mTvUserName.setTextColor(Color.parseColor("#ff7979"));
                            mListener.onItemClick(v,pos,true);
                        }else{
                            flag=false;
                            mIvDone.setVisibility(View.INVISIBLE);
                            mTvUserName.setTextColor(Color.parseColor("#000000"));
                            mListener.onItemClick(v,pos,false);
                        }
//                        notifyItemChanged(pos);
                    }
                }
            });

        }

    }
}
