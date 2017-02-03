package com.example.tuantu.testthreadpool;
import android.accounts.OnAccountsUpdateListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    private Handler handler;
    private String link, fileName;
    private long fileLength;
    private ThreadPoolExecutor executorPool;
    private LinearLayout progressLayout;
    private int numthreads = 1;
    private BroadcastReceiver mMessageReceiver;
    private TextView txtSpeed;
    //private Spinner s;
    private long avrSpeed = 0;
    private int[] speeds = new int[8];
    private ArrayList<WorkerThread> threads = new ArrayList<WorkerThread>();
    private int threadCompleted;
    private long maxAvrSpeed;
    private boolean calcCompleted;
    private View view;
    private TextView numT;
    private boolean isWaiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] arraySpinner = new String[] {
                "1", "2", "3", "4", "5", "6", "7", "8"
        };
        threadCompleted = 0;
        initSpeeds();
        maxAvrSpeed = 0;
        txtSpeed = (TextView)findViewById(R.id.speed);
        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra("message");
                if (message.equals("completed")) threadCompleted++;
                if ((threadCompleted == threads.size() + 1) && calcCompleted == true)
                {
                    try {
                        for (int i = 0; i < threads.size(); i ++) {
                            if (threads.size() > 1) {
                                File file = new File((String) Environment.getExternalStorageDirectory().toString() + "/" + fileName + i);
                                byte[] fileData;
                                DataInputStream dis = new DataInputStream(new FileInputStream(file));

                                if (i < threads.size() - 1)
                                {
                                  fileData = new byte[(int) fileLength / 8];
                                    dis.read(fileData, 0, (int)fileLength / 8);
                                }
                                else
                                {
                                  fileData = new byte[(int) (file.length())];
                                    dis.read(fileData);
                                }
                                dis.close();
                                //OutputStream fos = new FileOutputStream((String) Environment.getExternalStorageDirectory().toString() + "/" + fileName + (0), true);
                                OutputStream fos1 = new FileOutputStream((String) Environment.getExternalStorageDirectory().toString() + "/" + "FinalResult.mp4", true);
                                fos1.write(fileData);
                                fos1.close();
                            }
                        }
                    }catch (Exception e)
                    {

                    }
                }
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("custom-event-name"));
        //s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        // s.setAdapter(adapter);
        Button download = (Button)findViewById(R.id.download);
        progressLayout = (LinearLayout)findViewById(R.id.progress);

        numT = (TextView)findViewById(R.id.numthreads);
        numT.setText("0");

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threads.clear();
                threadCompleted = 0;
                calcCompleted = false;
                //numthreads = Integer.parseInt(s.getSelectedItem().toString());
                //progressLayout.removeAllViews();
              /*  for(int i=0; i < numthreads; i++){
                    WorkerThread t = new WorkerThread(v, numthreads, getApplicationContext(), "cmd"+i, fileLength, i, fileName, link);
                    executorPool.execute(t);
                    threads.add(t);
                }*/
                //Execute first thread
                WorkerThread t = new WorkerThread(v, 1, getApplicationContext(), "cmd" + 0, fileLength, 0, 0, fileName, link);
                executorPool.execute(t);
                threads.add(t);
                view = v;
                numT.setText(String.valueOf(threads.size()));
            }
        });

        try {
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
            executorPool = new ThreadPoolExecutor(8, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2), threadFactory, rejectionHandler);
            //start the monitoring thread

            MyMonitorThread monitor = new MyMonitorThread(executorPool, 3);
            Thread monitorThread = new Thread(monitor);
            monitorThread.start();
            link = "http://channelz2.mp3.zdn.vn/zv/08df5f5a4564d3221a7290655dd82447/5780bd10/2016/05/19/d/a/da17464383458f156c9785099495c953.mp4";
            fileName = "testThreadPool";

            handler = new Handler();
            try {
                Runnable r = new Runnable() {
                    public void run() {
                        avrSpeed = 0;
                            isWaiting = false;
                        for (int i = 0; i < threads.size(); i++) {
                            avrSpeed += threads.get(i).speed;
                            if (threads.get(i).isStart == false) isWaiting = true;

                        }
            //Calculating threads number
            if (avrSpeed >= maxAvrSpeed && calcCompleted == false && isWaiting == false) {
                if (threads.size() + 1 <= 8) {
                    try {
                        maxAvrSpeed = avrSpeed;
                        //long divide = fileLength / 2 / 7;
                        WorkerThread t = new WorkerThread(view, 1, getApplicationContext(), "cmd" + threads.size(), fileLength, threads.get(threads.size() - 1).startByte + fileLength / 8, threads.size(), fileName, link);
                        executorPool.execute(t);
                        threads.add(t);
                        numthreads = threads.size();
                        if (threads.size() == 8) calcCompleted = true;
                        //String test = "";
                        // numT.setText(threads.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if ((threads.size() > 1 && calcCompleted == false && isWaiting == false)) {
                //Finish calculate threads
                //If we have only 1 thread, we will not terminate it
                calcCompleted = true;
                //Terminate final thread
                threads.get(threads.size() - 1).stop = true;
                threads.remove(threads.size() - 1);
                numthreads = threads.size();

                for (int i = 0; i < threads.size(); i++) {
                    threads.get(i).numThreads = threads.size();
                }

                //numT.setText(String.valueOf(String.valueOf(threads.size())));
            }

                        handler.post(new Runnable() {
                            public void run() {
                                txtSpeed.setText(String.valueOf(avrSpeed) + " KB/s");
                                numT.setText(String.valueOf(String.valueOf(numthreads)));
                            }
                        });
                        handler.postDelayed(this, 1000);
                    }
                };
                r.run();
                handler.postDelayed(r, 1000);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(link);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.connect();
                        fileLength = connection.getContentLength();
                        connection.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    private void initSpeeds()
    {
        avrSpeed = 0;
        for (int i = 0; i < 8; i++)
        {
            speeds[i] = 0;
        }
    }
}