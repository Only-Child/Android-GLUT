package com.example.wechat.info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.ActionBar;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wechat.R;
import com.example.wechat.Register_Activity;
import com.example.wechat.pojo.Goods;
import com.example.wechat.utils.DataBaseOperate;
import com.example.wechat.utils.MySqliteHelper;

public class Info_1Activity extends AppCompatActivity {

    private TextView mtextView;
    private ImageView mimageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_1);

        mtextView=findViewById(R.id.tv_info);
        mimageView=findViewById(R.id.miv);
        Bundle extras = getIntent().getExtras();
        String info = extras.getString("info");
        //从数据库中获取商品数据
        SQLiteDatabase database = DataBaseOperate.create(Info_1Activity.this);
        //查询商品图片并保存

        Cursor cursor = database.rawQuery("select * from goods where name =?",new String[]{info});

        cursor.moveToFirst();
        Goods goods1=new Goods();
        goods1.setName(cursor.getString(cursor.getColumnIndex("name")));
        goods1.setCategory(cursor.getString(cursor.getColumnIndex("category")));
        goods1.setPrice( cursor.getDouble(cursor.getColumnIndex("price")));
        goods1.setSrc(cursor.getString(cursor.getColumnIndex("src")));
        goods1.setStorage(cursor.getInt(cursor.getColumnIndex("storage")));
        cursor.close();
        // 拿到图片名字
        String iconName=goods1.getSrc();
        // 拿到图片ID
        int icon = this.getResources().getIdentifier(iconName, "drawable", this.getPackageName());
        // 设置图片
        Glide.with(this).load(icon).into(mimageView);

        //设置文字
        mtextView.setText(goods1.getName()+"   ￥"+goods1.getPrice());

    }


}
