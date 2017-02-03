package com.example.tuantu.week8_news;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {

    String url = "http://www.hcmus.edu.vn/index.php?option=com_content&task=blogcategory&id=38&Itemid=238";
    public static Elements categories;
    public static Elements news;
    Spinner cateView = null;
    ListView neView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cateView = (Spinner)findViewById(R.id.spinner);
        neView = (ListView) findViewById(R.id.listView);
        (new loadNews()).execute(new String[]{url});

        /*ArrayList<String> itemStrings = new ArrayList<>();
        itemStrings.add("A");
        itemStrings.add("B");
        itemStrings.add("C");
        itemStrings.add("D");
        ListView listView = (ListView)findViewById(R.id.listViewItem);
        ListAdapter adapter = new ListAdapter(
                this,
                R.layout.list_custom,
                itemStrings
        );*/


       // listView.setAdapter(adapter);


        cateView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                Elements init = news.get(arg2 + 5).select("a.latestnews");
                Elements initTime = news.get(arg2 + 5).select("span.date_title");
                ArrayList<String> newsName = new ArrayList<String>();
                ArrayList<String> time = new ArrayList<String>();
                ArrayList<String> arUrl = new ArrayList<String>();
                for (int i = 0; i < init.size(); i++)
                {
                    newsName.add(init.get(i).select("a").text());
                    time.add(initTime.get(i).select("span.date_title").text());
                    arUrl.add(init.get(i).select("a").attr("href").toString());
                }
                ListAdapter newsAdapter = new ListAdapter(getApplicationContext(),R.layout.list_custom, newsName, time, arUrl);
                neView.setAdapter(newsAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    private class loadNews extends AsyncTask<String, Void, ArrayList<ArrayList<String>>> {
        ProgressDialog prg;
        @Override
        protected ArrayList<ArrayList<String>> doInBackground(String... strings) {

            ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
            try{
                //Your code here
                ArrayList<String> cateName = new ArrayList<String>();


                org.jsoup.nodes.Document doc = Jsoup.connect(strings[0]).get();
                categories = doc.select(".moduletable_title_span");
                news = doc.select("tbody table.moduletable");

                Elements init = news.get(0).select("a.latestnews");
                for (int i = 0; i < categories.size(); i++)
                {
                    cateName.add(categories.get(i).text());

                }
              /*  for (int i = 0; i < news.size(); i++)
                {
                    newsName.add(init.get(i).select("a").text());
                    time.add(init.get(i).select("span.date_title").text());
                }*/

                result.add(cateName);

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<String>> s) {
            super.onPostExecute(s);
            ArrayAdapter<String> categoriesAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, s.get(0));
            cateView.setAdapter(categoriesAdapter);

            //prg.dismiss();
        }
    }
}
