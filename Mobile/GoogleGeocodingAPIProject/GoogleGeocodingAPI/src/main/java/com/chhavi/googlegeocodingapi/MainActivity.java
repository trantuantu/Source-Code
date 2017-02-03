package com.chhavi.googlegeocodingapi;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {

    private Context context;
    //private static String url = "http://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&sensor=false";
    private static String url="http://maps.googleapis.com/maps/api/geocode/json?address=";
    private static final String TAG_RESULTS="results";
    private static final String TAG_ADDRESS_COMPONENTS="address_components";
   
    private static final String TAG_LONG_NAME="long_name";
    private static final String TAG_SHORT_NAME="short_name";
    private static final String TAG_TYPES="types";
   
    private static final String TAG_FRORMATTED_ADDRESS="formatted_address";

    private static final String TAG_GEOMETRY="geometry";
    
    private static final String TAG_LOCATION="location";
   
    private static final String TAG_LAT="lat";
    private static final String TAG_LNG="lng";
   
    private static final String TAG_LOCATION_TYPE="location_type";

    private static final String TAG_VIEW_PORT="view_port";
   
    private static final String TAG_NORTHEAST="north_east";

    private static final String TAG_SOUTHWEST="south_west";
  
    private static final String TAG_STREET_ADDRESS="street_address";
    private static final String TAG_STATUS="status";

    ArrayList<HashMap<String, String>> jlist = new ArrayList<HashMap<String, String>>();
    ListView listv ;
    JSONArray results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String enter= intent.getStringExtra("place");
        Log.i("place..",enter+"");
        url=url+enter+"&sensor=false";
        new Progress(MainActivity.this).execute();
    }

    private class Progress extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog;

        public Progress(ListActivity activity) {

            Log.i("class progress", "constructor");
            context = activity;
            dialog = new ProgressDialog(context);
        }

        private Context context;

        protected void onPreExecute() {
            this.dialog.setMessage("Searching latitude and longitude");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            Log.i("jasonlist",jlist.size()+"");
            ListAdapter ad = new SimpleAdapter(context, jlist,R.layout.list_layout, new String[] { TAG_LAT, TAG_LNG }, new int[] { R.id.lat, R.id.lng });
            setListAdapter(ad);
            listv = getListView();
        }

        protected Boolean doInBackground(final String... args) {

            JSONParsering jParser = new JSONParsering();
            JSONObject jsonobj = jParser.getJSONFromUrl(url);

           try {
               results = jsonobj.getJSONArray(TAG_RESULTS);
               if(results==null)
               {
                   HashMap<String, String> hmap = new HashMap<String, String>();
                   hmap.put("TAG_LAT","Null");
                   hmap.put("TAG_LNG","Null");
               }
               else
               {
               for(int i = 0; i < results.length(); i++)
               {
                   JSONObject c = results.getJSONObject(i);
                   JSONObject geometry = c.getJSONObject(TAG_GEOMETRY);
                   JSONObject location = geometry.getJSONObject(TAG_LOCATION);

                   Log.i("check",location.toString());
                   double lat=location.getDouble(TAG_LAT);
                   double lng=location.getDouble(TAG_LNG);

                   String latS=Double.toString(lat);
                   String lngS=Double.toString(lng);

                   HashMap<String, String> hmap = new HashMap<String, String>();
                   Log.i("check",latS+"");
                   hmap.put(TAG_LAT, "LATITUDE: "+latS + "");
                   hmap.put(TAG_LNG,"LONGITUDE: "+lngS+"");

                   jlist.add(hmap);
               }
               }
              }

           catch(JSONException e)
           {
                e.printStackTrace();
           }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}


