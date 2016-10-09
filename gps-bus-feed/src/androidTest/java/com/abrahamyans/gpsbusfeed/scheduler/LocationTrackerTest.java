package com.abrahamyans.gpsbusfeed.scheduler;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.abrahamyans.gpsbusfeed.persist.SerializationManager;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Samvel Abrahamyan
 */
@RunWith(AndroidJUnit4.class)
public class LocationTrackerTest {

    private final Context context = InstrumentationRegistry.getContext();

    @Test
    public void testLocationTrackerSerialization(){
        //Default one time request
        LocationTracker tracker = new LocationTracker.Builder().build();
        SerializationManager.getInstance().serialize(context, tracker);
        LocationTracker trackerDeserialized = SerializationManager.getInstance().deserialize(context, LocationTracker.class);
        Assert.assertNotNull(trackerDeserialized);
    }

}
