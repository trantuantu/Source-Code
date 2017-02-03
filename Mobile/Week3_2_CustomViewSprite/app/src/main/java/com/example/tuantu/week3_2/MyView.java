package com.example.tuantu.week3_2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by MinhTrietTRAN on 3/15/2016.
 */
public class MyView extends View {
    ArrayList<MySprite> sprites;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context) {
        super(context);
        InitMyView();
    }


    public void InitMyView()
    {
        sprites = new ArrayList<MySprite>();
//        sprites.add(CreateBackground());
        sprites.add(CreateAngel(100, 100));
        sprites.add(CreateAngel(200, 400));
//        sprites.add(CreateAngel(100, 100));
    }

    private MySprite CreateAngel(int left, int top) {
        Bitmap[] BMP = new Bitmap[]
                {
                        BitmapFactory.decodeResource(getResources(), R.drawable.angel01),
                        BitmapFactory.decodeResource(getResources(), R.drawable.angel02),
                        BitmapFactory.decodeResource(getResources(), R.drawable.angel03),
                        BitmapFactory.decodeResource(getResources(), R.drawable.angel04),
                        BitmapFactory.decodeResource(getResources(), R.drawable.angel05),
                        BitmapFactory.decodeResource(getResources(), R.drawable.angel06),
                        BitmapFactory.decodeResource(getResources(), R.drawable.angel07),
                        BitmapFactory.decodeResource(getResources(), R.drawable.angel08),
                        BitmapFactory.decodeResource(getResources(), R.drawable.angel09),
                        BitmapFactory.decodeResource(getResources(), R.drawable.angel10),
                        BitmapFactory.decodeResource(getResources(), R.drawable.angel11),
                        BitmapFactory.decodeResource(getResources(), R.drawable.angel12),
                        BitmapFactory.decodeResource(getResources(), R.drawable.angel13),
                        BitmapFactory.decodeResource(getResources(), R.drawable.angel14),
                        BitmapFactory.decodeResource(getResources(), R.drawable.angel15)
                };
        MySprite sprite = new MySprite(BMP, 15, left, top, BMP[0].getWidth(), BMP[0].getHeight());
        return sprite;
    }

    private MySprite CreateBackground() {
        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
      //  canvas.drawRGB(10, 20, 30);

        for (int i=0; i<sprites.size(); i++)
            sprites.get(i).Draw(canvas);
//        super.onDraw(canvas);
    }

    CountDownTimer countDownTimer = new CountDownTimer(20000, 40) {
        @Override
        public void onTick(long millisUntilFinished) {
            update();
        }

        @Override
        public void onFinish() {

        }
    };

    private void update() {
        for (int i=0; i<sprites.size(); i++)
            sprites.get(i).Update();

        invalidate();
    }
    public void Start()
    {
        countDownTimer.start();
    }
}