package com.example.tuantu.imageloader;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by TUAN TU on 8/10/2016.
 */
public class PageFragment extends Fragment {
    private TextView en, vn, point;
    private ImageView buyTicket;
    private  ViewPager mPager;
    private pagerAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pager_view, container, false);
        en = (TextView) v.findViewById(R.id.en);
        vn = (TextView) v.findViewById(R.id.vn);
        point = (TextView) v.findViewById(R.id.point);
        buyTicket = (ImageView) v.findViewById(R.id.buyTicket);
        buyTicket.setImageResource(R.drawable.buy_ticket);
        mPager = (ViewPager)v.findViewById(R.id.pager);

        return  v;
    }

    public void setPagerAdapter(ArrayList<PagerItem> items, Context context)
    {
        if (adapter == null)
        {
            adapter = new pagerAdapter(context, items, en, vn, point);
        }
        adapter.setViewEn(en);
        adapter.setViewVi(vn);
        adapter.setViewPoint(point);
        mPager.setAdapter(adapter);
    }
}
