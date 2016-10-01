package com.abrahamyans.gpsbusfeed.scheduler;

import com.abrahamyans.gpsbusfeed.event.LocationAvailableEvent;
import com.abrahamyans.gpsbusfeed.filter.LocationEventFilter;
import com.abrahamyans.gpsbusfeed.location.DefaultLocationRequestFactory;
import com.abrahamyans.gpsbusfeed.location.LocationRequestFactory;
import com.abrahamyans.gpsbusfeed.time.RequestTiming;
import com.abrahamyans.gpsbusfeed.time.SingleRequestTiming;
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

    private LocationTracker(){
        super();
    }

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

    public static class Builder {

        private RequestTiming timingStrategy;
        private List<LocationEventFilter> locationEventFilters = new ArrayList<>();
        private LocationRequestFactory requestFactory;


        public Builder timingStrategy(RequestTiming timing){
            if (timing == null)
                throw new IllegalArgumentException("timing cannot be null");
            verifyIsNotOverridden(this.timingStrategy, "timingStrategy");
            this.timingStrategy = timing;
            return this;
        }

        public Builder filter(LocationEventFilter locationEventFilter){
            if (locationEventFilter == null)
                throw new IllegalArgumentException("locationEventFilter cannot be null");
            this.locationEventFilters.add(locationEventFilter);
            return this;
        }

        public Builder requestFactory(LocationRequestFactory factory){
            if (requestFactory == null)
                throw new IllegalArgumentException("factory cannot be null");
            verifyIsNotOverridden(this.requestFactory, "requestFactory");
            this.requestFactory = factory;
            return this;
        }

        private void verifyIsNotOverridden(Object val, String field){
            if (val != null)
                throw new IllegalStateException("Field " + field + " is already set");
        }

        public LocationTracker build(){
            LocationTracker locationTracker = new LocationTracker();
            locationTracker.filters = locationEventFilters;
            locationTracker.timing = timingStrategy == null ? new SingleRequestTiming(): timingStrategy;
            locationTracker.requestFactory = requestFactory == null ? new DefaultLocationRequestFactory() : requestFactory;
            return locationTracker;
        }
    }
}
