package com.abrahamyans.gpsbusfeedsample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.abrahamyans.gpsbusfeed.LocationTracker;
import com.abrahamyans.gpsbusfeed.client.observer.event.LocationChangedEvent;
import com.abrahamyans.gpsbusfeed.client.tracker.time.EqualIntervalTiming;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    LocationTracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((MyApplication) this.getApplicationContext()).getGpsBusFeedComponent().inject(this);

    }

    @Override
    protected void onStop() {
        tracker.unsubscribe(this);
        super.onStop();
    }

    @Override
    protected void onStart() {
        tracker.subscribe(this);
        super.onStart();
    }


    public void onTurnOnTracking(View view) {
        if (!tracker.isTrackerRunning()) {
            //TODO think of clever builder subclassing pattern
//            tracker.startTracker(
//                    this,
//                    new LocationTracker.ConfigBuilder()
//                            .timingStrategy(new EqualIntervalTiming(5000))
//
//
//            );
            tracker.subscribePermanent(new LocationEventListener());
        } else {
            Toast.makeText(this, "Tracker is already running", Toast.LENGTH_SHORT).show();
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

        for (String k : history.keySet())
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
