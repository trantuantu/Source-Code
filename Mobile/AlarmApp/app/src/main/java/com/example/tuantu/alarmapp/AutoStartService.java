package com.example.tuantu.alarmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by TUAN TU on 6/28/2016.
 */
public class AutoStartService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            try {
                if (MainActivity.events == null) MainActivity.events = new ArrayList<Event>();
                MainActivity.getData(context);
                SimpleDateFormat format = new SimpleDateFormat("hh : mm");
                Date date1 = format.parse(MainActivity.events.get(MainActivity.events.size() - 1)._time);
                Calendar c = Calendar.getInstance();
                Date date2 = c.getTime();

                String test = date2.getHours() + " : " + date2.getMinutes();
                Date date3 = format.parse(test);

                long interval = date1.getTime() - date3.getTime();
                if (interval < 0) interval += 86400000;

                AlarmService.interval = interval;
                context.startService(new Intent(context, AlarmService.class));
                Toast.makeText(context, "Boot completed!", Toast.LENGTH_LONG).show();
                Toast.makeText(context, String.valueOf(interval), Toast.LENGTH_LONG).show();
            }catch (Exception e)
            {
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
