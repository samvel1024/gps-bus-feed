package com.abrahamyans.gpsbusfeed;

import android.content.Context;
import android.content.SharedPreferences;

import com.abrahamyans.gpsbusfeed.client.tracker.TrackerConfigRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Samvel Abrahamyan
 */
@Module
public class AndroidModule {

    private Context context;

    public AndroidModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    public SharedPreferences providePreferences(){
        return context.getSharedPreferences("gps_bus_feed", Context.MODE_PRIVATE);
    }


    @Provides
    @Singleton
    public PreferenceRepository providePreferenceRepository(SharedPreferences preferences){
        return new PreferenceRepository(preferences);
    }

    @Provides
    @Singleton
    public LocationTracker provideLocationTracker(TrackerConfigRepository configRepository, PreferenceRepository preferenceRepository){
        return new LocationTracker(configRepository, preferenceRepository);
    }
}
