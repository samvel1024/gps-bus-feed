package com.abrahamyans.gpsbusfeedsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.abrahamyans.gpsbusfeed.GpsBusFeed;
import com.abrahamyans.gpsbusfeed.LocationAvailableEvent;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity {

    private GpsBusFeed bus = GpsBusFeed.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);
    }

    public void onRequestLocation(View view) {
        bus.getImmediateLocation(this);
    }

    @Subscribe
    public void onLocationUpdated(LocationAvailableEvent locationEvent){
        ((TextView) findViewById(R.id.text)).setText(locationEvent.getLocation().toString());
    }

}
