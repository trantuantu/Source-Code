package com.example.tuantu.mapgeocodingapi;

/**
 * Created by TUAN TU on 4/1/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Search extends Activity {
    EditText enter;
    Button b;
    String place;
    final Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        enter=(EditText)findViewById(R.id.enter);
        b=(Button)findViewById(R.id.search);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,MainActivity.class);
                place=enter.getText().toString();
                i.putExtra("place",place);
                startActivity(i);

            }
        });
    }
}