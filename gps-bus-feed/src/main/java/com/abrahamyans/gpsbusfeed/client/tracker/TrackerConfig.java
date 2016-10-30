package com.abrahamyans.gpsbusfeed.client.tracker;

import android.location.Location;

import com.abrahamyans.gpsbusfeed.client.tracker.filter.LocationEventFilter;
import com.abrahamyans.gpsbusfeed.client.tracker.request.DefaultLocationRequestFactory;
import com.abrahamyans.gpsbusfeed.client.tracker.request.LocationRequestFactory;
import com.abrahamyans.gpsbusfeed.client.tracker.time.RequestDate;
import com.abrahamyans.gpsbusfeed.client.tracker.time.RequestTiming;
import com.abrahamyans.gpsbusfeed.client.tracker.time.SingleRequestTiming;
import com.google.android.gms.location.LocationRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Samvel Abrahamyan
 */

public class TrackerConfig implements Serializable{

    private static final long serialVersionUID = -480534867039443386L;
    private List<LocationEventFilter> filters;
    private RequestTiming timing;
    private LocationRequestFactory requestFactory;

    private TrackerConfig(){
        super();
    }

    public RequestDate getNextRequestDate(Date lastRequestDate){
        return timing.nextRequestDate(lastRequestDate);
    }

    public LocationRequest createLocationRequest(){
        return requestFactory.createLocationRequest();
    }

    public boolean isValidLocationEvent(Location location){
        boolean isValid = true;
        for (LocationEventFilter filter: filters)
            isValid &= filter.shouldBroadcastLocation(location);
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

        public Builder requestFactory(DefaultLocationRequestFactory factory){
            if (factory == null)
                throw new IllegalArgumentException("factory cannot be null");
            verifyIsNotOverridden(this.requestFactory, "requestFactory");
            this.requestFactory = factory;
            return this;
        }

        private void verifyIsNotOverridden(Object val, String field){
            if (val != null)
                throw new IllegalStateException("Field " + field + " is already set");
        }

        public TrackerConfig build(){
            TrackerConfig trackerConfig = new TrackerConfig();
            trackerConfig.filters = locationEventFilters;
            trackerConfig.timing = timingStrategy == null ? new SingleRequestTiming(): timingStrategy;
            trackerConfig.requestFactory = requestFactory == null ? new DefaultLocationRequestFactory() : requestFactory;
            return trackerConfig;
        }
    }
}
