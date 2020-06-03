package cn.cslg.snowymoney.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "create table account(_id text primary key ,is_del integer default 0)";
        db.execSQL(sql1);
        String sql2 = "create table record(_id integer primary key autoincrement ,content text unique)";
        db.execSQL(sql2);
        String sql3 = "create table user(_id text primary key , name text ,img text)";
        db.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql1 = "drop table if exists account";
        db.execSQL(sql1);
        String sql2 = "drop table if exists record";
        db.execSQL(sql2);
        String sql3 = "drop table if exists user";
        db.execSQL(sql3);
        onCreate(db);
    }
}
