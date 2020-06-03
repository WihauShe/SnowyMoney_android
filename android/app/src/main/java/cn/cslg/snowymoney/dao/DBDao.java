package cn.cslg.snowymoney.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBDao {
    private SQLiteDatabase db;

    //init db
    public DBDao(Context context){
        DBHelper dbHelper = new DBHelper(context,"SnowyMoney.db",null,2);
        db = dbHelper.getReadableDatabase();
    }

    //different actions on account
    public void insertAccount(String account){
        String sql = "insert into account values(?,?)";
        db.execSQL(sql,new String[]{account,Integer.toString(0)});
    }
    public Cursor getAccountById(String id){
        String sql = "select * from account where _id = ?";
        return db.rawQuery(sql,new String[]{id});
    }
    public Cursor getAccountByConstraint(String constraint){
        String sql = "select * from account where _id like '%"+constraint+"%'";
        return db.rawQuery(sql,null);
    }
    public void deleteAccount(String account){
        String sql = "update account set is_del = 1 where _id = ?";
        db.execSQL(sql,new String[]{account});
    }

    //different actions on record
    public void insertRecord(String content){
        String sql = "insert into record(content) values(?)";
        db.execSQL(sql,new String[]{content});
    }
    public Cursor getRecordByContent(String content){
        String sql = "select * from record where content = ?";
        return db.rawQuery(sql,new String[]{content});
    }
    public Cursor getRecordByConstraint(String constraint){
        String sql = "select * from record where content glob '*"+constraint+"*'";
        return db.rawQuery(sql,null);
    }
    public void deleteRecord(int recordId){
        String sql = "delete from record where _id = ?";
        db.execSQL(sql,new String[]{Integer.toString(recordId)});
    }

    public void insertUser(String userId,String userName,String userImg){
        String sql = "insert into user values(?,?,?)";
        db.execSQL(sql,new String[]{userId,userName,userImg});
    }
    public Cursor getUsers(){
        String sql = "select * from user";
        return db.rawQuery(sql,null);
    }
    public Cursor getUserById(String id){
        String sql = "select * from user where _id = ?";
        return db.rawQuery(sql,new String[]{id});
    }
}
