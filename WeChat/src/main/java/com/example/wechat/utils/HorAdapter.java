package com.example.wechat.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wechat.R;

public class HorAdapter extends RecyclerView.Adapter<HorAdapter.LinearViewHolder>{
    private Context mcontext;
    private OnItemClickListener monItemClickListener;
    public HorAdapter(Context context, OnItemClickListener onItemClickListener){
        mcontext=context;
        monItemClickListener=onItemClickListener;
    }
    @NonNull
    @Override
    public HorAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.layout_hor_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HorAdapter.LinearViewHolder holder, final int position) {


        holder.sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monItemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }
    class LinearViewHolder extends RecyclerView.ViewHolder{

        private ImageView sort;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            sort=itemView.findViewById(R.id.iv_sort);

        }
    }
    public interface OnItemClickListener{
        void onClick(int pos);
    }
}
