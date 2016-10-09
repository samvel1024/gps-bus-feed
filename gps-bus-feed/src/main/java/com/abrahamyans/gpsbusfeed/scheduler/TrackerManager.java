package com.abrahamyans.gpsbusfeed.scheduler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.abrahamyans.gpsbusfeed.persist.PreferenceManager;
import com.abrahamyans.gpsbusfeed.persist.SerializationManager;

import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */

public class TrackerManager {

    private static final String TAG = "TrackerManager";

    private static TrackerManager instance;

    private LocationTracker tracker;

    private PreferenceManager preferenceManager;

    private TrackerManager(Context context){
        this.preferenceManager = new PreferenceManager(context);
        if (preferenceManager.isTrackingEnabled()){
            tracker = SerializationManager.getInstance().deserialize(context, LocationTracker.class);
        }
    }

    public static TrackerManager getInstance(Context context){
        if(instance == null)
            instance = new TrackerManager(context);
        return instance;
    }

    public void startTracker(Context context, LocationTracker tracker){
        if (tracker == null)
            throw new IllegalArgumentException("tracker cannot be null");
        if(isTrackerEnabled())
            throw new IllegalStateException("The tracker is already enabled, consider checking with isTrackerEnabled");
        this.tracker = tracker;
        preferenceManager.setTrackingEnabled(true);
        context.sendBroadcast(new Intent(context, AlarmBroadcastReceiver.class));
        Log.d(TAG, "Started tracker");
    }


    public void stopTracker(){
        if(!isTrackerEnabled())
            throw new IllegalStateException("The tracker is already disabled, consider checking with isTrackerEnabled");
        preferenceManager.setTrackingEnabled(false);
        this.tracker = null;
        Log.d(TAG, "Stopped tracker");
    }

    public boolean isTrackerEnabled(){
        return preferenceManager.isTrackingEnabled();
    }

    public Date getLastLocationRequestDate(){
        return preferenceManager.getLastLocationRequestDate();
    }

    void saveNextRequestDate(Date date){
        preferenceManager.updateLastLocationRequestDate(date);
    }

    public LocationTracker getRunningTracker(){
        if (tracker == null)
            throw new IllegalStateException("Requested tracker instance but none is running currently");
        return tracker;
    }

}
