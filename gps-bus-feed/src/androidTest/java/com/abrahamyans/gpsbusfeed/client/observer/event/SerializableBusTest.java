package com.abrahamyans.gpsbusfeed.client.observer.event;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.otto.Subscribe;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;


/**
 * @author Samvel Abrahamyan
 */
@RunWith(AndroidJUnit4.class)
public class SerializableBusTest {

    private final Context context = InstrumentationRegistry.getContext();


    @Test
    public void evBusSerializationTest() {
        Listener listener = new Listener();
    }


    public static class Listener implements Serializable {
        private static final long serialVersionUID = 7700827243119194721L;

        @Subscribe
        public void onEvent(LocationChangedEvent ev) {

        }
    }
}
