package com.abrahamyans.gpsbusfeed.filter;

import android.location.Location;

/**
 * @author Samvel Abrahamyan
 */

public interface LocationFilter {
    boolean shouldBroadcastLocation(Location location);
}
