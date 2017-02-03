package com.example.tuantu.hw2;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static int convertDpToPixels(float dp, Context context){
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ImageView img = (ImageView) findViewById(R.id.imageView);
        TextView courseName = (TextView) findViewById(R.id.textView);
        TextView name = (TextView) findViewById(R.id.textView1);
        TextView mssv = (TextView) findViewById(R.id.textView2);
        GridLayout gr = (GridLayout)findViewById((R.id.content));

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayout.LayoutParams param1 = new GridLayout.LayoutParams();
            param1.rowSpec = GridLayout.spec(1);
            param1.columnSpec = GridLayout.spec(0);
           // param1.rowSpec = gr.row
            param1.topMargin = convertDpToPixels(80, this.getBaseContext());
            param1.width = convertDpToPixels(150, this.getBaseContext());
            param1.height = convertDpToPixels(150, this.getBaseContext());
            img.setLayoutParams(param1);

           // LinearLayout li = new LinearLayout(this.getBaseContext());
           // li.setOrientation(LinearLayout.VERTICAL);
           // gr.addView(new LinearLayout(this.getBaseContext()),new GridLayout.LayoutParams(GridLayout.spec(1), GridLayout.spec(1)));

            GridLayout.LayoutParams param2 = new GridLayout.LayoutParams();
            param2.rowSpec = GridLayout.spec(1);
            param2.columnSpec = GridLayout.spec(1);
            param2.topMargin = convertDpToPixels(70, this.getBaseContext());
            param2.leftMargin = convertDpToPixels(80, this.getBaseContext());
            courseName.setLayoutParams(param2);
            GridLayout.LayoutParams param3 = new GridLayout.LayoutParams();
            param3.rowSpec = GridLayout.spec(1);
            param3.columnSpec = GridLayout.spec(1);

            param3.leftMargin = convertDpToPixels(130, this.getBaseContext());
            param3.topMargin =convertDpToPixels(100, this.getBaseContext());
            GridLayout.LayoutParams param4 = new GridLayout.LayoutParams();
            param4.rowSpec = GridLayout.spec(1);
            param4.columnSpec = GridLayout.spec(1);

            param4.leftMargin = convertDpToPixels(150, this.getBaseContext());
            param4.topMargin = convertDpToPixels(130, this.getBaseContext());

           // param3.topMargin = 210;
            name.setLayoutParams(param3);
            mssv.setLayoutParams(param4);





        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_main);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
