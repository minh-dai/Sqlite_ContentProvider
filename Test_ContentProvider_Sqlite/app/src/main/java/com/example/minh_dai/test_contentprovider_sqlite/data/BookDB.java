package com.example.minh_dai.test_contentprovider_sqlite.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BookDB";
    private static final int DATABASE_VERSION = 1;

    public BookDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Util.DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        sqLiteDatabase.execSQL(Util.DATABASE_CREATE);
    }
}
