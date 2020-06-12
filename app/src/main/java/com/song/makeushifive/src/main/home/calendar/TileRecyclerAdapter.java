package com.song.makeushifive.src.main.home.calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.song.makeushifive.R;

import java.text.ParseException;
import java.util.ArrayList;

public class TileRecyclerAdapter extends RecyclerView.Adapter<TileRecyclerAdapter.ViewHolder> {
    ArrayList<TileItem> tileItems;
    Context context;

    public interface OnItemClickListener{
        void onItemClick(View v, int pos,int year,int month,int day) throws ParseException;
    }
    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public TileRecyclerAdapter(ArrayList<TileItem> tileItems, Context context) {
        this.tileItems = tileItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tile_recycler,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int color = tileItems.get(position).getColor();
//        Log.e("들어오는color","color: "+color);
        if(color==1){
            holder.title.setTextColor(Color.parseColor("#F87BAB"));
            holder.showColor.setBackgroundResource(R.drawable.small_border1);
            holder.showColor.setBackgroundColor(Color.parseColor("#F87BAB"));
        }
        if(color==2){
            holder.showColor.setBackgroundResource(R.drawable.small_border2);
            holder.title.setTextColor(Color.parseColor("#FAB462"));
        }
        if(color==3){
            holder.showColor.setBackgroundResource(R.drawable.small_border3);
            holder.title.setTextColor(Color.parseColor("#4A62E3"));
        }
        if(color==4){
            holder.showColor.setBackgroundResource(R.drawable.small_border4);
            holder.title.setTextColor(Color.parseColor("#D10000"));
        }
        if(color==5){
            holder.showColor.setBackgroundResource(R.drawable.small_border5);
            holder.title.setTextColor(Color.parseColor("#53BFAB"));
        }
        if(color==6){
            holder.showColor.setBackgroundResource(R.drawable.small_border6);
            holder.title.setTextColor(Color.parseColor("#9B50D3"));
        }
        if(color==7){
            holder.showColor.setBackgroundResource(R.drawable.small_border7);
            holder.title.setTextColor(Color.parseColor("#191919"));
        }
        if(color==8) {
            holder.showColor.setBackgroundResource(R.drawable.small_border8);
            holder.title.setTextColor(Color.parseColor("#7BC94B"));
        }
        holder.title.setText(tileItems.get(position).getTitle());
    }



    @Override
    public int getItemCount() {
        return tileItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView showColor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title =itemView.findViewById(R.id.tile_tv);
            showColor=itemView.findViewById(R.id.tile_iv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        int year = tileItems.get(pos).getYear();
                        int month = tileItems.get(pos).getMonth();
                        int day = tileItems.get(pos).getDay();
                        try {
                            Log.e("타일",""+year+" "+month+" "+day);
                            mListener.onItemClick(v,pos,year,month-1,day);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}
