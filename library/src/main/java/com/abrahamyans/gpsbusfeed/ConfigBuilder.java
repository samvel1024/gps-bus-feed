package com.abrahamyans.gpsbusfeed;

import android.content.Context;

import com.abrahamyans.gpsbusfeed.client.tracker.TrackerConfig;
import com.abrahamyans.gpsbusfeed.client.tracker.filter.LocationEventFilter;
import com.abrahamyans.gpsbusfeed.client.tracker.request.DefaultLocationRequestFactory;
import com.abrahamyans.gpsbusfeed.client.tracker.time.RequestTiming;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Samvel Abrahamyan
 */

public class ConfigBuilder extends TrackerConfig.Builder {

    transient List<Class<? extends Serializable>> permanentListeners = new ArrayList<>();

    transient Context context;

    private ConfigBuilder(Context context) {
        super();
        this.context = context;
    }

    public static ConfigBuilder withContext(Context context){
        return new ConfigBuilder(context);
    }

    @Override
    public ConfigBuilder filter(LocationEventFilter locationEventFilter) {
        super.filter(locationEventFilter);
        return this;
    }

    @Override
    public ConfigBuilder timingStrategy(RequestTiming timing) {
        super.timingStrategy(timing);
        return this;
    }

    @Override
    public ConfigBuilder requestFactory(DefaultLocationRequestFactory factory) {
        super.requestFactory(factory);
        return this;
    }

    public ConfigBuilder permanentListener(Class<? extends Serializable> listenerClass){
        if (listenerClass == null)
            throw new IllegalArgumentException("listenerClass cannot be null");
        permanentListeners.add(listenerClass);
        return this;
    }

}