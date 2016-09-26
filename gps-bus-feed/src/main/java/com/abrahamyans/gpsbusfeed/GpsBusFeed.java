package com.abrahamyans.gpsbusfeed;

import android.os.Handler;
import android.os.Looper;

import com.abrahamyans.gpsbusfeed.builder.LocationTrackingStrategyBuilder;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * @author Samvel Abrahamyan
 */
public class GpsBusFeed{

    private static final GpsBusFeed instance = new GpsBusFeed();

    private final Bus bus = new Bus(ThreadEnforcer.MAIN, "GpsBusFeed");

    private final Handler main = new Handler(Looper.getMainLooper());

    private GpsBusFeed() {
        super();
    }

    public static GpsBusFeed getInstance() {
        return instance;
    }

    void postEvent(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            bus.post(event);
        } else {
            main.post(new Runnable() {
                @Override
                public void run() {
                    postEvent(event);
                }
            });
        }
    }

    public void getImmediateLocation(){

    }


    public void startLocationTracking(LocationTrackingStrategyBuilder strategyBuilder){

    }

    public boolean isTrackingEnabled(){
        return false;
    }


}
