package com.example.tuantu.week8_news;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter<String> {
    private int resource;
    Context c = null;
    ArrayList<String> _name = new ArrayList<String>();
    ArrayList<String> _time = new ArrayList<String>();
    ArrayList<String> _url = new ArrayList<String>();

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        c = context;
    }

    public ListAdapter(Context context, int resource, ArrayList<String> name, ArrayList<String> time, ArrayList<String> url) {
        super(context, resource, name);
        _name = name;
        _time = time;
        _url = url;
        this.resource = resource;
        c = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(resource, null);
        }

        //  Item p = getItem(position);

        //if (p != null) {
        TextView name = (TextView) v.findViewById(R.id.Name);
        TextView time = (TextView) v.findViewById(R.id.Time);
        name.setText(_name.get(position).toString());
        time.setText(_time.get(position).toString());
        v.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(c, newsDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    c.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                newsDetails.url = _url.get(position);
            }
        });
        return v;
    }
}