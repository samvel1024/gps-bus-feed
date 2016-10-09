package com.abrahamyans.gpsbusfeed.persist;

import com.abrahamyans.gpsbusfeed.scheduler.LocationTracker;

import org.junit.Test;

/**
 * @author Samvel Abrahamyan
 */

public class SerializationManagerTest {

    @Test
    public void trackerSerializationTest(){
        LocationTracker tracker = new LocationTracker.Builder().build();
        SerializationManager manager = SerializationManager.getInstance();
        manager.serialize(tracker);
        LocationTracker deserialized = manager.deserialize(LocationTracker.class);
    }


}
