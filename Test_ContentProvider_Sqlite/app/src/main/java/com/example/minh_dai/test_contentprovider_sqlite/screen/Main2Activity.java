package com.example.minh_dai.test_contentprovider_sqlite.screen;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.minh_dai.test_contentprovider_sqlite.R;
import com.example.minh_dai.test_contentprovider_sqlite.data.Book;
import com.example.minh_dai.test_contentprovider_sqlite.data.Util;
import com.example.minh_dai.test_contentprovider_sqlite.recyclerview.RecyclerViewApdater;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private List<Book> mList;
    private RecyclerView mRecyclerView;
    private RecyclerViewApdater mApdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mList = readFromContentProvider();
        mApdater = new RecyclerViewApdater(mList);

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mApdater);

    }

    private List<Book> readFromContentProvider() {
        Cursor curTvShows = getContentResolver().query(
                Util.CONTENT_URI,
                null,
                null,
                null,
                null);
        List<Book> shows = new ArrayList<Book>();
        if (curTvShows != null) {
            while (curTvShows.moveToNext())
                shows.add(Book.fromCursor(curTvShows));
            curTvShows.close();
        }
        return shows;
    }
}
