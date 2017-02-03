package com.example.tuantu.testthreadpool;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by TUAN TU on 7/1/2016.
 */
public class WorkerThread implements Runnable {
    private String command;
    private long fileLength;
    private int _id;
    private String fileName;
    private String link;
    public boolean pause, stop;
    private Context context;
    private Handler handler = new Handler();
   // private ProgressBar prog;
    private TextView speedRate;
    private long total = 0;
    public int numThreads = 0;
    private final double NANOS_PER_SECOND = 1000000000.0;
    private long start;
    private double seconds = 0;
    private double temp;
    private long totalLast = 0;
    public long speed;
    public long startByte;
    public boolean isStart;

    public WorkerThread(View v, int numthreads, Context c, String s, long fileLength, long startByte, int id, String fileName, String link){
        this.command=s;
        this.fileLength = fileLength;
        this._id = id;
        this.fileName = fileName;
        this.link = link;
        context = c;
        this.startByte = startByte;
        speed = 0;
        isStart = false;
        //LinearLayout parent = (LinearLayout)v.getParent().getParent();
        //LinearLayout progressLayout = (LinearLayout)parent.findViewById(R.id.progress);
        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //params.weight = 1;
        //prog = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        //prog.setLayoutParams(params);
        //progressLayout.addView(prog);

        this.numThreads = numthreads;
        //prog.setMax((int)fileLength / numThreads);
        //speedRate = (TextView)parent.findViewById(R.id.speed);
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " Start. Command = " + command);
            //speed = 0;
            processDownload(fileLength, _id, fileName, link);
            speed = 0;
            Intent intent = new Intent("custom-event-name");
            intent.putExtra("message", "completed");
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            System.out.println(Thread.currentThread().getName() + " End.");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void processDownload(final long fileLength, int id, String fileName, String link) {
        InputStream in = null;
        OutputStream fos = null;
        OutputStream bout = null;
        if (id == 6)
        {
            String test = "";
        }
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (id > 0) connection.setRequestProperty("Range", "bytes=" + (startByte) + "-");
            connection.connect();
            in = new BufferedInputStream(connection.getInputStream());
            fos = new FileOutputStream((String) Environment.getExternalStorageDirectory().toString() + "/" + fileName + id);
            bout = new BufferedOutputStream(fos, 1024);
            byte[] data = new byte[1024];
            int x = 0;
            start = System.nanoTime();

            while ((x = in.read(data, 0, 1024)) >= 0) {

                if ((total + x) >= (((int)fileLength / numThreads)) && (id < numThreads - 1) && numThreads > 1)
                {
                    x = x - (int)((total + x) - (((int)fileLength / numThreads)));
                    bout.write(data, 0, x);
                    total += x;
                    handler.post(new Runnable() {
                        public void run() {
                            //prog.setProgress((int)total);
                        }
                    });
                    bout.close();
                    in.close();
                    fos.close();
                    connection.disconnect();
                    System.out.println("Blah blah");
                    return;
                }
                bout.write(data, 0, x);
                total += x;

                if (fileLength > 0)
                {
                    handler.post(new Runnable() {
                        public void run() {
                           // prog.setProgress((int)total);

                            temp = ((System.nanoTime() - start + 1) / NANOS_PER_SECOND);
                            if (temp > (seconds + 1))
                            {
                                isStart = true;
                                speed = (long)((total - totalLast) / 1024 / (temp - seconds));
                                totalLast = total;
                                seconds = temp;
                            }
                        }
                    });
                }

                if (pause == true || stop == true) {
                    return;
                }
            }
            bout.close();
            in.close();
            fos.close();
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }
    @Override
    public String toString(){
        return this.command;
    }
}