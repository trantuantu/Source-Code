package com.example.tuantu.hw3_1;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by TUAN TU on 3/10/2016.
 */
public class p implements Parcelable {
    public String mData;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mData);
    }

    public static final Parcelable.Creator<p> CREATOR
            = new Parcelable.Creator<p>() {
        public p createFromParcel(Parcel in) {
            return new p(in);
        }

        public p[] newArray(int size) {
            return new p[size];
        }
    };

    protected p(Parcel in) {
        mData = in.readString();
    }


    public TextView showResult(Context c){
        return null;
    }
}