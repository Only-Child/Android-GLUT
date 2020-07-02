package com.example.wechat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static java.security.AccessController.getContext;

public class Browsing_history extends AppCompatActivity {

    private int uid; //用户id
    private MyOpenHelper myOpenHelper;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browsing_history);

        // 查询用户id
        sp = Browsing_history.this.getSharedPreferences("mrsoft", Browsing_history.this.MODE_PRIVATE);
        String username = sp.getString("username", "");
        uid = findUid(username);

        myOpenHelper = new MyOpenHelper(this);

        ListView listView = findViewById(R.id.lv_history);
        listView.setAdapter(new MyAdapter());

        // 返回按钮
        findViewById(R.id.ib_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // 删除浏览记录按钮
        findViewById(R.id.ib_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup();
            }
        });
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            SQLiteDatabase db = myOpenHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select count(*) from history where uid = ?", new String[]{String.valueOf(uid)});
            cursor.moveToNext();
            int count = cursor.getInt(cursor.getColumnIndex("count(*)"));

            cursor.close();
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

            SQLiteDatabase db = myOpenHelper.getReadableDatabase();

            // 查询出指定用户浏览历史表中数据（第 position + 1 行）
            Cursor cursor = db.rawQuery("select * from history where uid = ? limit ?,1", new String[]{String.valueOf(uid), String.valueOf(position)});
            cursor.moveToNext();

            // 获取商品gid
            int gid = cursor.getInt(cursor.getColumnIndex("gid"));

            // 根据gid查询商品信息
            Cursor goodsCorsor = db.rawQuery("select * from goods where id = ?", new String[]{String.valueOf(gid)});
            goodsCorsor.moveToNext();
            // 获取商品名称
            String name = goodsCorsor.getString(goodsCorsor.getColumnIndex("name"));
            // 获取商品单价
            String price = goodsCorsor.getString(goodsCorsor.getColumnIndex("price"));


            // 将 item.xml 转换为 view
            View item = View.inflate(Browsing_history.this, R.layout.item_history, null);

            // 获取商品名称view，并设置值
            TextView titleView = item.findViewById(R.id.tv_title);
            titleView.setText(name);
            // 获取商品价格view，并设置值
            TextView priceView = item.findViewById(R.id.tv_price);
            priceView.setText("￥" + price);
            // 获取商品图片view，并设置值
            ImageView iconView = item.findViewById(R.id.iv_icon);
            String iconName = "item_" + gid;
            // 拿到图片ID
            int icon = Browsing_history.this.getResources().getIdentifier(iconName, "drawable", Browsing_history.this.getPackageName());
            // 设置图片
            Glide.with(Browsing_history.this).load(icon).into(iconView);

            cursor.close();
            goodsCorsor.close();
            db.close();
            return item;
        }
    }

    // 返回上一个活动
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //的弹窗
    private void popup() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.select_menu, null, false);//引入弹窗布局
        final PopupWindow popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);

        //设置进出动画
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);

        //引入依附的布局
        View parentView = LayoutInflater.from(Browsing_history.this).inflate(R.layout.select_menu, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

        // 点击确定
        vPopupWindow.findViewById(R.id.btn_determine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 清除浏览历史
                SQLiteDatabase db = myOpenHelper.getReadableDatabase();
                db.execSQL("DELETE FROM history WHERE uid = ?", new String[]{String.valueOf(uid)});
                db.close();

                finish();
                startActivity(getIntent());
            }
        });

        // 点击取消
        vPopupWindow.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });;

    }

    // 查询uid
    private int findUid(String username) {
        SQLiteDatabase db = new MyOpenHelper(Browsing_history.this).getReadableDatabase();
        Cursor cursor = db.rawQuery("select id from user where username = ?", new String[]{username});
        cursor.moveToNext();
        int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
        db.close();
        return id;
    }

}
