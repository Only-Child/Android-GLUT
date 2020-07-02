package com.example.wechat.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wechat.R;
import com.example.wechat.pojo.Goods;

import java.util.ArrayList;
import java.util.List;


public class SortStaggeredGridAdapter extends RecyclerView.Adapter<SortStaggeredGridAdapter.LinearViewHolder> {


    private List<Goods> goodsimg=new ArrayList<>();
    private Context mcontext;
    private OnItemClickListener monItemClickListener;
    private String mpos;
    //从数据库中获取商品数据
   private  SQLiteDatabase mdatabase;

    public SortStaggeredGridAdapter(Context context, OnItemClickListener onItemClickListener,String pos,SQLiteDatabase database) {
        mcontext=context;
        monItemClickListener=onItemClickListener;
        mpos=pos;
        mdatabase=database;
    }


    @NonNull
    @Override
    public SortStaggeredGridAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //设置布局
        return new LinearViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.activity_main_item, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull SortStaggeredGridAdapter.LinearViewHolder holder, final int position) {

        //查询商品图片并保存
        Cursor cursor = mdatabase.query("goods", null, "category = ?", new String[]{mpos},null,null,null);
        if(cursor.moveToFirst()){
            for(int i = 0; i<cursor.getCount();i++)
            {
                Goods goodsitem=new Goods();
                goodsitem.setName(cursor.getString(cursor.getColumnIndex("name")));
                goodsitem.setCategory(cursor.getString(cursor.getColumnIndex("category")));
                goodsitem.setPrice( cursor.getDouble(cursor.getColumnIndex("price")));
                goodsitem.setSrc(cursor.getString(cursor.getColumnIndex("src")));
                goodsitem.setStorage(cursor.getInt(cursor.getColumnIndex("storage")));
                goodsimg.add(goodsitem);
                cursor.moveToNext();
            }

        }
        cursor.close();

        //将图片放入Imageview
        for(int i=0;i<50;i++){
            if(position==i){
                // 拿到图片名字
                String iconName=goodsimg.get(i).getSrc();
                // 拿到图片ID
                int icon = mcontext.getResources().getIdentifier(iconName, "drawable", mcontext.getPackageName());
                // 设置图片
                Glide.with(mcontext).load(icon).into(holder.iView);

                //设置文字
                holder.textView.setText(goodsimg.get(i).getName()+"   ￥"+goodsimg.get(i).getPrice());
            }
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monItemClickListener.onClick(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        //查询商品图片并保存
        Cursor cursor = mdatabase.query("goods", null, "category = ?", new String[]{mpos},null,null,null);
        //得到商品数
        int num=cursor.getCount();
        cursor.close();
        return num;
    }
    static class LinearViewHolder extends RecyclerView.ViewHolder{

        private ImageView iView;
        private TextView textView;
        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            iView=itemView.findViewById(R.id.iv_item);
            textView=itemView.findViewById(R.id.tv_item);
        }
    }
    public interface OnItemClickListener{
        void onClick(int pos);
    }



}
