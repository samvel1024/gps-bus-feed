package com.abrahamyans.gpsbusfeed.builder;

import android.location.Location;

/**
 * @author Samvel Abrahamyan
 */

public interface Validator {
    boolean isValidLocation(Location location);
}
