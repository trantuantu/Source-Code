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
public class p2 extends p{


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mData);
    }

    public static final Parcelable.Creator<p2> CREATOR
            = new Parcelable.Creator<p2>() {
        public p2 createFromParcel(Parcel in) {
            return new p2(in);
        }

        public p2[] newArray(int size) {
            return new p2[size];
        }
    };

    private p2(Parcel in) {
        super(in);
    }

    @Override
    public TextView showResult(Context c){
        TextView text = new TextView(c);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(1000, LinearLayout.LayoutParams.WRAP_CONTENT);
        param.bottomMargin = 50;
        param.gravity = Gravity.CENTER;
        text.setText(mData);
        text.setBackgroundColor(Color.parseColor("#ffa500"));
        text.setLayoutParams(param);
        return text;
    }
}
