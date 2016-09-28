package com.abrahamyans.gpsbusfeed;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;

import com.abrahamyans.gpsbusfeed.error.GpsBusFeedError;
import com.abrahamyans.gpsbusfeed.filter.LocationFilter;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * @author Samvel Abrahamyan
 */
public class GpsBusFeed{

    private static final GpsBusFeed instance = new GpsBusFeed();

    private final Bus bus = new Bus(ThreadEnforcer.MAIN, "GpsBusFeed");

    private final Handler main = new Handler(Looper.getMainLooper());

    private TrackerBuilder currentConfig;

    private GpsBusFeed() {
        super();
    }

    public static GpsBusFeed getInstance() {
        return instance;
    }

    void onError(GpsBusFeedError error){
        bus.post(error);
    }

    void onLocationAvailable(LocationAvailableEvent event){
        boolean shouldPublish = true;
        for(LocationFilter filter: currentConfig.getLocationFilters()) {
            shouldPublish &= filter.shouldBroadcastLocation(event.getLocation());
        }
        if (shouldPublish) {
            postEventToBus(event);
        }
    }

    private void postEventToBus(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            bus.post(event);
        } else {
            main.post(new Runnable() {
                @Override
                public void run() {
                    postEventToBus(event);
                }
            });
        }
    }

    public void register(Object listener){
        bus.register(listener);
    }

    public void unregister(Object listener){
        bus.unregister(listener);
    }

    public void startTracker(Context context, TrackerBuilder config){
        if (isTrackingEnabled())
            throw new IllegalStateException("An instance of the tracker is already running, consider calling isTrackingEnabled before starting");
        this.currentConfig = config;
        context.startService(new Intent(context, LocationService.class));
    }


    public void stopTracker(){
        currentConfig = null;
    }

    public boolean isTrackingEnabled(){
        return currentConfig != null;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean isPermissionGranted(Context context){
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }

}
