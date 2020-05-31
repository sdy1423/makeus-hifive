package com.example.makeushifive.src.main.home.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeushifive.R;

import java.util.ArrayList;

public class TileRecyclerAdapter extends RecyclerView.Adapter<TileRecyclerAdapter.ViewHolder> {
    ArrayList<TileItem> tileItems;
    Context context;

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
        if(color==1){
            holder.title.setTextColor(Color.parseColor("#F87BAB"));
            holder.showColor.setBackgroundColor(Color.parseColor("#F87BAB"));
        }else if(color==2){
            holder.showColor.setBackgroundColor(Color.parseColor("#FAB462"));
            holder.title.setTextColor(Color.parseColor("#FAB462"));
        }
        else if(color==3){
            holder.showColor.setBackgroundColor(Color.parseColor("#4A62E3"));
            holder.title.setTextColor(Color.parseColor("#4A62E3"));
        }else if(color==4){
            holder.showColor.setBackgroundColor(Color.parseColor("#D10000"));
            holder.title.setTextColor(Color.parseColor("#D10000"));
        }else if(color==5){
            holder.showColor.setBackgroundColor(Color.parseColor("#53BFAB"));
            holder.title.setTextColor(Color.parseColor("#53BFAB"));
        }else if(color==6){
            holder.showColor.setBackgroundColor(Color.parseColor("#9B50D3"));
            holder.title.setTextColor(Color.parseColor("#9B50D3"));
        }else if(color==7){
            holder.showColor.setBackgroundColor(Color.parseColor("#191919"));
            holder.title.setTextColor(Color.parseColor("#191919"));
        }else if(color==8) {
            holder.showColor.setBackgroundColor(Color.parseColor("#7BC94B"));
            holder.title.setTextColor(Color.parseColor("#7BC94B"));
        }
        holder.title.setText(tileItems.get(position).getTitle());
    }



    @Override
    public int getItemCount() {
        return tileItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView showColor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title =itemView.findViewById(R.id.tile_tv);
            showColor=itemView.findViewById(R.id.tile_iv);
        }
    }
}
