package  com.example.tuantu.week3_2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by MinhTrietTRAN on 3/15/2016.
 */
public class MySprite {
    public Bitmap[]   BMP;
    public int idxBMP;
    public int nBMPs;
    public int left;
    public int top;
    public int width;
    public int height;

    public MySprite(Bitmap[] bmp, int nbmps, int l, int t, int w, int h)
    {
        BMP = bmp;
        nBMPs = nbmps;
        idxBMP =0;
        left = l;
        top= t;
        width = w;
        height = h;
    }

    int dx = 1;
    int d2x = 1;

    public void Update()
    {
        idxBMP = (idxBMP +1 ) % nBMPs;
        if (Math.abs(dx)==10)
            d2x*=-1;

        dx += d2x;

    }

    public void Draw(Canvas canvas)
    {
//        canvas.drawBitmap(BMP[idxBMP], left, top, null);
        Rect sourceRect = new Rect(0, 0, width-1, height-1);
        Rect destRect = new Rect(left-dx, top-dx, left+width-1+2*dx,
                top+height-1 +2*dx);
        canvas.drawBitmap(BMP[idxBMP],sourceRect, destRect, null);
    }

    public boolean IsSelected(int x, int y)
    {
        if (x>=left && x<left+width &&
                y>=top && y<top+height)
            return true;
        return false;

    }


}
