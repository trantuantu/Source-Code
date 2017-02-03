package com.example.tuantu.alarmapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by TUAN TU on 6/29/2016.
 */
public class DismissService extends Service {
    public void onCreate()
    {
        super.onCreate();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        ActivityAlarm.stop();

        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }
    public class LocalBinder extends Binder {
        DismissService getService() {
            return DismissService.this;
        }
    }
    private final IBinder mBinder = new LocalBinder();
}
