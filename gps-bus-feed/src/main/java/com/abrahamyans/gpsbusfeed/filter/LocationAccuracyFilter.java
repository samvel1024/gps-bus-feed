package com.abrahamyans.gpsbusfeed.filter;

import android.location.Location;

/**
 * @author Samvel Abrahamyan
 */

public class LocationAccuracyFilter implements LocationFilter{

    private final float maximumRadius;

    public LocationAccuracyFilter(float maximumRadius){
        if (maximumRadius <= 0)
            throw new IllegalArgumentException("Invalid value " + maximumRadius + " for minimumAccuracy");
        this.maximumRadius = maximumRadius;
    }

    @Override
    public boolean shouldBroadcastLocation(Location location) {
        return location.hasAccuracy() && location.getAccuracy() <= maximumRadius;
    }
}
