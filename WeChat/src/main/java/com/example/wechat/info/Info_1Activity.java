package com.example.wechat.info;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.wechat.R;

public class Info_1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_1);








    }
    private void setActionBar() {
        ActionBar actionBar = getActionBar();
        // 显示返回按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
        // 去掉logo图标
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("返回");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:   //返回键的id
                this.finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
