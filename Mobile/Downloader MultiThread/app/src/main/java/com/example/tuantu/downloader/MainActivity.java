package com.example.tuantu.downloader;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private ArrayList<DownloadItem> downloadItems;
    private ThreadPoolExecutor executorPool;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //--------------------------------
        //RejectedExecutionHandler implementation
        RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
        //Get the ThreadFactory implementation to use
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //creating the ThreadPoolExecutor
        //2: minimum 2 threads will run (if queue full (contain tasks for a thread), new threads will create to process, and maximum is 10 threads)
        //10: maximum 10 threads will run
        //10: Alive time for excess threads. These thread will be terminated after 10s not actived.
        //Size of Task Queue for a thread, see more in Java Official web (it will be add if number of current thread greater than 2)
        //ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2), threadFactory, rejectionHandler);
        executorPool = new ThreadPoolExecutor(16, 20, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(16), threadFactory, rejectionHandler);
        //start the monitoring thread

        MyMonitorThread monitor = new MyMonitorThread(executorPool, 3);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();
        //link = "http://stream.raphay.com/lofi_mp3/vietnam/rap/Jay_Tee/The_Mini_Album_2010/Imma_Heartbreaker_%28Ft._Emily,_Lil_Knight%29_-_Justatee.mp3";
        //fileName = "testThreadPool";

        //handler = new Handler();
        list = (ListView) findViewById(R.id.listDownload);
        initListDownload();
        DownloadAdapter adapter = new DownloadAdapter(executorPool, getApplicationContext(), R.layout.item_download, downloadItems);
        list.setAdapter(adapter);

        list.setAdapter(adapter);
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                Intent intent = new Intent("stop-draw");
                intent.putExtra("message", "stop");
                intent.putExtra("id", String.valueOf(0));
                intent.putExtra("startPos", String.valueOf(view.getFirstVisiblePosition()));
                intent.putExtra("endPos", String.valueOf(view.getLastVisiblePosition()));
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                  /*  Intent intent = new Intent("stop-draw");
                    intent.putExtra("message", "stop");
                    intent.putExtra("id", String.valueOf(0));
                    intent.putExtra("startPos", String.valueOf(view.getFirstVisiblePosition()));
                    intent.putExtra("endPos", String.valueOf(view.getLastVisiblePosition()));
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);*/
                }
            }
        });
    }

    private void initListDownload() {
        downloadItems = new ArrayList<DownloadItem>();
        downloadItems.add(new DownloadItem("a", "Trọng Hiếu", "Single", R.drawable.logo, "http://zmp3-mp3-mv1.r.za.zdn.vn/5d1a09473702de5c8713/8317374331221575221?key=j59AA7cwol5ZK29pub1-Gw&expires=1489685682"));
        downloadItems.add(new DownloadItem("b", "Hariwon", "Single", R.drawable.logo1, "http://zmp3-mp3-mv1.r.za.zdn.vn/5d1a09473702de5c8713/8317374331221575221?key=j59AA7cwol5ZK29pub1-Gw&expires=1489685682"));
        downloadItems.add(new DownloadItem("c", "ERIK ST.319", "Single", R.drawable.logo2, "http://dl.channelz2.mp3.zdn.vn/zv/3bc9a815c574f5b3abff9154d39a0e1d/579831c0/2016/07/20/7/c/7c9f082246b5cb78398691bd794663b2.mp4?filename=Trai%20Tim%20Con%20Trinh%20-%20Vy%20Oanh%20-%20360p.mp4"));
        downloadItems.add(new DownloadItem("d", "Trọng Hiếu", "Single", R.drawable.logo, "http://dl.channelz2.mp3.zdn.vn/zv/3bc9a815c574f5b3abff9154d39a0e1d/579831c0/2016/07/20/7/c/7c9f082246b5cb78398691bd794663b2.mp4?filename=Trai%20Tim%20Con%20Trinh%20-%20Vy%20Oanh%20-%20360p.mp4"));
        downloadItems.add(new DownloadItem("e", "Hariwon", "Single", R.drawable.logo1, "http://dl.channelz2.mp3.zdn.vn/zv/3bc9a815c574f5b3abff9154d39a0e1d/579831c0/2016/07/20/7/c/7c9f082246b5cb78398691bd794663b2.mp4?filename=Trai%20Tim%20Con%20Trinh%20-%20Vy%20Oanh%20-%20360p.mp4"));
        downloadItems.add(new DownloadItem("f", "ERIK ST.319", "Single", R.drawable.logo2, "http://dl.channelz2.mp3.zdn.vn/zv/3bc9a815c574f5b3abff9154d39a0e1d/579831c0/2016/07/20/7/c/7c9f082246b5cb78398691bd794663b2.mp4?filename=Trai%20Tim%20Con%20Trinh%20-%20Vy%20Oanh%20-%20360p.mp4"));
        downloadItems.add(new DownloadItem("g", "Trọng Hiếu", "Single", R.drawable.logo, "http://dl.channelz2.mp3.zdn.vn/zv/3bc9a815c574f5b3abff9154d39a0e1d/579831c0/2016/07/20/7/c/7c9f082246b5cb78398691bd794663b2.mp4?filename=Trai%20Tim%20Con%20Trinh%20-%20Vy%20Oanh%20-%20360p.mp4"));
        downloadItems.add(new DownloadItem("h", "Hariwon", "Single", R.drawable.logo1, "http://dl.channelz2.mp3.zdn.vn/zv/3bc9a815c574f5b3abff9154d39a0e1d/579831c0/2016/07/20/7/c/7c9f082246b5cb78398691bd794663b2.mp4?filename=Trai%20Tim%20Con%20Trinh%20-%20Vy%20Oanh%20-%20360p.mp4"));
        downloadItems.add(new DownloadItem("i", "ERIK ST.319", "Single", R.drawable.logo2, "http://dl.channelz2.mp3.zdn.vn/zv/3bc9a815c574f5b3abff9154d39a0e1d/579831c0/2016/07/20/7/c/7c9f082246b5cb78398691bd794663b2.mp4?filename=Trai%20Tim%20Con%20Trinh%20-%20Vy%20Oanh%20-%20360p.mp4"));
        downloadItems.add(new DownloadItem("k", "Trọng Hiếu", "Single", R.drawable.logo, "http://dl.channelz2.mp3.zdn.vn/zv/3bc9a815c574f5b3abff9154d39a0e1d/579831c0/2016/07/20/7/c/7c9f082246b5cb78398691bd794663b2.mp4?filename=Trai%20Tim%20Con%20Trinh%20-%20Vy%20Oanh%20-%20360p.mp4"));
        downloadItems.add(new DownloadItem("l", "Hariwon", "Single", R.drawable.logo1, "http://dl.channelz2.mp3.zdn.vn/zv/3bc9a815c574f5b3abff9154d39a0e1d/579831c0/2016/07/20/7/c/7c9f082246b5cb78398691bd794663b2.mp4?filename=Trai%20Tim%20Con%20Trinh%20-%20Vy%20Oanh%20-%20360p.mp4"));
        downloadItems.add(new DownloadItem("m", "ERIK ST.319", "Single", R.drawable.logo2, "http://dl.channelz2.mp3.zdn.vn/zv/3bc9a815c574f5b3abff9154d39a0e1d/579831c0/2016/07/20/7/c/7c9f082246b5cb78398691bd794663b2.mp4?filename=Trai%20Tim%20Con%20Trinh%20-%20Vy%20Oanh%20-%20360p.mp4"));
        downloadItems.add(new DownloadItem("n", "Trọng Hiếu", "Single", R.drawable.logo, "http://dl.channelz2.mp3.zdn.vn/zv/3bc9a815c574f5b3abff9154d39a0e1d/579831c0/2016/07/20/7/c/7c9f082246b5cb78398691bd794663b2.mp4?filename=Trai%20Tim%20Con%20Trinh%20-%20Vy%20Oanh%20-%20360p.mp4"));
        downloadItems.add(new DownloadItem("r", "Hariwon", "Single", R.drawable.logo1, "http://dl.channelz2.mp3.zdn.vn/zv/3bc9a815c574f5b3abff9154d39a0e1d/579831c0/2016/07/20/7/c/7c9f082246b5cb78398691bd794663b2.mp4?filename=Trai%20Tim%20Con%20Trinh%20-%20Vy%20Oanh%20-%20360p.mp4"));
        downloadItems.add(new DownloadItem("p", "ERIK ST.319", "Single", R.drawable.logo2, "http://dl.channelz2.mp3.zdn.vn/zv/3bc9a815c574f5b3abff9154d39a0e1d/579831c0/2016/07/20/7/c/7c9f082246b5cb78398691bd794663b2.mp4?filename=Trai%20Tim%20Con%20Trinh%20-%20Vy%20Oanh%20-%20360p.mp4"));
        downloadItems.add(new DownloadItem("q", "Trọng Hiếu", "Single", R.drawable.logo, "http://dl.channelz2.mp3.zdn.vn/zv/3bc9a815c574f5b3abff9154d39a0e1d/579831c0/2016/07/20/7/c/7c9f082246b5cb78398691bd794663b2.mp4?filename=Trai%20Tim%20Con%20Trinh%20-%20Vy%20Oanh%20-%20360p.mp4"));
        downloadItems.add(new DownloadItem("s", "Hariwon", "Single", R.drawable.logo1, "http://dl.channelz2.mp3.zdn.vn/zv/3bc9a815c574f5b3abff9154d39a0e1d/579831c0/2016/07/20/7/c/7c9f082246b5cb78398691bd794663b2.mp4?filename=Trai%20Tim%20Con%20Trinh%20-%20Vy%20Oanh%20-%20360p.mp4"));
        downloadItems.add(new DownloadItem("t", "ERIK ST.319", "Single", R.drawable.logo2, "http://dl.channelz2.mp3.zdn.vn/zv/3bc9a815c574f5b3abff9154d39a0e1d/579831c0/2016/07/20/7/c/7c9f082246b5cb78398691bd794663b2.mp4?filename=Trai%20Tim%20Con%20Trinh%20-%20Vy%20Oanh%20-%20360p.mp4"));
        downloadItems.add(new DownloadItem("u", "Trọng Hiếu", "Single", R.drawable.logo, "http://dl.channelz2.mp3.zdn.vn/zv/3bc9a815c574f5b3abff9154d39a0e1d/579831c0/2016/07/20/7/c/7c9f082246b5cb78398691bd794663b2.mp4?filename=Trai%20Tim%20Con%20Trinh%20-%20Vy%20Oanh%20-%20360p.mp4"));
        downloadItems.add(new DownloadItem("v", "Hariwon", "Single", R.drawable.logo1, "http://dl.channelz2.mp3.zdn.vn/zv/3bc9a815c574f5b3abff9154d39a0e1d/579831c0/2016/07/20/7/c/7c9f082246b5cb78398691bd794663b2.mp4?filename=Trai%20Tim%20Con%20Trinh%20-%20Vy%20Oanh%20-%20360p.mp4"));
    }
}