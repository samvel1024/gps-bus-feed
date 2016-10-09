package com.abrahamyans.gpsbusfeed.event;

import android.location.Location;
import android.support.test.runner.AndroidJUnit4;

import com.abrahamyans.gpsbusfeed.persist.SerializationManager;
import com.squareup.otto.Subscribe;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.io.Serializable;
import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;


/**
 * @author Samvel Abrahamyan
 */
@RunWith(AndroidJUnit4.class)
public class GpsBusFeedTest {

    @Test
    public void evBusSerializationTest() {
        GpsBusFeed bus = GpsBusFeed.getInstance();
        Listener listener = Mockito.mock(Listener.class);
        bus.registerPermanent(listener);
        SerializationManager.getInstance().serialize(bus);
        GpsBusFeed deserialized = SerializationManager.getInstance().deserialize(GpsBusFeed.class);
        deserialized.onLocationChanged(new LocationChangedEvent(
                new Location("anything"),
                new Date()
        ));
        verify(listener).onEvent(any(LocationChangedEvent.class));
    }


    public static class Listener implements Serializable {
        @Subscribe
        public void onEvent(LocationChangedEvent ev) {

        }
    }
}
