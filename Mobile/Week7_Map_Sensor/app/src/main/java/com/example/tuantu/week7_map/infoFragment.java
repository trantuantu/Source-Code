package com.example.tuantu.week7_map;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by TUAN TU on 4/11/2016.
 */
public class infoFragment extends android.support.v4.app.Fragment {

    public static View rootView = null;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(
                    R.layout.info, container, false);
        }
        return rootView;
    }
}
