package com.abrahamyans.gpsbusfeed.location;

import com.google.android.gms.location.LocationRequest;

/**
 * @author Samvel Abrahamyan
 */

public interface LocationRequestFactory {
    LocationRequest createLocationRequest();
}
