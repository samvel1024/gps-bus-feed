package com.abrahamyans.gpsbusfeed.client.observer;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.abrahamyans.gpsbusfeed.client.observer.event.GpsBusFeedErrorEvent;
import com.abrahamyans.gpsbusfeed.client.observer.event.LocationChangedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Samvel Abrahamyan
 */
public class SerializableBus implements Serializable {

    private static final long serialVersionUID = 4067049333336042083L;
    /**
     * Contains class names of all registered permanent listeners
     */
    private final List<String> permanentListeners = new ArrayList<>();
    private transient Bus bus;
    private transient Handler main;

    @Inject
    public SerializableBus() {
        super();
        this.bus = new Bus(ThreadEnforcer.MAIN);
        this.main = new Handler(Looper.getMainLooper());
    }

    public void onError(GpsBusFeedErrorEvent error) {
        postEventToMainThread(error);
    }

    public void onLocationChanged(LocationChangedEvent event) {
        postEventToMainThread(event);
    }

    private void postEventToMainThread(final Object event) {
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                bus.post(event);
            }
        });
    }

    private void runOnMainThread(final Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            main.post(new Runnable() {
                @Override
                public void run() {
                    runOnMainThread(runnable);
                }
            });
        }
    }

    public void registerPermanent(Serializable listener) {
        String className = listener.getClass().getName();
        if (!permanentListeners.contains(className)) {
            permanentListeners.add(listener.getClass().getName());
            subscribe(listener);
        }
    }

    public void subscribe(final Object listener) {
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                bus.register(listener);
            }
        });
    }

    public void unsubscribe(final Object listener) {
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                bus.unregister(listener);
            }
        });
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        main = new Handler(Looper.getMainLooper());
        bus = new Bus(ThreadEnforcer.MAIN);
        restorePermanentListeners();
    }

    private void restorePermanentListeners() {
        for (String className : permanentListeners) {
            try {
                subscribe(Class.forName(className).newInstance());
            } catch (Exception e) {
                throw new IllegalStateException("Could not instantiate class " + className, e);
            }
        }
        Log.d("SerializableListeners", permanentListeners.toString());

    }
}
