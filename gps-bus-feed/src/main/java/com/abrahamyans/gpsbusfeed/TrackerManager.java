package com.abrahamyans.gpsbusfeed;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.abrahamyans.gpsbusfeed.event.GpsBusFeedError;
import com.abrahamyans.gpsbusfeed.preference.PreferenceManager;
import com.squareup.otto.Subscribe;

/**
 * @author Samvel Abrahamyan
 */

public class TrackerManager {

    private static TrackerManager instance;

    private LocationTracker tracker;

    private PreferenceManager preferenceManager;

    private TrackerManager(Context context){
        if(context instanceof Activity)
            throw new IllegalStateException("Do not pass instance of activity as context, use getApplicationContext");
        this.preferenceManager = new PreferenceManager(context);
        GpsBusFeed.getInstance().register(new TrackerErrorListener());
    }

    public static TrackerManager getInstance(Context context){
        if(instance == null)
            instance = new TrackerManager(context);
        return instance;
    }

    public void startTracker(LocationTracker tracker){
        if (tracker == null)
            throw new IllegalArgumentException("tracker cannot be null");
        if(isTrackerEnabled())
            throw new IllegalStateException("The tracker is already enabled, consider checking with isTrackerEnabled");
        this.tracker = tracker;
        preferenceManager.setTrackingEnabled(true);
    }


    public void stopTracker(){
        if(!isTrackerEnabled())
            throw new IllegalStateException("The tracker is already disabled, consider checking with isTrackerEnabled");
        preferenceManager.setTrackingEnabled(false);
        this.tracker = null;
    }

    public boolean isTrackerEnabled(){
        return preferenceManager.isTrackingEnabled();
    }

    LocationTracker getRunningTracker(){
        if (tracker == null)
            throw new IllegalStateException("Requested tracker instance but none is running currently");
        return tracker;
    }

    private static class TrackerErrorListener{

        @Subscribe
        public void onError(GpsBusFeedError err){
            TrackerManager.instance.stopTracker();
            Log.e("DefaultErrorListener", "Stopped tracker because of error " + err.getStatus());
        }

    }
}
