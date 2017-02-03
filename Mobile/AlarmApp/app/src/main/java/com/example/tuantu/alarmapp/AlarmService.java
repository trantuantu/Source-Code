package com.example.tuantu.alarmapp;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by TUAN TU on 6/28/2016.
 */
public class AlarmService extends Service {
    AlarmReceiver alarm = new AlarmReceiver();
    public static long interval;
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        alarm.SetAlarm(this, interval);
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId)
    {}

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }
    public class LocalBinder extends Binder {
        AlarmService getService() {
            return AlarmService.this;
        }
    }
    private final IBinder mBinder = new LocalBinder();
}
