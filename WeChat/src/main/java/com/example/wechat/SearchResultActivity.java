package com.example.wechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wechat.info.Info_1Activity;
import com.example.wechat.pojo.Goods;
import com.example.wechat.utils.DataBaseOperate;
import com.example.wechat.utils.LinearAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

        private RecyclerView recyclerView;
        private List<Goods> goodsimg=new ArrayList<>();
        private List list=new ArrayList();
        private Button button;
        private TextView textView;

       @Override
       protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        recyclerView=findViewById(R.id.result);
        button=findViewById(R.id.btn_return);
        textView=findViewById(R.id.tv_fail);
         //返回键
           button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   onBackPressed();
               }
           });
        //得到搜索结果
        Bundle extras = getIntent().getExtras();
        final String result = extras.getString("result");
           //判断查询结果是否为空
           //从数据库中获取商品数据
           SQLiteDatabase database = DataBaseOperate.create(SearchResultActivity.this);
           //查询商品
           Cursor cursor = database.rawQuery("select * from goods where name like '%"+result+"%'",null);
           if(cursor.getCount()!=0){
               textView.setText(" ");
           }
           cursor.close();
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));
        recyclerView.addItemDecoration(new MyDecoration());
        recyclerView.setAdapter(new LinearAdapter(SearchResultActivity.this, new LinearAdapter.OnItemClickListener(){
            @Override
            public void onClick(int pos) {
                //从数据库中获取商品数据
                SQLiteDatabase database = DataBaseOperate.create(SearchResultActivity.this);

                    //查询商品
                    Cursor cursor = database.rawQuery("select * from goods where name like '%" + result + "%'", null);


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
                        cursor.close();
                        //查询商品保存并传递到详情页
                        Bundle bundle = new Bundle();
                        bundle.putString("info", goodsimg.get(pos).getName());
                        Intent intent = new Intent(SearchResultActivity.this, Info_1Activity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }

        },result));

    }
    //自定义装饰2
    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int gap1=getResources().getDimensionPixelSize(R.dimen.dividerHeight2);
            outRect.set(0,0,0,gap1);
        }
    }
    //返回
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
