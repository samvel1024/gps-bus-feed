package com.abrahamyans.gpsbusfeed;

import android.content.Context;
import android.content.Intent;

import com.abrahamyans.gpsbusfeed.client.observer.ObserverRepository;
import com.abrahamyans.gpsbusfeed.client.observer.SerializableBus;
import com.abrahamyans.gpsbusfeed.client.tracker.TrackerConfig;
import com.abrahamyans.gpsbusfeed.client.tracker.TrackerConfigRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Samvel Abrahamyan
 */

public class LocationTracker implements Serializable {

    private TrackerConfigRepository trackerConfigRepository;
    private ObserverRepository observerRepository;
    private TrackerConfig trackerConfig;
    private PreferenceRepository preferenceRepository;
    private SerializableBus bus;

    public LocationTracker(
            ObserverRepository observerRepository,
            TrackerConfigRepository trackerConfigRepository,
            SerializableBus bus, TrackerConfig trackerConfig,
            PreferenceRepository preferenceRepository
    ) {
        this.observerRepository = observerRepository;
        this.trackerConfig = trackerConfig;
        this.preferenceRepository = preferenceRepository;
        this.bus = bus;
        this.trackerConfigRepository = trackerConfigRepository;
    }

    public TrackerConfig getRunningTrackerConfig() {
        if (!isTrackerRunning())
            throw new IllegalStateException("Tracker is not running");
        return trackerConfig;
    }

    public boolean isTrackerRunning() {
        return preferenceRepository.isTrackerRunning();
    }

    public void stopTracker() {
        if (!isTrackerRunning())
            throw new IllegalStateException("Tracker is not running");
        preferenceRepository.setTrackerRunningState(false);
        trackerConfigRepository.delete();
    }

    public void startTracker(Context context, ConfigBuilder configBuilder) {
        if (isTrackerRunning())
            throw new IllegalStateException("Tracker is already running");
        this.trackerConfig = configBuilder.build();
        trackerConfigRepository.save(trackerConfig);
        registerPermanentListeners(configBuilder);
        observerRepository.save(bus);

        preferenceRepository.setTrackerRunningState(true);
        context.sendBroadcast(new Intent(context, AlarmBroadcastReceiver.class));
    }

    private void registerPermanentListeners(ConfigBuilder builder){
        for (Class<? extends Serializable> listenerClass: builder.permanentListeners){
            try {
                bus.registerPermanent(listenerClass.newInstance());
            } catch (Exception e) {
                throw new IllegalStateException(listenerClass.getName() + " should have public empty constructor");
            }
        }
    }

    public Date getLastRequestDate() {
        return preferenceRepository.getLastRequestDate();
    }

    public void setLastRequestDate(Date date) {
        preferenceRepository.setLastRequestDate(date);
    }

    public void subscribe(Object listener) {
        bus.subscribe(listener);
    }

    public void unsubscribe(Object listener) {
        bus.unsubscribe(listener);
    }

    public static class ConfigBuilder extends TrackerConfig.Builder {

        private transient List<Class<? extends Serializable>> permanentListeners = new ArrayList<>();

        public ConfigBuilder() {
            super();
        }

        public ConfigBuilder permanentListener(Class<? extends Serializable> listenerClass){
            if (listenerClass == null)
                throw new IllegalArgumentException("listenerClass cannot be null");
            permanentListeners.add(listenerClass);
            return this;
        }

    }
}
