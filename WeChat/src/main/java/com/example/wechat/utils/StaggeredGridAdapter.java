package com.example.wechat.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wechat.MainActivity;
import com.example.wechat.R;

import java.util.ArrayList;
import java.util.List;




public class StaggeredGridAdapter extends RecyclerView.Adapter<StaggeredGridAdapter.LinearViewHolder> {


    private List<String> goodsimg=new ArrayList<>();
//    private List<String> gooditem=new ArrayList<>();
    private Context mcontext;
    private OnItemClickListener monItemClickListener;





    public StaggeredGridAdapter(Context context,OnItemClickListener onItemClickListener) {
        mcontext=context;
        monItemClickListener=onItemClickListener;
    }
   //生成数据库实例
    public  SQLiteDatabase create(Context context) {
        MySqliteHelper mySQLDatabase = new MySqliteHelper(mcontext, "db", null, 1);
        return mySQLDatabase.getWritableDatabase();

    }

    @NonNull
    @Override
    public StaggeredGridAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //设置布局
        return new LinearViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.activity_main_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StaggeredGridAdapter.LinearViewHolder holder, final int position) {
        //从数据库中获取商品数据
        SQLiteDatabase database = create(mcontext);
        //查询商品图片并保存
        Cursor cursor = database.query("goods", new String[]{"src"}, null, null,null,null,null);
        if(cursor.moveToFirst()){
            for(int i = 0; i<cursor.getCount();i++)
            {
                goodsimg.add(cursor.getString(cursor.getColumnIndex("src")));
                cursor.moveToNext();
            }
        }
        //将图片放入Imageview
        for(int i=0;i<50;i++){
            if(position==i){
                // 拿到图片名字
                String iconName=goodsimg.get(i);
                // 拿到图片ID
                int icon = mcontext.getResources().getIdentifier(iconName, "drawable", mcontext.getPackageName());
                // 设置图片
                holder.iView.setImageResource(icon);
                //设置文字
                holder.textView.setText("joker");
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
        return 50;
    }
    class LinearViewHolder extends RecyclerView.ViewHolder{

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
    //根据图片路径获得id


}
