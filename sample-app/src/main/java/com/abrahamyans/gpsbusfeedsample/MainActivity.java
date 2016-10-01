package com.abrahamyans.gpsbusfeedsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.abrahamyans.gpsbusfeed.event.GpsBusFeed;
import com.abrahamyans.gpsbusfeed.event.LocationAvailableEvent;
import com.abrahamyans.gpsbusfeed.filter.LocationAccuracyEventFilter;
import com.abrahamyans.gpsbusfeed.location.DefaultLocationRequestFactory;
import com.abrahamyans.gpsbusfeed.scheduler.LocationTracker;
import com.abrahamyans.gpsbusfeed.scheduler.TrackerManager;
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

    public void onRequestLocation(View view) {
        if (!trackerManager.isTrackerEnabled()) {
            trackerManager.startTracker(
                    this,
                    LocationTracker.builder()
                            .requestFactory(DefaultLocationRequestFactory.create())
                            .timingStrategy(SingleRequestTiming.create())
                            .filter(LocationAccuracyEventFilter.withMaxRadius(500F))
                            .build()
            );
        }
    }

    @Subscribe
    public void onLocationUpdated(LocationAvailableEvent locationEvent) {
        ((TextView) findViewById(R.id.text)).setText(locationEvent.getLocation().toString());
    }

}
