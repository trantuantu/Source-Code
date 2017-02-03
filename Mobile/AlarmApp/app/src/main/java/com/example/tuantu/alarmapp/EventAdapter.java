package com.example.tuantu.alarmapp;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by TUAN TU on 6/28/2016.
 */
public class EventAdapter extends ArrayAdapter<Event> {
    ArrayList<Event> _event;
    Context context;
    int resource;
    public EventAdapter(Context context, int resource, ArrayList<Event> event) {
        super(context, resource);
        _event = event;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(resource, null);
        }

        TextView time = (TextView)v.findViewById(R.id.txtTime);
        TextView date = (TextView)v.findViewById(R.id.txtDate);
        Switch sw = (Switch)v.findViewById(R.id.switch1);

        time.setText(_event.get(position)._time);
        date.setText(_event.get(position)._date);

        if (_event.get(position).isOn == true)
            sw.setChecked(true);
        else sw.setChecked(false);

        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_event.get(position).isOn == true)
                {
                    Event temp = _event.get(position);
                    temp.isOn = false;
                    _event.set(position, temp);
                    MainActivity.update(false, position + 1);
                }
                else
                {
                    Event temp = _event.get(position);
                    temp.isOn = true;
                    _event.set(position, temp);
                    MainActivity.update(true, position + 1);
                    //AlarmManager alm = (AlarmManager)
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("hh : mm");
                        Date date1 = format.parse(_event.get(position)._time);
                        Calendar c = Calendar.getInstance();
                        Date date2 = c.getTime();

                        String test = date2.getHours() + " : " + date2.getMinutes();
                        Date date3 = format.parse(test);

                        long interval = date1.getTime() - date3.getTime();
                        if (interval < 0) interval += 86400000;

                        AlarmService.interval = interval;
                        context.startService(new Intent(context, AlarmService.class));

                        String formatInterval = String.valueOf(interval/1000/3600) + " : " + String.valueOf(interval/1000%3600/60);
                        Toast.makeText(context, "Alarm will ring in " + formatInterval, Toast.LENGTH_LONG).show();

                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    //AlarmService.timeInterVal;
                }
            }
        });
        return v;
    }
    public void update(ArrayList<Event> items) {
        this._event = items;
        this.notifyDataSetChanged();
    }
    @Override
    public Event getItem(int position) {
        return _event.get(position);
    }

    @Override
    public int getCount() {
        return _event.size();
    }
}
