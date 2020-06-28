package com.example.wechat;

//import android.app.Fragment;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2016/2/18.
 */
public class ShoppingCart_Fragment extends Fragment {

    private MyOpenHelper myOpenHelper;
    private int uid;
    private String getSpU;//新加
    SharedPreferences sp;  //新加


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_shoppingcart__fragment, null);

        // 模拟用户id
        uid = 1;

       /*新加*/
        sp=getContext().getSharedPreferences("mrsoft",getContext().MODE_PRIVATE);
        getSpU=sp.getString("username","");

        myOpenHelper = new MyOpenHelper(getActivity());

        // 获取listview设置适配器
        ListView listView = view.findViewById(R.id.lv_car);
        // 设置监听器，传入用户id和当前view对象
        listView.setAdapter(new MyAdapter(uid, view));

        // 查询用户购物车总记录条数
        SQLiteDatabase db = myOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from car where uid = ?", new String[]{String.valueOf(uid)});
        cursor.moveToNext();

        // 购物车为空
        if (cursor.getInt(cursor.getColumnIndex("count(*)")) == 0){
            // 隐藏结算条
            view.findViewById(R.id.botm).setVisibility(View.GONE);

            Toast.makeText(getActivity(), "购物车为空，赶紧去购物吧~", Toast.LENGTH_SHORT).show();
        }


        // 结算
        view.findViewById(R.id.pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myOpenHelper.getReadableDatabase();
                db.execSQL("delete from car where uid = ?", new String[]{String.valueOf(uid)});
                refresh();
                Toast.makeText(getActivity(), "购物车已清空~", Toast.LENGTH_SHORT).show();

                db.close();
            }
        });

        cursor.close();
        db.close();
        return view;
    }


    // 适配器
    private class MyAdapter extends BaseAdapter {

        private Integer uid; //用户id
        private View view;

        public MyAdapter(Integer uid, View view) {
            this.uid = uid;
            this.view = view;
        }

        @Override
        public int getCount() {

            // 查询用户购物车总记录条数
            SQLiteDatabase db = myOpenHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select count(*) from car where uid = ?", new String[]{String.valueOf(uid)});
            cursor.moveToNext();
            int count = cursor.getInt(cursor.getColumnIndex("count(*)"));

            // 设置购物车合计总价
            TextView textView = view.findViewById(R.id.tv_carsum);
            Cursor carsum = db.rawQuery("select sum(total) from car where uid = ?", new String[]{String.valueOf(uid)});
            carsum.moveToNext();
            double sum = carsum.getDouble(carsum.getColumnIndex("sum(total)"));
            textView.setText("￥" + String.format("%.2f", sum));


            cursor.close();
            db.close();
            return count;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            // 查询出指定用户购物车表中数据（第 position + 1 行）
            SQLiteDatabase db = myOpenHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from car where uid = ? limit ?,1", new String[]{String.valueOf(uid), String.valueOf(position)});
            cursor.moveToNext();

            // 获取商品gid
            int gid = cursor.getInt(cursor.getColumnIndex("gid"));
            // 获取商品数量
            int amount = cursor.getInt(cursor.getColumnIndex("amount"));

            // 根据gid查询商品信息
            Cursor goodsCorsor = db.rawQuery("select * from goods where id = ?", new String[]{String.valueOf(gid)});
            goodsCorsor.moveToNext();
            // 获取商品名称
            String name = goodsCorsor.getString(goodsCorsor.getColumnIndex("name"));
            // 获取商品单价
            String price = goodsCorsor.getString(goodsCorsor.getColumnIndex("price"));


            // 将 item.xml 转换为 view
            View item = View.inflate(getActivity(), R.layout.item, null);

            // 获取商品名称view，并设置值
            TextView titleView = item.findViewById(R.id.tv_title);
            titleView.setText(name);
            // 获取商品价格view，并设置值
            TextView priceView = item.findViewById(R.id.tv_price);
            priceView.setText("￥" + price);
            //  获取商品数量view，并设置值
            TextView amountView = item.findViewById(R.id.tv_amount);
            amountView.setText(String.valueOf(amount));
            // 获取商品图片view，并设置值
            ImageView iconView = item.findViewById(R.id.iv_icon);
            String iconName = "item_" + gid;
            // 拿到图片ID
            int icon = getActivity().getResources().getIdentifier(iconName, "drawable", getActivity().getPackageName());
            // 设置图片
            Glide.with(getActivity()).load(icon).into(iconView);

            // + - 按钮功能实现
            item.findViewById(R.id.btn_incr).setOnClickListener(new MyButtonOnClickListener(uid, position, amountView, price, view));
            item.findViewById(R.id.btn_desc).setOnClickListener(new MyButtonOnClickListener(uid, position, amountView, price, view));

            // 删除商品按钮
            item.findViewById(R.id.btn_delete).setOnClickListener(new MyButtonOnClickListener(uid, position, amountView, price, view));

            goodsCorsor.close();
            cursor.close();
            db.close();
            return item;
        }

    }

    // 自定义按钮点击监听器
    private class MyButtonOnClickListener implements View.OnClickListener {

        private int uid; // 用户id
        private int position; // item索引
        TextView amountView; // 商品数量view
        String price; // 商品单价
        View view;  // 合计view

        public MyButtonOnClickListener(int uid, int position, TextView amountView, String price, View view) {
            this.uid = uid;
            this.position = position;
            this.amountView = amountView;
            this.price = price;
            this.view = view;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_incr: { //购物车商品数量增加
                    // 购物车数量 TextView，  +1
                    amountView.setText(String.valueOf((Integer.parseInt(amountView.getText().toString()) + 1)));

                    // 获取商品gid
                    SQLiteDatabase db = myOpenHelper.getReadableDatabase();
                    Cursor cursor = db.rawQuery("select * from car where uid = ? limit ?,1", new String[]{String.valueOf(uid), String.valueOf(position)});
                    cursor.moveToNext();
                    int gid = cursor.getInt(cursor.getColumnIndex("gid"));

                    // 商品数量增加
                    db.execSQL("update car set amount = amount + 1 where gid = ? and uid = ?", new String[]{String.valueOf(gid), String.valueOf(uid)});
                    // 商品总价增加
                    db.execSQL("update car set total = total + ? where gid = ? and uid = ?", new String[]{String.valueOf(price), String.valueOf(gid), String.valueOf(uid)});

                    // 购物车合计总价变化
                    TextView textView = view.findViewById(R.id.tv_carsum);
                    Cursor carsum = db.rawQuery("select sum(total) from car where uid = ?", new String[]{String.valueOf(uid)});
                    carsum.moveToNext();
                    double sum = carsum.getDouble(carsum.getColumnIndex("sum(total)"));
                    textView.setText("￥" + String.format("%.2f", sum));

                    cursor.close();
                    db.close();
                    break;
                }
                case R.id.btn_desc: {  //购物车商品数量减少
                    if (Integer.parseInt(amountView.getText().toString()) == 1) {
                        return;
                    }
                    // 修改购物车中TextView
                    amountView.setText(String.valueOf((Integer.parseInt(amountView.getText().toString()) - 1)));

                    // 获取商品gid
                    SQLiteDatabase db = myOpenHelper.getReadableDatabase();
                    Cursor cursor = db.rawQuery("select * from car where uid = ? limit ?,1", new String[]{String.valueOf(uid), String.valueOf(position)});
                    cursor.moveToNext();
                    int gid = cursor.getInt(cursor.getColumnIndex("gid"));

                    // 商品数量增加
                    db.execSQL("update car set amount = amount - 1 where gid = ? and uid = ?", new String[]{String.valueOf(gid), String.valueOf(uid)});
                    // 商品总价减少
                    db.execSQL("update car set total = total - ? where gid = ? and uid = ?", new String[]{String.valueOf(price), String.valueOf(gid), String.valueOf(uid)});

                    // 购物车合计总价变化
                    TextView textView = view.findViewById(R.id.tv_carsum);
                    Cursor carsum = db.rawQuery("select sum(total) from car where uid = ?", new String[]{String.valueOf(uid)});
                    carsum.moveToNext();
                    double sum = carsum.getDouble(carsum.getColumnIndex("sum(total)"));
                    textView.setText("￥" + String.format("%.2f", sum));

                    // 释放资源
                    cursor.close();
                    db.close();
                    break;
                }
                case R.id.btn_delete: { //删除商品
                    // 获取商品gid
                    SQLiteDatabase db = myOpenHelper.getReadableDatabase();
                    Cursor cursor = db.rawQuery("select * from car where uid = ? limit ?,1", new String[]{String.valueOf(uid), String.valueOf(position)});
                    cursor.moveToNext();
                    int gid = cursor.getInt(cursor.getColumnIndex("gid"));

                    // 从购物车中删除该商品
                    db.execSQL("delete from car where uid = ? and gid = ?", new String[]{String.valueOf(uid), String.valueOf(gid)});

                    // 释放资源
                    cursor.close();
                    db.close();

                    // 刷新
                    refresh();
                }
            }
        }
    }

    // 刷新fragment
    private void refresh() {
        ShoppingCart_Fragment shoppingCart_fragment=new ShoppingCart_Fragment();
        System.out.println("refresh...");
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, shoppingCart_fragment).addToBackStack(null).commitAllowingStateLoss();
    }

}
