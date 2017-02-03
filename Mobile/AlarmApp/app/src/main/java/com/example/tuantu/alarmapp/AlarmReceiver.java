package com.example.tuantu.alarmapp;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
            try {
                Intent intent1 = new Intent(context, ActivityAlarm.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
                SetAlarm(context, 10000);
            } catch (Exception e) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
    }

    public void SetAlarm(Context context, long interval)
    {
        try {
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(context, AlarmReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
            am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + interval, pi);
            //Toast.makeText(context, "Set time compeleted!" + interval, Toast.LENGTH_LONG).show();
        }catch (Exception e)
        {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public static void CancelAlarm(Context context)
    {
        Toast.makeText(context, "Cancel", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}