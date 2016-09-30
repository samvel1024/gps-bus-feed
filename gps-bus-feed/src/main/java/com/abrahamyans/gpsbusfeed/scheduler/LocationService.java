package com.abrahamyans.gpsbusfeed.scheduler;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import com.abrahamyans.gpsbusfeed.event.ErrorStatus;
import com.abrahamyans.gpsbusfeed.event.GpsBusFeed;
import com.abrahamyans.gpsbusfeed.event.GpsBusFeedErrorEvent;
import com.abrahamyans.gpsbusfeed.event.LocationAvailableEvent;
import com.abrahamyans.gpsbusfeed.location.LocationApiListener;
import com.abrahamyans.gpsbusfeed.location.LocationApiProvider;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationRequest;

import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */
public class LocationService extends Service implements
        LocationApiListener {

    private static final String TAG = LocationService.class.getName();
    private final GpsBusFeed feed = GpsBusFeed.getInstance();
    private final TrackerManager trackerManager = TrackerManager.getInstance(getApplicationContext());
    private final LocationTracker tracker = trackerManager.getRunningTracker();
    private final LocationApiProvider apiProvider = new LocationApiProvider(this, this.getBaseContext());

    private void disconnect() {
        stopSelf();
        apiProvider.disconnect();
    }

    private void requestLocation(){
        LocationRequest locationRequest = tracker.createLocationRequest();
        apiProvider.requestLocation(locationRequest);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        apiProvider.connect();
        return START_NOT_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Received location " + location);
        LocationAvailableEvent event = new LocationAvailableEvent(location, new Date());
        if (tracker.isValidLocationEvent(event)) {
            feed.onLocationAvailable(new LocationAvailableEvent(location, new Date()));
        }
        disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConnected(Bundle bundle) {
        requestLocation();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        feed.onError(new GpsBusFeedErrorEvent(ErrorStatus.API_CONNECTION_FAILURE));
        disconnect();
    }

    @Override
    public void onConnectionSuspended(int i) {
        feed.onError(new GpsBusFeedErrorEvent(ErrorStatus.API_CONNECTION_FAILURE, "Connection was suspended"));
        Log.e(TAG, "Connection has been suspended");
    }
}
