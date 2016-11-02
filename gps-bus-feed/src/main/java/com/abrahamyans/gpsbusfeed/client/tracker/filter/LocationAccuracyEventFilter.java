package com.abrahamyans.gpsbusfeed.client.tracker.filter;

import android.location.Location;

/**
 * @author Samvel Abrahamyan
 */

public class LocationAccuracyEventFilter implements LocationEventFilter {

    private static final long serialVersionUID = -5417518929027426806L;

    private final float maximumRadius;

    public LocationAccuracyEventFilter(float maximumRadius) {
        if (maximumRadius <= 0)
            throw new IllegalArgumentException("Invalid value " + maximumRadius + " for minimumAccuracy");
        this.maximumRadius = maximumRadius;
    }

    @Override
    public boolean shouldBroadcastLocation(Location location) {
        return location.hasAccuracy() && location.getAccuracy() <= maximumRadius;
    }


}
