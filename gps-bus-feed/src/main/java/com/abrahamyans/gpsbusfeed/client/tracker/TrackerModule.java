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
    public TrackerConfigRepository provideTrackerRepository() {
        return new TrackerConfigRepository(context);
    }

    @Provides
    @Singleton
    public TrackerConfig provideTrackerConfig(TrackerConfigRepository trackerConfigRepository) {
        TrackerConfig config = trackerConfigRepository.getSerializedInstance();
        if (config != null) {
            return config;
        }
        return TrackerConfig.NO_CONFIG;
    }

}
