package com.example.wechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.wechat.pojo.User;
import com.example.wechat.utils.DataBaseOperate;

import java.util.ArrayList;
import java.util.List;

public class Login_Activity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login;
    private Button register;
    private String getped;
    private String getuname;
    private RadioButton remember;
    public List<User> users=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        /*********获取组件*******/
        username=findViewById(R.id.eusername);
        password=findViewById(R.id.epassword);
        login=findViewById(R.id.btn_login);
        password=findViewById(R.id.epassword);
        username=findViewById(R.id.eusername);
        remember=findViewById(R.id.radioRemember);
        register=findViewById(R.id.toRegister);
        /************获取组件结束***********/

        if (NavUtils.getParentActivityName(Login_Activity.this)!=null){     //显示返回按钮
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        /*****************注册按钮事件**********/
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //跳转到注册界面
                Intent intent=new Intent(Login_Activity.this,Register_Activity.class);
                startActivity(intent);
            }
        });
        /*****************注册按钮结束*****************/
        //获取  Share Preferences
        final SharedPreferences sp=getSharedPreferences("mrsoft",MODE_PRIVATE);
        final String spUsername=sp.getString("username",null);
        final String spPassword=sp.getString("password",null);
        /**********判断文件是否有存储过*******************/
        if (spUsername!=null&&spPassword!=null){

            Intent intent=new Intent(Login_Activity.this,ShoppingCart_Fragment.class);
            startActivity(intent);

        }

        /*************登入按钮单机事件**************/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getped=password.getText().toString();
                getuname=username.getText().toString();
                if (iflogin()==true){
                    if (remember.isChecked()) {
                        SharedPreferences.Editor editor = sp.edit();           //获取Editor对象
                        editor.putString("username", getuname);           //保存账号
                        editor.putString("password", getped);           //保存密码
                        editor.commit();                                     //提交
                    }
//                   System.out.println(users.toString());
                    Intent intent=new Intent(Login_Activity.this,MainActivity.class);
                    startActivity(intent);


                }else {
                    Toast.makeText(Login_Activity.this,"密码或者账号错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**********************单击事件结束************************/
    }
    public boolean iflogin(){
        SQLiteDatabase sqLiteDatabase=DataBaseOperate.create(Login_Activity.this);
        Cursor cursor=sqLiteDatabase.query("user",null,"username=? and password=?",new String[]{getuname,getped},null,null,null);

        if (cursor.moveToFirst()) {    //移动到第一行

            User user=new User();
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            users.add(user);


            cursor.close();
            return true;
        }

        return  false;
    }

}
