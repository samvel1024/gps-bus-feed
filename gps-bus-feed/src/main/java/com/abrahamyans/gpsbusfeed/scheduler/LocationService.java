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
import com.abrahamyans.gpsbusfeed.event.LocationChangedEvent;
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

    private static final String TAG = LocationService.class.getSimpleName();
    private GpsBusFeed feed;
    private LocationTracker tracker;
    private LocationApiProvider apiProvider;
    private Intent wakeLock;
    private boolean processingLocation;

    private void disconnect() {
        Log.d(TAG, "Disconnecting from location API");
        stopSelf();
        ServiceInvokerReceiver.completeWakefulIntent(wakeLock);
        apiProvider.disconnect();
    }

    private void requestLocation(){
        Log.d(TAG, "Requesting location");
        LocationRequest locationRequest = tracker.createLocationRequest();
        apiProvider.requestLocation(locationRequest);
        processingLocation = true;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Created LocationService");
        super.onCreate();
        feed = GpsBusFeed.getInstance();
        tracker = TrackerManager.getInstance(getApplicationContext()).getRunningTracker();
        apiProvider = new LocationApiProvider(this, this.getBaseContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (processingLocation){
            Log.w(TAG, "Skipping location request, service is busy processing location");
        }else {
            this.wakeLock = intent;
            apiProvider.connect();
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
        if (tracker.isValidLocationEvent(event)) {
            feed.onLocationChanged(new LocationChangedEvent(getApplicationContext(), location, new Date()));
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

    private void broadcastError(GpsBusFeedErrorEvent ev){
        TrackerManager.getInstance(getApplicationContext()).stopTracker();
        feed.onError(ev);
    }
}

