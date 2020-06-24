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

public class MainActivity extends Activity {

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


        //设置布局启动器
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //添加装饰--设置四周间隔
        recyclerView.addItemDecoration(new MyDecoration());
        //设置适配器
        recyclerView.setAdapter(new StaggeredGridAdapter(MainActivity.this, new StaggeredGridAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(MainActivity.this,"click..."+pos,Toast.LENGTH_SHORT).show();
            }
        }));


        home.setOnClickListener(onclick);//为第一个导航图片添加单机事件
        extend.setOnClickListener(onclick);//为第二个导航图片添加单机事件
        shoppingcart.setOnClickListener(onclick);//为第三个导航图片添加单机事件
        person.setOnClickListener(onclick);//为第四个导航图片添加单机事件





    }
    //创建单机事件监听器
    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getFragmentManager();   // 获取Fragment
            FragmentTransaction ft = fm.beginTransaction(); // 开启一个事务
            Fragment f = null; //为Fragment初始化
            switch (v.getId()) {    //通过获取点击的id判断点击了哪个张图片
                case R.id.home:
                    f = new Home_Fragment(); //创建第一个Fragment
                    break;
                case R.id.extend:
                    f = new Extend_Fragment();//创建第二个Fragment
                    break;
                case R.id.shoppingcart:
                    f = new ShoppingCart_Fragment();//创建第三个Fragment
                    break;
                case R.id.person:
                    f = new Person_Fragment();//创建第四个Fragment
                    break;
                default:
                    break;
            }
            ft.replace(R.id.fragment, f); //替换Fragment
            ft.commit(); //提交事务
        }
    };
    //自定义装饰
    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int gap=getResources().getDimensionPixelSize(R.dimen.dividerHeight2);
            int gap1=getResources().getDimensionPixelSize(R.dimen.dividerHeight3);
            outRect.set(gap,gap,gap,gap);
        }
    }
}
