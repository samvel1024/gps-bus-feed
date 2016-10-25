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
    public TrackerRepository provideTrackerRepository(){
        return new TrackerRepository(context);
    }

    @Provides
    @Singleton
    public LocationTracker provideLocationTracker(TrackerRepository trackerRepository){
        return trackerRepository.getSerializedInstance();
    }

}
