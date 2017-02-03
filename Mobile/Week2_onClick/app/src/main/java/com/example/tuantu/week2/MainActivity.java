package com.example.tuantu.week2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        GridLayout.LayoutParams a;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //Cach 1
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });

        //Add view dynamically

        final LinearLayout li = (LinearLayout)findViewById(R.id.content);
        final EditText et = new EditText(this.getBaseContext());
        li.addView(et);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(v);
        /*new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String control = et.getText().toString();
                if(control.equals("Button")){
                    Button btn = new Button(MainActivity.this);
                    li.addView(btn);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btn.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                }else if(control.equals("TextView")){
                    TextView txtView = new TextView(MainActivity.this);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) txtView.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                    li.addView(txtView);
                }else if(control.equals("EditText")){
                    EditText txtView = new EditText(MainActivity.this);
                    li.addView(txtView);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) txtView.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
            }
        });


*/

    }

    Temp t = new Temp();
    //Cach 2
    View.OnClickListener v=  new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

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
