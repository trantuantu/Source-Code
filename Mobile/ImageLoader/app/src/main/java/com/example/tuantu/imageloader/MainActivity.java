package com.example.tuantu.imageloader;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getFragmentManager();
    private ListView list;
    private ArrayList<String> downloadItems = new ArrayList<String>();
    private ArrayList<String> portrails = new ArrayList<String>();
    private ArrayList<String> points = new ArrayList<String>();
    private ArrayList<String> names_vn = new ArrayList<String>();
    private ArrayList<String> names_en = new ArrayList<String>();
    private ArrayList<PagerItem> pagerItems = new ArrayList<PagerItem>();
    //private ThreadPoolExecutor executorPool;
    private BroadcastReceiver mMessageReceiver;
    private String str = "";
    private boolean flag = false;
    private ListFragment list_fragment;
    private PageFragment pager_fragment;
    private boolean s1, s2;
    private ListAdapter adapter = null;
    public static Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        c = this;
        list_fragment = new ListFragment();
        pager_fragment = new PageFragment();
        //FragmentManager fragmentManager = getFragmentManager();
        /*FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        list_fragment = new ListFragment();
        pager_fragment = new PageFragment();
        fragmentTransaction.remove(list_fragment);
        fragmentTransaction.replace(android.R.id.content, list_fragment);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();*/
        replaceFragment(list_fragment);
        //----------------------------------------
        //RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
        //Get the ThreadFactory implementation to use
        //ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //creating the ThreadPoolExecutor
        //2: minimum 2 threads will run (if queue full (contain tasks for a thread), new threads will create to process, and maximum is 10 threads)
        //10: maximum 10 threads will run
        //10: Alive time for excess threads. These thread will be terminated after 10s not actived.
        //Size of Task Queue for a thread, see more in Java Official web (it will be add if number of current thread greater than 2)
        //ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2), threadFactory, rejectionHandler);
        //executorPool = new ThreadPoolExecutor(16, 20, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20), threadFactory, rejectionHandler);
        //start the monitoring thread
        //MyMonitorThread monitor = new MyMonitorThread(executorPool, 3);
        //Thread monitorThread = new Thread(monitor);
        //monitorThread.start();
        //-------------------------------------------
        //Init adpater
        JSONParser jsonParser = new JSONParser(getApplicationContext(), "getJson", "https://api.myjson.com/bins/2yc4p");
        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                downloadItems.clear();
                points.clear();
                flag = false;

                if (str.equals(""))
                    str = intent.getStringExtra("message");
                try {
                    JSONArray root = new JSONArray(str); //Convert string to jsonArray
                    for(int i=0; i < root.length(); i++) {
                        JSONObject jObject = root.getJSONObject(i);
                        String landscape = jObject.getString("poster_landscape");
                        String point = jObject.getString("avg_point_all");
                        String name_vn = jObject.getString("film_name_vn");
                        String name_en = jObject.getString("film_name_en");
                        String portrail = jObject.getString("poster_url");

                        downloadItems.add(landscape);
                        points.add(point);
                        portrails.add(portrail);
                        names_vn.add(name_vn);
                        names_en.add(name_en);
                    }
                } catch (Exception e) {

                }
                    list = (ListView) list_fragment.getView().findViewById(R.id.listDownload);
                    if (adapter == null) adapter = new ListAdapter(getApplicationContext(), R.layout.item, downloadItems, points);
                else
                    {
                        adapter.setEndPost(0);
                        adapter.setStartPos(0);
                    }
                    list.setAdapter(adapter);
                list.setOnScrollListener(new AbsListView.OnScrollListener() {
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        if (flag == true) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent("custom-event-name");
                            intent.putExtra("message", "stop");
                            intent.putExtra("id", String.valueOf(0));
                            intent.putExtra("startPos", String.valueOf(view.getFirstVisiblePosition()));
                            intent.putExtra("endPos", String.valueOf(-1));
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                        }
                        else flag = true;
                    }

                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                            //Log.i("a", "scrolling stopped...");
                            //Toast.makeText(getApplicationContext(), "Stop" + view.getFirstVisiblePosition(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent("custom-event-name");
                            intent.putExtra("message", "stop");
                            intent.putExtra("id", String.valueOf(0));
                            intent.putExtra("startPos", String.valueOf(view.getFirstVisiblePosition()));
                            intent.putExtra("endPos", String.valueOf(view.getLastVisiblePosition()));
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                        }
                    }
                });
            }
        };
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver, new IntentFilter("loadList"));

        //-----------------------------------------

        try{
            InputStream inputStream = openFileInput("config.txt");
        if (inputStream.available() != 0)
        {
                if ( inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    while ( (receiveString = bufferedReader.readLine()) != null ) {
                        stringBuilder.append(receiveString);
                    }

                    inputStream.close();
                    str = stringBuilder.toString();
                }

            Intent intent = new Intent("loadList");
            intent.putExtra("message", str.toString());
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

        }
        }
        catch (FileNotFoundException e) {
            // Log.e("login activity", "File not found: " + e.toString());
            //executorPool.execute(jsonParser);

            RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
            //Get the ThreadFactory implementation to use
            ThreadFactory threadFactory = Executors.defaultThreadFactory();
            //creating the ThreadPoolExecutor
            //2: minimum 2 threads will run (if queue full (contain tasks for a thread), new threads will create to process, and maximum is 10 threads)
            //10: maximum 10 threads will run
            //10: Alive time for excess threads. These thread will be terminated after 10s not actived.
            //Size of Task Queue for a thread, see more in Java Official web (it will be add if number of current thread greater than 2)
            ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2), threadFactory, rejectionHandler);
            executorPool = new ThreadPoolExecutor(16, 20, 5, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>(20, new Comparator<Runnable>() {
                @Override
                public int compare(Runnable t1, Runnable t2) {

                    return (int)(((WorkerThread)t2).time - ((WorkerThread)t1).time);
                }
            }), threadFactory, rejectionHandler);
            //start the monitoring thread
            MyMonitorThread monitor = new MyMonitorThread(executorPool, 3);
            Thread monitorThread = new Thread(monitor);
            monitorThread.start();
            executorPool.execute(jsonParser);
        } catch (IOException e) {
            //Log.e("login activity", "Can not read file: " + e.toString());
        }
        //-----------------------------------------
    }

    private void initListDownload() {
        downloadItems.add("http://worldartsme.com/images/pokemon-characters-clipart-1.jpg");
        downloadItems.add("https://thecliparts.com/wp-content/uploads/2016/05/pokemon-clip-art-free-animations.jpg");
        downloadItems.add("http://www.picgifs.com/clip-art/cartoons/pokemon/clip-art-pokemon-291183.jpg");
        downloadItems.add("http://worldartsme.com/images/pokemon-characters-clipart-1.jpg");
        downloadItems.add("https://thecliparts.com/wp-content/uploads/2016/05/pokemon-clip-art-free-animations.jpg");
        downloadItems.add("http://www.picgifs.com/clip-art/cartoons/pokemon/clip-art-pokemon-291183.jpg");
        downloadItems.add("http://worldartsme.com/images/pokemon-characters-clipart-1.jpg");
        downloadItems.add("https://thecliparts.com/wp-content/uploads/2016/05/pokemon-clip-art-free-animations.jpg");
        downloadItems.add("http://www.picgifs.com/clip-art/cartoons/pokemon/clip-art-pokemon-291183.jpg");
        downloadItems.add("http://worldartsme.com/images/pokemon-characters-clipart-1.jpg");
        downloadItems.add("https://thecliparts.com/wp-content/uploads/2016/05/pokemon-clip-art-free-animations.jpg");
        downloadItems.add("http://www.picgifs.com/clip-art/cartoons/pokemon/clip-art-pokemon-291183.jpg");
        downloadItems.add("http://worldartsme.com/images/pokemon-characters-clipart-1.jpg");
        downloadItems.add("https://thecliparts.com/wp-content/uploads/2016/05/pokemon-clip-art-free-animations.jpg");
        downloadItems.add("http://www.picgifs.com/clip-art/cartoons/pokemon/clip-art-pokemon-291183.jpg");
        downloadItems.add("http://worldartsme.com/images/pokemon-characters-clipart-1.jpg");
        downloadItems.add("https://thecliparts.com/wp-content/uploads/2016/05/pokemon-clip-art-free-animations.jpg");
        downloadItems.add("http://www.picgifs.com/clip-art/cartoons/pokemon/clip-art-pokemon-291183.jpg");
        downloadItems.add("http://worldartsme.com/images/pokemon-characters-clipart-1.jpg");
        downloadItems.add("https://thecliparts.com/wp-content/uploads/2016/05/pokemon-clip-art-free-animations.jpg");
        downloadItems.add("http://www.picgifs.com/clip-art/cartoons/pokemon/clip-art-pokemon-291183.jpg");
    }

    public void changeToPager(View v)
    {

        pagerItems.clear();
        for (int i = 0; i < downloadItems.size(); i++)
        {
            PagerItem item = new PagerItem(portrails.get(i), names_vn.get(i), names_en.get(i), points.get(i));
            pagerItems.add(item);
        }
        /*FragmentTransaction fragmentTrans
        action = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, pager_fragment);
        fragmentTransaction.commit();*/
        ImageLoader imgLoader = ImageLoader.getInstance();
        imgLoader.setOutPath((String) Environment.getExternalStorageDirectory().toString() + "/p");
        replaceFragment(pager_fragment);
        fragmentManager.executePendingTransactions();
        pager_fragment.setPagerAdapter(pagerItems, getApplicationContext());
    }
    public void changeToList(View v)
    {
        ImageLoader imgLoader = ImageLoader.getInstance();
        imgLoader.setOutPath((String) Environment.getExternalStorageDirectory().toString() + "/");
       /* try {
            //fragmentManager
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(android.R.id.content, list_fragment);
            fragmentTransaction.commit();
            Intent intent = new Intent("loadList");
            intent.putExtra("message", str.toString());
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        }catch (Exception e)
        {
            e.printStackTrace();
        }*/

        replaceFragment(list_fragment);
        Intent intent = new Intent("loadList");
        intent.putExtra("message", str.toString());
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);


       // s1 = true;
    }

    private void replaceFragment (Fragment fragment){
        try {
            String backStateName = fragment.getClass().getName();
            String fragmentTag = backStateName;
            boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);

            if (!fragmentPopped && fragmentManager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(android.R.id.content, fragment, fragmentTag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(backStateName);
                ft.commit();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}