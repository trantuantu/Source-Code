package com.example.tuantu.mapexample2;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    static EditText ed;
    private static GoogleMap mMap;
    private Context context;
    double lat, lng;

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
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ed = (EditText)findViewById(R.id.editText1);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void search(View v)
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ed.getWindowToken(), 0);

        url="http://maps.googleapis.com/maps/api/geocode/json?address=";
        StringBuilder temp = new StringBuilder(ed.getText().toString());


        for (int i = 0; i < temp.length(); i++)
            if (temp.charAt(i) == ' ')
                temp.setCharAt(i, '+');

        url=url+temp+"&sensor=false";
        new Progress(MapsActivity.this).execute();
    }

    private class Progress extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog;

        public Progress(FragmentActivity activity) {

           // Log.i("class progress", "constructor");
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
            MapsActivity.moveMap(lat, lng);

            //Log.i("jasonlist",jlist.size()+"");
            //ListAdapter ad = new SimpleAdapter(context, jlist,R.layout.list_layout, new String[] { TAG_LAT, TAG_LNG }, new int[] { R.id.lat, R.id.lng });
           // setListAdapter(ad);
           // listv = getListView();
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

                        //Log.i("check",location.toString());
                        lat=location.getDouble(TAG_LAT);
                        lng=location.getDouble(TAG_LNG);


                        //String latS=Double.toString(lat);
                        //String lngS=Double.toString(lng);
                       // HashMap<String, String> hmap = new HashMap<String, String>();
                        //Log.i("check",latS+"");
                      //  hmap.put(TAG_LAT, "LATITUDE: "+latS + "");//Json parsing Latitude
                      //  hmap.put(TAG_LNG, "LONGITUDE: " + lngS + "");//Json parsing Longtitude
                        //jlist.add(hmap);
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
    static void moveMap(double lat, double lng)
    {
        LatLng sydney = new LatLng(lat, lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
        mMap.addMarker(new MarkerOptions().position(sydney).title(ed.getText().toString()));
    }
}