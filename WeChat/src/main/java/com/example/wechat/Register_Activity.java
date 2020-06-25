package com.example.wechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wechat.utils.DataBaseOperate;

public class Register_Activity extends AppCompatActivity {
    private EditText inUsername;
    private EditText inPassword;
    private EditText inPassword2;
    private Button btn_register;
    private Button btn_tologin;
    private EditText inName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
        inUsername=findViewById(R.id.reg_username);
        inPassword=findViewById(R.id.reg_password);
        inPassword2=findViewById(R.id.reg_password2);
        btn_register=findViewById(R.id.reg_btn_sure);
        btn_tologin=findViewById(R.id.reg_btn_login);
        inName=findViewById(R.id.reg_name);
        if (NavUtils.getParentActivityName(Register_Activity.this)!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        /******************点进去登入*******************/
        btn_tologin.setOnClickListener(new View.OnClickListener() {  //返回登入
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register_Activity.this,Login_Activity.class);
                startActivity(intent);
            }
        });
       /********************************/
       /****************点击注册事件********************/
       btn_register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (comfirmPasswors()&&CheckIsDataAlreadyInDBorNot()){
                   SQLiteDatabase sqLiteDatabase= DataBaseOperate.create(Register_Activity.this);

                   ContentValues values=new ContentValues();
                   values.put("username",inUsername.getText().toString());
                   values.put("password",inPassword.getText().toString());
                   values.put("name",inName.getText().toString());
                   sqLiteDatabase.insert("user",null,values);
                   sqLiteDatabase.close();

                   Toast.makeText(Register_Activity.this,"注册成功",Toast.LENGTH_SHORT).show();
               }
               else {
                   Toast.makeText(Register_Activity.this,"用户名已注册",Toast.LENGTH_SHORT).show();
               }

           }
       });
    }

    public boolean comfirmPasswors(){
        if (inPassword.getText().toString().equals(inPassword2.getText().toString()))             //前后输入的密码一致
            return true;

        return  false;
    }

    public boolean CheckIsDataAlreadyInDBorNot(){
        SQLiteDatabase sqLiteDatabase= DataBaseOperate.create(Register_Activity.this);
        String sql="select * from user where username=?";
        Cursor cursor=sqLiteDatabase.rawQuery(sql,new String[]{inUsername.getText().toString()});
        if (cursor.getCount()>0){
            cursor.close();
            return false;
        }
        return true;
    }

}
