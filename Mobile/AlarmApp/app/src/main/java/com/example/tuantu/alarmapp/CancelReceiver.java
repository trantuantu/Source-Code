package com.example.tuantu.alarmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by TUAN TU on 6/29/2016.
 */
public class CancelReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            //AlarmReceiver.CancelAlarm(context);
            context.startService(new Intent(context, DismissService.class));
        }catch (Exception e)
        {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
