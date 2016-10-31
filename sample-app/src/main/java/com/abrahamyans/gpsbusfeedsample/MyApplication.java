package com.abrahamyans.gpsbusfeedsample;

import android.app.Application;

import com.abrahamyans.gpsbusfeed.AndroidModule;
import com.abrahamyans.gpsbusfeed.TrackerAwareApplication;
import com.abrahamyans.gpsbusfeed.client.observer.ObserverModule;
import com.abrahamyans.gpsbusfeed.client.tracker.TrackerModule;

/**
 * @author Samvel Abrahamyan
 */

public class MyApplication extends Application implements TrackerAwareApplication {

    private TrackerComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerTrackerComponent
                .builder()
                .androidModule(new AndroidModule(this))
                .trackerModule(new TrackerModule(this))
                .observerModule(new ObserverModule(this))
                .build();
    }

    @Override
    public TrackerComponent getGpsBusFeedComponent() {
        return component;
    }
}
