package com.abrahamyans.gpsbusfeedsample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.abrahamyans.gpsbusfeed.ConfigBuilder;
import com.abrahamyans.gpsbusfeed.LocationTracker;
import com.abrahamyans.gpsbusfeed.client.observer.event.LocationChangedEvent;
import com.abrahamyans.gpsbusfeed.client.tracker.filter.LocationAccuracyEventFilter;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.abrahamyans.gpsbusfeed.ConfigBuilder.withContext;
import static com.abrahamyans.gpsbusfeed.client.tracker.time.EqualIntervalTiming.onEveryMillis;

public class MainActivity extends AppCompatActivity {

    private LocationTracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.tracker = ((MyApplication) this.getApplicationContext()).getTracker();
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


    public void onTurnOnTracker(View view) {
        if (!tracker.isTrackerRunning()) {
            ConfigBuilder trackerConfig = withContext(this)
                            .timingStrategy(onEveryMillis(5000))
                            .filter(new LocationAccuracyEventFilter(500))
                            .permanentListener(LocationEventListener.class);
            tracker.startTracker(trackerConfig);
            Toast.makeText(this, "Tracker started", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Tracker is already running", Toast.LENGTH_SHORT).show();
        }
    }

    public void onTurnOffTracker(View v){
        if (tracker.isTrackerRunning()){
            tracker.stopTracker();
            Toast.makeText(this, "Tracker stopped", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Tracker is not running", Toast.LENGTH_SHORT).show();
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
