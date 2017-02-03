package com.example.tuantu.downloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import java.util.ArrayList;

/**
 * Created by TUAN TU on 7/25/2016.
 */
public class MySprite extends AppCompatActivity {
    private ArrayList<Bitmap> BMP = new ArrayList<Bitmap>();
    int left, top, width, height;
    public MySprite(Context context, int left, int top)
    {
        width = dpToPx(context, 50);
        height = width;
        try {
            BMP.add((Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.download), width, height,false)));
            BMP.add((Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.pause), width, height, false)));
            BMP.add((Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.stop),   width, height, false)));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        this.left = left;
        this.top = top;
    }

    public void drawCircle(Canvas canvas, long percent)
    {
        //float radius = width / 2;
        RectF oval = new RectF();
        //oval.set(width / 2 - radius, 0 - radius, width / 2 + radius, 0 + radius);
        //Path myPath = new Path();
        //myPath.arcTo(oval, startAngle, -(float) sweepAngle, true);
        Paint  paint =  new Paint();
        paint.setColor(Color.parseColor("#00BFFF"));
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        //paint.setTextScaleX(5);
        //paint.

        oval.set(5, 10, width, height);
        if (percent > 100)
        {
            String test = "";
        }
        canvas.drawArc(oval, -90, (percent * 360 / 100), false, paint);
    }
    public void drawButton(Canvas canvas, int id)
    {
        //Rect sourceRect = new Rect(0, 0, (width), (height));
        //canvas.drawBitmap(BMP.get(id), sourceRect, sourceRect, null);
        canvas.drawBitmap(BMP.get(id), 5, 5, null);
    }

    public int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
