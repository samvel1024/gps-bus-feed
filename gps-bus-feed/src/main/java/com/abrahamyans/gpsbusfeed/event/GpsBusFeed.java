package com.abrahamyans.gpsbusfeed.event;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Samvel Abrahamyan
 */
public class GpsBusFeed implements Serializable{

    private static final GpsBusFeed instance = new GpsBusFeed();

    private transient final Bus bus = new Bus(ThreadEnforcer.MAIN, "GpsBusFeed");

    private transient final Handler main = new Handler(Looper.getMainLooper());

    private final List<String> permanentListeners = new ArrayList<>();

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

    public void registerPermanent(Serializable listener){
        permanentListeners.add(listener.getClass().getName());
        register(listener);
    }

    public void register(Object listener) {
        bus.register(listener);
    }

    public void unregister(Object listener) {
        bus.unregister(listener);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        for (String className: permanentListeners){
            try {
                register(Class.forName(className).newInstance());
            } catch (Exception e) {
                throw new IllegalStateException("Could not instantiate class " + className, e);
            }
        }
    }
}
