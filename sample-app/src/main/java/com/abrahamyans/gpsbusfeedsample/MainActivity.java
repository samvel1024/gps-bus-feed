package com.abrahamyans.gpsbusfeedsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.abrahamyans.gpsbusfeed.GpsBusFeed;
import com.abrahamyans.gpsbusfeed.LocationTracker;
import com.abrahamyans.gpsbusfeed.TrackerManager;
import com.abrahamyans.gpsbusfeed.event.LocationAvailableEvent;
import com.abrahamyans.gpsbusfeed.filter.LocationAccuracyEventFilter;
import com.abrahamyans.gpsbusfeed.location.DefaultLocationRequestFactory;
import com.abrahamyans.gpsbusfeed.scheduler.SingleRequestTiming;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity {

    private final GpsBusFeed feed = GpsBusFeed.getInstance();
    private final TrackerManager trackerManager = TrackerManager.getInstance(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void onRequestLocation(View view) {
        if (trackerManager.isTrackerEnabled()) {
            trackerManager.startTracker(
                    new LocationTracker.Builder()
                            .timingStrategy(new SingleRequestTiming())
                            .filter(new LocationAccuracyEventFilter(500F))
                            .requestFactory(new DefaultLocationRequestFactory())
                            .build()
            );
        }

    }

    @Subscribe
    public void onLocationUpdated(LocationAvailableEvent locationEvent) {
        ((TextView) findViewById(R.id.text)).setText(locationEvent.getLocation().toString());
    }

}
