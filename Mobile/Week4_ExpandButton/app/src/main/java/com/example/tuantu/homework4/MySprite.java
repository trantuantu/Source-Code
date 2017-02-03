package com.example.tuantu.homework4;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import java.util.ArrayList;

/**
 * Created by MinhTrietTRAN on 3/15/2016.
 */
public class MySprite extends AppCompatActivity {
    public ArrayList<Bitmap> BMP = new ArrayList<Bitmap>();
    public Point destPoint;
    //public int idxBMP;
    //public int nBMPs;
    float _alpha;
    Point root = new Point();
    public Point curentPoint = new Point();
    //int radiant;
    public MySprite(Bitmap bmp, int l, int t, float alpha, Point dPoint)
    {
        Bitmap temp = bmp;
        BMP.add(temp);
        //nBMPs = nbmps;
        //idxBMP = 1;
        root.x = l;
        root.y = t;
        curentPoint.x = 0;
        curentPoint.y = 0;
        //radiant = r;
        destPoint = dPoint;
        _alpha = alpha;
        x = 400;
        y = root.y;
        //destPoint = calculateCoordinate(root, nBMPs - 1);
    }
    int dx = 1;
    int d2x = 1;
//For normal button----------------------------
    public void Update()
    {
        //idxBMP = (idxBMP +1 ) % nBMPs;

    }
    public void Update1()
    {
        calculatePrameters(destPoint, _alpha, false);
    }
    public void Update2()
    {
        calculatePrameters(destPoint, _alpha, true);
    }
    public void Draw(Canvas canvas)
    {
//        canvas.drawBitmap(BMP[idxBMP], left, top, null);
        int width = BMP.get(0).getWidth();
        int height = BMP.get(0).getHeight();
        Rect sourceRect = new Rect(0, 0,width - 1, height - 1);
        Rect destRect = new Rect(root.x, root.y, root.x + 300, root.y + 300);
        canvas.drawBitmap(BMP.get(0), sourceRect, destRect, null);
    }
    public void Draw1(Canvas canvas)
    {
        int width = BMP.get(0).getWidth();
        int height = BMP.get(0).getHeight();
        Rect sourceRect = new Rect(0, 0,width - 1, height - 1);
        Rect destRect = new Rect(x, y, x + 300, y + 300);
        canvas.drawBitmap(BMP.get(0), sourceRect, destRect, null);
    }
    //--------------------------------------------------------------------------------------
    //For all child image - expand them
    public void Expand(Canvas canvas)
    {
       // calculateCoordinate(root, BMP.size() - 1);
    }

    //Calculate Parameters for 1 point
    public void calculatePrameters(Point a, double alpha, boolean isRestore)
    {
        double horizontal;
        float timeConstant = 0.5f;
        if (alpha < 90 || alpha > 90) horizontal = root.Distance(a) * Math.cos(Math.toRadians(45));
       //else if (alpha > 90) horizontal = root.Distance(a) * Math.cos((Math.toRadians(45)));
        else horizontal = y - destPoint.y;
        float speed = (float)horizontal / timeConstant;
        Point v = new Point();

        //if (alpha <= 90)
        //{
            v.x = -(a.y - root.y);
            v.y = a.x - root.x;
        //}
      /* else
        {
            v.x = -(root.y - a.y);
            v.y =   a.x - root.x;
        }

      */
        if (isRestore == false) moveButton(speed, v, alpha);
        else moveButton1(speed, v, alpha);
    }
    int x;
    int y;
    //Calculate current pos for draw
    public void moveButton(float speed, Point vertex, double alpha)
    {
            //Point updatePoint = new Point();
            if (alpha < 90) x += 300 * 0.1;
            else if (alpha > 90) x -= 300 * 0.1;

            //calculate y
           if (alpha < 90 || alpha > 90) y = (vertex.x * (x - root.x) / (-vertex.y)) + root.y;
           else y -= 300 * 0.1;
            //Draw1(canvas, x, y);
          /*  flag++;
            if (flag % 500 == 0)
            {
                //idxBMP = (idxBMP + 1) % nBMPs;
                //idxBMP++;
                x = root.x;
                y = 0;
            }*/
            //return updatePoint;
            //draw
    }
    public void moveButton1(float speed, Point vertex, double alpha)
    {
        if (alpha < 90) x -= 300 * 0.1;
        else if (alpha > 90) x += 300 * 0.1;

        //calculate y
        if (alpha < 90 || alpha > 90) y = (vertex.x * (x - root.x) / (-vertex.y)) + root.y;
        else y += 300 * 0.1;
    }
}