package com.abrahamyans.gpsbusfeed;

import android.app.Application;

import com.abrahamyans.gpsbusfeed.client.observer.ObserverModule;
import com.abrahamyans.gpsbusfeed.client.tracker.TrackerModule;

/**
 * @author Samvel Abrahamyan
 */

public class TrackerApplication extends Application implements TrackerAwareApplication {

    private GpsBusFeedComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        this.component = DaggerGpsBusFeedComponent.builder()
                .androidModule(new AndroidModule(this))
                .observerModule(new ObserverModule(this))
                .trackerModule(new TrackerModule(this))
                .build();
    }

    @Override
    public GpsBusFeedComponent getGpsBusFeedComponent() {
        return component;
    }
}
