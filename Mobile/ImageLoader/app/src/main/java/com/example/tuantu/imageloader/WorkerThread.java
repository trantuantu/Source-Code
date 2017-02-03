package com.example.tuantu.imageloader;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
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
    private Context context;//For check internet connection, you can remove it and check connect outside the thread
    private String command;
    private String link;
    private int position;
    public long fileLength;
    public long total;
    public String type;
    public boolean isTerminate;
    ImageView imageView;
    public long time;
    Handler handler = new Handler();
    File file;
    boolean downloadOnly;
    String outPath;
    //public boolean isCompleted

    public WorkerThread(int position, Context c, String s, String link, ImageView imageView, String type, boolean downloadOnly, String outPath){
        context = c;
        this.command=s;
        this.link = link;
        this.position = position;
        fileLength = 0;
        this.type = type;
        isTerminate = false;
        this.imageView = imageView;
        this.downloadOnly = downloadOnly;
        this.outPath = outPath;
    }
    @Override
    public void run() {
        //Your code here. Define a task for a thread. (Divide file to 10 parts if number tasks is 10
        System.out.println(Thread.currentThread().getName() + " Start. Command = " + command);
        file = new File(outPath + String.valueOf(position) + ".jpg");
            if (file.exists()) {
                if (downloadOnly == false) {
                    try {
                        handler.post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
                            @Override
                            public void run() {
                                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                                imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions));
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else
            {
                processDownload(outPath + String.valueOf(position) + ".jpg", link);
                if (downloadOnly == false) {
                    try {
                        handler.post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
                            @Override
                            public void run() {
                                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                                imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions));
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            Intent intent = new Intent("custom-event-name");
            intent.putExtra("message", "completed");
            intent.putExtra("type", type);
            intent.putExtra("id", String.valueOf(position));
            intent.putExtra("pos", String.valueOf(-1));
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


        //imageView.setImageBitmap();
    }

    private void processDownload(String fileName, String link) {
        InputStream in = null;
        OutputStream fos = null;
        OutputStream bout = null;
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            File file = new File(fileName);
            if (file.exists()) {
                total = (int) file.length();
                connection.setRequestProperty("Range", "bytes=" + (total) + "-");
                connection.connect();
            } else {
                total = 0;
                connection.connect();
            }

            fileLength = connection.getContentLength();
            in = new BufferedInputStream(connection.getInputStream());
            fos = (total == 0) ? new FileOutputStream(fileName) : new FileOutputStream(fileName, true);
            bout = new BufferedOutputStream(fos, 1024);
            byte[] data = new byte[1024];
            int x = 0;

                while ((x = in.read(data, 0, 1024)) >= 0) {
                    bout.write(data, 0, x);
                    total += x;
                    if (isTerminate == true)
                    {
                        bout.close();
                        in.close();
                        fos.close();
                        connection.disconnect();
                        //isTerminate = false;
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

 /*   public static final class DownloadDb {
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

        *//* Inner class that defines the table contents *//*
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
                    *//*Event event = new Event();
                    event._time = cursor.getString(cursor.getColumnIndex(DownloadDb.DownloadDes.HOUR)) + " : " +  cursor.getString(cursor.getColumnIndex(DownloadDb.DownloadDes.MINUTE));
                    if (cursor.getString(cursor.getColumnIndex(DownloadDb.DownloadDes.ISON)).equals("true"))
                        event.isOn = true;
                    else event.isOn = false;
                    events.add(event);*//*
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
    }*/
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
            }
        }
        return false;
    }
}