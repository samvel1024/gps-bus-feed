package com.abrahamyans.gpsbusfeed.client.tracker;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.abrahamyans.gpsbusfeed.client.tracker.request.DefaultLocationRequestFactory;
import com.abrahamyans.gpsbusfeed.client.tracker.time.EqualIntervalTiming;
import com.abrahamyans.gpsbusfeed.client.tracker.time.HoursWindowTiming;
import com.abrahamyans.gpsbusfeed.client.tracker.time.TimeInDay;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Samvel Abrahamyan
 */
@RunWith(AndroidJUnit4.class)
public class TrackerConfigRepositoryTest {

    private final Context context = InstrumentationRegistry.getContext();

    @Test
    public void testLocationTrackerSerialization() {
        SerializableConfigRepository repository = new SerializableConfigRepository(context);

        TrackerConfig config = new TrackerConfig.Builder()
                .requestFactory(new DefaultLocationRequestFactory())
                .timingStrategy(new HoursWindowTiming(new TimeInDay(10, 20), new TimeInDay(22, 20), new EqualIntervalTiming(5000)))
                .build();

        repository.save(config);
        TrackerConfig deserializedConfig = repository.getInstance();

        Assert.assertNotSame(deserializedConfig, config);
        Assert.assertNotNull(deserializedConfig);

    }

}
