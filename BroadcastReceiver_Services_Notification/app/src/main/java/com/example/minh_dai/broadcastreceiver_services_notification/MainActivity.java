package com.example.minh_dai.broadcastreceiver_services_notification;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private ImageView img;
    private ImageButton btn;
    private boolean check = false;
    private MyService mBoundService;
    private Intent mIntent;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check){
                    btn.setImageResource(R.drawable.pause);
                    check = true;
                    // true dang chay
                    mBoundService.setCheckNotification(true);
                    // chay nhac cua bound service
                    mBoundService.startMediaplayer();

                    // chay foregroud service
                    startService(mIntent);
                }else {

                    // false dung lai
                    mBoundService.setCheckNotification(false);
                    check = false;
                    btn.setImageResource(R.drawable.stop);

                    // dung nhac cua bound service
                    mBoundService.stopMediaPlayer();

                    // dung foregroud service
                    //stopService(mIntent);
                }
            }

        });
    }

    private void checkStatusPlay(){
        if (mBoundService != null){
            if (!mBoundService.getCheckNotification()){
                // nhac dung
                check = false;
                btn.setImageResource(R.drawable.stop);
            }
            else {
                btn.setImageResource(R.drawable.pause);
                // nhac chay
                check = true;
            }

        }
    }

    private void initView() {
        btn = findViewById(R.id.imageButton);
        img = findViewById(R.id.imageView);
        mIntent = new Intent(this, MyService.class);

        // chay bound service
        bindService(mIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

        mHandler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                if (msg!= null){
                    checkStatusPlay();
                }
            }
        };
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            mBoundService = binder.getService();
            mBoundService.setHandler(mHandler);
            initBaseValue();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    private void initBaseValue() {
        checkStatusPlay();
    }


}
