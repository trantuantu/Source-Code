package com.example.tuantu.downloader;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.provider.BaseColumns;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by TUAN TU on 6/28/2016.
 */
public class DownloadAdapter extends ArrayAdapter<DownloadItem> {
    private ArrayList<DownloadItem> _event;
    private Context context;
    int resource;
    private int[] totalPercent;
    public static ThreadPoolExecutor executorPool;
    public static ArrayList<ArrayList<WorkerThread>> childThreads = new ArrayList<ArrayList<WorkerThread>>();
    private int[] childThreadNumber;
    public static int[] threadCompleted;
    private boolean[] calcCompleted;
    private boolean[] isWaiting;
    private long[] maxAvrSpeed;
    private long[] avrSpeed;
    private View[] view;
    private boolean[] status;
    private boolean[] isResume;
    private boolean[] isStop;
    private boolean[] isDraw;
    MyView[] bDownload;
    MyView[] bStop;
    private BroadcastReceiver mMessageReceiver;
    private BroadcastReceiver mMessageReceiver1;
    private Handler handler;
    int flag = 0;
    int mutex = 1;
    int startPos, endPos;

    public DownloadAdapter(final ThreadPoolExecutor executor, Context c, int resource, ArrayList<DownloadItem> event) {
        super(c, resource);
        _event = event;
        this.context = c;
        this.resource = resource;
        executorPool = executor;
        childThreadNumber = new int[20];
        threadCompleted = new int[20];
        calcCompleted = new boolean[20];
        totalPercent = new int[20];
        isWaiting = new boolean[20];
        maxAvrSpeed = new long[20];
        avrSpeed = new long[20];
        view = new View[20];
        status = new boolean[20];
        isResume = new boolean[20];
        isStop = new boolean[20];
        bDownload = new MyView[20];
        bStop = new MyView[20];
        isDraw = new boolean[20];
        startPos = 0;
        endPos = 0;

        for (int i = 0; i < 20; i++)
        {
            childThreadNumber[i] = 0;
            threadCompleted[i] = 0;
            totalPercent[i] = 0;
            calcCompleted[i] = false;
            //childThreadNumber[i] = 0;
            ArrayList<WorkerThread> temp = new ArrayList<WorkerThread>();
            childThreads.add(temp);
        }
        handler = new Handler();
        try {
            Runnable r = new Runnable() {
                public void run() {
                    //avrSpeed = 0;
                    //isWaiting = false;
                    for (int i = 0; i < 20; i++) {
                        avrSpeed[i] = 0;
                        isWaiting[i] = false;
                        for (int j = 0; j < childThreads.get(i).size(); j++) {
                            avrSpeed[i] += childThreads.get(i).get(j).speed;
                            if (childThreads.get(i).get(j).isStart == false)
                                isWaiting[i] = true;
                            //totalPercent[i] += childThreads.get(i).get(j).total;
                        }

                    }
                    //Calculating threads number
                    for (int i = 0; i < 20; i++) {
                        if (_event.get(i).isDownload == true) {
                            if (avrSpeed[i] >= maxAvrSpeed[i] && calcCompleted[i] == false && isWaiting[i] == false) {
                                if (childThreads.get(i).size() + 1 <= 8) {
                                    try {
                                        if (executorPool.getQueue().size() <= 15) {
                                            maxAvrSpeed[i] = avrSpeed[i];
                                            WorkerThread t = new WorkerThread(1, i, view[i], context, "cmd" + childThreads.get(i).size(), Environment.getExternalStorageDirectory().toString() + "/" + _event.get(i).fileName, _event.get(i).link, childThreads.get(i).size(), i, childThreads.get(i).get(childThreads.get(i).size() - 1).startByte, false);
                                            executorPool.execute(t);
                                            childThreads.get(i).add(t);
                                        }

                                        //if (k < childThreads.get(i).size() - 1) childThreads.get(i).get(k).prog.setMax((int)( fileLength / childThreads.get(i).size()));
                                        //else  childThreads.get(i).get(k).prog.setMax((int) (fileLength - (childThreads.get(i).size() - 1) * (fileLength / childThreads.get(i).size())));
                                        //childThreads.get(i).get(k).progLayout.addView(childThreads.get(i).get(k).prog);


                                        if (childThreads.get(i).size() == 8) {
                                            for (int k = 0; k < childThreads.get(i).size(); k++) {
                                                childThreads.get(i).get(k).numThreads = 8;
                                            }


                                            calcCompleted[i] = true;
                                            insertToDb(i, "8");
                                        }

                                        else if (executorPool.getQueue().size() == 16)
                                        {
                                            for (int k = 0; k < childThreads.get(i).size(); k++) {
                                                childThreads.get(i).get(k).numThreads =  childThreads.get(i).size();
                                            }
                                            threadCompleted[i] = 1;
                                            calcCompleted[i] = true;
                                            insertToDb(i, String.valueOf(childThreads.get(i).size()));
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else if ((childThreads.get(i).size() > 1 && calcCompleted[i] == false && isWaiting[i] == false)) {
                                //Finish calculate threads
                                //If we have only 1 thread, we will not terminate it
                                calcCompleted[i] = true;
                                //Terminate final thread
                                childThreads.get(i).get(childThreads.get(i).size() - 1).stop = true;
                                childThreads.get(i).get(childThreads.get(i).size() - 1).progLayout.removeView(childThreads.get(i).get(childThreads.get(i).size() - 1).prog);
                                childThreads.get(i).remove(childThreads.get(i).size() - 1);
                                //numthreads = threads.size();
                                for (int k = 0; k < childThreads.get(i).size(); k++) {
                                    childThreads.get(i).get(k).numThreads = childThreads.get(i).size();
                                }
                                insertToDb(i, String.valueOf(childThreads.get(i).size()));
                                //for (int k = 0; k < childThreads.get(i).size(); k++)
                                //{
                                    //long fileLength = WorkerThread.getData(context, i);
                                    //if (k < childThreads.get(i).size() - 1) childThreads.get(i).get(k).prog.setMax((int)( fileLength / childThreads.get(i).size()));
                                    //else  childThreads.get(i).get(k).prog.setMax((int) (fileLength - (childThreads.get(i).size() - 1) * (fileLength / childThreads.get(i).size())));
                                    //childThreads.get(i).get(k).progLayout.addView(childThreads.get(i).get(k).prog);
                                //}
                            } else if ((childThreads.get(i).size() == 1 && calcCompleted[i] == false)) {
                            	threadCompleted[i] = 1;//New line
                            	calcCompleted[i] = true;
                            	insertToDb(i, "1");
                            }

                        }

                    }
                    handler.postDelayed(this, 1000);
                }
            };
            r.run();
            handler.postDelayed(r, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMessageReceiver1 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Your code here
                String message = intent.getStringExtra("message");
                if (message.equals("stop")) {
                    startPos = Integer.parseInt(intent.getStringExtra("startPos"));
                    endPos = Integer.parseInt(intent.getStringExtra("endPos"));
                    for (int i = 0 ; i < 20; i++) {
                        if (i < startPos || i > endPos && !(startPos == 0 && endPos == 0))
                        {
                            isDraw[i] = false;
                            //bDownload[i].updatePercent(bDownload[i].percent);
                        }
                        else {
                            isDraw[i] = true;
                            //bDownload[i].updatePercent(0);
                        }
                    }
                }
            }
        };
        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String message = intent.getStringExtra("message");
                int id = Integer.parseInt(intent.getStringExtra("id"));
                int threadID = Integer.parseInt(intent.getStringExtra("threadID"));
                long fileLength = Long.parseLong(intent.getStringExtra("fileLength"));
                //if (message.equals("completed")) threadCompleted[id]++;


               // if (message.equals("update") && childThreads.)
                if (message.equals("update"))
               {
                    totalPercent[id] = 0;
                    for (int i = 0; i < childThreads.get(id).size(); i++)
                    {
                        if (childThreads.get(id).get(i).total >= 0) totalPercent[id] +=  childThreads.get(id).get(i).total;
                    }
                    if (childThreads.get(id).size() > 0 && isDraw[id] == true) bDownload[id].updatePercent((long)((long)totalPercent[id] * 100 / childThreads.get(id).get(0).fileLength));
                   else if (isDraw[id] == false)
                    {
                       // bDownload[id].updatePercent(0);
                    }
                }
                if (message.equals("network"))
                {
                    //TO DO:
                    //Kill app when calculate not complete
                    //Kill app when merge file not complete
                    //------------------------------------------
                    //------------------------------------------
                    _event.get(threadID).isDownload = false;
                    status[threadID] = false;
                    isResume[threadID] = true;
                    isStop[threadID] = false;
                }
                //Calculating total percent here
                //---------------------------------------------------------------------------------------
                long total = 0;
                for (int i = 0; i < childThreads.get(id).size(); i++)
                {
                   // total += childThreads.get(id).get(threadID).total;
                }
                //---------------------------------------------------------------------------------------
                if (((threadCompleted[id] == childThreads.get(id).size() + 1) || threadCompleted[id] == 8)  && calcCompleted[id] == true)
                {
                    mutex--;
                    //if (mutex)
                    //while
                    bDownload[id].Draw(0);
                    bStop[id].setVisibility(View.GONE);

                    isStop[id] = true;
                    isResume[id] = false;
                    status[id] = false;

                    Toast.makeText(context, "Download Completed", Toast.LENGTH_LONG).show();
                   /* try {
                        for (int i = 0; i < childThreads.get(id).size(); i ++) {
                            if (childThreads.get(id).size() > 1) {
                                File file = new File((String) Environment.getExternalStorageDirectory().toString() + "/" + _event.get(id).fileName + i);
                                byte[] fileData;
                                DataInputStream dis = new DataInputStream(new FileInputStream(file));

                                if (i < childThreads.get(id).size() - 1)
                                {
                                    fileData = new byte[(int) (fileLength  / 8)];
                                    dis.read(fileData, 0, (int)(fileLength / 8));
                                }
                                else
                                {
                                    //long test1 = file.length();
                                    //long test2 = (fileLength - childThreads.get(id).size() - 1) * (int)(fileLength / 8);
                                    //int offset = (int)(file.length() - (fileLength - (childThreads.get(id).size() - 1) * (int)(fileLength / 8)));
                                    fileData = new byte[(int) (file.length())];
                                    dis.read(fileData);
                                }
                                dis.close();
                                //OutputStream fos = new FileOutputStream((String) Environment.getExternalStorageDirectory().toString() + "/" + fileName + (0), true);
                                OutputStream fos1 = new FileOutputStream((String) Environment.getExternalStorageDirectory().toString() + "/" + _event.get(id).fileName +".mp4", true);
                                fos1.write(fileData);
                                fos1.close();
                            }
                        }
                    }catch (Exception e)
                    {

                    }*/
                }

            }
        };
        LocalBroadcastManager.getInstance(c).registerReceiver(mMessageReceiver, new IntentFilter("custom-event-name"));
        LocalBroadcastManager.getInstance(c).registerReceiver(mMessageReceiver1, new IntentFilter("stop-draw"));

    }

    public static class ViewHolderItem{
        ImageView logo;
        TextView fileName;
        TextView title1;
        TextView title2;
        MyView btnDownload;
        ProgressBar progressBar;
        TextView progress;
        MyView btnStop;
        LinearLayout progLayout;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View v = convertView;
        ViewHolderItem viewHolder = null;
        if (v == null) {
            try {
                v = LayoutInflater.from(context).inflate(resource, null);
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            viewHolder = new ViewHolderItem();
            viewHolder.logo = (ImageView)v.findViewById(R.id.imgLogo);
            viewHolder.fileName = (TextView) v.findViewById(R.id.txtFileName);
            viewHolder.title1 = (TextView) v.findViewById(R.id.txtTitle1);
            viewHolder.title2 = (TextView) v.findViewById(R.id.txtTitle2);
            viewHolder.btnDownload = (MyView) v.findViewById(R.id.btnDown);
            viewHolder.progLayout = (LinearLayout)v.findViewById(R.id.prog);
            viewHolder.progress = (TextView)v.findViewById(R.id.progress);
            viewHolder.btnStop = (MyView)v.findViewById(R.id.btnStop);
            viewHolder.btnStop.Draw(2);
            viewHolder.btnDownload.Draw(0);
            v.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }
        //viewHolder.progLayout = (LinearLayout)v.findViewById(R.id.prog);
        //viewHolder.btnDownload = (MyView) v.findViewById(R.id.btnDown);
        viewHolder.logo.setImageResource(_event.get(position).imageId);
        viewHolder.fileName.setText(_event.get(position).fileName);
        viewHolder.title1.setText(_event.get(position).title1);
        viewHolder.title2.setText(_event.get(position).title2);

        if (status[position] == true)
        {
            viewHolder.btnDownload.Draw(1);
            //viewHolder.btnDownload.updatePercent(viewHolder.btnDownload.percent);
            viewHolder.btnStop.setVisibility(View.VISIBLE);
            viewHolder.progLayout.setVisibility(View.VISIBLE);
            viewHolder.progLayout.removeAllViews();
            viewHolder.btnDownload.updatePercent(viewHolder.btnDownload.percent);

            for (int i = 0; i < childThreads.get(position).size(); i++)
            {
                try {
                    viewHolder.progLayout.addView(childThreads.get(position).get(i).prog);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        if (status[position] == false)
        {
            viewHolder.btnDownload.updatePercent(0);
            viewHolder.btnDownload.Draw(0);
            if (isResume[position] == false) viewHolder.btnStop.setVisibility(View.GONE);
            else viewHolder.btnStop.setVisibility(View.VISIBLE);
        }
        viewHolder.progLayout.setVisibility(View.VISIBLE);
        viewHolder.progLayout.removeAllViews();
        for (int i = 0; i < childThreads.get(position).size(); i++)
        {
            try {
                viewHolder.progLayout.addView(childThreads.get(position).get(i).prog);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        viewHolder.btnDownload.updatePercent(viewHolder.btnDownload.percent);
        viewHolder.btnStop.setVisibility(View.VISIBLE);

        viewHolder.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < childThreads.get(position).size(); i++) {
                    File file = new File((String) Environment.getExternalStorageDirectory().toString() + "/" + _event.get(position).fileName + i);
                    file.delete();
                }

                File file = new File((String) Environment.getExternalStorageDirectory().toString() + "/" + _event.get(position).fileName + ".mp4");
                file.delete();

                file = new File((String) Environment.getExternalStorageDirectory().toString() + "/" + _event.get(position).fileName + childThreads.get(position).size());
                file.delete();

                Toast.makeText(context, "Stop", Toast.LENGTH_LONG).show();
                bDownload[position].Draw(0);
                v.setVisibility(View.GONE);
                if (childThreads.get(position) != null) stop(childThreads.get(position), true);
                isStop[position] = true;
                isResume[position] = false;
                status[position] = false;
                _event.get(position).isDownload = false;
                deleteRow(position);
                childThreads.get(position).clear();
            }
        });

        //viewHolder.btnStop.setVisibility(View.GONE);
        File file = new File((String) Environment.getExternalStorageDirectory().toString() + "/" + _event.get(position).fileName);
        if (file.exists()) {
            long total = (int) file.length();
            //long fileLength = WorkerThread.getData(context, position);
            //viewHolder.progressBar.setProgress((int) (total * 100 / fileLength));
            //viewHolder.progress.setText(String.valueOf((int) (total * 100 / fileLength)) + " %");
            //if ((total * 100 / fileLength) != 100) viewHolder.btnStop.setVisibility(View.VISIBLE);
            childThreadNumber[position] = 0;
        }
        viewHolder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_event.get(position).isDownload == false)
                    _event.get(position).isDownload = true;
                else _event.get(position).isDownload = false;

                if (getData(context, position) != -1 && childThreads.get(position).size() == 0)
                {
                    MyView a = (MyView) v;
                    a.Draw(1);
                    long num = getData(context, position);
                    //All threads will work
                    if (num + executorPool.getQueue().size() <= 16) {
                        threadCompleted[position] = 1;

                        calcCompleted[position] = true;
                        isStop[position] = false;
                        isResume[position] = false;
                        status[position] = true;
                        //finalViewHolder.btnStop.setVisibility(View.VISIBLE);
                        //_event.get(position).isDownload = true;
                        bStop[position].setVisibility(View.VISIBLE);
                        WorkerThread thread = new WorkerThread((int) num, position, v, context, "cmd", Environment.getExternalStorageDirectory().toString() + "/" + _event.get(position).fileName, _event.get(position).link, 0, position, 0, true);
                        childThreads.get(position).add(thread);
                        executorPool.execute(thread);
                        for (int i = 0; i < num - 1; i++) {
                            try {
                                String test = Environment.getExternalStorageDirectory().toString() + "/" + _event.get(position).fileName;
                                WorkerThread t = new WorkerThread((int) num, position, v, context, "cmd" + childThreads.get(position).size(), Environment.getExternalStorageDirectory().toString() + "/" + _event.get(position).fileName, _event.get(position).link, childThreads.get(position).size(), position, childThreads.get(position).get(childThreads.get(position).size() - 1).startByte, true);
                                executorPool.execute(t);
                                childThreads.get(position).add(t);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    return;
                }
                else if (_event.get(position).isDownload == true && childThreads.get(position).size() == 0 && executorPool.getQueue().size() <= 16) {
                    isStop[position] = false;
                    isResume[position] = false;
                    status[position] = true;
                    //status[position] = true;
                    String strUrl = _event.get(position).link;
                    String name = _event.get(position).fileName;
                    if (strUrl.isEmpty() || name.isEmpty()) {
                        return;
                    }
                    clearThreads(childThreads.get(position));
                    threadCompleted[position] = 0;
                    calcCompleted[position] = false;
                    view[position] = v;
                    WorkerThread thread;
                    if (childThreads.get(position).size() == 0) {
                        thread = new WorkerThread(1, position, v, context, "cmd", Environment.getExternalStorageDirectory().toString() + "/" + _event.get(position).fileName, _event.get(position).link, childThreads.get(position).size(), position, 0, true);
                    }
                    else
                    {
                         thread = new WorkerThread(1, position, v, context, "cmd" + childThreads.get(position).size(), Environment.getExternalStorageDirectory().toString() + "/" + _event.get(position).fileName, _event.get(position).link, 0, position, childThreads.get(position).get(childThreads.get(position).size() - 1).startByte, true);
                    }
                    childThreads.get(position).add(thread);
                    pause(childThreads.get(position), false);
                    MyView a = (MyView) v;
                    a.Draw(1);
                    executorPool.execute(thread);
                    try {
                        LinearLayout layout = (LinearLayout) v.getParent();
                        //ImageView stop = (ImageView) layout.findViewById(R.id.btnStop);
                        //stop.setVisibility(View.VISIBLE);
                        bStop[position].setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }else if (_event.get(position).isDownload == true && childThreads.get(position).size() != 0 && ((executorPool.getQueue().size() + childThreads.get(position).size()) <= 16))
                {
                    //Make sure that all of threads will work
                    isStop[position] = false;
                    isResume[position] = false;
                    status[position] = true;
                    threadCompleted[position] = 1;
                    //finalViewHolder.btnStop.setVisibility(View.VISIBLE);
                    //_event.get(position).isDownload = true;
                    bStop[position].setVisibility(View.VISIBLE);

                    for (int i = 0; i < childThreads.get(position).size(); i++) {
                        childThreads.get(position).get(i).isInterrupt = false;
                        childThreads.get(position).get(i).pause = false;
                        childThreads.get(position).get(i).stop = false;
                        executorPool.execute(childThreads.get(position).get(i));

                        MyView a = (MyView) v;
                        a.Draw(1);
                    }
                }
                else if (_event.get(position).isDownload == false) {
                    MyView a = (MyView) v;
                    a.Draw(0);
                    pause(childThreads.get(position), true);
                    threadCompleted[position] = 1;
                    isStop[position] = false;
                    isResume[position] = true;
                    status[position] = false;
                }
            }
        });
        bDownload[position] = viewHolder.btnDownload;
        bStop[position] = viewHolder.btnStop;

        //----------------
        //Prevent draw
        return v;
    }

    public void pause(ArrayList<WorkerThread> threads, boolean isPause)
    {
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).pause = isPause;
            if (isPause == true) executorPool.remove(threads.get(i));
        }


    }
    public void stop(ArrayList<WorkerThread> threads, boolean isStop)
    {
        for (int i = 0; i < threads.size(); i++)
            threads.get(i).stop = isStop;
    }
    public void clearThreads(ArrayList<WorkerThread> threads)
    {
             threads.clear();
    }

    public void update(ArrayList<DownloadItem> items) {
        this._event = items;
        this.notifyDataSetChanged();
    }

    @Override
    public DownloadItem getItem(int position) {
        return _event.get(position);
    }

    @Override
    public int getCount() {
        return _event.size();
    }


    public static final class DownloadDb {
        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DownloadDes.TABLE_NAME + " (" +
                        DownloadDes._ID + " INTEGER PRIMARY KEY," +
                        DownloadDes.NUM_THREADS + TEXT_TYPE + ")";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + DownloadDes.TABLE_NAME;
        // To prevent someone from accidentally instantiating the contract class,
        // give it an empty constructor.

        public DownloadDb() {
        }

        /* Inner class that defines the table contents */
        public static abstract class DownloadDes implements BaseColumns {
            public static final String TABLE_NAME = "downloadItem";
            public static final String NUM_THREADS = "fileLength";
        }

        public static class DownloadDbHelper extends SQLiteOpenHelper {
            // If you change the database schema, you must increment the database version.
            public static final int DATABASE_VERSION = 1;
            public static final String DATABASE_NAME = "DownloaderThread.db";

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

    public void insertToDb(int id, String fileLength) {
        DownloadDb.DownloadDbHelper DownloadDbHelperDb = new DownloadDb.DownloadDbHelper(context);
        SQLiteDatabase dlDb = DownloadDbHelperDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DownloadDb.DownloadDes._ID, String.valueOf(id + 1));
        values.put(DownloadDb.DownloadDes.NUM_THREADS, fileLength);
        dlDb.insert(DownloadDb.DownloadDes.TABLE_NAME, "", values);
    }
    public  long getData(Context context, int id) {
        //Get Data--------------------------------------------------
        DownloadDb.DownloadDbHelper mDbHelper = new DownloadDb.DownloadDbHelper(context);
        String[] projection = {
                DownloadDb.DownloadDes._ID,
                DownloadDb.DownloadDes.NUM_THREADS
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
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(DownloadDb.DownloadDes._ID))) == (id + 1)) return Long.parseLong(cursor.getString(cursor.getColumnIndex(DownloadDb.DownloadDes.NUM_THREADS)));
                cursor.moveToNext();
            }
        }
        return -1;
    }
    public void deleteRow(int id)
    {
        DownloadDb.DownloadDbHelper DownloadDbHelperDb = new DownloadDb.DownloadDbHelper(context);
        SQLiteDatabase dlDb = DownloadDbHelperDb.getWritableDatabase();
        // Define 'where' part of query.
        String selection = DownloadDb.DownloadDes._ID + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(id + 1) };
// Issue SQL statement.
        dlDb.delete(DownloadDb.DownloadDes.TABLE_NAME, selection, selectionArgs);
    }

}