package com.example.tuantu.downloader;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.BaseColumns;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by TUAN TU on 6/28/2016.
 */
public class DownloadAdapter extends ArrayAdapter<DownloadItem> {
    ArrayList<DownloadItem> _event;
    private Context context;
    int resource;
    DownloadTask[] threads = new DownloadTask[3];
    //long fileLength;
    int flag = 0;

    //int count = 0;

    public DownloadAdapter(Context context, int resource, ArrayList<DownloadItem> event) {
        super(context, resource);
        _event = event;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(resource, null);
        }
        ImageView logo = (ImageView) v.findViewById(R.id.imgLogo);
        TextView fileName = (TextView) v.findViewById(R.id.txtFileName);
        TextView title1 = (TextView) v.findViewById(R.id.txtTitle1);
        TextView title2 = (TextView) v.findViewById(R.id.txtTitle2);
        final ImageView btnDownload = (ImageView) v.findViewById(R.id.btnDown);

        logo.setImageResource(_event.get(position).imageId);
        fileName.setText(_event.get(position).fileName);
        title1.setText(_event.get(position).title1);
        title2.setText(_event.get(position).title2);

        final ProgressBar progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        final TextView progress = (TextView)v.findViewById(R.id.progress);
        ImageView btnStop = (ImageView)v.findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File((String) Environment.getExternalStorageDirectory().toString() + "/" + _event.get(position).fileName);
                file.delete();
                Toast.makeText(context, "Stop", Toast.LENGTH_LONG).show();
                progress.setText("0 %");
                progressBar.setProgress(0);
                btnDownload.setImageResource(R.drawable.download);
                v.setVisibility(View.GONE);
                if (threads[position] != null) threads[position].stop = true;
            }
        });
        btnStop.setVisibility(View.GONE);
        //Set status here
        File file = new File((String) Environment.getExternalStorageDirectory().toString() + "/" + _event.get(position).fileName);
        if (file.exists()) {
            long total = (int) file.length();
            long fileLength = getData(context, position);
            progressBar.setProgress((int) (total * 100 / fileLength));
            progress.setText(String.valueOf((int) (total * 100 / fileLength)) + " %");
            if ((total * 100 / fileLength) != 100) btnStop.setVisibility(View.VISIBLE);
        }
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //count++;
                if (_event.get(position).isDownload == false)
                    _event.get(position).isDownload = true;
                else _event.get(position).isDownload = false;

                if (_event.get(position).isDownload == true) {


                    String strUrl = _event.get(position).link;
                    String filename = _event.get(position).fileName;
                    if (strUrl.isEmpty() || filename.isEmpty()) {
                        //Toast.makeText(this, "Invalid Url or filename", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    DownloadTask downloadTask = new DownloadTask(context, v, position);
                    threads[position] = downloadTask;
                    threads[position].pause = false;
                    Object params[] = {
                            strUrl,
                            Environment.getExternalStorageDirectory().toString() + "/" + filename
                    };
                    //Toast.makeText(context, "Downloading...", Toast.LENGTH_LONG).show();
                    ImageView a = (ImageView) v;
                    a.setImageResource(R.drawable.pause);
                    downloadTask.execute(params);
                    try {
                        LinearLayout layout = (LinearLayout) v.getParent();
                        ImageView stop = (ImageView) layout.findViewById(R.id.btnStop);
                        stop.setVisibility(View.VISIBLE);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    }
                   /* ImageView stop = new ImageView(context);
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(dpToPx(50), dpToPx(50));
                    stop.setImageResource(R.drawable.stop);
                    stop.*/

                } else {
                    //Toast.makeText(context, "Paused", Toast.LENGTH_LONG).show();
                    ImageView a = (ImageView) v;
                    a.setImageResource(R.drawable.download);
                    threads[position].pause = true;
                }
            }
        });
        return v;
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

    private class DownloadTask extends AsyncTask<Object, Integer, Object> {

        private Context context1;
        private ProgressBar progressBar;
        private TextView txtProgress;
        private ImageView btnDownload;
        ImageView btnStop;
        LinearLayout item;
        public boolean pause;
        public boolean stop;
        int postion;
        long fileLength;
        //ArrayList<DownloadItem> temp = new ArrayList<DownloadItem>();


        public DownloadTask(Context context, View v, int pos) {
            this.context1 = context;
            try {
                LinearLayout parent1 = (LinearLayout)v.getParent().getParent().getParent();
                progressBar = (ProgressBar) parent1.findViewById(R.id.progressBar);
                txtProgress = (TextView)  parent1.findViewById(R.id.progress);
                btnDownload = (ImageView)  parent1.findViewById(R.id.btnDown);
                btnStop = (ImageView)  parent1.findViewById(R.id.btnStop);
                postion = pos;
                progressBar.setMax(100);
                pause = false;
                stop = false;
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Object... params) {
            InputStream in = null;
            OutputStream fos = null;
            OutputStream bout = null;
            try {
                URL url = new URL((String) params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                long total = 0;
                File file = new File((String) params[1]);
                if (file.exists()) {
                    total = (int) file.length();
                    fileLength = getData(context1, postion);
                    //-------------------
                    connection.setRequestProperty("Range", "bytes=" + (file.length()) + "-");
                    connection.connect();
                } else {

                    connection.connect();
                    fileLength = connection.getContentLength();
                    insertToDb(postion, String.valueOf(fileLength));
                }

                in = new BufferedInputStream(connection.getInputStream());
                fos = (total == 0) ? new FileOutputStream((String) params[1]) : new FileOutputStream((String) params[1], true);
                bout = new BufferedOutputStream(fos, 1024);
                byte[] data = new byte[1024];
                int x = 0;

                while ((x = in.read(data, 0, 1024)) >= 0) {
                    bout.write(data, 0, x);
                    total += x;
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));

                    if (pause == true || stop == true) {
                        return null;
                    }
                }
                bout.close();
                in.close();
                fos.close();
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            txtProgress.setText(String.valueOf(progress[0]) + " %");
            progressBar.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(Object result) {
            btnDownload.setImageResource(R.drawable.download);
            _event.get(postion).isDownload = false;
           if (pause == false) btnStop.setVisibility(View.GONE);
            if (stop == true)
            {
                txtProgress.setText("0 %");
                progressBar.setProgress(0);
            }
            //resume = false;
            //progressBar.setProgress(50);
            if (result != null)
                Toast.makeText(context1, "Download error: " + result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context1, "File downloaded", Toast.LENGTH_SHORT).show();
        }

        public void insertToDb(int id, String fileLength) {
            DownloadDb.DownloadDbHelper DownloadDbHelperDb = new DownloadDb.DownloadDbHelper(context);
            SQLiteDatabase dlDb = DownloadDbHelperDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DownloadDb.DownloadDes._ID, String.valueOf(id + 1));
            values.put(DownloadDb.DownloadDes.FILE_LENGHT, fileLength);
            dlDb.insert(DownloadDb.DownloadDes.TABLE_NAME, "", values);
        }



        //Create Database here
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

    public long getData(Context context, int id) {
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
}