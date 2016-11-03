package com.abrahamyans.gpsbusfeed.client.observer;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.abrahamyans.gpsbusfeed.client.observer.event.LocationChangedEvent;
import com.squareup.otto.Subscribe;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Samvel Abrahamyan
 */
@RunWith(AndroidJUnit4.class)
public class ObserverRepositoryTest {

    private final Context context = InstrumentationRegistry.getContext();


    @Test
    public void evBusSerializationTest() {
        //Run test on UI thread
        new Handler(context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ObserverRepository repository = new ObserverRepository(context);

                SerializableBus bus = new SerializableBus();
                bus.registerPermanent(new TestListener());
                repository.save(bus);

                SerializableBus deserializedBus = repository.getSerializedInstance();
                TestableLocationEvent ev = new TestableLocationEvent(context, new Location("Test"), new Date());
                deserializedBus.onLocationChanged(ev);

                Assert.assertTrue(ev.isReceived);
                Assert.assertNotNull(deserializedBus);
                Assert.assertNotSame(deserializedBus, bus);
            }
        });
    }

    private static class TestableLocationEvent extends LocationChangedEvent {

        private boolean isReceived = false;

        public TestableLocationEvent(Context ctx, Location location, Date date) {
            super(ctx, location, date);
        }

        public void onReceived(){
            isReceived = true;
        }

    }


    public static class TestListener implements Serializable {
        private static final long serialVersionUID = 7700827243119194721L;

        @Subscribe
        public void onEvent(TestableLocationEvent ev) {
            ev.onReceived();
        }
    }
}
