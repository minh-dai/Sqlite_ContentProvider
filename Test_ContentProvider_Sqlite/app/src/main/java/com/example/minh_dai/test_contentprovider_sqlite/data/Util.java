package com.example.minh_dai.test_contentprovider_sqlite.data;

import android.net.Uri;

public interface Util {

    String TABLE_NAME = "book";
    String ID = "_id";
    String TITLE = "title";
    String AUTHOR = "author";

    String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" +
            ID + " integer   primary key autoincrement, " +
            TITLE + " text not null, " +
            AUTHOR + " text not null " +
            ");";

    String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.minh_dai.book";
    String CONTENT_DIR_TYPE = "vnd.android.cursor.dir/vnd.minh_dai.book";
    String AUTHORITY = "com.example.minh_dai.test_contentprovider_sqlite.provider";
    Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/books");
}
