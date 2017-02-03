package com.example.tuantu.week6;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.BoringLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;

public class player extends android.support.v4.app.Fragment {
    private ViewPager viewPager;
    private TabPagerAdapter mAdapter;
    private ActionBar actionBar;
    public static org.jsoup.nodes.Document doc;
    static Context c = null;
    // Tab titles
    private String[] tabs = { "Top Rated", "Games", "Movies" };
    String extra = null;
    static MediaPlayer mp = new MediaPlayer();
    static int flag = 0;
    int flag2 = 0;
    public static TextView songName;
    public static TextView singer;
    public static ImageView image;
    public static String ablumUrl = null;
    Field[] fields = R.raw.class.getFields();
    static View rootView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.player, container, false);
            mp = MediaPlayer.create(getActivity(), Uri.parse("android.resource://com.example.tuantu.week6/raw/song1"));
            songName = (TextView) rootView.findViewById(R.id.songName);
            singer = (TextView) rootView.findViewById(R.id.singer);
            image = (ImageView) rootView.findViewById(R.id.albumImage);
            c = getActivity().getApplicationContext();
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(getActivity(), Uri.parse("android.resource://com.example.tuantu.week6/raw/song1"));
            songName.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
            singer.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        }
            final ImageButton play = (ImageButton) rootView.findViewById(R.id.imageButton2);
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ImageButton b = (ImageButton) rootView.findViewById(R.id.imageButton2);

                    //sLinearLayout.LayoutParams li = new LinearLayout.LayoutParams(70, 70);
                    //li.

                    // if (flag == 0)  mp = MediaPlayer.create(getActivity(), Uri.parse("android.resource://com.example.tuantu.week6/raw/song1"));
                    if (!mp.isPlaying()) {
                        mp.start();
                        // Drawable d = getResources().getDrawable(R.drawable.pause);
                        b.setBackgroundResource(R.drawable.pause);
                    } else if (mp.isPlaying()) {
                        mp.pause();
                        b.setBackgroundResource(R.drawable.play);
                    }
                }

            });
            ImageButton prev = (ImageButton) rootView.findViewById(R.id.imageButton);
            prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    previousSong(v);

                }
            });
            ImageButton next = (ImageButton) rootView.findViewById(R.id.imageButton3);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextSong(v);

                }
            });
            ImageButton listSong = (ImageButton) rootView.findViewById(R.id.imageButton4);
            listSong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listSong(v);

                }
            });
            ImageButton zing = (ImageButton) rootView.findViewById(R.id.imageButton5);
            zing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    topSong(v);
                }
            });
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
            {
                @Override
                public void onCompletion(MediaPlayer mp)
                {
                    //your code

                    ImageButton play1 = (ImageButton) rootView.findViewById(R.id.imageButton2);
                    play1.setBackgroundResource(R.drawable.play);
                    Toast.makeText(c, "Không thể phát nhạc trực tuyến lúc này", Toast.LENGTH_LONG).show();

                }
            });

        return rootView;
    }
    public static void setImage(Bitmap b)
    {
        try {
            image.setImageBitmap(b);
        }catch (Exception e)
        {
            Toast.makeText(c, "Không thể phát nhạc trực tuyến lúc này", Toast.LENGTH_LONG).show();
        }
    }
    public static void Execute(String url)
    {
        (new ParseURL()).execute(new String[]{url});
    }

    public static class checkEnd extends  AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            while (mp.isPlaying()) {
                try {
                    Thread.currentThread().sleep(2000);                 //1000 milliseconds is one second.
                } catch (InterruptedException ex) {
                    //Thread.currentThread().interrupt();
                }
            }
            return true;
        }
        @Override
        protected void onPostExecute(Boolean a) {
            ImageButton b = (ImageButton) rootView.findViewById(R.id.imageButton2);
            b.setBackgroundResource(R.drawable.play);
        }
    }
    public  static void setLyric(final String s)
    {
    /*    runOnUiThread(new Runnable(){
            public void run() {
                //If there are stories, add them to the table
                for (Parcelable currentHeadline : allHeadlines) {
                    addHeadlineToTable(currentHeadline);
                }
                try {
                    dialog.dismiss();
                } catch (final Exception ex) {
                    Log.i("---", "Exception in thread");
                }
            }
        });*/
       (new Lyrics.getLyric()).execute(new String[]{s});
        Lyrics.text = s;

    }
   // static String lyric;
    private static class ParseURL extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuffer buffer = new StringBuffer();
            try {
                doc = Jsoup.connect(strings[0]).get();
                Elements content = doc.select("div[^data-xml]");
                String temp = content.attr("data-xml");
                Elements content2 = doc.select("div#lyrics div.fn-container div");
                //------------------------------------------
                setLyric(content2.get(0).select("p").html());
                org.jsoup.nodes.Document doc1 = Jsoup.connect(temp).get();
                Elements content1 = doc1.select("source");
                String temp1 = content1.text();
                buffer.append(temp1);
                //Get albumImage
                URL newurl = new URL(ablumUrl);
                Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                if (mIcon_val != null)
                    player.setImage(mIcon_val);
                //GetLyrics
            } catch (Exception e) {

            }
            return buffer.toString();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mp.stop();
                mp.release();
                mp = new MediaPlayer();
                mp.setDataSource(s);
                mp.prepare(); // might take long! (for buffering, etc)
                mp.start();
            }catch (Exception e)
            {
                Toast.makeText(c, "Không thể phát nhạc trực tuyến lúc này", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void listSong(View v) {
        Intent intent = new Intent(getActivity(), playList.class);
        startActivity(intent);

    }
    public void topSong(View v)
    {
        Intent intent = new Intent(getActivity(), topSong.class);
        startActivity(intent);
    }


    public void PlaySong(View v)
    {


    }
    public void nextSong(View v)
    {
        image.setImageResource(R.drawable.album);
        flag++;

        if (mp.isPlaying())
        {
            flag2 = 1;
            mp.stop();
        }
        else flag2 = 0;
        if (flag < fields.length) mp = MediaPlayer.create(getActivity(), Uri.parse("android.resource://com.example.tuantu.week6/raw/" + fields[flag].getName()));
        else
        {
            mp = MediaPlayer.create(getActivity(), Uri.parse("android.resource://com.example.tuantu.week6/raw/" + fields[0].getName()));
            flag = 0;
        }
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(getActivity(), Uri.parse("android.resource://com.example.tuantu.week6/raw/" + fields[flag].getName()));
        songName.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        singer.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        if (flag2 == 1) mp.start();
    }
    public void previousSong(View v)
    {
        image.setImageResource(R.drawable.album);
        flag--;

        if (mp.isPlaying())
        {
            flag2 = 1;
            mp.stop();
        }
        else flag2 = 0;
        if (flag >= 0) mp = MediaPlayer.create(getActivity(), Uri.parse("android.resource://com.example.tuantu.week6/raw/" + fields[flag].getName()));
        else
        {
            mp = MediaPlayer.create(getActivity(), Uri.parse("android.resource://com.example.tuantu.week6/raw/" + fields[fields.length - 1].getName()));
            flag = fields.length - 1;
        }
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(getActivity(), Uri.parse("android.resource://com.example.tuantu.week6/raw/" + fields[flag].getName()));
        songName.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        singer.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        if (flag2 == 1) mp.start();
    }
    public static void playMusic(Context c, Uri linked)
    {
        if (mp.isPlaying())
        {
            mp.stop();

        }
        mp = MediaPlayer.create(c, linked);
        mp.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        ImageButton b = (ImageButton)rootView.findViewById(R.id.imageButton2);
        if (mp.isPlaying())
        {
            b.setBackgroundResource(R.drawable.pause);
        }
    }
    public static String br2nl(String html) {
        org.jsoup.nodes.Document document = ( org.jsoup.nodes.Document) Jsoup.parse(html);
        document.select("br").append("\\n");
        document.select("p").prepend("\\n\\n");
        return document.text().replace("\\n", "\n");
    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}