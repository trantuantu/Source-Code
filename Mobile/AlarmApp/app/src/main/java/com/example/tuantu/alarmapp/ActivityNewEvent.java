package com.example.tuantu.alarmapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.ArrayList;

/**
 * Created by TUAN TU on 6/28/2016.
 */
public class ActivityNewEvent extends AppCompatActivity {
    Button btnOK;
    Button btnCancel;
    String time, date;
    boolean isOn;
    TimePicker timePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newevent);

        btnOK = (Button)findViewById(R.id.ok);
        btnCancel = (Button)findViewById(R.id.cancel);
        timePicker = (TimePicker)findViewById(R.id.timePicker);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    time = String.valueOf(timePicker.getHour()) + " : " + String.valueOf(timePicker.getMinute());
                    isOn = false;
                    Event newEvent = new Event();
                    newEvent._time = time;
                    newEvent.isOn = isOn;
                    MainActivity.events.add(newEvent);
                    MainActivity.adapter.update(MainActivity.events);
                    MainActivity.insert(MainActivity.events.size(), String.valueOf(timePicker.getHour()), String.valueOf(timePicker.getMinute()), newEvent.isOn);
                    finish();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
