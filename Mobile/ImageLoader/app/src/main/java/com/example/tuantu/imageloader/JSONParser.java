package com.example.tuantu.imageloader;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.provider.BaseColumns;
import android.renderscript.ScriptGroup;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
/**
 * Created by TUAN TU on 7/1/2016.
 */
public class JSONParser implements Runnable {
    private Context context;
    private String command;
    private String link;
    public long total;
    private StringBuilder str = new StringBuilder();

    public JSONParser(Context c, String s, String link){
        context = c;
        this.command=s;
        this.link = link;
    }
    @Override
    public void run() {
        //Your code here. Define a task for a thread. (Divide file to 10 parts if number tasks is 10
        System.out.println(Thread.currentThread().getName() + " Start. Command = " + command);
        if (hasActiveInternetConnection(context))
            processDownload((String) Environment.getExternalStorageDirectory().toString() + "/" + "jsonFile", link);

        Intent intent = new Intent("loadList");
        intent.putExtra("message", str.toString());
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void processDownload(String fileName, String link) {
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            File file = new File(fileName);
            if (file.exists()) {
                total = (int) file.length();
                connection.connect();
            } else {

            }
            InputStream inputStream = (InputStream)connection.getContent();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            int x = 0;
            String line = null;
            while ((line = in.readLine()) != null) {
                total += x;
                str.append(line);
            }
            in.close();
            connection.disconnect();
            outputStreamWriter.write(str.toString());
            outputStreamWriter.close();
            try {
                JSONArray root = new JSONArray(str.toString()); //Convert string to jsonArray
                for(int i=0; i < root.length(); i++) {
                    JSONObject jObject = root.getJSONObject(i);
                    String name = jObject.getString("film_id");
                    String filmName = jObject.getString("film_name_vn");
                    String test = "";
                }
            } catch (Exception e) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
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
        } else {
        }
        return false;
    }
}