package com.example.minh_dai.broadcastreceiver_services_notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

public class MyService extends Service {

    private static final int ID = 1;
    private final IBinder iBinder = new LocalBinder();
    private MediaPlayer mediaPlayer;
    private boolean checkNotification;
    private RemoteViews mRemoteViews;
    private Notification mNotification;
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mediaPlayer == null)
            mediaPlayer = MediaPlayer.create(this, R.raw.cap_doi_dep_nhat);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotification("Cap Doi Dep Nhat", R.drawable.pause);
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case "pause":
                    handler.sendEmptyMessage(0);
                    if (checkNotification) {
                        stopMedia();
                    }
                    else {
                        playMedia();
                    }
                    break;
                case "stop":
                    stopMedia();
                    stopSelf();
                    break;
            }
        }
        return START_STICKY;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void playMedia(){
        // tu dang dung sang chay
        checkNotification = true;
        createNotification("Cap Doi Dep Nhat", R.drawable.pause);
        startMediaplayer();
    }

    private void stopMedia(){
        // tu dang chay sang dung
        checkNotification = false;
        createNotification("Cap Doi Dep Nhat", R.drawable.stop);
        stopMediaPlayer();

    }

    public void updateNotification() {
        if (!checkNotification) {
            stopMedia();
        } else {
            playMedia();
        }
        mediaPlayer.start();
    }

    public void stopMediaPlayer() {
        mediaPlayer.pause();
    }

    public void startMediaplayer() {
        mediaPlayer.start();
    }

    public void setCheckNotification(boolean checkNotification) {
        this.checkNotification = checkNotification;
        updateNotification();
    }

    public boolean getCheckNotification() {
        return checkNotification;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    public class LocalBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    public void createNotification(String title, int d) {

        mRemoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        mRemoteViews.setTextViewText(R.id.textView, title);
        mRemoteViews.setImageViewResource(R.id.btnStart, d);
        Intent intent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent =
                PendingIntent.getActivities(this, (int) System.currentTimeMillis(),
                        new Intent[]{intent}, 0);

        Intent intent1 = new Intent(this , MyService.class);
        intent1.setAction("pause");
        PendingIntent pendingIntent1 =
                PendingIntent.getService(this, (int) System.currentTimeMillis(),
                        intent1, 0);

        Intent intentActionStop = new Intent(this, MyService.class);
        intentActionStop.setAction("stop");
        PendingIntent pStopSelf =
                PendingIntent.getService(this, (int) System.currentTimeMillis(), intentActionStop,
                        0);

        mRemoteViews.setOnClickPendingIntent(R.id.btnStart , pendingIntent1);
        mRemoteViews.setOnClickPendingIntent(R.id.layout , pendingIntent);
        mRemoteViews.setOnClickPendingIntent(R.id.btnThoat, pStopSelf);

        Notification.Builder notificationBuilder =
                new Notification.Builder(getApplicationContext());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNotification = notificationBuilder.setSmallIcon(R.drawable.shark_icon)
                    .setDefaults(Notification.FLAG_NO_CLEAR)
                    .setContent(mRemoteViews)
                    .build();
        }
        startForeground(ID, mNotification);
    }


}


