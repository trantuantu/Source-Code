package com.example.tuantu.jnicarogame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by TUAN TU on 8/30/2016.
 */
public class ChessboardView extends ImageView {

    // These matrices will be used to move and zoom image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    public int[][] arr;
    Bitmap bmp1;
    Bitmap bmp2;
    Bitmap bmp1_1;
    Bitmap bmp2_2;
    public Context c;
    public boolean isWin;
    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    public int selection, px, py;
    float x, y;
    public ArrayList<Step> win1 = new ArrayList<Step>();
    public ArrayList<Step> win2 = new ArrayList<Step>();
    public int winner;
    boolean move, scale;
    int mode = NONE;
    float size;
    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    String savedItemClicked;
    private ScaleGestureDetector mScaleDetector;
    Paint backgroundPaint, borderPaint;
    float[] values = new float[9];
    private static final int MAX_CLICK_DURATION = 100;
    long startClickTime;
    int bout = 1;

    public ChessboardView(Context context) {
        super(context);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        borderPaint = new Paint();
        borderPaint.setARGB(255, 255, 128, 0);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4);

        backgroundPaint = new Paint();
        backgroundPaint.setARGB(255, 255, 255, 255);
        backgroundPaint.setStyle(Paint.Style.FILL);
        arr = new int[50][50];
        for (int i = 0; i < 50; i++)
            for (int j = 0; j < 50; j++)
                arr[i][j] = 0;
        bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.square_x);
        bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.square_o);
        bmp1_1 = BitmapFactory.decodeResource(getResources(), R.drawable.x_win);
        bmp2_2 = BitmapFactory.decodeResource(getResources(), R.drawable.o_win);
        isWin = false;
        winner = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //canvas.drawRect(0, 0, getWidth() / 2, getHeight() / 2, borderPaint);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        //if (this.getDrawable() != null) {
        size = getWidth() / 10;
            canvas.save();
            matrix.getValues(values);
        int count = 0;
        Bitmap bmp3 = null;
        Bitmap bmp4 = null;
            //if ((selection == 1 || scale == true || move == true) && selection != 2)
            {
                for (int i = 0; i < 50; i++)
                    for (int j = 0; j < 50; j++) {
                        canvas.drawRect(values[matrix.MTRANS_X] + j * values[matrix.MSCALE_X] * size, values[matrix.MTRANS_Y] + i * values[matrix.MSCALE_Y] * size, (values[matrix.MTRANS_X] + (j + 1) * values[matrix.MSCALE_X] * size), (values[matrix.MTRANS_Y] + (i + 1) * values[matrix.MSCALE_Y] * size), borderPaint);
                        if (arr[i][j] == 1 || arr[i][j] == 2)
                        {
                            count++;
                            RectF rect = new RectF();
                            rect.left = values[matrix.MTRANS_X] + j * values[matrix.MSCALE_X] * size;
                            rect.top =  values[matrix.MTRANS_Y] + i * values[matrix.MSCALE_Y] * size;
                            rect.right = (values[matrix.MTRANS_X] + (j + 1) * values[matrix.MSCALE_X] * size);
                            rect.bottom = (values[matrix.MTRANS_Y] + (i + 1) * values[matrix.MSCALE_Y] * size);
                            if (count == 1)
                            {
                                bmp3 = Bitmap.createScaledBitmap(bmp1, (int) (rect.right - rect.left), (int) (rect.bottom - rect.top), false);
                                bmp4 = Bitmap.createScaledBitmap(bmp2, (int) (rect.right - rect.left), (int) (rect.bottom - rect.top), false);
                            }
                            if (arr[i][j] == 1)
                            {
                                canvas.drawBitmap(bmp3, rect.left, rect.top, null);
                                //bout = 2;
                            }
                            else if (arr[i][j] == 2)
                            {
                                canvas.drawBitmap(bmp4, rect.left, rect.top, null);
                            }
                        }
                    }
            }
            if (selection == 2)
            {
                //Detect i, j
               /* RectF rect = new RectF();
                rect.left = ((int)(x / (values[matrix.MSCALE_X] * size)) * (values[matrix.MSCALE_X] * size));
                rect.top = ((int)(y / (values[matrix.MSCALE_Y] * size)) * (values[matrix.MSCALE_Y] * size));
                rect.right = rect.left + values[matrix.MSCALE_X] * size;
                rect.bottom = rect.top + values[matrix.MSCALE_Y] * size;*/

               /* int j = (int)(x / (values[matrix.MSCALE_X] * size));
                int i = (int)(y / (values[matrix.MSCALE_Y] * size));
                RectF rect = new RectF();
                rect.left = values[matrix.MTRANS_X] + j * values[matrix.MSCALE_X] * size;
                rect.top =  values[matrix.MTRANS_Y] + i * values[matrix.MSCALE_Y] * size;
                rect.right = (values[matrix.MTRANS_X] + (j + 1) * values[matrix.MSCALE_X] * size);
                rect.bottom = (values[matrix.MTRANS_Y] + (i + 1) * values[matrix.MSCALE_Y] * size);
                canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.square_x), (int)(rect.right - rect.left), (int)(rect.bottom - rect.top), false),rect.left, rect.top,null);*/
                arr[px][py] = 0;
                //Bitmap bmp5 = BitmapFactory.decodeResource(getResources(), R.drawable.square);
                RectF rect = new RectF();
                rect.left = values[matrix.MTRANS_X] + px * values[matrix.MSCALE_X] * size;
                rect.top =  values[matrix.MTRANS_Y] + py * values[matrix.MSCALE_Y] * size;
                rect.right = (values[matrix.MTRANS_X] + (px + 1) * values[matrix.MSCALE_X] * size);
                rect.bottom = (values[matrix.MTRANS_Y] + (py + 1) * values[matrix.MSCALE_Y] * size);
                canvas.drawRect(values[matrix.MTRANS_X] + py * values[matrix.MSCALE_X] * size, values[matrix.MTRANS_Y] + px * values[matrix.MSCALE_Y] * size, (values[matrix.MTRANS_X] + (py + 1) * values[matrix.MSCALE_X] * size), (values[matrix.MTRANS_Y] + (px + 1) * values[matrix.MSCALE_Y] * size), backgroundPaint);
                canvas.drawRect(values[matrix.MTRANS_X] + py * values[matrix.MSCALE_X] * size, values[matrix.MTRANS_Y] + px * values[matrix.MSCALE_Y] * size, (values[matrix.MTRANS_X] + (py + 1) * values[matrix.MSCALE_X] * size), (values[matrix.MTRANS_Y] + (px + 1) * values[matrix.MSCALE_Y] * size), borderPaint);
               // canvas.drawBitmap(bmp4, rect.left, rect.top, null);
            }
            if (selection == 3 || isWin == true)
            {
                int count1 = 0;
                if (winner == 1)
                {
                    for (int i = 0; i < win1.size(); i++)
                    {
                        count1++;
                        int ax = win1.get(i).x;
                        int ay = win1.get(i).y;
                        RectF rect = new RectF();
                        rect.left = values[matrix.MTRANS_X] + ay * values[matrix.MSCALE_X] * size;
                        rect.top =  values[matrix.MTRANS_Y] + ax * values[matrix.MSCALE_Y] * size;
                        rect.right = (values[matrix.MTRANS_X] + (ay + 1) * values[matrix.MSCALE_X] * size);
                        rect.bottom = (values[matrix.MTRANS_Y] + (ax + 1) * values[matrix.MSCALE_Y] * size);
                        if (count1 == 1)
                        {
                            bmp3 = Bitmap.createScaledBitmap(bmp1_1, (int) (rect.right - rect.left), (int) (rect.bottom - rect.top), false);
                            //bmp4 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.o_win), (int) (rect.right - rect.left), (int) (rect.bottom - rect.top), false);
                        }
                        canvas.drawBitmap(bmp3, rect.left, rect.top, null);
                    }
                }
                else if (winner == 2)
                {
                    for (int i = 0; i < win2.size(); i++)
                    {
                        count1++;
                        int ax = win2.get(i).x;
                        int ay = win2.get(i).y;
                        RectF rect = new RectF();
                        rect.left = values[matrix.MTRANS_X] + ay * values[matrix.MSCALE_X] * size;
                        rect.top =  values[matrix.MTRANS_Y] + ax * values[matrix.MSCALE_Y] * size;
                        rect.right = (values[matrix.MTRANS_X] + (ay + 1) * values[matrix.MSCALE_X] * size);
                        rect.bottom = (values[matrix.MTRANS_Y] + (ax + 1) * values[matrix.MSCALE_Y] * size);
                        if (count1 == 1)
                        {
                            bmp3 = Bitmap.createScaledBitmap(bmp2_2, (int) (rect.right - rect.left), (int) (rect.bottom - rect.top), false);
                            //bmp4 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.o_win), (int) (rect.right - rect.left), (int) (rect.bottom - rect.top), false);
                        }
                        canvas.drawBitmap(bmp3, rect.left, rect.top, null);
                    }
                }
            }
            if (move == true) move = false;
            if (scale == true) scale = false;

            //canvas.drawRect(x * values[matrix.MTRANS_X], values[matrix.MTRANS_Y],  (values[matrix.MTRANS_X] + values[matrix.MSCALE_X] * getWidth() / 2), (values[matrix.MTRANS_Y] + values[matrix.MSCALE_Y] * getHeight() / 2), borderPaint);
            //canvas.translate(mPosX, mPosY);

            //Matrix matrix = new Matrix();
            //matrix.postScale(mScaleFactor, mScaleFactor, pivotPointX,
            //        pivotPointY);
            // canvas.setMatrix(matrix);

            /*canvas.drawBitmap(
                    ((BitmapDrawable) this.getDrawable()).getBitmap(), matrix,
                    null);
*/
            // this.getDrawable().draw(canvas);
            canvas.restore();
       // }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        // TODO Auto-generated method stub
        mScaleDetector.onTouchEvent(event);
        //ImageView view = (ImageView) v;
        dumpEvent(event);

        // Handle touch events here...
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                startClickTime = Calendar.getInstance().getTimeInMillis();
                //Log.d(TAG, "mode=DRAG");
                mode = DRAG;
                //drawChess(2);
                break;
            case MotionEvent.ACTION_UP:
                long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                if(clickDuration < MAX_CLICK_DURATION && isWin == false) {
                    //click event has occurred
                    x = event.getX();
                    y = event.getY();
                    int j = (int)((x  - values[matrix.MTRANS_X]) / (values[matrix.MSCALE_X] * size));
                    int i = (int)((y - values[matrix.MTRANS_Y]) / (values[matrix.MSCALE_Y] * size));
                    if (i < 50 && j < 50 && i >= 0 && j >= 0)
                    {
                        if (bout == 1 && arr[i][j] == 0)
                        {
                            arr[i][j] = 1;
                            bout = 2;
                            Intent intent = new Intent("custom-event-name");
                            intent.putExtra("message", "click");
                            intent.putExtra("i", String.valueOf(i));
                            intent.putExtra("j", String.valueOf(j));
                            LocalBroadcastManager.getInstance(c).sendBroadcast(intent);
                        }else if (bout == 2 && arr[i][j] == 0)
                        {
                            arr[i][j] = 2;
                            bout = 1;
                            Intent intent = new Intent("custom-event-name");
                            intent.putExtra("message", "click");
                            intent.putExtra("i", String.valueOf(i));
                            intent.putExtra("j", String.valueOf(j));
                            LocalBroadcastManager.getInstance(c).sendBroadcast(intent);
                        }
                    }
                    selection = 1;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                //Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    //Log.d(TAG, "mode=ZOOM");
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                //Log.d(TAG, "mode=NONE");
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    // ...
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY()
                            - start.y);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    //Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                move = true;
                selection = 1;
                invalidate();
                break;
        }

        //view.setImageMatrix(matrix);
        return true;
    }

    private void dumpEvent(MotionEvent event) {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(
                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }
        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }
        sb.append("]");
        //Log.d(TAG, sb.toString());
    }

    /** Determine the space between the first two fingers */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }

    /** Calculate the mid point of the first two fingers */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    private class ScaleListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            //mScaleFactor *= detector.getScaleFactor();

            //pivotPointX = detector.getFocusX();
            //pivotPointY = detector.getFocusY();

            /*Log.d(LOG_TAG, "mScaleFactor " + mScaleFactor);
            Log.d(LOG_TAG, "pivotPointY " + pivotPointY + ", pivotPointX= "
                    + pivotPointX);*/
            //mScaleFactor = Math.max(0.05f, mScaleFactor);
            scale = true;
            invalidate();
            return true;
        }
    }

    public void drawChess(int code)
    {
        switch (code) {
            case 1:
                selection = 1;
                invalidate();
                break;
            case 2:
                selection = 2;
                invalidate();
                break;
            case 3:
                selection = 3;
                invalidate();
                break;
            case 4:
                break;
            default:
                break;
        }
    }
}