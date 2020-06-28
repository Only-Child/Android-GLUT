package com.example.wechat.info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.wechat.R;

public class Info_2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getActionBar();
//        if (NavUtils.getParentActivityName(Info_2Activity.this)!=null){     //显示返回按钮
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
    }
    //设置返回按钮
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
