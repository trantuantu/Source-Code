package com.example.tuantu.hw3_1;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

/**
 * Created by TUAN TU on 3/10/2016.
 */
public class activity3_1 extends AppCompatActivity {

    public String viewName;
    public String width;
    public String height;
    EditText vn,w,h;
    Button b;
    LinearLayout li;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout3_1);
        li = (LinearLayout)findViewById(R.id.content);
        vn = (EditText)findViewById(R.id.viewName);
        w = (EditText)findViewById(R.id.width);
        h = (EditText)findViewById(R.id.height);
        b = (Button)findViewById(R.id.create);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewName = vn.getText().toString();
                width = w.getText().toString();
                height = h.getText().toString();

                if (viewName.equals("EditText"))
                {
                   if ((!width.isEmpty() && Integer.parseInt(width) <= 0)|| width.isEmpty() ) Toast.makeText(getApplicationContext(),"Invalid width", Toast.LENGTH_LONG).show();
                   else if ((!height.isEmpty() && Integer.parseInt(height) <= 0) || height.isEmpty()) Toast.makeText(getApplicationContext(),"Invalid height", Toast.LENGTH_LONG).show();
                   else createEditTextByName(width, height);
                }
                else if (viewName.equals("Button")) {
                    if ((!width.isEmpty() && Integer.parseInt(width) <= 0)|| width.isEmpty() ) Toast.makeText(getApplicationContext(),"Invalid width", Toast.LENGTH_LONG).show();
                    else if ((!height.isEmpty() && Integer.parseInt(height) <= 0) || height.isEmpty()) Toast.makeText(getApplicationContext(),"Invalid height", Toast.LENGTH_LONG).show();
                    else createButtonByName(width, height);
                }
                else Toast.makeText(getApplicationContext(), "Wrong Name", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createButtonByName(String width, String height) {

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams((int)dipToPixels(this.getBaseContext(),Integer.parseInt(width)), (int)dipToPixels(this.getBaseContext(),Integer.parseInt(height)));

        Button b1 = new Button(this.getBaseContext());

        b1.setLayoutParams(param);
        b1.setText("Button");
        li.addView(b1);
    }

    private void createEditTextByName(String width, String height) {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams((int)dipToPixels(this.getBaseContext(),Integer.parseInt(width)), (int)dipToPixels(this.getBaseContext(),Integer.parseInt(height)));
        EditText e = new EditText(this.getBaseContext());
        e.setLayoutParams(param);
        e.setHint("Edit text");
        e.setTextColor(Color.parseColor("#ffffff"));
        e.setBackgroundColor(Color.parseColor("#3f3f3f"));
        li.addView(e);
    }
    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
}