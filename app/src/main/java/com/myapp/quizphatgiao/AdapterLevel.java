package com.myapp.quizphatgiao;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterLevel extends RecyclerView.Adapter<AdapterLevel.MViewHolder> {

    ArrayList<Level> arr;
    Context context;
    ListenerItem listenerItem;
    Activity activity;

    public AdapterLevel(ArrayList<Level> arr,Context context, ListenerItem listener, Activity activity){
        this.arr = arr;
        this.context = context;
        listenerItem = listener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater  = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_level,parent,false);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MViewHolder holder, int position) {
        String t = arr.get(position).txt;
        holder.tv_level.setText(t);
        String t2  = arr.get(position).src;
        int level = arr.get(position).level;
        int index = holder.getAdapterPosition();
        Resources res = context.getResources();

        int resourceId = res.getIdentifier(
                t2, "drawable", context.getPackageName() );

        holder.im_level.setImageResource( resourceId );
        holder.im_level.setOnClickListener(v->{
            listenerItem.click(index,activity,level);
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MViewHolder extends RecyclerView.ViewHolder{

        ImageView im_level;
        TextView tv_level;
        public MViewHolder(@NonNull View itemView) {
            super(itemView);
            im_level = itemView.findViewById(R.id.image_level);
            tv_level = itemView.findViewById(R.id.tv_level);
        }
    }
}
