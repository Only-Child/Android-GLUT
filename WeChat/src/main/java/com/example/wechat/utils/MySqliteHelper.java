package com.example.wechat.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySqliteHelper extends SQLiteOpenHelper {
    static int dbVersion=1;
    private static final String db_name = "db";
    public MySqliteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String  sql ="create table wx_user(" +
                "id int primary key," +
                "name varchar(30)," +
                "tou varchar(20)," +
                "content varchar(50)," +
                "dateTime varchar(30)" +
                ")";
        db.execSQL(sql);
        return;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
