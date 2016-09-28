package com.abrahamyans.gpsbusfeed;

import com.abrahamyans.gpsbusfeed.filter.LocationFilter;
import com.abrahamyans.gpsbusfeed.location.DefaultLocationRequestFactory;
import com.abrahamyans.gpsbusfeed.location.LocationRequestFactory;
import com.abrahamyans.gpsbusfeed.scheduler.RequestTiming;
import com.abrahamyans.gpsbusfeed.scheduler.SingleRequestTiming;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Samvel Abrahamyan
 */

public class Tracker {

    private List<LocationFilter> filters;
    private RequestTiming timing;
    private LocationRequestFactory requestFactory;



    public static class Builder {

        private RequestTiming timingStrategy;
        private List<LocationFilter> locationFilters = new ArrayList<>();
        private LocationRequestFactory requestFactory;

        public Builder timingStrategy(RequestTiming timing){
            if (timing == null)
                throw new IllegalArgumentException("timing cannot be null");
            this.timingStrategy = timing;
            return this;
        }

        public Builder filter(LocationFilter locationFilter){
            if (locationFilter == null)
                throw new IllegalArgumentException("locationFilter cannot be null");
            this.locationFilters.add(locationFilter);
            return this;
        }

        private Builder requestFactory(LocationRequestFactory factory){
            if (requestFactory == null)
                throw new IllegalArgumentException("factory cannot be null");
            this.requestFactory = factory;
            return this;
        }

        public Tracker build(){
            Tracker tracker = new Tracker();
            tracker.filters = locationFilters;
            tracker.timing = timingStrategy == null ? new SingleRequestTiming() : timingStrategy;
            tracker.requestFactory = requestFactory == null ? new DefaultLocationRequestFactory() : requestFactory;
            return tracker;
        }
    }
}
