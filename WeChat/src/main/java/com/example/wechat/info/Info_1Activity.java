package com.example.wechat.info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.wechat.R;
import com.example.wechat.pojo.Goods;
import com.example.wechat.utils.DataBaseOperate;
import com.example.wechat.utils.MySqliteHelper;

public class Info_1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getActionBar();

        //从数据库中获取商品数据
        SQLiteDatabase database = DataBaseOperate.create(Info_1Activity.this);

        Cursor cursor = database.query("goods", null, "id=1", null,null,null,null);
        cursor.moveToFirst();
        Goods goods1=new Goods();


    }


    //设置返回按钮
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}
