package com.example.student;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper( Context context,  String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table timu (sid interger primary key ,title text,answer text, " +
                "style text,zhangshu text, jieshu text);";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
