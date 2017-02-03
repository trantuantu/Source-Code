package com.example.tuantu.imageloader;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by TUAN TU on 8/9/2016.
 */
public class pagerAdapter extends PagerAdapter {
    private ArrayList<PagerItem> _items = new ArrayList<PagerItem>();
    private Context _context;
    private LayoutInflater inflater;
    private ImageView image;
    private ImageLoader _tempLoader;
    //private ThreadPoolExecutor excutor;
    private TextView en, vn, point;
    private BroadcastReceiver mMessageReceiver;
    private View currentObj;
    private int curentPos;
    boolean []completed;

    public pagerAdapter(Context context, ArrayList<PagerItem> items, TextView en, TextView vn, TextView point)
    {
        this._context = context;
        this._items = items;
        inflater = LayoutInflater.from(context);
        this.en = en;
        this.vn = vn;
        this.point = point;
        completed = new boolean[50];

        //RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
        //ThreadFactory threadFactory = Executors.defaultThreadFactory();
       // excutor = new ThreadPoolExecutor(16, 20, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20), threadFactory, rejectionHandler);
        _tempLoader = ImageLoader.getInstance();
        _tempLoader.setOutPath((String) Environment.getExternalStorageDirectory().toString() + "/p");

        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String type = intent.getStringExtra("type");
                String message = intent.getStringExtra("message");
                int id = Integer.parseInt(intent.getStringExtra("id"));
/*
                if (message.equals("completed") && type.equals("portrail"))
                {
                    if (id == curentPos && completed[id] == false)
                    {
                        ImageView currentImg = (ImageView)currentObj.findViewById(R.id.image);
                        //currentImg.setImageBitmap(_tempLoader.loadImage(id, context, _items.get(id).image, "portrail"));
                        //_tempLoader.loadImage(id, context, _items.get(id).image, "portrail", currentImg);
                        currentImg.invalidate();
                        completed[id] = true;
                    }
                   // notifyDataSetChanged();
                }*/
            }
        };

        LocalBroadcastManager.getInstance(_context).registerReceiver(mMessageReceiver, new IntentFilter("custom-event-name"));
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.pager_layout, view, false);
        assert imageLayout != null;
        image = (ImageView) imageLayout.findViewById(R.id.image);

        Bitmap bmp = null;
        _tempLoader.loadImage(_context, _items.get(position).image, "portrail", image, false);
        //if (bmp != null)
        //image.setImageBitmap(bmp);
        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        point.setText(_items.get(position).point);
        vn.setText(_items.get(position).name_vn);
        en.setText(_items.get(position).name_en);
        currentObj = (View)object;
        curentPos = position;

        image = (ImageView)currentObj.findViewById(R.id.image);

        Bitmap bmp = null;
        _tempLoader.loadImage(_context, _items.get(position).image, "portrail", image, false);
        if (bmp != null)
        {
            //image.setImageBitmap(bmp);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return _items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
    public void setViewEn(TextView en)
    {
        this.en = en;
    }
    public void setViewVi(TextView vi)
    {
        this.vn = vi;
    }
    public void setViewPoint(TextView point)
    {
        this.point = point;
    }
}