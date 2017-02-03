package com.example.tuantu.week6;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by TUAN TU on 4/3/2016.
 */
public class topSongAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> _songNames;
    String[] _choices;
    static int count = 0;
    private static LayoutInflater inflater = null;

    public topSongAdapter(Context a, ArrayList<String> songNames, String[] choices)
    {
        context = a;
        _songNames = songNames;
        _choices = choices;
       // context.setTheme(R.style.AppTheme);
        inflater = LayoutInflater.from(context);


    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return _songNames.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    static class ViewHolder{
        public  Button button;
        public  Spinner spinner;
        public  TextView textView;
    }

    ViewHolder holder = null;
    int flag = 0;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

            // TODO Auto-generated method stub

        //Maximize performances listView
            //View rowView;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.custom_listview, null);
            holder.button = (Button) convertView.findViewById(R.id.button);
            holder.spinner = (Spinner) convertView.findViewById(R.id.spinner);
            holder.textView = (TextView) convertView.findViewById(R.id.artist);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, _choices); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spinner.setAdapter(spinnerArrayAdapter);
            convertView.setTag(holder);
            //GradientDrawable gradientDrawable = (GradientDrawable) holder.button.getBackground();
            //gradientDrawable.mutate(); // needed line
            //gradientDrawable.invalidateSelf();

            //Spinner--------------------------------------------------------
        }
       /* else
            holder = convertView.getTag();*/
        else
        holder = (ViewHolder)convertView.getTag();

        holder.button.setText(_songNames.get(position));
        holder.textView.setText(topSong.listSong.get(position).select("div.inblock.ellipsis").text());

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String temp = topSong.listSong.get(position).select("h3.title-item a").attr("href");
                        if (isNetworkConnected() && isOnline()) {
                            (new topSong.loadAblum()).execute(new String[]{temp});
                            player.Execute(temp);
                            player.songName.setText(topSong.listSong.get(position).select("h3.title-item a").attr("title"));
                            player.singer.setText(topSong.listSong.get(position).select("h4.title-sd-item a").text());
                        }
                        else if (!isNetworkConnected())
                        {
                            Toast.makeText(context, "Không có kết nối mạng", Toast.LENGTH_LONG).show();
                        }
                        else if (isNetworkConnected() && !isOnline())  Toast.makeText(context, "Đường truyền không ổn định", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

{

}

            //holder.spinner.setSelection(0);


            //holder.spinner.setVisibility();

       /* holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(context, "Show", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });*/
            //---------------------------------------------------------------

        /*rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //Toast.makeText(context, "You Clicked ", Toast.LENGTH_LONG).show();
            }
        });*/


       /* holder.button.setOnTouchListener(new View.OnTouchListener() {
            @Override
        public boolean onTouch(View v, MotionEvent event)
            {
                if (v.isPressed()) v.setBackground("#198791");
            }
        });*/
            return convertView;

    // return  null;
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
