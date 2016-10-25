package com.abrahamyans.gpsbusfeed.scheduler;

import android.app.Application;

import com.abrahamyans.gpsbusfeed.client.ClientComponent;
import com.abrahamyans.gpsbusfeed.client.DaggerClientComponent;
import com.abrahamyans.gpsbusfeed.client.observer.ObserverModule;
import com.abrahamyans.gpsbusfeed.client.tracker.TrackerModule;

/**
 * @author Samvel Abrahamyan
 */
public class TrackerApplication extends Application{

    private ClientComponent clientComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.clientComponent = DaggerClientComponent
                .builder()
                .observerModule(new ObserverModule(this))
                .trackerModule(new TrackerModule(this))
                .build();
    }

    public ClientComponent getClientComponent() {
        return clientComponent;
    }
}
