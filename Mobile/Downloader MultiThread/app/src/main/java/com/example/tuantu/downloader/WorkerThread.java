package com.example.tuantu.downloader;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.provider.BaseColumns;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
/**
 * Created by TUAN TU on 7/1/2016.
 */
public class WorkerThread implements Runnable {
    private String command;
    public long fileLength;
    private String fileName;
    public String link;
    boolean pause, stop;
    private Context context;
    private Handler handler = new Handler();
    public ProgressBar prog;
    public long prevTotal;
    public long total = 0;
    int position;
    private final double NANOS_PER_SECOND = 1000000000.0;
    private long start;
    private double seconds = 0;
    private double temp;
    private long totalLast = 0;
    public long speed;
    public long startByte;
    public boolean isStart;
    public int numThreads = 0;
    public TextView progress;
    private LinearLayout parent1;
    int parentId;
    private int _id;
    public LinearLayout progLayout;
    MyView btnStop;
    MyView btnDownload;
    boolean netWorkError;
    public boolean isInterrupt;
    OutputStream fos1;
    DataInputStream dis;
//    private long startByte;
    public WorkerThread(int numThreads, int position, View v, Context c, String s, String fileName, String link, int id, int parent, long startByte, boolean isInterrupt){
        this.command=s;
        this.fileLength = fileLength;
        //this.id = id;
        this.fileName = fileName;
        this.link = link;
        context = c;
        parent1 = (LinearLayout)v.getParent().getParent().getParent();
        //prog = (ProgressBar) parent1.findViewById(R.id.progressBar);
        progress = (TextView)parent1.findViewById(R.id.progress);
        progLayout = (LinearLayout) parent1.findViewById(R.id.prog);
        btnStop = (MyView)parent1.findViewById(R.id.btnStop);
        btnDownload = (MyView)parent1.findViewById(R.id.btnDown);

        prog = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 20;
        prog.setLayoutParams(params);

        progLayout.addView(prog);
        this.position = position;
        this._id = id;
        this.numThreads = numThreads;
        this.startByte = startByte;
        this.parentId = parent;
        pause = false;
        stop = false;
        netWorkError = false;
        this.isInterrupt = isInterrupt;
        prevTotal = 0;
    }

