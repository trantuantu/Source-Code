package com.example.tuantu.week8_news;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by TUAN TU on 4/18/2016.
 */
public class newsDetails extends Activity {
    public static String url = "";
    WebView textContent = null;
    String html = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details);
        textContent = (WebView)findViewById(R.id.webview);

        textContent.getSettings();
        textContent.setBackgroundColor(Color.BLACK);
        textContent.loadUrl("javascript:document.body.style.backgroundColor ='white';");
        textContent.loadUrl("javascript:document.body.style.fontSize ='20pt'");
        (new loadNews()).execute(new String[]{url});

    }

    private class loadNews extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            try {
                org.jsoup.nodes.Document doc = Jsoup.connect(strings[0]).get();
                Elements content = doc.select("table.contentpaneopen");
                html = content.get(1).html();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return html;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            s =  "<html><header><style>p{color:white;font-size:120%;text-align:center;}br{display: block; margin:150% 0;}</style></header><body><p>" + s + "</p></body></html>";
            textContent.loadData(s,"text/html; charset=utf-8", "utf-8");
        }
    }
}
