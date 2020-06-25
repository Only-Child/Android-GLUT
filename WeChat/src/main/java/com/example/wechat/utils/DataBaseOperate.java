package com.example.wechat.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.wechat.info.Info_1Activity;

public class DataBaseOperate {

    //生成数据库实例
    public static SQLiteDatabase create(Context context) {
        MySqliteHelper mySQLDatabase = new MySqliteHelper(context, "db", null, 1);
        return mySQLDatabase.getWritableDatabase();

    }
}
