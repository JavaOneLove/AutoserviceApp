package com.example.autoserviceapp.fragmentData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    final String LOG_TAG = "DBLog";
    ContentValues cv;
    SQLiteDatabase db;

    public SQLiteHelper(@Nullable Context context) {
        super(context, "MyDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table User ("
                + "id integer primary key autoincrement,"
                + "Username text,"
                + "Email text,"
                + "Password text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onInsert(String Username,String Email,String Password){
        cv = new ContentValues();
        db = getWritableDatabase();
        cv.put("id",1);
        cv.put("Username",Username);
        cv.put("Email",Email);
        cv.put("Password",Password);
        db.insert("User",null,cv);
        Log.d(LOG_TAG,"Insert user: "
                + Username + ", "
                + Email + ", "
                + Password);
        db.close();
    }
    public void onDelete(){
        cv = new ContentValues();
        db = getWritableDatabase();
        db.delete("User",null,null);
        Log.d(LOG_TAG, "deleted rows");
        db.close();
    }

    public int onRead(){
        db = getWritableDatabase();
        Log.d(LOG_TAG, "--- Rows in User: ---");
        Cursor c = db.query("User", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int id = c.getInt(idColIndex);
            Log.d(LOG_TAG, Integer.toString(id));
            c.close();
            db.close();
            return id;
        } else {
            Log.d(LOG_TAG, "0 rows");
            c.close();
            db.close();
            return 0;
        }
    }
}
