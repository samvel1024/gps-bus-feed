package com.abrahamyans.gpsbusfeed;

import android.content.Context;
import android.content.Intent;

import com.abrahamyans.gpsbusfeed.client.observer.ObserverRepository;
import com.abrahamyans.gpsbusfeed.client.observer.SerializableBus;
import com.abrahamyans.gpsbusfeed.client.tracker.TrackerConfig;
import com.abrahamyans.gpsbusfeed.client.tracker.TrackerConfigRepository;

import java.io.Serializable;
import java.util.Date;

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

    public void startTracker(Context context, TrackerConfig config) {
        if (isTrackerRunning())
            throw new IllegalStateException("Tracker is already running");
        this.trackerConfig = config;
        trackerConfigRepository.save(config);
        preferenceRepository.setTrackerRunningState(true);
        context.sendBroadcast(new Intent(context, AlarmBroadcastReceiver.class));
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

    public void subscribePermanent(Serializable listener) {
        bus.registerPermanent(listener);
        observerRepository.save(bus);

    }
}
