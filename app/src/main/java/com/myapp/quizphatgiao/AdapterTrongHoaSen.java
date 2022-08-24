package com.myapp.quizphatgiao;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AdapterTrongHoaSen extends RecyclerView.Adapter<AdapterTrongHoaSen.MViewHolder> {

    ArrayList<TrongHoaSen> arr;
    Context context;
    ListenerItem listenerItem;
    Activity activity;

    public AdapterTrongHoaSen(ArrayList<TrongHoaSen> arr, Context context, ListenerItem listener, Activity activity){
        this.arr = arr;
        this.context = context;
        listenerItem = listener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater  = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_tronghoasen,parent,false);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MViewHolder holder, int position) {
        String t = arr.get(position).txt;
        holder.tv_hoasen.setText(t);
        String t2  = arr.get(position).src;
        int index = holder.getAdapterPosition();
        Resources res = context.getResources();
        int resourceId = res.getIdentifier(
                t2, "drawable", context.getPackageName() );
        holder.im_hoasen.setImageResource( resourceId );
        holder.im_hoasen.setOnClickListener(v->{
            /*Date currentTime = Calendar.getInstance().getTime();
            long time = currentTime.getTime();
            holder.tv_hoasen.setText("Đang Trồng");
            holder.im_hoasen.setBackgroundResource(R.drawable.dangtrong);*/
            listenerItem.click(index,activity,0);

        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MViewHolder extends RecyclerView.ViewHolder{

        //ProgressBar progressBar;
        ImageView im_hoasen;
        TextView tv_hoasen;
        public MViewHolder(@NonNull View itemView) {
            super(itemView);
            im_hoasen = itemView.findViewById(R.id.image_hoasen);
            tv_hoasen = itemView.findViewById(R.id.tv_hoasen);
            //progressBar = itemView.findViewById(R.id.process);
        }
    }
}
