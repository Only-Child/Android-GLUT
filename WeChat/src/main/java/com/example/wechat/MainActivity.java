package com.example.wechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Home_Fragment  homefragment;
    Extend_Fragment extend_fragment;
    Person_Fragment person_fragment;
    ShoppingCart_Fragment shoppingCart_fragment;

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ImageView home = (ImageView) findViewById(R.id.home);//获取布局文件的第一个导航图片
        ImageView extend = (ImageView) findViewById(R.id.extend);//获取布局文件的第二个导航图片
        ImageView shoppingcart = (ImageView) findViewById(R.id.shoppingcart);//获取布局文件的第三个导航图片
        ImageView person = (ImageView) findViewById(R.id.person);//获取布局文件的第四个导航图片
        homefragment=new Home_Fragment();
        Bundle bundle=new Bundle();
        bundle.putString("title","l");
        homefragment.setArguments(bundle);

        home.setOnClickListener(this);//为第一个导航图片添加单机事件
        extend.setOnClickListener(this);//为第二个导航图片添加单机事件
        shoppingcart.setOnClickListener(this);//为第三个导航图片添加单机事件
        person.setOnClickListener(this);//为第四个导航图片添加单机事件
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, homefragment,"").commitAllowingStateLoss();




    }
   //创建单机事件监听器


    @Override
    public void onClick(View v) {
        sp=getSharedPreferences("mrsoft",MODE_PRIVATE);
        String getuser=sp.getString("username","");
        switch (v.getId()) {    //通过获取点击的id判断点击了哪个张图片
            case R.id.home:
                homefragment=new Home_Fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, homefragment).addToBackStack(null).commitAllowingStateLoss();

                break;
            case R.id.extend:

                    extend_fragment = new Extend_Fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, extend_fragment).addToBackStack(null).commitAllowingStateLoss();

                break;
            case R.id.person:
                if (getuser.isEmpty()){
                    Intent intent=new Intent(MainActivity.this,Login_Activity.class);
                    Toast.makeText(MainActivity.this,"请先登入",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else {
                    person_fragment=new Person_Fragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, person_fragment).addToBackStack(null).commitAllowingStateLoss();
                }


                break;
            case R.id.shoppingcart:

                shoppingCart_fragment=new ShoppingCart_Fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, shoppingCart_fragment).addToBackStack(null).commitAllowingStateLoss();

                break;

            default:
                break;
        }

    }
    /**********设置点击fragment后改变图标*******************/
    public void setImage(){


    }


}
