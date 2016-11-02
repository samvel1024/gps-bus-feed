package com.abrahamyans.gpsbusfeed.location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;

/**
 * @author Samvel Abrahamyan
 */

public interface LocationApiListener extends GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {
}
