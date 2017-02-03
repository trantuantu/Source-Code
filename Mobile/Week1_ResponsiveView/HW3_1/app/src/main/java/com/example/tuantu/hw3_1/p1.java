package com.example.tuantu.hw3_1;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by TUAN TU on 3/10/2016.
 */
public class p1 extends p{


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mData);
    }

    public static final Parcelable.Creator<p1> CREATOR
            = new Parcelable.Creator<p1>() {
        public p1 createFromParcel(Parcel in) {
            return new p1(in);
        }

        public p1[] newArray(int size) {
            return new p1[size];
        }
    };

    private p1(Parcel in) {
        super(in);
    }
    @Override
    public TextView showResult(Context c){
        TextView text = new TextView(c);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(1000, LinearLayout.LayoutParams.WRAP_CONTENT);
        param.bottomMargin = 50;
        param.gravity = Gravity.CENTER;
        text.setText(mData);
        text.setBackgroundColor(Color.parseColor("#5518ab"));
        text.setLayoutParams(param);
        return text;
    }
}
