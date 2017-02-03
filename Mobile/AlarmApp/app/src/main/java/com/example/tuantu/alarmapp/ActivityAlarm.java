package com.example.tuantu.alarmapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by TUAN TU on 6/28/2016.
 */
public class ActivityAlarm extends AppCompatActivity {
    Button btnDismiss;
    Button btnCancel;
    public static MediaPlayer mp;
    private static ActivityAlarm ins;
    boolean isDismiss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isDismiss == false) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
            wl.acquire();
            ins = this;
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            setContentView(R.layout.activity_alarm);
            mp = MediaPlayer.create(this, R.raw.ringtone);
            mp.start();

            btnDismiss = (Button) findViewById(R.id.dismiss);
            btnCancel = (Button) findViewById(R.id.cancel);
            btnDismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlarmReceiver.CancelAlarm(getApplicationContext());
                    isDismiss = true;
                    mp.stop();
                    finish();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mp.stop();
                    finish();
                }
            });
            try {
                Intent intent = new Intent(this, DismissReceiver.class);
                //intent.putExtra("df",);
                PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

                Intent intent1 = new Intent(this, CancelReceiver.class);
                //intent.putExtra("df",);
                PendingIntent pIntent1 = PendingIntent.getBroadcast(this, 0, intent1, 0);

                Notification n = new Notification.Builder(this)
                        .setContentTitle("New mail from " + "test@gmail.com")
                        .setContentText("Subject")
                        .setVisibility(Notification.VISIBILITY_PUBLIC)
                        .setSmallIcon(R.drawable.dismiss)
                        .addAction(R.drawable.color, "Dismiss", pIntent)
                        .addAction(R.drawable.color, "Cancel", pIntent1)
                        .build();
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                notificationManager.notify(0, n);
            }catch (Exception e)
            {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
            // the addAction re-use the same intent to keep the example short
        }

    }
    public static void stop()
    {
        if (mp.isPlaying())
         mp.stop();
        ins.finish();
    }
    public static ActivityAlarm getIns(){
        return ins;
    }
}