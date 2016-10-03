package com.abrahamyans.gpsbusfeedsample;

import android.util.Log;

import com.abrahamyans.gpsbusfeed.event.LocationChangedEvent;
import com.squareup.otto.Subscribe;

/**
 * @author Samvel Abrahamyan
 */

public class LocationEventListener {

    @Subscribe
    public void onLocationEvent(LocationChangedEvent ev){
        Location loc = new Location(ev.getDate(), ev.getLocation().getLatitude(), ev.getLocation().getLongitude());
        Log.d("LocationEventListener", loc.toString());
    }

}
