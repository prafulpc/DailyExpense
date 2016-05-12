package com.example.android.dailyexpense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by PRAFUL on 3/23/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SpendCategory2.db";
    public static final String TABLE_NAME = "Spend";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "AMOUNT";
    public static final String COL_3 = "CATEGORY";
    public static final String COL_4 ="DATE";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
        SQLiteDatabase db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT TEXT, CATEGORY TEXT, DATE DATE)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData (String spend_amt, String category, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,spend_amt);
        contentValues.put(COL_3,category);
        contentValues.put(COL_4,date);
        long result = db.insert(TABLE_NAME, null,contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME,null);
        return res;
    }
}
