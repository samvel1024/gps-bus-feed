package com.abrahamyans.gpsbusfeed.client.tracker.request;

import com.google.android.gms.location.LocationRequest;

import java.io.Serializable;

/**
 * @author Samvel Abrahamyan
 */

public interface LocationRequestFactory extends Serializable {
    LocationRequest createLocationRequest();
}
