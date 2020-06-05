package com.example.makeushifive.src.main.home.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.makeushifive.R;
import com.example.makeushifive.src.main.chatting.ChattingActivity;
import static com.example.makeushifive.src.ApplicationClass.DATE_FORMAT;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static com.example.makeushifive.src.ApplicationClass.Chatting;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {

    private ArrayList<ITEM> items = null;
    int color=0;
    int colors[]={R.color.one,R.color.two,R.color.three,R.color.four,R.color.five,R.color.six,R.color.seven, R.color.eight};
    Context mContext;

    public SearchRecyclerAdapter(ArrayList<ITEM> items,Context context) {
        this.items = items;
        this.mContext = context;
    }

    @NonNull
    @Override
    public SearchRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_schedules,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerAdapter.ViewHolder holder, int position) {
        //todo 화면에 표시할 것들
        String title = items.get(position).getTitle();
        color = items.get(position).getColor();
        String day = items.get(position).getDay();
//        taskNo = items.get(position).getTaskNo();
        Date dateformat = null;
        try {
            dateformat = DATE_FORMAT.parse(day); //string to date
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert dateformat != null;
        String today = Chatting.format(dateformat); //date to stringSS
        holder.mTvDay.setText(today);


        for(int i=0;i<8;i++){
            if(i==color-1){
                holder.mIvColor[i].setVisibility(View.VISIBLE);
            }else{
                holder.mIvColor[i].setVisibility(View.INVISIBLE);
            }
        }


//        String numofpeople = tasks.get(position).getCount();
//        holder.mTvNumOfPeople.setText(numofpeople);
        if(color==1){

            holder.mTvTitle.setTextColor(Color.parseColor("#F87BAB"));
            holder.mTvTitle.setText(title);
        }else if(color==2){
            holder.mTvTitle.setTextColor(Color.parseColor("#FAB462"));
            holder.mTvTitle.setText(title);

        }
        else if(color==3){
            holder.mTvTitle.setTextColor(Color.parseColor("#4A62E3"));
            holder.mTvTitle.setText(title);

        }else if(color==4){
            holder.mTvTitle.setTextColor(Color.parseColor("#D10000"));
            holder.mTvTitle.setText(title);

        }else if(color==5){
            holder.mTvTitle.setTextColor(Color.parseColor("#53BFAB"));
            holder.mTvTitle.setText(title);

        }else if(color==6){
            holder.mTvTitle.setTextColor(Color.parseColor("#9B50D3"));
            holder.mTvTitle.setText(title);

        }else if(color==7){
            holder.mTvTitle.setTextColor(Color.parseColor("#191919"));
            holder.mTvTitle.setText(title);

        }else if(color==8){
            holder.mTvTitle.setTextColor(Color.parseColor("#7BC94B"));
            holder.mTvTitle.setText(title);

        }
        int numOfPeople =items.get(position).getUserInfos().size();
        if(numOfPeople<=1){
            holder.mLlnumOfPeople.setVisibility(View.INVISIBLE);
        }else {
            holder.mLlnumOfPeople.setVisibility(View.VISIBLE);

            if(numOfPeople==2){
                holder.linearTwo.setVisibility(View.VISIBLE);
                holder.linearThree.setVisibility(View.INVISIBLE);
                holder.linearFour.setVisibility(View.INVISIBLE);
                for(int i=0;i<numOfPeople;i++){
                    if(i==0){
                        //프사
                        Glide.with(mContext)
                                .load(items.get(position).getUserInfos().get(i).getProfileUrl())
                                .centerCrop()
                                .into(holder.twoFirst);

                        //rounded imageview
                        holder.twoFirst.setBackground(new ShapeDrawable(new OvalShape()));
                        holder.twoFirst.setClipToOutline(true);
                    }else if(i==1){
                        //프사
                        Glide.with(mContext)
                                .load(items.get(position).getUserInfos().get(i).getProfileUrl())
                                .centerCrop()
                                .into(holder.twoSecond);
                        //rounded imageview
                        holder.twoSecond.setBackground(new ShapeDrawable(new OvalShape()));
                        holder.twoSecond.setClipToOutline(true);
                    }
                }
            }else if(numOfPeople==3){
                holder.linearTwo.setVisibility(View.INVISIBLE);
                holder.linearThree.setVisibility(View.VISIBLE);
                holder.linearFour.setVisibility(View.INVISIBLE);
                for(int i=0;i<numOfPeople;i++){
                    if(i==0){
                        //프사
                        Glide.with(mContext)
                                .load(items.get(position).getUserInfos().get(i).getProfileUrl())
                                .centerCrop()
                                .into(holder.threeFirst);

                        //rounded imageview
                        holder.threeFirst.setBackground(new ShapeDrawable(new OvalShape()));
                        holder.threeFirst.setClipToOutline(true);
                    }else if(i==1){
                        //프사
                        Glide.with(mContext)
                                .load(items.get(position).getUserInfos().get(i).getProfileUrl())
                                .centerCrop()
                                .into(holder.threeSecond);
                        //rounded imageview
                        holder.threeSecond.setBackground(new ShapeDrawable(new OvalShape()));
                        holder.threeSecond.setClipToOutline(true);
                    }else if(i==2){
                        //프사
                        Glide.with(mContext)
                                .load(items.get(position).getUserInfos().get(i).getProfileUrl())
                                .centerCrop()
                                .into(holder.threeThird);
                        //rounded imageview
                        holder.threeThird.setBackground(new ShapeDrawable(new OvalShape()));
                        holder.threeThird.setClipToOutline(true);
                    }
                }

            }else{
                holder.linearTwo.setVisibility(View.INVISIBLE);
                holder.linearThree.setVisibility(View.INVISIBLE);
                holder.linearFour.setVisibility(View.VISIBLE);
                for(int i=0;i<numOfPeople;i++){
                    if(i==0){
                        //프사
                        Glide.with(mContext)
                                .load(items.get(position).getUserInfos().get(i).getProfileUrl())
                                .centerCrop()
                                .into(holder.fourFirst);
                        //rounded imageview
                        holder.fourFirst.setBackground(new ShapeDrawable(new OvalShape()));
                        holder.fourFirst.setClipToOutline(true);
                    }else if(i==1){
                        //프사
                        Glide.with(mContext)
                                .load(items.get(position).getUserInfos().get(i).getProfileUrl())
                                .centerCrop()
                                .into(holder.fourSecond);
                        //rounded imageview
                        holder.fourSecond.setBackground(new ShapeDrawable(new OvalShape()));
                        holder.fourSecond.setClipToOutline(true);
                    }else if(i==2){
                        //프사
                        Glide.with(mContext)
                                .load(items.get(position).getUserInfos().get(i).getProfileUrl())
                                .centerCrop()
                                .into(holder.fourThird);
                        //rounded imageview
                        holder.fourThird.setBackground(new ShapeDrawable(new OvalShape()));
                        holder.fourThird.setClipToOutline(true);
                    }
                }

            }
        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTvTitle,mTvDay,mTvNumOfPeople;
        ImageView[] mIvColor = new ImageView[8];
        Context context;
        LinearLayout mLlschedules,mLlnumOfPeople;

        LinearLayout linearTwo,linearThree,linearFour;
        ImageView twoFirst,twoSecond,threeFirst,threeSecond,threeThird,fourFirst,fourSecond,fourThird;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //TODO findviewbyid 등등
            mTvTitle = itemView.findViewById(R.id.feed_tv_title);
            mTvDay=itemView.findViewById(R.id.feed_tv_today);
            mIvColor[0]=itemView.findViewById(R.id.feed_iv_color_1);
            mIvColor[1]=itemView.findViewById(R.id.feed_iv_color_2);
            mIvColor[2]=itemView.findViewById(R.id.feed_iv_color_3);
            mIvColor[3]=itemView.findViewById(R.id.feed_iv_color_4);
            mIvColor[4]=itemView.findViewById(R.id.feed_iv_color_5);
            mIvColor[5]=itemView.findViewById(R.id.feed_iv_color_6);
            mIvColor[6]=itemView.findViewById(R.id.feed_iv_color_7);
            mIvColor[7]=itemView.findViewById(R.id.feed_iv_color_8);
            context = itemView.getContext();
            linearTwo=itemView.findViewById(R.id.linear_two);
            linearThree=itemView.findViewById(R.id.linear_three);
            linearFour=itemView.findViewById(R.id.linear_four);

            twoFirst=itemView.findViewById(R.id.two_person_first);
            twoSecond=itemView.findViewById(R.id.two_person_second);

            threeFirst=itemView.findViewById(R.id.three_person_first);
            threeSecond=itemView.findViewById(R.id.three_person_second);
            threeThird=itemView.findViewById(R.id.three_person_third);

            fourFirst=itemView.findViewById(R.id.four_person_first);
            fourSecond=itemView.findViewById(R.id.four_person_second);
            fourThird=itemView.findViewById(R.id.four_person_third);


            context = itemView.getContext();
            mLlnumOfPeople=itemView.findViewById(R.id.show_profile);

//            mLlschedules =itemView.findViewById(R.id.feed_ll_schedules);
//            mLlschedules.setOnClickListener(context);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        int TaskNo = items.get(getAdapterPosition()).getTaskNo();
                        int Color = items.get(getAdapterPosition()).getColor();
                        Intent intent = new Intent(context, ChattingActivity.class);
                        intent.putExtra("taskNo",TaskNo);
                        intent.putExtra("color",Color);
                        Log.e("보내는taskNo",""+TaskNo);
                        Log.e("보내는color",""+Color);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
