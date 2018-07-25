package com.example.minh_dai.test_contentprovider_sqlite.data;

import android.content.ContentValues;
import android.database.Cursor;

public class Book {

    private String mTitle;
    private String mAuthor;

    public Book() {
    }

    public Book(String mTitle, String mAuthor) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(Util.AUTHOR, mAuthor);
        values.put(Util.TITLE, mTitle);
        return values;
    }

    public static Book fromCursor(Cursor curTvShows) {
        String title = curTvShows.getString(curTvShows.getColumnIndex(Util.TITLE));
        String author = curTvShows.getString(curTvShows.getColumnIndex(Util.AUTHOR));
        return new Book(title, author);
    }

    @Override
    public String toString() {
        return mTitle + " (" + mAuthor + ")";
    }
}
