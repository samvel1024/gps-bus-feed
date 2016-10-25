package com.abrahamyans.gpsbusfeedsample;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.abrahamyans.gpsbusfeed.client.observer.event.LocationChangedEvent;
import com.squareup.otto.Subscribe;

import java.io.Serializable;

/**
 * @author Samvel Abrahamyan
 */

public class LocationEventListener implements Serializable {
    private static final long serialVersionUID = 5971805574725594903L;

    @Subscribe
    public void onLocationChanged(LocationChangedEvent ev){
        Log.d("***CLIENT***", "Received: " + ev);
        Context context = ev.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("location_history", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(
                String.valueOf(ev.getDate().getTime()),
                "(" + ev.getLocation().getLatitude() + ", " + ev.getLocation().getLongitude() + ")"
        ).apply();
    }
}
