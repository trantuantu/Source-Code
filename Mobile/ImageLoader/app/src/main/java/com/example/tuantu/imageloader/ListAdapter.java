package com.example.tuantu.imageloader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by TUAN TU on 7/26/2016.
 */
public class ListAdapter extends ArrayAdapter<String> {
    private int resource;
    private ArrayList<String> _items = new ArrayList<String>();
    private ArrayList<String> points = new ArrayList<String>();
    private Context context;
    private ImageView[] _images;
    private TextView[] _points;
    private WorkerThread[] threads;
    private BroadcastReceiver mMessageReceiver;
    private boolean[] completed;
    private ImageLoader imgLoader;
    private Stack<ThreadItem> stack;
    private int startPos, endPost;
    private boolean empty = false;

    public ListAdapter(Context c, int resource, ArrayList<String> event, final ArrayList<String> points) {
        super(c, resource);

        this.context = c;
        this._items = event;
        this.points = points;
        threads = new WorkerThread[50];
        _images = new ImageView[50];
        _points = new TextView[50];
        completed = new boolean[50];
        stack = new Stack<ThreadItem>();
        stack.empty();
        startPos = 0;
        endPost = 0;
        this.resource = resource;
        //this.excutor = executorPool;
        imgLoader = ImageLoader.getInstance();
        imgLoader.setOutPath((String) Environment.getExternalStorageDirectory().toString() + "/");

        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra("message");
                int id = Integer.parseInt(intent.getStringExtra("id"));
                String type = intent.getStringExtra("type");
                //if (message.equals("completed") && type.equals("landscape")) {
                   /* if (((id >= startPos && id <= endPost) || id <= 4) && completed[id] == false) {
                        //Boolean b = imgLoader.loadImage(id, context, _items.get(id), "landscape", _images[id]);
                        //_images[id].setImageBitmap(bitmap);
                        //_images[id].invalidate();
                        //completed[id] = true;
                        //notifyDataSetChanged();
                    }*/
                    if (message.equals("stop") && Integer.parseInt(intent.getStringExtra("endPos")) != -1) {
                    empty = false;
                    startPos = Integer.parseInt(intent.getStringExtra("startPos"));
                    endPost = Integer.parseInt(intent.getStringExtra("endPos"));
                    //Interrupt all threads
                    /*//excutor.shutdown(); /*//****ToDO:shutDownNow, save file sizes to database first*****
                    RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    //excutor = new ThreadPoolExecutor(16, 20, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20), threadFactory, rejectionHandler);*/


                    //imgLoader.terminateAll();
                    for (int i = startPos; i <= endPost; i++) {
                      /* if (startPos - (endPost - startPos) + i >= 0 && startPos - (endPost - startPos) + i < startPos) {
                           //Truyen vao imageView de terminate
                            imgLoader.terminate(startPos - (endPost - startPos) + i);
                        }*/

                        if (_images[i] != null) {
                            imgLoader = ImageLoader.getInstance();
                                Boolean b = imgLoader.loadImage(context, _items.get(i), "landscape", _images[i], false);
                                if (b == true) {
                                    //_images[i].setImageBitmap(bmp);
                                    completed[i] = true;
                                }
                        }
                    }

                    for (int i = 0; i < stack.size(); i++) {
                        ThreadItem currentItem = stack.pop();
                        if (_images[currentItem.id] != null && !(currentItem.id >= startPos && currentItem.id <= endPost)) {
                            imgLoader = ImageLoader.getInstance();
                            if (completed[currentItem.id] == false) {
                                boolean b = imgLoader.loadImage(context, _items.get(currentItem.id), "landscape", _images[currentItem.id], true);
                                if (b == true) completed[currentItem.id] = true;
                            }
                        }
                    }
                } else if (message.equals("stop") && Integer.parseInt(intent.getStringExtra("endPos")) == -1 && empty == false) {
                    stack.empty();
                    empty = true;
                }
            }
        };
        LocalBroadcastManager.getInstance(c).registerReceiver(mMessageReceiver, new IntentFilter("custom-event-name"));
    }
    @Override
    public String getItem(int position) {
        return _items.get(position);
    }

    @Override
    public int getCount() {
        return _items.size();
    }
    public class ViewHolderItem{
        ImageView image;
        ImageView buyTicket;
        TextView point;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolderItem viewHolder = null;
        if (v == null) {
            try {
                v = LayoutInflater.from(context).inflate(resource, null, true);
                viewHolder = new ViewHolderItem();
                viewHolder.image = (ImageView)v.findViewById(R.id.image);
                viewHolder.buyTicket = (ImageView)v.findViewById(R.id.buyTicket);
                viewHolder.point = (TextView)v.findViewById(R.id.point);
                v.setTag(viewHolder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            viewHolder = (ViewHolderItem) v.getTag();
        }

        viewHolder.buyTicket.setImageResource(R.drawable.buy_ticket);
        if (_images[position] == null) {
            _images[position] = viewHolder.image;
            if (_points[position] == null)
                _points[position] = viewHolder.point;
        }
        try
        {
            if (threads[position] == null) {
                WorkerThread thread = new WorkerThread(position, context, "cmd" + position, _items.get(position), _images[position], "landscape", false, "");
                threads[position] = thread;
                //Push to stack
                if (stack.size() < 100 && completed[position] == false)
                {
                    ThreadItem item = new ThreadItem(threads[position], position);
                    stack.push(item);
                }
            }
            _points[position] = viewHolder.point;
            _images[position] = viewHolder.image;
            _points[position].setText(points.get(position));
            viewHolder.image.setImageResource(R.drawable.place_holder_h);
            if (endPost == startPos && position < 4)
            {
                Boolean b = imgLoader.loadImage(context, _items.get(position), "landscape", _images[position], false);
                if (b == true)
                {
                   // viewHolder.image.setImageBitmap(bmp);
                    completed[position] = true;
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Check your internet connection", Toast.LENGTH_LONG).show();
        }

        return v;
    }
    public void setEndPost(int endPost)
    {this.endPost = endPost;}
    public void setStartPos(int startPos)
    {
        this.startPos = startPos;
    }
}