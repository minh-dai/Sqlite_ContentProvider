package com.example.minh_dai.broadcastreceiver_services_notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "On or Off Internet", Toast.LENGTH_SHORT).show();
    }
}
