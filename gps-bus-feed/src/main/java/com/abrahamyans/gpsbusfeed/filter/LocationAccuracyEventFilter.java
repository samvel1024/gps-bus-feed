package com.abrahamyans.gpsbusfeed.filter;

import com.abrahamyans.gpsbusfeed.event.LocationAvailableEvent;

/**
 * @author Samvel Abrahamyan
 */

public class LocationAccuracyEventFilter implements LocationEventFilter {

    private final float maximumRadius;

    LocationAccuracyEventFilter(float maximumRadius){
        if (maximumRadius <= 0)
            throw new IllegalArgumentException("Invalid value " + maximumRadius + " for minimumAccuracy");
        this.maximumRadius = maximumRadius;
    }

    @Override
    public boolean shouldBroadcastLocation(LocationAvailableEvent location) {
        return location.getLocation().hasAccuracy() && location.getLocation().getAccuracy() <= maximumRadius;
    }

    public static LocationEventFilter withMaxRadius(float maxRadius){
        return new LocationAccuracyEventFilter(maxRadius);
    }
}
