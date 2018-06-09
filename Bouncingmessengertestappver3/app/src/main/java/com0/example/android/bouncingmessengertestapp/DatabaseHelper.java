package com0.example.android.bouncingmessengertestapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by arihant on 1/25/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="phone.db";
    public static final String TABLE_NAME ="contacts";
    public static final String col1 ="id";
    public static final String col2 ="name";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME ,null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     db.execSQL("create table "+TABLE_NAME+" (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
      db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
      onCreate(db);
    }
    public boolean insertData(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col2,name);
        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
}
