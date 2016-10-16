package com.abrahamyans.gpsbusfeed.event;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.abrahamyans.gpsbusfeed.persist.SerializationManager;
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
public class GpsBusFeed implements Serializable {

    private static final long serialVersionUID = 4067049333336042083L;

    private static GpsBusFeed instance;

    private transient Bus bus = new Bus(ThreadEnforcer.MAIN, "GpsBusFeed");

    private transient Handler main = new Handler(Looper.getMainLooper());

    /**
     * Contains class names of all registered permanent listeners
     */
    private final List<String> permanentListeners = new ArrayList<>();

    private GpsBusFeed() {
        super();
    }

    public static GpsBusFeed getInstance(Context context) {
        if (instance != null)
            return instance;
        GpsBusFeed deserialized =  SerializationManager.getInstance().deserialize(context, GpsBusFeed.class);
        instance =  deserialized == null ? new GpsBusFeed() : deserialized;
        return instance;
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
        permanentListeners.add(listener.getClass().getName());
        register(listener);
    }

    public void register(final Object listener) {
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                bus.register(listener);
            }
        });
    }

    public void unregister(final Object listener) {
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
        bus = new Bus(ThreadEnforcer.MAIN, "GpsBusFeed");
        restorePermanentListeners();
    }

    private void restorePermanentListeners(){
        for (String className : permanentListeners) {
            try {
                register(Class.forName(className).newInstance());
            } catch (Exception e) {
                throw new IllegalStateException("Could not instantiate class " + className, e);
            }
        }
    }
}
