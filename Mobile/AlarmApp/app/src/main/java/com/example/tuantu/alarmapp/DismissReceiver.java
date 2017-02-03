package com.example.tuantu.alarmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

/**
 * Created by TUAN TU on 6/29/2016.
 */
public class DismissReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
          AlarmReceiver.CancelAlarm(context);
            //if (ActivityAlarm.mp == null)  ActivityAlarm.mp = MediaPlayer.create(context, R.raw.ringtone);
            //ActivityAlarm.mp.release();
            //if (ActivityAlarm.mp.isPlaying()) ActivityAlarm.mp.stop();
           // context.getSharedPreferences("DismissReceiver", Context.MODE_PRIVATE);
            //ActivityAlarm.getIns().stop();
            context.startService(new Intent(context, DismissService.class));
        }catch (Exception e)
        {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
