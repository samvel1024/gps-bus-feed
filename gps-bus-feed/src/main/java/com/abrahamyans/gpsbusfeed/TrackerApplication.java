package com.abrahamyans.gpsbusfeed;

import android.app.Application;

/**
 * @author Samvel Abrahamyan
 */

public class TrackerApplication extends Application implements TrackerAwareApplication {

    private GpsBusFeedComponent gpsBusFeedComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        gpsBusFeedComponent = DaggerGpsBusFeedComponent.builder().build();
    }

    public GpsBusFeedComponent getGpsBusFeedComponent() {
        return null;
    }
}
