package com.example.minh_dai.test_contentprovider_sqlite.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class BookContenProvider extends ContentProvider {

    public static final String PATH = "books";
    public static final int PATH_TOKEN = 100;
    public static final String PATH_FOR_ID = "books/*";
    public static final int PATH_FOR_ID_TOKEN = 200;
    public static final UriMatcher URI_MATCHER = getUriMathcher();

    private BookDB bookDB;

    @Override
    public boolean onCreate() {
        bookDB = new BookDB(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        SQLiteDatabase db = bookDB.getReadableDatabase();
        int math = URI_MATCHER.match(uri);
        switch (math) {
            case PATH_TOKEN:
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(Util.TABLE_NAME);
                Cursor cursor =
                     builder.query(db , strings, s, strings1, null , null , s1);
                return cursor;
            case PATH_FOR_ID_TOKEN:
                int bookId = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder1 = new SQLiteQueryBuilder();
                builder1.setTables(Util.TABLE_NAME);
                builder1.appendWhere(Util.ID + "=" + bookId);

                Cursor cursor1 =
                        builder1.query(db , strings, s, strings1, null , null , s1);
                return cursor1;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int math = URI_MATCHER.match(uri);
        switch (math) {
            case PATH_TOKEN:
                return Util.CONTENT_DIR_TYPE;
            case PATH_FOR_ID_TOKEN:
                return Util.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("URI " + uri + " is not supported.");
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = bookDB.getWritableDatabase();
        int token = URI_MATCHER.match(uri);
        switch (token) {
            case PATH_TOKEN: {
                long id = db.insert(Util.TABLE_NAME, null, contentValues);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return Util.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            default: {
                throw new UnsupportedOperationException("URI: " + uri + " not supported.");
            }
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = bookDB.getWritableDatabase();
        int token = URI_MATCHER.match(uri);
        int rowsDeleted = -1;
        switch (token) {
            case (PATH_TOKEN):
                rowsDeleted = db.delete(Util.TABLE_NAME, s, strings);
                break;
            case (PATH_FOR_ID_TOKEN):
                String tvShowIdWhereClause = Util.ID + "=" + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(s))
                    tvShowIdWhereClause += " AND " + s;
                rowsDeleted = db.delete(Util.TABLE_NAME, tvShowIdWhereClause, strings);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        // Notifying the changes, if there are any
        if (rowsDeleted != -1)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    public static UriMatcher getUriMathcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Util.AUTHORITY, PATH, PATH_TOKEN);
        uriMatcher.addURI(Util.AUTHORITY, PATH_FOR_ID, PATH_FOR_ID_TOKEN);
        return uriMatcher;
    }
}
