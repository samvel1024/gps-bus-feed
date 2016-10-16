package com.abrahamyans.gpsbusfeed.event;

import android.content.Context;
import android.location.Location;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.abrahamyans.gpsbusfeed.persist.SerializationManager;
import com.squareup.otto.Subscribe;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Samvel Abrahamyan
 */
@RunWith(AndroidJUnit4.class)
public class GpsBusFeedTest {

    private final Context context = InstrumentationRegistry.getContext();


    @Test
    public void evBusSerializationTest() {
        GpsBusFeed bus = GpsBusFeed.getInstance(context);
        Listener listener = new Listener();
        bus.registerPermanent(listener);
        SerializationManager.getInstance().serialize(context, bus);
        GpsBusFeed deserialized = SerializationManager.getInstance().deserialize(context, GpsBusFeed.class);
        deserialized.onLocationChanged(new LocationChangedEvent(
                context,
                new Location("anything"),
                new Date()
        ));
    }


    public static class Listener implements Serializable {
        private static final long serialVersionUID = 7700827243119194721L;

        @Subscribe
        public void onEvent(LocationChangedEvent ev) {

        }
    }
}
