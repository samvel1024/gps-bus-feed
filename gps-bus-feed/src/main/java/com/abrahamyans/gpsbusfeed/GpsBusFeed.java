package com.abrahamyans.gpsbusfeed;

import android.os.Handler;
import android.os.Looper;

import com.abrahamyans.gpsbusfeed.event.GpsBusFeedError;
import com.abrahamyans.gpsbusfeed.event.LocationAvailableEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * @author Samvel Abrahamyan
 */
public class GpsBusFeed {

    private static final GpsBusFeed instance = new GpsBusFeed();

    private final Bus bus = new Bus(ThreadEnforcer.MAIN, "GpsBusFeed");

    private final Handler main = new Handler(Looper.getMainLooper());


    private GpsBusFeed() {
        super();
    }

    public static GpsBusFeed getInstance() {
        return instance;
    }

    void onError(GpsBusFeedError error) {
        bus.post(error);
    }

    void onLocationAvailable(LocationAvailableEvent event) {
        postEventToBus(event);
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

    public void register(Object listener) {
        bus.register(listener);
    }

    public void unregister(Object listener) {
        bus.unregister(listener);
    }
}
