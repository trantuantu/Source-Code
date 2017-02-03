package com.example.tuantu.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * Created by TUAN TU on 8/1/2016.
 */
public class ImageLoader {
    //private WorkerThread[] threads;
    private static ThreadPoolExecutor excutor;
    private static ImageLoader _mInstance = null;
    private String outPath;
    public static ImageLoader getInstance()
    {
        if (_mInstance == null)
        {
            _mInstance = new ImageLoader();
            return _mInstance;
        }
        return _mInstance;
    }

    private ImageLoader()
    {
        RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
        //Get the ThreadFactory implementation to use
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //creating the ThreadPoolExecutor
        //2: minimum 2 threads will run (if queue full (contain tasks for a thread), new threads will create to process, and maximum is 10 threads)
        //10: maximum 10 threads will run
        //10: Alive time for excess threads. These thread will be terminated after 10s not actived.
        //Size of Task Queue for a thread, see more in Java Official web (it will be add if number of current thread greater than 2)
        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2), threadFactory, rejectionHandler);
        executorPool = new ThreadPoolExecutor(16, 20, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20), threadFactory, rejectionHandler);
        //start the monitoring thread
        MyMonitorThread monitor = new MyMonitorThread(executorPool, 3);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();
        this.excutor = executorPool;
        //threads = new WorkerThread[50];
    }

    public boolean loadImage(Context c, String link, String type, ImageView image, boolean downloadOnly)
    {
        //TO DO: Save URL with ID
        Bitmap bitmap = null; //New line
        boolean isDownOnly = downloadOnly;
        File file;
        int id  = link.hashCode();

        WorkerThread thread = new WorkerThread(id, c, "s" + id, link, image, type, downloadOnly, outPath);
        thread.time = System.currentTimeMillis();

        //threads[id] = thread;
        file = new File(outPath + String.valueOf(id) + ".jpg");
          //excutor.execute(thread); //Remove this comment to resume
        //TODO: Check if valid bitmap (if bmp not have load completed), save file size in database and check to resume if need
        //TODO: Truyen vao imageView to setImage
        //TODO: Save URL with corresponding id, don't need to pass id to loadImage function
            if (file.exists()) {
                if (isDownOnly == false) {
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    //return BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
                    image.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions));
                }
                return true;
            }
            else excutor.execute(thread);
        return false;
    }

    /*public void terminateAll()
    {
        excutor.shutdown(); /*//****ToDO:shutDownNow, save file sizes to database first*****
        RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        excutor = new ThreadPoolExecutor(16, 20, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20), threadFactory, rejectionHandler);
    }

    public void terminate(int pos)
    {
        //if (threads[pos] != null) threads[pos].isTerminate = true;
    }*/
    public void setOutPath(String out)
    {
        outPath = out;
    }
}