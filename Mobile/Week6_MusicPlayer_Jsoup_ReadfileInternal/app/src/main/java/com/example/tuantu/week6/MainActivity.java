package com.example.tuantu.week6;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.Scanner;


public class MainActivity extends FragmentActivity {
    private ViewPager viewPager;
    private TabPagerAdapter mAdapter;
    private ActionBar actionBar;
    public static FragmentActivity activity;
    // Tab titles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        //---------------------------------TAB-VIEW-------------------------
        viewPager = (ViewPager) findViewById(R.id.pager);
        TabPagerAdapter PagerAdapter =
                new TabPagerAdapter(
                        getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager .setAdapter(PagerAdapter);

        //---------------------------------TAB-VIEW-------------------------
    }
    public static void resume()
    {

    }
    @Override
   public void onResume()
    {
        super.onResume();
    }


}