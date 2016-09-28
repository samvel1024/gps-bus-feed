package com.abrahamyans.gpsbusfeed;

import android.content.Context;

import com.abrahamyans.gpsbusfeed.event.LocationAvailableEvent;
import com.abrahamyans.gpsbusfeed.filter.LocationEventFilter;
import com.abrahamyans.gpsbusfeed.location.DefaultLocationRequestFactory;
import com.abrahamyans.gpsbusfeed.location.LocationRequestFactory;
import com.abrahamyans.gpsbusfeed.preference.PreferenceManager;
import com.abrahamyans.gpsbusfeed.scheduler.RequestDate;
import com.abrahamyans.gpsbusfeed.scheduler.RequestTiming;
import com.abrahamyans.gpsbusfeed.scheduler.SingleRequestTiming;
import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Samvel Abrahamyan
 */

public class LocationTracker {

    private List<LocationEventFilter> filters;
    private RequestTiming timing;
    private LocationRequestFactory requestFactory;
    private PreferenceManager preferenceManager;

    public RequestDate getNextRequestDate(Date lastRequestDate){
        return timing.getNextLocationRequestDate(lastRequestDate);
    }

    public LocationRequest createLocationRequest(){
        return requestFactory.createLocationRequest();
    }

    public boolean isValidLocationEvent(LocationAvailableEvent ev){
        boolean isValid = true;
        for (LocationEventFilter filter: filters)
            isValid &= filter.shouldBroadcastLocation(ev);
        return isValid;
    }

    public void startTracker(){
        if(isTrackerEnabled())
            throw new IllegalStateException("The tracker is already enabled, consider checking with isTrackerEnabled");
        preferenceManager.setTrackingEnabled(true);
    }

    public void stopTracker(){
        if(!isTrackerEnabled())
            throw new IllegalStateException("The tracker is already disabled, consider checking with isTrackerEnabled");
    }

    public boolean isTrackerEnabled(){
        return preferenceManager.isTrackingEnabled();
    }

    public static class Builder {

        private RequestTiming timingStrategy;
        private List<LocationEventFilter> locationEventFilters = new ArrayList<>();
        private LocationRequestFactory requestFactory;
        private Context context;

        public Builder(Context context){
            this.context = context;
        }

        public Builder timingStrategy(RequestTiming timing){
            if (timing == null)
                throw new IllegalArgumentException("timing cannot be null");
            this.timingStrategy = timing;
            return this;
        }

        public Builder filter(LocationEventFilter locationEventFilter){
            if (locationEventFilter == null)
                throw new IllegalArgumentException("locationEventFilter cannot be null");
            this.locationEventFilters.add(locationEventFilter);
            return this;
        }

        private Builder requestFactory(LocationRequestFactory factory){
            if (requestFactory == null)
                throw new IllegalArgumentException("factory cannot be null");
            this.requestFactory = factory;
            return this;
        }

        public LocationTracker build(){
            LocationTracker locationTracker = new LocationTracker();
            locationTracker.filters = locationEventFilters;
            locationTracker.timing = timingStrategy == null ? new SingleRequestTiming() : timingStrategy;
            locationTracker.requestFactory = requestFactory == null ? new DefaultLocationRequestFactory() : requestFactory;
            locationTracker.preferenceManager = new PreferenceManager(context);
            return locationTracker;
        }
    }
}
