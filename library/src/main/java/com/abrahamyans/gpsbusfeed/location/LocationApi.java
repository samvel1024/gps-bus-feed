package com.abrahamyans.gpsbusfeed.location;

import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * @author Samvel Abrahamyan
 */

public class LocationApi {

    private final LocationApiListener listener;
    private final GoogleApiClient apiClient;

    public LocationApi(LocationApiListener listener, Context context) {
        this.listener = listener;
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS) {
            apiClient = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .addOnConnectionFailedListener(listener)
                    .addConnectionCallbacks(listener)
                    .build();
        } else {
            throw new IllegalStateException("Unable to connect to google play services");
        }
    }

    public void requestLocation(LocationRequest locationRequest) {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    apiClient, locationRequest, listener);
        } catch (SecurityException e) {
            throw new IllegalStateException("Could not access location services", e);
        }
    }

    public void disconnect() {
        apiClient.disconnect();
    }

    public void connect() {
        if (!apiClient.isConnected() || !apiClient.isConnecting())
            apiClient.connect();
    }
}
