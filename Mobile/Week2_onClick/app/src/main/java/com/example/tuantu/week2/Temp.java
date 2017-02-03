package com.example.tuantu.week2;

import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

/**
 * Created by TUAN TU on 3/8/2016.
 */
//Cach 3
public class Temp implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        ((EditText))newView = CreateViewByName(((EditText)owner.findViewById(R.id.text)))
    }
private MainActivity owner;
    public  void setOwner (MainActivity mainActivity)
    {
        owner = mainActivity;
    }
}
