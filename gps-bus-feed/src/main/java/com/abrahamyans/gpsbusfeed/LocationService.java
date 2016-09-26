package com.abrahamyans.gpsbusfeed;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * @author Samvel Abrahamyan
 */
public class LocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = LocationService.class.getName();

    private boolean currentlyProcessingLocation = false;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        validatePermissions();
        // if we are currently trying to get a location and the alarm manager has called this again,
        // no need to start processing a new location.
        if (!currentlyProcessingLocation) {
            currentlyProcessingLocation = true;
            startTracking();
        }

        return START_NOT_STICKY;
    }


    private void validatePermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            stopSelf();
            throw new IllegalStateException("Not enough permissions, required ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION");
        }
    }

    private void startTracking() {
        Log.d(TAG, "startTracking");

        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {

            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            if (!googleApiClient.isConnected() || !googleApiClient.isConnecting()) {
                googleApiClient.connect();
            }
        } else {
            Log.e(TAG, "Unable to connect to google play services.");
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) {
            Log.d(TAG, "Called onLocationChanged, but got null location");
            return;
        }

        Log.d(TAG, "Received location " + location);
        if (location.getAccuracy() < 500.0f) {
            stopLocationUpdates();
        }
    }

    private void stopLocationUpdates() {
        googleApiClient.disconnect();
    }

    /**
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */
    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected");

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000); // milliseconds
        locationRequest.setFastestInterval(1000); // the fastest rate in milliseconds at which your app can handle location updates
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        validatePermissions();
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed");

        stopLocationUpdates();
        stopSelf();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "GoogleApiClient connection has been suspend");
    }
}
