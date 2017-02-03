package com.example.tuantu.testadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter<String> {
    private int resource;
    ArrayList<String> _items = new ArrayList<String>();

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapter(Context context, int resource, ArrayList<String> items) {
        super(context, resource, items);
        _items = items;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(resource, null);
        }

      //  Item p = getItem(position);

        //if (p != null) {
            TextView txtName = (TextView) v.findViewById(R.id.txtName);
            TextView txtDateOfPublish = (TextView) v.findViewById(R.id.txtDateOfPublish);
            txtName.setText(_items.get(position).toString());
        return v;
    }

}