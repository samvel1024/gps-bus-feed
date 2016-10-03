package com.abrahamyans.gpsbusfeedsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.abrahamyans.gpsbusfeed.event.GpsBusFeed;
import com.abrahamyans.gpsbusfeed.event.LocationChangedEvent;
import com.abrahamyans.gpsbusfeed.filter.LocationAccuracyEventFilter;
import com.abrahamyans.gpsbusfeed.location.DefaultLocationRequestFactory;
import com.abrahamyans.gpsbusfeed.scheduler.LocationTracker;
import com.abrahamyans.gpsbusfeed.scheduler.TrackerManager;
import com.abrahamyans.gpsbusfeed.time.EqualIntervalTiming;
import com.abrahamyans.gpsbusfeed.time.SingleRequestTiming;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity {

    private GpsBusFeed feed;
    private TrackerManager trackerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feed = GpsBusFeed.getInstance();
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

    public void onRequestLocationOnce(View view) {
        if (!trackerManager.isTrackerEnabled()) {
            trackerManager.startTracker(
                    this,
                    new LocationTracker.Builder()
                            .requestFactory(new DefaultLocationRequestFactory())
                            .timingStrategy(new SingleRequestTiming())
                            .filter(new LocationAccuracyEventFilter(500F))
                            .build()
            );
            ((TextView) findViewById(R.id.text)).setText("Waiting...");
        } else {
            Toast.makeText(this, "Tracker is already enabled", Toast.LENGTH_SHORT).show();
        }
    }

    public void onTurnOnTracking(View view) {
        if (!trackerManager.isTrackerEnabled()) {
            trackerManager.startTracker(
                    this,
                    new LocationTracker.Builder()
                            .timingStrategy(new EqualIntervalTiming(5000)).build()
            );
            ((TextView) findViewById(R.id.text)).setText("Waiting...");
        }else {
            Toast.makeText(this, "Tracker is already enabled", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void onLocationUpdated(LocationChangedEvent locationEvent) {
        ((TextView) findViewById(R.id.text)).setText(locationEvent.getLocation().toString());
    }

}
