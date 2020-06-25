package com.example.wechat;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * Created by Administrator on 2016/2/18.
 */
public class ShoppingCart_Fragment extends Fragment {

    MyOpenHelper myOpenHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_shoppingcart__fragment,null);

        myOpenHelper = new MyOpenHelper(getActivity());

        // 获取listview设置适配器
        ListView listView = view.findViewById(R.id.lv_car);
        listView.setAdapter(new MyAdapter());

        return view;
    }


    // 适配器
    private class MyAdapter extends BaseAdapter{

        private Integer uid;

        @Override
        public int getCount() {
            // 获取当前登录用户 id
            uid = 1; // 模拟用户id

            // 查询用户购物车总记录条数
            SQLiteDatabase db = myOpenHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select count(*) from car where uid = ?", new String[]{String.valueOf(uid)});
            cursor.moveToNext();
            int count = cursor.getInt(cursor.getColumnIndex("count(*)"));

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
            priceView.setText(price);
            //  获取商品数量view，并设置值
            TextView amountView = item.findViewById(R.id.tv_amount);
            amountView.setText(String.valueOf(amount));

            // + - 按钮功能实现
            item.findViewById(R.id.btn_incr).setOnClickListener(new MyButtonOnClickListener(uid, position, amountView, price));
            item.findViewById(R.id.btn_desc).setOnClickListener(new MyButtonOnClickListener(uid, position, amountView, price));


            goodsCorsor.close();
            cursor.close();
            db.close();
            return item;
        }

    }

    // 自定义按钮点击监听器
    private class MyButtonOnClickListener implements View.OnClickListener{

        private int uid;
        private int position;
        TextView amountView;
        String price;

        public MyButtonOnClickListener(int uid, int position, TextView amountView, String price) {
            this.uid = uid;
            this.position = position;
            this.amountView = amountView;
            this.price = price;
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_incr:{
                    // 修改购物车中TextView
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

                    cursor.close();
                    db.close();
                    break;
                }
                case R.id.btn_desc:{
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

                    cursor.close();
                    db.close();
                    break;
                }
            }
        }
    }

}
