package com.example.wechat.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.wechat.Browsing_history;
import com.example.wechat.R;
import com.example.wechat.SearchResultActivity;
import com.example.wechat.pojo.Goods;

import java.util.ArrayList;
import java.util.List;

public class LinearAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mcontext;
    private OnItemClickListener monItemClickListener;
    private String result1;
    private int result;
    private List<Goods> goodsimg=new ArrayList<>();
    //从数据库中获取商品数据
    private SQLiteDatabase database;


    public LinearAdapter(Context context,OnItemClickListener onItemClickListener,String result,SQLiteDatabase mdatabase){
        mcontext=context;
        monItemClickListener=onItemClickListener;
        result1=result;
        database=mdatabase;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RelativeViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.layout_linear_item, parent, false));

    }


    @SuppressLint("ShowToast")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        //查询商品图片并保存
        Cursor cursor = database.rawQuery("select * from goods where name like '%"+result1+"%'",null);
        //得到结果数
        result=cursor.getCount();

        if(result==0){
           //
        }else {
            if (cursor.moveToFirst()) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    Goods goodsitem = new Goods();
                    goodsitem.setName(cursor.getString(cursor.getColumnIndex("name")));
                    goodsitem.setCategory(cursor.getString(cursor.getColumnIndex("category")));
                    goodsitem.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
                    goodsitem.setSrc(cursor.getString(cursor.getColumnIndex("src")));
                    goodsitem.setStorage(cursor.getInt(cursor.getColumnIndex("storage")));
                    goodsimg.add(goodsitem);
                    cursor.moveToNext();
                }


            }
            cursor.close();
            //将图片放入Imageview
            for (int i = 0; i < 50; i++) {
                if (position == i) {
                    // 拿到图片名字
                    String iconName = goodsimg.get(i).getSrc();
                    // 拿到图片ID
                    int icon = mcontext.getResources().getIdentifier(iconName, "drawable", mcontext.getPackageName());
                    // 设置图片添加圆角
                    RequestOptions requestOptions=new RequestOptions().error(R.drawable.customitem).bitmapTransform(new RoundedCorners(10));
                    Glide.with(mcontext).load(icon).apply(requestOptions).into(((RelativeViewHolder) holder).imageView);

                    //设置文字
                    ((RelativeViewHolder) holder).result1.setText(goodsimg.get(i).getName() );

                    ((RelativeViewHolder) holder).mprice.setText( "￥" + goodsimg.get(i).getPrice());

                    ((RelativeViewHolder) holder).mstock.setText("库存："+goodsimg.get(i).getStorage());
                }
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
        Cursor cursor = database.rawQuery("select * from goods where name like '%"+result1+"%'",null);
        //得到结果数
        result=cursor.getCount();
        return result;
    }

    static class RelativeViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView result1;
        private TextView mprice;
        private TextView mstock;
        RelativeViewHolder(@NonNull View itemView) {
            super(itemView);
            result1=itemView.findViewById(R.id.tv_result1);
            imageView=itemView.findViewById(R.id.iv_result1);
            mprice=itemView.findViewById(R.id.tv_price);
            mstock=itemView.findViewById(R.id.tv_stock);
        }
    }

    public interface OnItemClickListener{
        void onClick(int pos);
    }
}
