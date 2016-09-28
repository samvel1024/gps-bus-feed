package com.abrahamyans.gpsbusfeed.location;

import com.google.android.gms.location.LocationRequest;

/**
 * @author Samvel Abrahamyan
 */

public class DefaultLocationRequestFactory implements LocationRequestFactory {
    @Override
    public LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }
}
