package com.abrahamyans.gpsbusfeed.location;

import com.google.android.gms.location.LocationRequest;

import java.io.Serializable;

/**
 * @author Samvel Abrahamyan
 */

public interface LocationRequestFactory extends Serializable {
    LocationRequest createLocationRequest();
}
