package com.example.tuantu.hw3_1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by TUAN TU on 3/10/2016.
 */
public class activity3_2res extends AppCompatActivity
{
    public ArrayList<p> arr = new ArrayList<p>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout3_2res);
        LinearLayout li = (LinearLayout)findViewById(R.id.resLay);
        arr = getIntent().getParcelableArrayListExtra("info");

        for (int i = 0; i < arr.size(); i++)
        {
            TextView text = arr.get(i).showResult(getApplicationContext());
            li.addView(text);
        }
    }
}
