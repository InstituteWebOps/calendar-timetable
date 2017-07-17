

package com.example.android.calendar;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    //RecyclerView recyclerView;
   // RecyclerView.Adapter recyclerAdapter;
    //RecyclerView.LayoutManager layoutManager;
    int currentMonth;
    public static int monthForRecyclerView=Calendar.getInstance().get(Calendar.MONTH),yearForRecyclerView=2017;
    private long CalID;
    private String[] months = {"january", "february", "march", "april", "may", "june", "july", "august", "september",
            "october", "november", "december"};
    private String url = "";//url of api file
    private final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 99;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);
        currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        //monthForRecyclerView = currentMonth;
        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        MonthFmAdapter adapter = new MonthFmAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentMonth-6);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int current = position % 12;
                monthForRecyclerView = current+6;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

      //  recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
        //recyclerAdapter= new RecyclerAdapter();
        //recyclerView.setAdapter(recyclerAdapter);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_CALENDAR)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                Snackbar snackbar = Snackbar
                        .make(relativeLayout, "Granting this permission will allow the app to integrate official insti calendar with your personal calendar.", Snackbar.LENGTH_LONG);
                snackbar.show();

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_CALENDAR},
                        MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        //Utils.saveprefInt("TT_Screen", 0, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_CALENDAR: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    String acc = "students.iitm";
                    String disp = "IITM Calendar";
                    String inter = "IITM Calendar";

                    CalID = Utils.getprefLong("CalID", this);
                    if (CalID == -1) {
                        CalID = insertCalendar(acc, inter, disp);
                        Log.i("CalID", CalID + "");
                        Utils.saveprefLong("CalID", CalID, this);
                    }

                    for (int m = 0; m < 12; m++) {
                        sendJsonRequest(m);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    /*Intent intent = new Intent(CalendarActivity.this, HomeActivity.class);
                    startActivity(intent);
                       */

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    void sendJsonRequest(final int month) {
        JsonArrayRequest jrequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < 31; i++) {
                        if (response.isNull(i)) {
                            return;
                        }
                        JSONObject jsonObject = response.getJSONObject(i);
                        Calendar_Event event = new Calendar_Event();
                        event.setDate(jsonObject.getInt("date"));
                        event.setMonth(month);  //0 for Jan, 11 for December and so on 7 for August
                        event.setDay(jsonObject.getString("day"));
                        event.setDetails(jsonObject.getString("details"));
                        event.setHoliday(jsonObject.getInt("holiday") == 1);
                        event.setRemind(jsonObject.getInt("remind") == 1);

                        if (event.getDetails().length() > 0 && !exists(event)) {
                            insertEvents(CalID, event);
                        }

                        Log.i("JSONResp3", event.getDay());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("month", months[month]);
                return params;
            }
        };


    }

    void insertEvents(long calId, Calendar_Event event) {

        Calendar cal = new GregorianCalendar(2017, event.getMonth() - 1, event.getDate());    //Jan is 0
        cal.setTimeZone(TimeZone.getTimeZone("IST"));
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        long start = cal.getTimeInMillis();

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, start);
        values.put(CalendarContract.Events.DTEND, start);
        values.put(CalendarContract.Events.TITLE, event.getDetails());
        values.put(CalendarContract.Events.EVENT_LOCATION, "Chennai");
        values.put(CalendarContract.Events.CALENDAR_ID, calId);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "India");
        values.put(CalendarContract.Events.DESCRIPTION, event.isHoliday() ? "Holiday" : "");
// reasonable defaults exist:
        values.put(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_DEFAULT);
        values.put(CalendarContract.Events.SELF_ATTENDEE_STATUS,
                CalendarContract.Events.STATUS_CONFIRMED);
        values.put(CalendarContract.Events.ALL_DAY, 1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Uri uri =
                    getContentResolver().
                            insert(CalendarContract.Events.CONTENT_URI, values);
            long eventId = new Long(uri.getLastPathSegment());
            Log.i("EventID", eventId + "");
        }
    }

    boolean exists(Calendar_Event event) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

            String[] proj =
                    new String[]{CalendarContract.Events.TITLE};
            Calendar cal = new GregorianCalendar(2017, event.getMonth(), event.getDate());
            cal.setTimeZone(TimeZone.getTimeZone("IST"));
            cal.set(Calendar.HOUR, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            long start = cal.getTimeInMillis();

            Cursor cursor = getContentResolver().query(
                    CalendarContract.Events.CONTENT_URI,
                    proj,
                    CalendarContract.Events.TITLE + " = ? AND " + CalendarContract.Events.DTSTART + " = ?",
                    new String[]{event.getDetails(), Long.toString(start)},
                    null);
            return cursor.moveToFirst();
        }
        return false;
    }


    long getCalendarId(String acc) {
        Cursor cur = null;
        ContentResolver cr = getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = CalendarContract.Calendars.ACCOUNT_NAME + " = ?";
        String[] selectionArgs = new String[]{acc};
// Submit the query and get a Cursor object back.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            String[] EVENT_PROJECTION = new String[]{
                    CalendarContract.Calendars._ID,                           // 0
                    CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
                    CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
                    CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
            };

            cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
            if (cur.moveToFirst()) {
                return cur.getLong(0);
            }
            Log.i("CurCount", cur.getCount() + "");
        }
        return -1;
    }

    long insertCalendar(String acc, String inter, String disp) {

        Uri calUri = CalendarContract.Calendars.CONTENT_URI;
        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Calendars.ACCOUNT_NAME, acc);
        cv.put(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        cv.put(CalendarContract.Calendars.NAME, inter);
        cv.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, disp);
        //cv.put(CalendarContract.Calendars.CALENDAR_COLOR, yourColor);
        cv.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        cv.put(CalendarContract.Calendars.OWNER_ACCOUNT, true);
        cv.put(CalendarContract.Calendars.VISIBLE, 1);
        cv.put(CalendarContract.Calendars.SYNC_EVENTS, 1);

        calUri = calUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, acc)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL)
                .build();
        Uri result = this.getContentResolver().insert(calUri, cv);
        Log.i("Result", result.toString());
        return getCalendarId(acc);
    }



}
