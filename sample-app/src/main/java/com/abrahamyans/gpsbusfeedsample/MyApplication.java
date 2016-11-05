package com.abrahamyans.gpsbusfeedsample;

import android.app.Application;

import com.abrahamyans.gpsbusfeed.AndroidModule;
import com.abrahamyans.gpsbusfeed.DaggerGpsBusFeedComponent;
import com.abrahamyans.gpsbusfeed.GpsBusFeedComponent;
import com.abrahamyans.gpsbusfeed.LocationTracker;
import com.abrahamyans.gpsbusfeed.TrackerAwareApplication;
import com.abrahamyans.gpsbusfeed.client.observer.ObserverModule;
import com.abrahamyans.gpsbusfeed.client.tracker.TrackerModule;

/**
 * @author Samvel Abrahamyan
 */

public class MyApplication extends Application implements TrackerAwareApplication {

    private GpsBusFeedComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerGpsBusFeedComponent
                .builder()
                .androidModule(new AndroidModule(this))
                .trackerModule(new TrackerModule(this))
                .observerModule(new ObserverModule(this))
                .build();
    }

    @Override
    public GpsBusFeedComponent getGpsBusFeedComponent() {
        return component;
    }

    public LocationTracker getTracker(){
        return component.getTracker();
    }
}
