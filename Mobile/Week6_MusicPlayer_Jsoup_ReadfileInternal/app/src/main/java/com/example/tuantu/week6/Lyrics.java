package com.example.tuantu.week6;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by TUAN TU on 4/4/2016.
 */
 public class Lyrics extends android.support.v4.app.Fragment {
    public static WebView lyricText;
    static int count = 0;
    public static String text;
    static android.support.v4.app.Fragment c = null;
    public static View rootView = null;
    static LayoutInflater inflater1 = null;
    static ViewGroup viewGroup = null;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            inflater1 = inflater;
            viewGroup = container;
            c = this;
            rootView = inflater.inflate(
                    R.layout.lyrics, container, false);
            lyricText = (WebView) rootView.findViewById(R.id.webView);
            text =  "<html><header><style>p{color:white;font-size:120%;text-align:center;}br{display: block; margin:150% 0;}</style></header><body><p>" + "Không có lời bài hát" + "</p></body></html>";
            lyricText.loadData(text,"text/html; charset=utf-8", "utf-8");
            lyricText.getSettings();
            lyricText.setBackgroundColor(Color.TRANSPARENT);
            lyricText.loadUrl("javascript:document.body.style.backgroundColor ='white';");
            lyricText.loadUrl("javascript:document.body.style.fontSize ='20pt'");
            //lyricText.setMovementMethod(new ScrollingMovementMethod());
            //lyricText.setText("Không có lời bài hát\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }
            //setLyric(player.doc.select("p.fn-wlyrics.fn-content").text());
        return rootView;
    }
   public static void setLyric(String text) {
       try {
           lyricText.setBackgroundResource(R.drawable.lyric_back);

           text =  "<html><header><style>p{color:white;font-size:120%;text-align:center;}br{display: block; margin:150% 0;}</style></header><body><p>" + text + "</p></body></html>";
           lyricText.loadData(text,"text/html; charset=utf-8", "utf-8");
           //lyricText.setText(text);
           //lyricText.setHeight(1000);
           //lyricText.requestLayout();
           //ScrollView a = (ScrollView)rootView.findViewById(R.id.)
           //rootView.addOnLayoutChangeListener();




           /*lyricText.postInvalidate();
           lyricText.invalidate();
           rootView.invalidate();*/
           //getActivity().setContentView(R.layout.);
           //super.onResume();
       }
            catch(Exception e)
            {
                e.printStackTrace();
            }
    }


    public static class getLyric extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            /*if (count++ != 0) {
                int k = 0;

                *//*for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == '\\') k++;
                    lyricText.setText("\\n");
                }*//*

            }
            else count = 1;*/
            setLyric(s);
        }


    }
  /*  @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lyricText.setText(text);
        //tv.setText(text);
    }*/
/*    @Override
    public void onResume()
    {
        super.onResume();
        lyricText.setText(text);
    }
    @Override
public void onStart()
    {
        super.onStart();
        lyricText.setText(text);
    }*/

}
