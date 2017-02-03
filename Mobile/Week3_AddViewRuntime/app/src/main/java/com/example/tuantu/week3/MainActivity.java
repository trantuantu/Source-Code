package com.example.tuantu.week3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
TimerTask timerTask;
    Timer timer;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button btn = (Button)findViewById(R.id.stopBtn);
    }
    int[] a = new int[]{
        R.drawable.h1,
            R.drawable.h2,
                R.drawable.h3
    };
    int idx = 0;
    int nImage = 3;
//Press next to change image
public void onNext(View view)
{
   iv = (ImageView)findViewById(R.id.imageView);
    timer = new Timer();

    timerTask = new TimerTask() {
        @Override
        public void run() {
            iv.setImageResource(a[idx++%nImage]);
        }
    };
    timer.schedule(timerTask, 500, 40);
  /*
    ImageView iv = (ImageView)findViewById(R.id.imageView);
    iv.setImageResource(a[idx++%nImage]);*/

}
}