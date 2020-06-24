package com.example.wechat;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView home = (ImageView) findViewById(R.id.home);//获取布局文件的第一个导航图片
        ImageView extend = (ImageView) findViewById(R.id.extend);//获取布局文件的第二个导航图片
        ImageView shoppingcart = (ImageView) findViewById(R.id.shoppingcart);//获取布局文件的第三个导航图片
        ImageView person = (ImageView) findViewById(R.id.person);//获取布局文件的第四个导航图片


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


}
