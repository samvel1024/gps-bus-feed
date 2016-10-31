package com.abrahamyans.gpsbusfeedsample;

import com.abrahamyans.gpsbusfeed.AndroidModule;
import com.abrahamyans.gpsbusfeed.GpsBusFeedComponent;
import com.abrahamyans.gpsbusfeed.client.observer.ObserverModule;
import com.abrahamyans.gpsbusfeed.client.tracker.TrackerModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Samvel Abrahamyan
 */

@Component(modules = {TrackerModule.class, ObserverModule.class, AndroidModule.class})
@Singleton
public interface TrackerComponent extends GpsBusFeedComponent{
    void inject(MainActivity activity);
}
