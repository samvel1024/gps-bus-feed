package com.abrahamyans.gpsbusfeed.filter;

import com.abrahamyans.gpsbusfeed.event.LocationChangedEvent;

/**
 * @author Samvel Abrahamyan
 */

public class LocationAccuracyEventFilter implements LocationEventFilter {

    private final float maximumRadius;

    public LocationAccuracyEventFilter(float maximumRadius){
        if (maximumRadius <= 0)
            throw new IllegalArgumentException("Invalid value " + maximumRadius + " for minimumAccuracy");
        this.maximumRadius = maximumRadius;
    }

    @Override
    public boolean shouldBroadcastLocation(LocationChangedEvent location) {
        return location.getLocation().hasAccuracy() && location.getLocation().getAccuracy() <= maximumRadius;
    }


}
