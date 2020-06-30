package com.example.wechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.wechat.utils.DataBaseOperate;

public class AlterPassword extends AppCompatActivity {
        private EditText et_original_psw;
        private EditText et_new_psw;
        private EditText et_new_psw_again;
        private Button btn_save;
             SharedPreferences sp;
        private String getPassword;          //获取SharedPreferences中保存的密码
        private String getUsername;             //获取SharedPreferences中保存的账号
       private ImageButton back;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_password);
        sp=getSharedPreferences("mrsoft",MODE_PRIVATE);
        et_original_psw=findViewById(R.id.et_original_psw);
        et_new_psw=findViewById(R.id.et_new_psw);
        et_new_psw_again=findViewById(R.id.et_new_psw_again);
        btn_save=findViewById(R.id.btn_save);
        getPassword=sp.getString("password","");
        back=findViewById(R.id.ib_return);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        /************单击事件*************/
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sqLiteDatabase=DataBaseOperate.create(AlterPassword.this);         //创建数据库连接对象
                if (et_original_psw.getText().toString().equals(getPassword)&&et_new_psw.getText().toString().equals(et_new_psw_again.getText().toString()))
                {               //判断原始密码是否正确，两次输入密码是否正确
                     getUsername = sp.getString("username", "");       //获得SharedPreferences中用户分账号
                    String sql = "update user set password=? where username=?";
                    sqLiteDatabase.execSQL(sql, new String[]{et_new_psw_again.getText().toString(), getUsername});
                    sqLiteDatabase.close();
                    SharedPreferences.Editor editor = sp.edit();   //获得SharedPreferences编辑对象
                    editor.remove("username");   //清除SharedPreferences中的账号
                    editor.remove("password");   //清除SharedPreferences中的密码
                    editor.commit();             //提交事务
                    Toast.makeText(getApplicationContext(),"修改密码成功,请重新登入！",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(AlterPassword.this,Login_Activity.class);  //跳转到登入界面
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"您输入的密码有误，请重新输入！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**********单价事件结束*********8*/
    }


}
