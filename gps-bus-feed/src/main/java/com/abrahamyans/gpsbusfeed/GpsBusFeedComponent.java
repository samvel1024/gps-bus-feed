package com.abrahamyans.gpsbusfeed;

import com.abrahamyans.gpsbusfeed.client.observer.ObserverModule;
import com.abrahamyans.gpsbusfeed.client.tracker.TrackerModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Samvel Abrahamyan
 */
@Component(modules = {ObserverModule.class, TrackerModule.class, AndroidModule.class})
@Singleton
public interface GpsBusFeedComponent {
    void inject(LocationService service);
    void inject(AlarmBroadcastReceiver receiver);
    LocationTracker getTracker();
}
