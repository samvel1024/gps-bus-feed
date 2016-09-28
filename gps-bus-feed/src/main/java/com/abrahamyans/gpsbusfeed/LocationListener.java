package com.abrahamyans.gpsbusfeed;

import android.location.Location;

/**
 * @author Samvel Abrahamyan
 */

public interface LocationListener {
    void processLocation (Location location);
}
