package com.abrahamyans.gpsbusfeedsample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.abrahamyans.gpsbusfeed.event.GpsBusFeed;
import com.abrahamyans.gpsbusfeed.event.LocationChangedEvent;
import com.abrahamyans.gpsbusfeed.scheduler.LocationTracker;
import com.abrahamyans.gpsbusfeed.scheduler.TrackerManager;
import com.abrahamyans.gpsbusfeed.time.EqualIntervalTiming;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private GpsBusFeed feed;
    private TrackerManager trackerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feed = GpsBusFeed.getInstance(getApplicationContext());
        trackerManager = TrackerManager.getInstance(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        feed.unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        feed.register(this);
    }


    public void onTurnOnTracking(View view) {
        if (!trackerManager.isTrackerEnabled()) {
            trackerManager.startTracker(
                    this,
                    new LocationTracker.Builder()
                            .timingStrategy(new EqualIntervalTiming(5000)).build()
            );
            feed.registerPermanent(new LocationEventListener());
        } else {
            Toast.makeText(this, "Tracker is already enabled", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void onLocationUpdated(LocationChangedEvent locationEvent) {
        Toast.makeText(this, "Received location event", Toast.LENGTH_SHORT).show();
    }


    public void onUpdateList(View view) {
        List<String> list = getLocationHistory();
        ListView listView = (ListView) findViewById(R.id.location_history);
        listView.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                list
        ));
    }

    @SuppressWarnings("unchecked")
    private List<String> getLocationHistory() {
        SharedPreferences prefs = this.getSharedPreferences("location_history", Context.MODE_PRIVATE);
        Map<String, String> history = (Map<String, String>) prefs.getAll();
        List<String> historyList = new ArrayList<>();
        List<String> keyList = new ArrayList<>();

        for(String k: history.keySet())
            keyList.add(k);

        Collections.sort(keyList);

        for (String k : keyList) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(Long.parseLong(k));
            Date date = cal.getTime();
            historyList.add(date.toString() + " " + prefs.getString(k, "NULL"));
        }
        return historyList;
    }

    public void onClearHistory(View view) {
        SharedPreferences prefs = this.getSharedPreferences("location_history", Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }
}
