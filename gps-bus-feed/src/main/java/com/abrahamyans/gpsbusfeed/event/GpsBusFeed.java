package com.abrahamyans.gpsbusfeed.event;

import android.os.Handler;
import android.os.Looper;

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

    public void onError(GpsBusFeedErrorEvent error) {
        postEventToMainThread(error);
    }

    public void onLocationChanged(LocationChangedEvent event) {
        postEventToMainThread(event);
    }

    private void postEventToMainThread(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            bus.post(event);
        } else {
            main.post(new Runnable() {
                @Override
                public void run() {
                    postEventToMainThread(event);
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
