package com.example.tuantu.homework4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by TUAN TU on 3/17/2016.
 */
public class MyButton extends View {
    //Using array of sprite
ArrayList<Bitmap> images = new ArrayList<Bitmap>();
ArrayList<MySprite> sprites = new ArrayList<MySprite>();
//default position
int _top = 400;
int _left = 400;
MySprite sprite;
boolean buttonType; //Expandable or not


public MyButton(Context context, AttributeSet attr)
{
    super(context, attr);
}

public MyButton(Context context)
{
    super(context);
    //InitMyView(_top, _left);
}
    int radiant;
    ArrayList<Point> destPoint = new ArrayList<Point>();
    public void InitMyView(int top, int left, int r)
    {
        radiant = r;
        _top = top;
        _left = left;
        Point root = new Point();
        root.x = left;
        root.y = top;
        destPoint = calculateCoordinate(root, images.size() - 1);
        float alpha = 180 / images.size();
        for (int i = 0; i < images.size(); i++)
            if (i > 0)
            {
                MySprite temp = new MySprite(images.get(i), left, top, alpha*i, destPoint.get(i - 1));
                sprites.add(temp);
            }

        else
            {
                MySprite temp = new MySprite(images.get(i), left, top, alpha*i, null);
                sprites.add(temp);
            }

    }
    //The first image in list is the image of root button
    public void addButtonImage(int id)
    {
      images.add(BitmapFactory.decodeResource(getResources(), id));
    }
    public void setButtonPosition(int top, int left)
    {
        _top = top;
        _left = left;
    }
    public void setExpand(boolean isExpand)
    {
        buttonType = isExpand;
    }
    //Called when onClick occurs
    public void startExpand()
    {
     invalidate();
    }
    CountDownTimer countDownTimer = new CountDownTimer(20000, 1) {
        @Override
        public void onTick(long millisUntilFinished) {

            update();
        }

        @Override
        public void onFinish() {

        }
    };

    CountDownTimer countDownTimer1 = new CountDownTimer(20000, 1) {
        @Override
        public void onTick(long millisUntilFinished) {

           restore();
        }

        @Override
        public void onFinish() {

        }
    };

    int flag = 0;
    int index = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        //Create many sprite, each sprite for 1 button
        //if (buttonType == false)
        //    sprite.Draw(canvas);
        //else
          //  sprite.Expand(canvas);
      // sprite.Draw1(canvas);
        if (index == 0)
        {
            sprites.get(index).Draw(canvas);
            index++;
        }
        else
        {
            //sprites.get(index).Update1();
            //sprites.get(index).Update1();
            //Canvas canvas1 = new Canvas();
            //sprites.get(0).Draw(canvas);
                sprites.get(0).Draw(canvas);
                for (int i = 1; i < sprites.size(); i++) {
                    //sprites.get(index).Update1();
                    //if (i == 0) sprites.get(0).Draw(canvas);
                    sprites.get(i).Draw1(canvas);
                }
        }
    }
    int flag1 = 0;
  //  int index1 = 0;
    public void update()
    {
            flag1++;
            sprites.get(1).Update1();
            sprites.get(2).Update1();
            sprites.get(3).Update1();
            if (flag1 < 30)
                invalidate();
        else countDownTimer.cancel();
        }

    public void restore()
    {
        flag++;
        flag++;
        sprites.get(1).Update2();
        sprites.get(2).Update2();
        sprites.get(3).Update2();
        if (flag < 30)
            invalidate();
        else
        {
            index = 0;
            invalidate();
            countDownTimer1.cancel();
            return;
        }
    }
    //}
    //Calculate all coordinate for all Point
    private ArrayList<Point> calculateCoordinate(Point root, int num) {
        ArrayList<Point> res = new ArrayList<Point>();
        double alpha = 180 / (num + 1);
        //int radiant = dpToPx(100);
        for (int i = 0; i < num; i++) {
            Point temp = new Point();
            if (alpha <= 90) {
                temp.y = (int)(-radiant * Math.sin(Math.toRadians(alpha)) + root.y);
                temp.x = (int) Math.sqrt(Math.pow(radiant, 2) - (Math.pow(temp.y - root.y, 2))) + root.x;
            } else {
                temp.y = (int) (-radiant * Math.sin(Math.toRadians(180 - alpha)) + root.y);
                temp.x = -(int) Math.sqrt(Math.pow(radiant, 2) - (Math.pow(temp.y - root.y, 2))) + root.x;
            }
            alpha += 180 / (num + 1);
            res.add(temp);
        }
        return res;
    }
    public void Start(boolean isExpand)
    {
        if (isExpand == true) {

            for (int i = 1; i < sprites.size(); i++) {
                sprites.get(i).x = _left;
                sprites.get(i).y = _top;
                flag1 = 0;
                flag = 0;
                //index = 0;
            }
            countDownTimer.start();
        }
        else
        {
            countDownTimer.cancel();
            countDownTimer1.start();
        }
    }
}