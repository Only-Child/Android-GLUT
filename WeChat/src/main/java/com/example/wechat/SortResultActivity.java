package com.example.wechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wechat.info.Info_1Activity;
import com.example.wechat.pojo.Goods;
import com.example.wechat.utils.DataBaseOperate;
import com.example.wechat.utils.SortStaggeredGridAdapter;
import com.example.wechat.utils.StaggeredGridAdapter;

import java.util.ArrayList;
import java.util.List;

public class SortResultActivity extends AppCompatActivity {

    private RecyclerView mrecyclerView;
    private Button mbutton;
    private List<Goods> goodsList=new ArrayList<>();
    private SharedPreferences msharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_result);
        mrecyclerView=findViewById(R.id.ry_sort);
        mbutton=findViewById(R.id.btn_return);
        msharedPreferences=getSharedPreferences("mrsoft",MODE_PRIVATE);
        //返回键
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //得到传递过来的位置分类信息
        Bundle extras = getIntent().getExtras();
        final int pos2 = extras.getInt("pos");


        String goodsName2=getGoodsName(pos2);

        //设置布局启动器
        mrecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //添加装饰--设置四周间隔
        mrecyclerView.addItemDecoration(new MyDecoration());
        //设置适配器
        new DataBaseOperate();
        mrecyclerView.setAdapter(new SortStaggeredGridAdapter(SortResultActivity.this, new SortStaggeredGridAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                String goodsName=getGoodsName(pos2);
                //从数据库中获取商品数据
                SQLiteDatabase database = DataBaseOperate.create(SortResultActivity.this);
                //查询商品并保存
                Cursor cursor = database.query("goods", null, "category = ?", new String[]{goodsName},null,null,null);
                if(cursor.moveToFirst()) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        Goods goodsitem = new Goods();
                        goodsitem.setName(cursor.getString(cursor.getColumnIndex("name")));
                        goodsitem.setCategory(cursor.getString(cursor.getColumnIndex("category")));
                        goodsitem.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
                        goodsitem.setSrc(cursor.getString(cursor.getColumnIndex("src")));
                        goodsitem.setStorage(cursor.getInt(cursor.getColumnIndex("storage")));
                        goodsList.add(goodsitem);
                        cursor.moveToNext();
                    }

                }
                cursor.close();
                if(msharedPreferences.getString("username",null)!=null){
                    String name=msharedPreferences.getString("username","");
                    //查表得到userid
                    Cursor cursor2=database.rawQuery("select * from user where username =?",new String[]{name});
                    cursor2.moveToFirst();
                    int userid=cursor2.getInt(cursor2.getColumnIndex("id"));
                    cursor2.close();
                    //查表得到goodsid
                    Cursor cursor3=database.rawQuery("select * from goods where name =?",new String[]{goodsList.get(pos).getName()});
                    cursor3.moveToFirst();
                    int goodsid=cursor3.getInt(cursor3.getColumnIndex("id"));
                    cursor3.close();
                    //插入表中
                    Cursor cursor4 = database.rawQuery("select * from history where gid=?", new String[]{String.valueOf(goodsid)});
                    if(cursor4.getCount()==0) {
                        database.execSQL("insert into history(uid,gid) values(?,?)", new String[]{String.valueOf(userid), String.valueOf(goodsid)});
                    }
                    cursor4.close();
                }
                database.close();
                //将商品信息传到详情页
                Bundle bundle=new Bundle();
                bundle.putString("info",goodsList.get(pos).getName());

                Intent intent=new Intent(SortResultActivity.this, Info_1Activity.class);
                intent.putExtras(bundle);
                startActivity(intent);

        }
        }, goodsName2, DataBaseOperate.create(SortResultActivity.this)));
    }
    //自定义装饰1
    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int gap=getResources().getDimensionPixelSize(R.dimen.dividerHeight2);
            int gap1=getResources().getDimensionPixelSize(R.dimen.dividerHeight3);
            outRect.set(gap,gap,gap,gap);
        }
    }
    //根据位置信息获得分类名
    private String getGoodsName(int pos) {
        if(pos==0){
            return "clothes";
        }else if (pos==1){
            return "electric";
        }else if(pos==2){
            return "food";
        }else {
            return null;
        }
    }
}
