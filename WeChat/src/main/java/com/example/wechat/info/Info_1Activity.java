package com.example.wechat.info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wechat.Login_Activity;
import com.example.wechat.R;
import com.example.wechat.Register_Activity;
import com.example.wechat.pojo.Goods;
import com.example.wechat.utils.DataBaseOperate;
import com.example.wechat.utils.MySqliteHelper;

public class Info_1Activity extends AppCompatActivity {

    private TextView mtextView1,mtextView2,mtextView3;
    private ImageView mimageView;
    private Button button,mshop;
    private SharedPreferences msharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_1);

        mtextView1=findViewById(R.id.tv_price1);
        mtextView2=findViewById(R.id.tv_name);
        mtextView3=findViewById(R.id.tv_stock);
        mimageView=findViewById(R.id.miv);
        button=findViewById(R.id.btn_return2);
        mshop=findViewById(R.id.shop);
        msharedPreferences=getSharedPreferences("mrsoft",MODE_PRIVATE);

        //返回键
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Bundle extras = getIntent().getExtras();
        final String info = extras.getString("info");
        //从数据库中获取商品数据
        final SQLiteDatabase database = DataBaseOperate.create(Info_1Activity.this);
        //查询商品图片并保存

        final Cursor cursor = database.rawQuery("select * from goods where name =?",new String[]{info});

        cursor.moveToFirst();
        final Goods goods1=new Goods();
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
        mtextView1.setText("￥"+goods1.getPrice());
        mtextView2.setText(goods1.getName());
        mtextView3.setText("库存："+goods1.getStorage());
        //加入购物车
        mshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Info_1Activity.this);
                builder.setTitle("提示").setMessage("是否要加入购物车？")
                        .setIcon(R.drawable.tip)
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = msharedPreferences.getString("username", "");
                                if (msharedPreferences.getString("username", null) == null) {
                                    Toast.makeText(Info_1Activity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Info_1Activity.this, Login_Activity.class);

                                    startActivity(intent);
                                }else {
                                    //查表得到userid
                                    Cursor cursor2 = database.rawQuery("select * from user where username =?", new String[]{name});
                                    cursor2.moveToFirst();
                                    int userid = cursor2.getInt(cursor2.getColumnIndex("id"));
                                    cursor2.close();
                                    //查表得到goodsid
                                    Cursor cursor3 = database.rawQuery("select * from goods where name =?", new String[]{goods1.getName()});
                                    cursor3.moveToFirst();
                                    int goodsid = cursor3.getInt(cursor3.getColumnIndex("id"));
                                    Double total = cursor3.getDouble(cursor3.getColumnIndex("price"));
                                    cursor3.close();
                                    //插入表中
                                    Cursor cursor4 = database.rawQuery("select * from car where gid=?", new String[]{String.valueOf(goodsid)});

                                    if (cursor4.getCount() == 0) {
                                        Log.d("还是喜欢","getCount()");
                                        database.execSQL("insert into car(uid,gid,amount,total) values(?,?,1,?)", new String[]{String.valueOf(userid),String.valueOf(goodsid),String.valueOf(total)});
                                        Toast.makeText(Info_1Activity.this, "添加成功！", Toast.LENGTH_SHORT).show();
                                    }
                                    cursor4.close();

                                }
                            }
                        }).setNegativeButton("再考虑一下", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(Info_1Activity.this,"lj",Toast.LENGTH_SHORT).show();
                    }
                }).show();

            }
        });
    }
    //返回
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
