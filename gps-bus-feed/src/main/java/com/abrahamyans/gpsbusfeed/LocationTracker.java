package com.abrahamyans.gpsbusfeed;

import android.content.Intent;

import com.abrahamyans.gpsbusfeed.client.observer.ObserverRepository;
import com.abrahamyans.gpsbusfeed.client.observer.SerializableBus;
import com.abrahamyans.gpsbusfeed.client.tracker.TrackerConfig;
import com.abrahamyans.gpsbusfeed.client.tracker.SerializableConfigRepository;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Samvel Abrahamyan
 */
@Singleton
public class LocationTracker {

    private SerializableConfigRepository trackerConfigRepository;
    private ObserverRepository observerRepository;
    private TrackerConfig trackerConfig;
    private PreferenceRepository preferenceRepository;
    private SerializableBus bus;

    @Inject
    public LocationTracker(
            ObserverRepository observerRepository,
            SerializableConfigRepository trackerConfigRepository,
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
        preferenceRepository.setTrackerNotRunning();
        trackerConfigRepository.delete();
        observerRepository.delete();
    }

    public void startTracker(ConfigBuilder configBuilder) {
        if (isTrackerRunning())
            throw new IllegalStateException("Tracker is already running");
        this.trackerConfig = configBuilder.build();
        trackerConfigRepository.save(trackerConfig);
        registerPermanentListeners(configBuilder);
        observerRepository.save(bus);
        preferenceRepository.setTrackerRunning();
        configBuilder.context.sendBroadcast(new Intent(configBuilder.context, AlarmBroadcastReceiver.class));
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


}