    @Override
    public void run() {
        //Your code here. Define a task for a thread. (Divide file to 10 parts if number tasks is 10
        System.out.println(Thread.currentThread().getName() + " Start. Command = " + command);
        if (hasActiveInternetConnection(context)) {
            processDownload(_id, fileName, link);
        }
        speed = 0;
        if (pause == false && isNetworkConnected() && hasActiveInternetConnection(context)) {
            //boolean b = isInternetAvailable();
            Intent intent = new Intent("custom-event-name");
            intent.putExtra("message", "completed");
            intent.putExtra("threadID", String.valueOf(_id));
            intent.putExtra("id", String.valueOf(parentId));
            intent.putExtra("fileLength", String.valueOf(fileLength));

            DownloadAdapter.threadCompleted[parentId]++;
            if (DownloadAdapter.threadCompleted[parentId] == numThreads + 1 || DownloadAdapter.threadCompleted[parentId] == 8)
            {
                try
                {
                    //Delete old file
                    //OutputStream fos2 = new FileOutputStream((String) fileName +".mp4", true);
                    File testFile = new File((String) fileName + ".mp4");
                    if (testFile.exists())
                    {
                        //testFile.delete();
                        testFile.renameTo(new File((String)fileName + "c"));
                    }

                    for (int i = 0; i < numThreads; i ++) {
                    if (numThreads > 1) {
                        File file = new File((String) fileName + i);
                        byte[] fileData;
                        dis = new DataInputStream(new FileInputStream(file));

                        if (i < numThreads - 1)
                        {
                            fileData = new byte[(int) fileLength  / 8];
                            dis.read(fileData, 0, (int)fileLength / 8);
                        }
                        else
                        {
                          /*  fileData = new byte[(int) (fileLength - (numThreads - 1) * fileLength / 8)];
                            dis.read(fileData, 0, fileData.length);*/
                            int offset = (int)(file.length() - (fileLength - (numThreads - 1) * (int)(fileLength / 8)));
                            fileData = new byte[(int) (fileLength - (numThreads - 1) * (int)(fileLength / 8))];
                            byte[] temp = new byte[offset];
                            dis.read(temp);
                            dis.read(fileData);
                        }
                        dis.close();
                        //OutputStream fos = new FileOutputStream((String) Environment.getExternalStorageDirectory().toString() + "/" + fileName + (0), true);
                        fos1 = new FileOutputStream((String) fileName +".mp4", true);
                        fos1.write(fileData);
                        fos1.close();
                    }
                }
            }catch (Exception e)
            {

            }
        }
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            System.out.println(Thread.currentThread().getName() + " End.");
        }
        else if (!isNetworkConnected() || !hasActiveInternetConnection(context))
        {
            //Toast.makeText(context, "Check your internet connection", Toast.LENGTH_LONG).show();
            handler.post(new Runnable() {
                public void run() {
                    Toast.makeText(context, "Check your internet connection", Toast.LENGTH_LONG).show();
                    //btnDownload.setImageResource(R.drawable.download);
                    btnDownload.Draw(0);
                    Intent intent = new Intent("custom-event-name");
                    intent.putExtra("message", "network");
                    intent.putExtra("id", String.valueOf(parentId));
                    intent.putExtra("threadID", String.valueOf(_id));
                    intent.putExtra("fileLength", String.valueOf(fileLength));
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            });
        }
    }
    private void processDownload(int id, String fileName, String link) {
        InputStream in = null;
        OutputStream fos = null;
        OutputStream bout = null;
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //total = 0;
            File file = new File(fileName + id);
            if (file.exists()) {
                total = (int) file.length();
                fileLength = getData(context, position);

                if (id > 0 && isInterrupt == true) startByte += (long)(fileLength / 8);
                connection.setRequestProperty("Range", "bytes=" + (startByte + total) + "-");
                connection.connect();
            } else {
                total = 0;
                if (id == 0)
                {
                    connection.connect();
                    fileLength = connection.getContentLength();
                    insertToDb(position, String.valueOf(fileLength));
                }
               // if (id == 0)
                else
                {
                    fileLength = getData(context, position);
                    startByte = startByte + (long)(fileLength / 8);
                    //-------------------
                    connection.setRequestProperty("Range", "bytes=" + startByte + "-");
                    connection.connect();
                }
            }

            in = new BufferedInputStream(connection.getInputStream());
            fos = (total == 0) ? new FileOutputStream(fileName + id) : new FileOutputStream(fileName + id, true);
            bout = new BufferedOutputStream(fos, 1024);
            byte[] data = new byte[1024];
            int x = 0;

            while ((x = in.read(data, 0, 1024)) >= 0) {
                if ((total + x) >= (((int)fileLength / numThreads)) && (id < numThreads - 1) && numThreads > 1)
                {
                    x = x - (int)((total + x) - (((int)fileLength / numThreads)));
                    bout.write(data, 0, x);
                    prevTotal = total;
                    total += x;
                    handler.post(new Runnable() {
                        public void run() {
                            if (_id < numThreads - 1) prog.setMax((int)fileLength / numThreads);
                            else prog.setMax((int)(fileLength - (numThreads - 1) * (fileLength / numThreads)));
                            prog.setProgress((int)total);
                            btnDownload.updatePercent((int) (total * 100 / fileLength));
                            //Intent intent = new Intent("custom-event-name");
                            //intent.putExtra("threadID", _id);
                            Intent intent = new Intent("custom-event-name");
                            //intent.putExtra("newByte", String.valueOf((total - prevTotal) * 100 / fileLength));
                            intent.putExtra("message", "update");
                            intent.putExtra("threadID", String.valueOf(_id));
                            intent.putExtra("id", String.valueOf(parentId));
                            intent.putExtra("fileLength", String.valueOf(fileLength));
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
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
                prevTotal = total;
                total += x;

                if (fileLength > 0) // only if total length is known
                {
                    handler.post(new Runnable() {
                        public void run() {
                            if (_id < numThreads - 1) prog.setMax((int)fileLength / numThreads);
                            else prog.setMax((int)(fileLength - (numThreads - 1) * (fileLength / numThreads)));
                            prog.setProgress((int) total);

                            //btnDownload.updatePercent((int) ((total - prevTotal) * 100 / fileLength));

                            if ((int)(total * 100)/fileLength == 100)
                            {
                                if (_id < numThreads - 1) prog.setMax((int)fileLength / numThreads);
                                else prog.setMax((int)(fileLength - (numThreads - 1) * (fileLength / numThreads)));
                                prog.setProgress((int) total);
                                btnDownload.updatePercent((int) (total * 100 / fileLength));

                                Intent intent = new Intent("custom-event-name");
                                //intent.putExtra("newByte", String.valueOf(total);
                                intent.putExtra("message", "update");
                                intent.putExtra("threadID", String.valueOf(_id));
                                intent.putExtra("id", String.valueOf(parentId));
                                intent.putExtra("fileLength", String.valueOf(fileLength));
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                return;
                            }

                            temp = ((System.nanoTime() - start + 1) / NANOS_PER_SECOND);
                            if (temp > (seconds + 1))
                            {
                                isStart = true;
                                speed = (long)((total - totalLast) / 1024 / (temp - seconds));
                                totalLast = total;
                                seconds = temp;
                            }

                                Intent intent = new Intent("custom-event-name");
                                //intent.putExtra("newByte", String.valueOf((total - prevTotal) * 100 / fileLength));
                                intent.putExtra("message", "update");
                                intent.putExtra("threadID", String.valueOf(_id));
                                intent.putExtra("id", String.valueOf(parentId));
                                intent.putExtra("fileLength", String.valueOf(fileLength));
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        }
                    });
                }
                if (pause == true || stop == true) {
                    bout.close();
                    in.close();
                    fos.close();
                    connection.disconnect();
                    return;
                }

            }
            bout.close();
            in.close();
            fos.close();
            connection.disconnect();
        } catch (Exception e) {
            netWorkError = true;
            e.printStackTrace();
        }

        return;
    }

    private void processCommand() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return this.command;
    }

    public static final class DownloadDb {
        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DownloadDes.TABLE_NAME + " (" +
                        DownloadDes._ID + " INTEGER PRIMARY KEY," +
                        DownloadDes.FILE_LENGHT + TEXT_TYPE + ")";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + DownloadDes.TABLE_NAME;
        // To prevent someone from accidentally instantiating the contract class,
        // give it an empty constructor.

        public DownloadDb() {
        }

        /* Inner class that defines the table contents */
        public static abstract class DownloadDes implements BaseColumns {
            public static final String TABLE_NAME = "downloadItem";
            public static final String FILE_LENGHT = "fileLength";
        }

        public static class DownloadDbHelper extends SQLiteOpenHelper {
            // If you change the database schema, you must increment the database version.
            public static final int DATABASE_VERSION = 1;
            public static final String DATABASE_NAME = "Downloader.db";

            public DownloadDbHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

            public void onCreate(SQLiteDatabase db) {
                db.execSQL(SQL_CREATE_ENTRIES);
            }

            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // This database is only a cache for online data, so its upgrade policy is
                // to simply to discard the data and start over
                db.execSQL(SQL_DELETE_ENTRIES);
                onCreate(db);
            }

            public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                onUpgrade(db, oldVersion, newVersion);
            }
        }

    }

    public  long getData(Context context, int id) {
        //Get Data--------------------------------------------------
        DownloadDb.DownloadDbHelper mDbHelper = new DownloadDb.DownloadDbHelper(context);
        String[] projection = {
                DownloadDb.DownloadDes._ID,
                DownloadDb.DownloadDes.FILE_LENGHT
        };
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.query(
                DownloadDb.DownloadDes.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                // The sort order
        );

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!(cursor.getPosition() == cursor.getCount())) {
                    /*Event event = new Event();
                    event._time = cursor.getString(cursor.getColumnIndex(DownloadDb.DownloadDes.HOUR)) + " : " +  cursor.getString(cursor.getColumnIndex(DownloadDb.DownloadDes.MINUTE));
                    if (cursor.getString(cursor.getColumnIndex(DownloadDb.DownloadDes.ISON)).equals("true"))
                        event.isOn = true;
                    else event.isOn = false;
                    events.add(event);*/
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(DownloadDb.DownloadDes._ID))) == (id + 1)) return Long.parseLong(cursor.getString(cursor.getColumnIndex(DownloadDb.DownloadDes.FILE_LENGHT)));
                cursor.moveToNext();
            }
        }
        return -1;
    }
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
    public void insertToDb(int id, String fileLength) {
        DownloadDb.DownloadDbHelper DownloadDbHelperDb = new DownloadDb.DownloadDbHelper(context);
        SQLiteDatabase dlDb = DownloadDbHelperDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DownloadDb.DownloadDes._ID, String.valueOf(id + 1));
        values.put(DownloadDb.DownloadDes.FILE_LENGHT, fileLength);
        dlDb.insert(DownloadDb.DownloadDes.TABLE_NAME, "", values);
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
        //return false;
    }
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }

    }
    public boolean hasActiveInternetConnection(Context context) {
        if (isNetworkConnected()) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
               // Log.e(LOG_TAG, "Error checking internet connection", e);
            }
        } else {
            //Log.d(LOG_TAG, "No network available!");
        }
        return false;
    }
}