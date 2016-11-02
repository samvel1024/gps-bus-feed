package com.abrahamyans.gpsbusfeed.client.observer;

import android.content.Context;
import android.location.Location;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.abrahamyans.gpsbusfeed.client.observer.event.LocationChangedEvent;
import com.squareup.otto.Subscribe;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Samvel Abrahamyan
 */
@RunWith(AndroidJUnit4.class)
public class ObserverRepositoryTest extends TestCase {

    private final Context context = InstrumentationRegistry.getContext();


    @Test
    public void evBusSerializationTest() {
        ObserverRepository repository = new ObserverRepository(context);

        SerializableBus bus = new SerializableBus();
        bus.registerPermanent(new Listener());

        repository.save(bus);
        SerializableBus deserializedBus = repository.getSerializedInstance();

        Assert.assertNotNull(deserializedBus);
        Assert.assertNotSame(deserializedBus, bus);

        deserializedBus.onLocationChanged(new LocationChangedEvent(context, new Location("Test"), new Date()));


    }


    public static class Listener implements Serializable {
        private static final long serialVersionUID = 7700827243119194721L;

        @Subscribe
        public void onEvent(LocationChangedEvent ev) {
            System.out.println("qwer");
        }
    }
}
