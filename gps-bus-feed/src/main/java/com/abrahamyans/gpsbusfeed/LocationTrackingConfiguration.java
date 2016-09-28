package com.abrahamyans.gpsbusfeed;


import com.abrahamyans.gpsbusfeed.filter.LocationFilter;
import com.abrahamyans.gpsbusfeed.time.RequestTiming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Samvel Abrahamyan
 */
public class LocationTrackingConfiguration {

    private RequestTiming timingStrategy;
    private List<LocationFilter> locationFilters = new ArrayList<>();


    public LocationTrackingConfiguration timingStrategy(RequestTiming timing){
        if (timing == null)
            throw new IllegalArgumentException("timing cannot be null");
        this.timingStrategy = timing;
        return this;
    }


    public LocationTrackingConfiguration filters(LocationFilter... locationFilter){
        this.locationFilters.addAll(Arrays.asList(locationFilter));
        return this;
    }

    RequestTiming getTimingStrategy() {
        return timingStrategy;
    }

    List<LocationFilter> getLocationFilters() {
        return locationFilters;
    }

}
