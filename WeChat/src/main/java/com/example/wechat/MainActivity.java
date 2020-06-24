package com.example.wechat;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.wechat.pojo.Goods;
import com.example.wechat.utils.MySqliteHelper;
import com.example.wechat.utils.StaggeredGridAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    Home_Fragment  homefragment;
    Extend_Fragment extend_fragment;
    Person_Fragment person_fragment;
    ShoppingCart_Fragment shoppingCart_fragment;
    ImageView home;
    ImageView extend;
    ImageView shoppingcart;
    ImageView person;


    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView=findViewById(R.id.pu);
        ImageView home = (ImageView) findViewById(R.id.home);//获取布局文件的第一个导航图片
        ImageView extend = (ImageView) findViewById(R.id.extend);//获取布局文件的第二个导航图片
        ImageView shoppingcart = (ImageView) findViewById(R.id.shoppingcart);//获取布局文件的第三个导航图片
        ImageView person = (ImageView) findViewById(R.id.person);//获取布局文件的第四个导航图片





        home.setOnClickListener(this);//为第一个导航图片添加单机事件
        extend.setOnClickListener(this);//为第二个导航图片添加单机事件
        shoppingcart.setOnClickListener(this);//为第三个导航图片添加单机事件
        person.setOnClickListener(this);//为第四个导航图片添加单机事件





    }
//    //创建单机事件监听器


    @Override
    public void onClick(View v) {

        FragmentManager manager = getFragmentManager();   // 获取Fragment
        FragmentTransaction transaction = manager.beginTransaction(); // 开启一个事务
        switch (v.getId()) {    //通过获取点击的id判断点击了哪个张图片
            case R.id.home:
                if (homefragment == null) {
                    homefragment = new Home_Fragment();
                }
                transaction.replace(R.id.fragment, homefragment);
//                home.setImageResource(R.drawable.bottom_1);
                break;
            case R.id.extend:
                if (extend_fragment == null) {
                    extend_fragment = new Extend_Fragment();
                }
                transaction.replace(R.id.fragment, extend_fragment);
//                extend.setImageResource(R.drawable.bottom_2);
                break;
            case R.id.shoppingcart:
                if (person_fragment == null) {
                    person_fragment = new Person_Fragment();
                }
                transaction.replace(R.id.fragment, person_fragment);
//                shoppingcart.setImageResource(R.drawable.bottom_3);
                break;
            case R.id.person:
                if (shoppingCart_fragment == null) {
                    shoppingCart_fragment = new ShoppingCart_Fragment();
                }
                transaction.replace(R.id.fragment, shoppingCart_fragment);
//                person.setImageResource(R.drawable.bottom_4);
                break;

            default:
                break;
        }
        transaction.commit();
    }


}
