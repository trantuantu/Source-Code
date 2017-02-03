package com.example.tuantu.downloader;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by TUAN TU on 7/25/2016.
 */
public class MyView extends View {
    MySprite sprite;
    int []coor;
    int top, left;
    int id;
    boolean isUpdate;
    long percent;
    public MyView(Context context, int top, int left) {
        super(context);
    }

    public MyView(Context context, AttributeSet attr)
    {
        super(context, attr);
        coor = new int[2];
        getLocationInWindow(coor);
        this.top = coor[1];
        this.left = coor[0];
        sprite = new MySprite(context, left, top);
        percent = 0;
    }
    @Override
    protected void onDraw(Canvas canvas) {
    /*    if (isUpdate)
        {
            //DrawARC here
            sprite.drawCircle(canvas, 50);
            isUpdate = false;
        }
        else
        {
            //sprite.drawCircle(canvas, 50);
            sprite.drawButton(canvas, id);
            sprite.drawCircle(canvas, 50);
        }*/
        sprite.drawButton(canvas, id);
        if (isUpdate)
        {
            sprite.drawCircle(canvas, percent);
            return;
            //isUpdate = false;
        }

            //isUpdate = true;
    }

    public void Draw(int id)
    {
        this.id = id;
        isUpdate = false;
        invalidate();
    }

    public void updatePercent(long percent)
    {
        isUpdate = true;
        this.percent = percent;
        invalidate();
    }
}