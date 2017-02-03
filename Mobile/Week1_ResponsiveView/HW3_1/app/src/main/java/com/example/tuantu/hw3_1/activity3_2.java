package com.example.tuantu.hw3_1;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

public class activity3_2 extends AppCompatActivity {
    public Button b1, b2, b3;
    public Spinner sp;
    public LinearLayout li;
    public LinearLayout prev;
    public LinearLayout newLi;
    public int index = 0;
    public String res = "";
    ArrayList<EditText> arr = new ArrayList<EditText>();
    ArrayList<LinearLayout> arr2 = new ArrayList<LinearLayout>();
    ArrayList<p> arr1 = new ArrayList<p>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout3_2);

        //s.split();
        li = (LinearLayout)findViewById(R.id.content);
       b1 = (Button)findViewById(R.id.add);
        b2 = (Button)findViewById(R.id.save);
        b3 = (Button)findViewById(R.id.show);
        sp = (Spinner)findViewById(R.id.spinner);
        //Submit and show result
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == 0)
                {
                    prev = (LinearLayout)findViewById(R.id.start);
                    newLi = new LinearLayout(getApplicationContext());
                    newLi.setOrientation(LinearLayout.HORIZONTAL);
                    newLi.setGravity(Gravity.CENTER);
                    newLi.setBaselineAligned(true);
                    li.addView(newLi);
                    arr2.add(newLi);
                    index++;
                }
                else
                {
                    prev = newLi;
                    newLi = new LinearLayout(getApplicationContext());
                    newLi.setOrientation(LinearLayout.HORIZONTAL);
                    newLi.setGravity(Gravity.CENTER);
                    newLi.setBaselineAligned(true);
                    li.addView(newLi);
                    arr2.add(newLi);
                    index++;
                }
                LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                param1.weight = 1;
                EditText name = new EditText(getApplicationContext());
                name.setHint("Name");
                name.setTextColor(Color.parseColor("#ffffff"));
                name.setBackgroundColor(Color.parseColor("#0000ff"));
                name.setLayoutParams(param1);
                LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                param2.weight = 1;
                EditText value = new EditText(getApplicationContext());
                value.setHint("Value");
                value.setTextColor(Color.parseColor("#ffffff"));
                value.setBackgroundColor(Color.parseColor("#545454"));
                value.setLayoutParams(param2);


                prev.removeAllViews();
                newLi.addView(b1);
                newLi.addView(b2);
                newLi.addView(b3);

                prev.addView(name);
                prev.addView(value);
                arr.add(name);
                arr.add(value);
            }
        });
        b2.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v)
            {
                TextView t = new TextView(getApplicationContext());
                for (int i = 0; i < arr.size();)
                {
                res += arr.get(i).getText().toString() + ": " + arr.get(i + 1).getText().toString() + "\n";

                    i += 2;
                }
                Parcel source = Parcel.obtain();
                if (sp.getSelectedItemId() == 0)
                {
                    p1 obj = p1.CREATOR.createFromParcel(source);
                    obj.mData = res;
                    arr1.add(obj);
                }
                else if (sp.getSelectedItemId() == 1)
                {
                    p2 obj = p2.CREATOR.createFromParcel(source);
                    obj.mData = res;
                    arr1.add(obj);
                }

                Toast.makeText(getApplicationContext(), "Item has been saved", Toast.LENGTH_LONG).show();
                for (int i = 0; i < arr2.size() - 1; i++)
                {
                    li.removeView(arr2.get(i));
                }
                newLi = (LinearLayout)findViewById(R.id.start);
                newLi.removeAllViews();
                arr2.get(arr2.size() - 1).removeAllViews();//tranh trung id, ta phai remove truoc khi add lai
                newLi.addView(b1);
                newLi.addView(b2);
                newLi.addView(b3);
                li.removeView(arr2.get(arr2.size() - 1));
                arr.removeAll(arr);
                arr2.removeAll(arr2);
                index = 0;
                res = "";
            }
        }
        );

        b3.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v)
            {
                //Passing array via P
                Intent intent = new Intent("com.example.tuantu.activity3_2res");
                intent.putParcelableArrayListExtra("info", arr1);
                startActivity(intent);
            }

        });
    }
}
