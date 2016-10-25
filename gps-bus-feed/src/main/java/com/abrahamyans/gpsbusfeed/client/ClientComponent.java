package com.abrahamyans.gpsbusfeed.client;

import com.abrahamyans.gpsbusfeed.client.observer.ObserverModule;
import com.abrahamyans.gpsbusfeed.client.tracker.TrackerModule;
import com.abrahamyans.gpsbusfeed.scheduler.AlarmBroadcastReceiver;

import dagger.Component;

/**
 * @author Samvel Abrahamyan
 */

@Component(modules = {ObserverModule.class, TrackerModule.class})
public interface ClientComponent {
    void inject(AlarmBroadcastReceiver receiver);
}
