package com.abrahamyans.gpsbusfeed.client.tracker.filter;

import android.location.Location;

import java.io.Serializable;

/**
 * @author Samvel Abrahamyan
 */

public interface LocationEventFilter extends Serializable{
    boolean shouldBroadcastLocation(Location location);
}
