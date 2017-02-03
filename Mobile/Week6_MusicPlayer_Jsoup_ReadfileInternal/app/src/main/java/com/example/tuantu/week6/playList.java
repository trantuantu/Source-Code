package com.example.tuantu.week6;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by TUAN TU on 3/31/2016.
 */
public class playList extends Activity {

    private File root;
    private ArrayList<File> fileList = new ArrayList<File>();

    ListView li = null;
    ArrayList<String> listSong = new ArrayList<String>();
    ArrayList<String> listArtist = new ArrayList<String>();
    Field[] fields;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist);

        li = (ListView) findViewById(R.id.listView);

       // root = new File(Environment.getExternalStorageDirectory()
       //        .getAbsolutePath());

        root = new File("/storage/external_SD");

        getfile(root);

       // for (int i = 0; i < fileList.size(); i++) {
           // listSong.add(fileList.get(i).getName());

            // TextView textView = new TextView(this);
            // textView.setText(fileList.get(i).getName());
            // textView.setPadding(5, 5, 5, 5);

           // System.out.println(fileList.get(i).getName());

            // if (fileList.get(i).isDirectory()) {
            //     textView.setTextColor(Color.parseColor("#FF0000"));
            //}
            // view.addView(textView);
       // }
      //  li.setAdapter(new ArrayAdapter<String>(this,
       //         android.R.layout.simple_list_item_activated_1, listSong));

       loadSong();
        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Intent intent = new Intent(playList.this, MainActivity.class);
                //intent.putExtra("name", listSong.get(arg2));
                //startActivity(intent);
                //if (MainActivity.mp.isPlaying()) MainActivity.mp.stop();

                // player.playMusic(getApplicationContext(), Uri.parse("android.resource://com.example.tuantu.week6/raw/" + fields[arg2].getName()));
                player.playMusic(getApplicationContext(), Uri.parse(fileList.get(arg2).getAbsolutePath()));

                player.image.setImageResource(R.drawable.album);
                player.songName.setText(listSong.get(arg2));
                player.singer.setText(listArtist.get(arg2));
            }
        });
    }
//Load from resource
   /* void loadSong() {

        //fields = R.raw.class.getFields();
        for (int count = 0; count < fields.size(); count++) {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(getApplicationContext(), Uri.parse("android.resource://com.example.tuantu.week6/raw/" + fields.get(count).getName()));
            listSong.add(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
            listArtist.add(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        }
        li.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, listSong));
    }*/
    //Load from SD_Card
void loadSong() {

    //fields = R.raw.class.getFields();
    for (int count = 0; count < fileList.size(); count++) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
           // FileInputStream stream = new FileInputStream(fileList.get(count));
           // mp.setDataSource(stream.getFD());
            String a = fileList.get(count).getAbsolutePath();
            mmr.setDataSource(fileList.get(count).getAbsolutePath());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        listSong.add(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        listArtist.add(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
    }
    li.setAdapter(new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_activated_1, listSong));
}



    public ArrayList<File> getfile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
                    //fileList.add(listFile[i]);
                    getfile(listFile[i]);

                } else {
                    if (listFile[i].getName().endsWith(".mp3"))
                          /*  || listFile[i].getName().endsWith(".jpg")
                            || listFile[i].getName().endsWith(".jpeg")
                            || listFile[i].getName().endsWith(".gif"))
*/
                    {
                        fileList.add(listFile[i]);
                    }
                }

            }
        }
        return fileList;
    }
}