package com.abrahamyans.gpsbusfeedsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.abrahamyans.gpsbusfeed.GpsBusFeed;
import com.abrahamyans.gpsbusfeed.event.LocationAvailableEvent;
import com.abrahamyans.gpsbusfeed.TrackerBuilder;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity {

    private GpsBusFeed feed = GpsBusFeed.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(feed.isPermissionGranted(this)){
            Toast.makeText(this, "Grant location permissions", Toast.LENGTH_SHORT).show();
        }
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
        if (!feed.isTrackingEnabled())
            feed.startTracker(this, new TrackerBuilder());
        else
            Toast.makeText(this, "Tracking is already enabled", Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onLocationUpdated(LocationAvailableEvent locationEvent){
        ((TextView) findViewById(R.id.text)).setText(locationEvent.getLocation().toString());
    }

}
