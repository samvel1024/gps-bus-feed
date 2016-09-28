package com.abrahamyans.gpsbusfeed;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import com.abrahamyans.gpsbusfeed.event.ErrorStatus;
import com.abrahamyans.gpsbusfeed.event.GpsBusFeedError;
import com.abrahamyans.gpsbusfeed.event.LocationAvailableEvent;
import com.abrahamyans.gpsbusfeed.location.LocationApiListener;
import com.abrahamyans.gpsbusfeed.location.LocationApiProvider;
import com.google.android.gms.common.ConnectionResult;

import java.util.Date;

/**
 * @author Samvel Abrahamyan
 */
public class LocationService extends Service implements
        LocationApiListener {

    private static final String TAG = LocationService.class.getName();
    private final GpsBusFeed feed = GpsBusFeed.getInstance();

    private LocationApiProvider apiProvider;

    private void disconnect() {
        stopSelf();
        apiProvider.disconnect();
    }

    private void requestLocation(){

//        apiProvider.requestLocation(locationRequest, this);
    }

    @Override
    public void onCreate() {
        apiProvider = new LocationApiProvider(this, this.getBaseContext());
        super.onCreate();
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
        feed.onLocationAvailable(new LocationAvailableEvent(location, new Date()));
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
        feed.onError(new GpsBusFeedError(ErrorStatus.API_CONNECTION_FAILURE));
        disconnect();
    }

    @Override
    public void onConnectionSuspended(int i) {
        feed.onError(new GpsBusFeedError(ErrorStatus.API_CONNECTION_FAILURE, "Connection was suspended"));
        Log.e(TAG, "Connection has been suspended");
    }
}

