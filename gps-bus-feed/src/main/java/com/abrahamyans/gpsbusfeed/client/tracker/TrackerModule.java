package com.abrahamyans.gpsbusfeed.client.tracker;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Samvel Abrahamyan
 */
@Module
public class TrackerModule {

    private Context context;

    public TrackerModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public SerializableConfigRepository provideTrackerRepository() {
        return new SerializableConfigRepository(context);
    }

    @Provides
    @Singleton
    public TrackerConfig provideTrackerConfig(SerializableConfigRepository trackerConfigRepository) {
        TrackerConfig config = trackerConfigRepository.getInstance();
        if (config != null) {
            return config;
        }
        return TrackerConfig.NO_CONFIG;
    }

}
