package com.abrahamyans.gpsbusfeed;

import android.content.SharedPreferences;

/**
 * @author Samvel Abrahamyan
 */

public class PreferenceRepository {

    private SharedPreferences preferences;

    public PreferenceRepository(SharedPreferences preferences){
        this.preferences = preferences;
    }

    public boolean isTrackerRunning(){
        return preferences.getBoolean("tracker_enabled", false);
    }

    public void setTrackerRunningState(boolean state){
        preferences.edit().putBoolean("tracker_enabled", state).apply();
    }

}
