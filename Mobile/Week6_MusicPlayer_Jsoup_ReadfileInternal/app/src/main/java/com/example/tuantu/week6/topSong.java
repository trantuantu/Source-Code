package com.example.tuantu.week6;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by TUAN TU on 4/2/2016.
 */
public class topSong extends Activity {
    ListView li;
    TextView t;
    public static Elements listSong;
    public static Elements albums;
    static Context c = null;
    String[]_choices;

    String url = "http://mp3.zing.vn/bang-xep-hang/bai-hat-Viet-Nam/IWZ9Z08I.html";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationContext().setTheme(R.style.AppTheme);
        setContentView(R.layout.topsong);
        ImageView img;
        c = getApplicationContext();
        li = (ListView) findViewById(R.id.listView1);
        t = (TextView) findViewById(R.id.textView);
        if (isNetworkConnected() && isOnline())
        (new loadSong()).execute(new String[]{url});
        else  Toast.makeText(getApplicationContext(), "Không có kết nối mạng", Toast.LENGTH_LONG).show();

        if (isNetworkConnected() && !isOnline())  Toast.makeText(getApplicationContext(), "Đường truyền không ổn định", Toast.LENGTH_LONG).show();

        //ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, _choices);
        //loadSong();

     /*   li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //return the link to MainActivity

                String temp = listSong.get(arg2).select("h3.title-item a").attr("href");
                (new loadAblum()).execute(new String[]{temp});
                MainActivity.Execute(temp);
                MainActivity.songName.setText(listSong.get(arg2).select("h3.title-item a").attr("title"));
                MainActivity.singer.setText(listSong.get(arg2).select("h4.title-sd-item a").text());
                //loadAlbum

            }
        });*/

    }

    public static class loadAblum extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            ArrayList<String> buffer = new ArrayList<String>();
            try {
                org.jsoup.nodes.Document doc = Jsoup.connect("http://mp3.zing.vn" + strings[0]).get();
                //Get all albums of the airtist
                albums = doc.select("div.album-item");
                //Get album of the song
                Elements albumSong = doc.select("div.info-song-top.otr.fl");
                String container = albumSong.text();
                String albumName = null;
                String url1=null;
                if (container.contains("Album: "))
                {
                    String [] temp = container.split("Album: ");

                    albumName = temp[temp.length - 1];

                    for (int i = 0; i < albums.size(); i++)
                    {
                        if (albums.get(i).select("h3.title-item").text().equals(albumName)) {
                            url1 = albums.get(i).select("img").attr("src");
                            break;
                        }
                    }
                }
                buffer.add(url1);
                //MainActivity.urlNor(url);
            } catch (Exception t) {
                Toast.makeText(c, "Đường truyền không ổn định", Toast.LENGTH_LONG).show();
            }
            return buffer;
        }
        @Override
        protected void onPostExecute(ArrayList<String> s) {
            super.onPostExecute(s);
            try {
                String rss = urlNor(s.get(0));
                player.ablumUrl = rss;
            }
            catch (Exception e)
            {
                Toast.makeText(c, "Đường truyền không ổn định", Toast.LENGTH_LONG).show();
            }

        }
    }


public static String urlNor(String url1)
{

        String res = "http://image.mp3.zdn.vn";
        String temp = "";
        int k = 0;
    try {
        for (int i = 0; i < url1.length(); i++) {
            if (url1.charAt(i) == '/') k++;
            if (k >= 5) {
                temp += url1.charAt(i);
            }
        }
    }catch (Exception e)
    {
e.printStackTrace();
    }
    if (url1 != null) return res + temp;
    else return res;
}

    //---------------------------------------------------------
    private class loadSong extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            ArrayList<String> buffer = new ArrayList<String>();
            try {
                org.jsoup.nodes.Document doc = Jsoup.connect(strings[0]).get();
                Elements content = doc.select("div.weekly-show p.pull-left");
                String w = content.text();//Get weekly
                buffer.add(w);
                listSong = doc.select("div.table-body ul li");
                for (int i = 0; i < listSong.size(); i++)
                {
                    buffer.add(listSong.get(i).select("span.txt-rank").text()+ ". " + listSong.get(i).select("h3.title-item a").attr("title"));
                }
                //Get album image

                //org.jsoup.nodes.Document doc1 = Jsoup.connect(strings[0]).get();
               // org.jsoup.nodes.Document doc1 = Jsoup.connect(temp).get();
                //Elements content1 = doc1.select("source");
                //String temp1 = content1.text();
            } catch (Exception t) {

            }
            return buffer;
        }
        @Override
        protected void onPostExecute(ArrayList<String> s) {
            super.onPostExecute(s);
            try {
                t.setText(t.getText() + s.get(0));
                s.remove(0);
                //for (int i = 1; i < s.size(); i++)
                String[] temp2 = {"Download", "Share"};

                li.setAdapter(new topSongAdapter(getApplicationContext(), s, temp2));
            }catch (Exception e)
            {
                e.printStackTrace();
            }
           // li.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_activated_1, s));
          /*  mp = new MediaPlayer();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mp.setDataSource(s);
                mp.prepare(); // might take long! (for buffering, etc)
                mp.start();
            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(), "Check the connection!", Toast.LENGTH_LONG).show();
            }
*/

        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}

