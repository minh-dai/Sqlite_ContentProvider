package com.example.minh_dai.test_contentprovider_sqlite.screen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minh_dai.test_contentprovider_sqlite.R;
import com.example.minh_dai.test_contentprovider_sqlite.data.Book;
import com.example.minh_dai.test_contentprovider_sqlite.data.Util;

public class MainActivity extends AppCompatActivity {

    private EditText mEdt_title;
    private EditText mEdt_author;
    private Button mBtnAdd;
    private Button mBtnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEdt_author.getText().toString() == "" || mEdt_title.getText().toString() == ""){
                    Toast.makeText(MainActivity.this, "Moi Nhap Du", Toast.LENGTH_SHORT).show();
                }else {
                    Book book =
                            new Book(mEdt_title.getText().toString(),mEdt_author.getText().toString());

                    getContentResolver().insert(Util.CONTENT_URI, book.getContentValues());

                    Toast.makeText(MainActivity.this, "Added \"" + book.toString() + "\"",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mEdt_author = findViewById(R.id.edt_author);
        mEdt_title = findViewById(R.id.edt_title);
        mBtnAdd = findViewById(R.id.btn_add);
        mBtnShow = findViewById(R.id.btn_show);
    }
}
