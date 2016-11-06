package com.abrahamyans.gpsbusfeed;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import com.abrahamyans.gpsbusfeed.client.observer.SerializableBus;
import com.abrahamyans.gpsbusfeed.client.observer.event.ErrorStatus;
import com.abrahamyans.gpsbusfeed.client.observer.event.GpsBusFeedErrorEvent;
import com.abrahamyans.gpsbusfeed.client.observer.event.LocationChangedEvent;
import com.abrahamyans.gpsbusfeed.location.LocationApi;
import com.abrahamyans.gpsbusfeed.location.LocationApiListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationRequest;

import java.util.Date;

import javax.inject.Inject;

/**
 * @author Samvel Abrahamyan
 */
public class LocationService extends Service implements
        LocationApiListener {

    private static final String TAG = LocationService.class.getSimpleName();
    @Inject
    SerializableBus feed;
    @Inject
    LocationTracker tracker;
    private LocationApi locationApi;

    private Intent wakeLock;
    private boolean processingLocation;

    private void disconnect() {
        Log.d(TAG, "Disconnecting from location API");
        stopSelf();
        locationApi.disconnect();
    }

    private void requestLocation() {
        Log.d(TAG, "Requesting location");
        LocationRequest locationRequest = tracker.getRunningTrackerConfig().createLocationRequest();
        locationApi.requestLocation(locationRequest);
        processingLocation = true;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Created LocationService");
        locationApi = new LocationApi(this, getApplicationContext());
        ((TrackerAwareApplication) getApplicationContext()).getGpsBusFeedComponent().inject(this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (processingLocation) {
            Log.w(TAG, "Skipping location request, service is busy processing location");
            disconnect();
        } else {
            this.wakeLock = intent;
            locationApi.connect();
        }
        return START_NOT_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Changed location " + location);
        LocationChangedEvent event = new LocationChangedEvent(getApplicationContext(), location, new Date());
        if (tracker.getRunningTrackerConfig().isValidLocationEvent(event.getLocation())) {
            feed.onLocationChanged(event);
        }
        processingLocation = false;
        disconnect();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Destroyed");
        super.onDestroy();
    }

    @Override
    public void onConnected(Bundle bundle) {
        requestLocation();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        broadcastError(new GpsBusFeedErrorEvent(ErrorStatus.API_CONNECTION_FAILURE));
        disconnect();
    }

    @Override
    public void onConnectionSuspended(int i) {
        broadcastError(new GpsBusFeedErrorEvent(ErrorStatus.API_CONNECTION_FAILURE, "Connection was suspended"));
        Log.e(TAG, "Connection has been suspended");
    }

    private void broadcastError(GpsBusFeedErrorEvent ev) {
        feed.onError(ev);
    }
}

