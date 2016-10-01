package com.abrahamyans.gpsbusfeed.scheduler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.abrahamyans.gpsbusfeed.event.GpsBusFeed;
import com.abrahamyans.gpsbusfeed.event.GpsBusFeedErrorEvent;
import com.abrahamyans.gpsbusfeed.preference.PreferenceManager;
import com.squareup.otto.Subscribe;

import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */

public class TrackerManager {

    private static TrackerManager instance;

    private LocationTracker tracker;

    private PreferenceManager preferenceManager;

    private TrackerManager(Context context){
        this.preferenceManager = new PreferenceManager(context);
        GpsBusFeed.getInstance().register(new EventInterceptor());
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

    private static class EventInterceptor {

        @Subscribe
        public void onError(GpsBusFeedErrorEvent err){
            TrackerManager.instance.stopTracker();
            Log.e("DefaultErrorListener", "Stopped tracker because of error " + err.getStatus());
        }

    }
}
