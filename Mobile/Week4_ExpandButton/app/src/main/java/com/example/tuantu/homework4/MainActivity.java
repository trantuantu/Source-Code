package com.example.tuantu.homework4;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MyButton btn1 = new MyButton(this);
        btn1.addButtonImage(R.drawable.h1);
        btn1.addButtonImage(R.drawable.h2);
        btn1.addButtonImage(R.drawable.h3);
        btn1.addButtonImage(R.drawable.h4);
        btn1.InitMyView(1200, 500, dpToPx(300));
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.content);

      /*  MyButton btn2 = new MyButton(this);
        btn2.addButtonImage(R.drawable.cow);
        btn2.addButtonImage(R.drawable.pig);
        btn2.InitMyView(800, 400);
        layout.addView(btn2);
        btn2.Start();
*/
      /*  LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(dpToPx(400), dpToPx(400));
       btn1.setLayoutParams(param);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button click", Toast.LENGTH_LONG).show();
            }
        });*/
        //RelativeLayout.LayoutParams re = new RelativeLayout.LayoutParams(dpToPx(500), dpToPx(500));

       // btn1.setLayoutParams(re);
        layout.addView(btn1);
        //int flag = 0;
        btn1.setOnClickListener(new View.OnClickListener() {
            public int flag = 0;
            @Override
            public void onClick(View v) {
                flag++;
                if (flag % 2 != 0)
                {
                    btn1.Start(true);
                    Toast.makeText(getApplicationContext(), "Expand", Toast.LENGTH_LONG).show();
                }
                else
                {
                    btn1.Start(false);
                    Toast.makeText(getApplicationContext(), "Restore", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();

        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

}
