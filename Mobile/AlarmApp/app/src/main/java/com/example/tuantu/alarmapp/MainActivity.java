package com.example.tuantu.alarmapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btnAdd;
    public static ArrayList<Event> events;
    public static ListView listView;
    public static EventAdapter adapter;
    public static Context c;
    public static boolean isDismiss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (events == null) events = new ArrayList<Event>();
        c = this;
        btnAdd = (FloatingActionButton) findViewById(R.id.fab);
        if (events.size() == 0) getData(c);
        //EventAdapter adapter = new EventAdapter(this, R.layout.)
        listView = (ListView)findViewById(R.id.list);

        //----------------------------------------------------------

        adapter = new EventAdapter(getApplicationContext(), R.layout.item_event, events);
        listView.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityNewEvent.class);
                startActivity(intent);
            }
        });
        //startService(new Intent(this, AlarmService.class));


    }

    public static final class AlarmDb {
        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + AlarmDes.TABLE_NAME + " (" +
                        AlarmDes._ID + " INTEGER PRIMARY KEY," +
                        AlarmDes.HOUR + TEXT_TYPE + COMMA_SEP +
                        AlarmDes.MINUTE + TEXT_TYPE + COMMA_SEP +
                        AlarmDes.ISON + TEXT_TYPE + ")";
        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + AlarmDes.TABLE_NAME;
        // To prevent someone from accidentally instantiating the contract class,
        // give it an empty constructor.

        public AlarmDb() {
        }

        /* Inner class that defines the table contents */
        public static abstract class AlarmDes implements BaseColumns {
            public static final String TABLE_NAME = "foodnut";
            public static final String HOUR = "hour";
            public static final String MINUTE = "minute";
            public static final String ISON = "isOn";
        }

        public static class AlarmDbHelper extends SQLiteOpenHelper {
            // If you change the database schema, you must increment the database version.
            public static final int DATABASE_VERSION = 1;
            public static final String DATABASE_NAME = "Alarm.db";

            public AlarmDbHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

            public void onCreate(SQLiteDatabase db) {
                db.execSQL(SQL_CREATE_ENTRIES);
            }

            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // This database is only a cache for online data, so its upgrade policy is
                // to simply to discard the data and start over
                db.execSQL(SQL_DELETE_ENTRIES);
                onCreate(db);
            }

            public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                onUpgrade(db, oldVersion, newVersion);
            }
        }
    }
    public static void insert(int id, String hour, String minute, boolean isOn)
    {
        AlarmDb.AlarmDbHelper alarmDbHelperDb = new AlarmDb.AlarmDbHelper(c);
        SQLiteDatabase alarmDb = alarmDbHelperDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AlarmDb.AlarmDes._ID, String.valueOf(id));
        values.put(AlarmDb.AlarmDes.HOUR, hour);
        values.put(AlarmDb.AlarmDes.MINUTE, minute);
        values.put(AlarmDb.AlarmDes.ISON, String.valueOf(isOn));
        //Toast.makeText(c, String.valueOf(isOn), Toast.LENGTH_SHORT).show();
        alarmDb.insert(AlarmDb.AlarmDes.TABLE_NAME, "", values);
    }

    public static void update(boolean isOn, int id)
    {
        AlarmDb.AlarmDbHelper alarmDbDbHelper = new AlarmDb.AlarmDbHelper(c);
        SQLiteDatabase alarmDb = alarmDbDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AlarmDb.AlarmDes.ISON, String.valueOf(isOn));
        String[] selectionArgs = {String.valueOf(id)};
        String selection = AlarmDb.AlarmDes._ID + " LIKE ?";
        alarmDb.update(
                AlarmDb.AlarmDes.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
    public static void getData(Context context)
    {
        //Get Data--------------------------------------------------
        AlarmDb.AlarmDbHelper mDbHelper = new AlarmDb.AlarmDbHelper(context);
        String[] projection = {
                AlarmDb.AlarmDes._ID,
                AlarmDb.AlarmDes.HOUR,
                AlarmDb.AlarmDes.MINUTE,
                AlarmDb.AlarmDes.ISON
        };
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.query(
                AlarmDb.AlarmDes.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                // The sort order
        );

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();


            while (!(cursor.getPosition() == cursor.getCount())) {
                Event event = new Event();
                event._time = cursor.getString(cursor.getColumnIndex(AlarmDb.AlarmDes.HOUR)) + " : " +  cursor.getString(cursor.getColumnIndex(AlarmDb.AlarmDes.MINUTE));
                if (cursor.getString(cursor.getColumnIndex(AlarmDb.AlarmDes.ISON)).equals("true"))
                    event.isOn = true;
                else event.isOn = false;
                events.add(event);
                cursor.moveToNext();
            }
        }
    }
}